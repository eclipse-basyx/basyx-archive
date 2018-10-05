package org.eclipse.basyx.aas.impl.onem2m.managed;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.handler.IAssetAdministrationShellHandler;
import org.eclipse.basyx.aas.api.resources.basic.ParameterType;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedProperty;
import org.eclipse.basyx.aas.impl.onem2m.BasyxResourcesUtil;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedCollectionProperty;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedOperation;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedSingleProperty;
import org.eclipse.basyx.onem2m.manager.IOneM2MNotificationHandler;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.OneM2MSubscriptionHandler;
import org.eclipse.basyx.onem2m.manager.ResourceResult;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;

import com.google.gson.JsonArray;

public class AASHandler implements IOneM2MNotificationHandler, IAssetAdministrationShellHandler {

	class OperationEntry {
		OneM2MConnectedOperation coperation;
		Object object;
		Method method;
	}

	class PropertyEntry {
		ConnectedProperty cproperty;
		Object object;
		Method method;
		Field field;
	}

	class CollectionPropertyValueEntry {
		ConnectedProperty cproperty;
		String key;
		Object object;
		Method method;
		Field field;
	}

	String aasId = null;
	OneM2MResourceManager resourceManager;

	Map<String, OperationEntry> handledOperations = new HashMap<>(); // id => entry
	Map<String, PropertyEntry> handledProperties = new HashMap<>(); // id => entry
	Map<String, CollectionPropertyValueEntry> handledValues = new HashMap<>(); // id => entry

	public AASHandler(OneM2MResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public AASHandler(String aasId, OneM2MResourceManager resourceManager) {
		this.aasId = aasId;
		this.resourceManager = resourceManager;
	}

	@Override
	public void handleOperation(ConnectedOperation cop, Object obj, Method m) throws Exception {
		if (!(cop instanceof OneM2MConnectedOperation)) {
			throw new IllegalArgumentException("Can only handle oneM2M connected operations.");
		}
		String handle = this.resourceManager.addHandledSubscription(((OneM2MConnectedOperation) cop).getM2MRiREQ(),
				this);
		if (handle != null) {
			OperationEntry oe = new OperationEntry();
			oe.coperation = (OneM2MConnectedOperation) cop;
			oe.object = obj;
			oe.method = m;
			this.handledOperations.put(handle, oe);
		} else {
			if (this.resourceManager.getSubscriptionHandler() != null) {
				String url = this.resourceManager.getSubscriptionHandler().getMyURL();
				throw new Exception("Could not add subscription [Check whether CSE is able to connect configured URL ("
						+ url + ")]");
			} else {
				throw new Exception("Could not add subscription [No subscription handler available.]");
			}
		}
	}

	@Override
	public void handleProperty(ConnectedProperty cp, Object obj, Method m) throws Exception { // Not fully implemented
																								// yet
		if (!(cp instanceof OneM2MConnectedSingleProperty || cp instanceof OneM2MConnectedCollectionProperty)) {
			throw new IllegalArgumentException("Can only handle oneM2M connected operations.");
		}
		String ri = null;
		if (cp instanceof OneM2MConnectedSingleProperty) {
			ri = ((OneM2MConnectedSingleProperty) cp).getRiDATA();
		} else if (cp instanceof OneM2MConnectedCollectionProperty) {
			ri = ((OneM2MConnectedCollectionProperty) cp).getRiDATA();
			// TODO also in childs
		}
		String handle = this.resourceManager.addHandledSubscription(ri, this);
		if (handle != null) {
			PropertyEntry pe = new PropertyEntry();
			pe.cproperty = cp;
			pe.object = obj;
			pe.method = m;
			pe.field = null;
			this.handledProperties.put(handle, pe);
		} else {
			throw new Exception("Could not add subscription!");
		}

	}

	@Override
	public void handleProperty(ConnectedProperty cp, Object obj, Field f) throws Exception { // Not fully implemented
																								// yet
		// copy & paste from previous method
		if (!(cp instanceof OneM2MConnectedSingleProperty || cp instanceof OneM2MConnectedCollectionProperty)) {
			throw new IllegalArgumentException("Can only handle oneM2M connected operations.");
		}
		String ri = null;
		if (cp instanceof OneM2MConnectedSingleProperty) {
			ri = ((OneM2MConnectedSingleProperty) cp).getRiDATA();
		} else if (cp instanceof OneM2MConnectedCollectionProperty) {
			ri = ((OneM2MConnectedCollectionProperty) cp).getRiDATA();
			// TODO also in childs
		}
		String handle = this.resourceManager.addHandledSubscription(ri, this);
		if (handle != null) {
			PropertyEntry pe = new PropertyEntry();
			pe.cproperty = cp;
			pe.object = obj;
			pe.method = null;
			pe.field = f;
			this.handledProperties.put(handle, pe);

			if (cp instanceof OneM2MConnectedCollectionProperty) {
				Collection<String> keys = ((OneM2MConnectedCollectionProperty) cp).getKeys();

				for (String key : keys) {
					String handleNewEntry = null;
					try {
						String url = ((OneM2MConnectedCollectionProperty) cp).getLaDATA();
						url = url.substring(0, url.length() - 2) + key;
						handleNewEntry = this.resourceManager.addHandledSubscription(url, this, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (handleNewEntry != null) {
						CollectionPropertyValueEntry cpve = new CollectionPropertyValueEntry();
						cpve.key = key;
						cpve.cproperty = pe.cproperty;
						cpve.object = pe.object;
						if (pe.method != null) {
							cpve.method = pe.method;
						} else {
							cpve.field = pe.field;
						}
						this.handledValues.put(handleNewEntry, cpve);
					}
				}
			}

		} else {
			throw new Exception("Could not add subscription!");
		}

	}

	@Override
	public void incomingNotification(OneM2MSubscriptionHandler subscriptionHandler, Sub subscription, String handle,
			Notification notification) {

		String sur = notification.getSur();

		if (sur != null) {
			if (notification.getNev() != null) {
				OperationEntry opEntry = this.handledOperations.get(handle);
				PropertyEntry propEntry = this.handledProperties.get(handle);
				CollectionPropertyValueEntry valueEntry = this.handledValues.get(handle);
				if (opEntry != null) { // operation call must be handled
					Object rep = notification.getNev().getRep();
					if (rep == null || !(rep instanceof Cin)) {
						return;
					}
					Cin cin = (Cin) rep;
					// send proc
					try {
						Cin cinProc = new Cin();
						cinProc.getLbl().add("rel:" + cin.getRi());
						cinProc.setCon("");
						ResourceResult<Cin> rrProc = this.resourceManager
								.createContentInstance(opEntry.coperation.getM2MRiPROC(), cinProc, false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JsonArray paramsJA = BasyxResourcesUtil.fromM2MArrayValue(cin.getCon().toString());
					Class[] classesArray = opEntry.method.getParameterTypes();
					Object[] objectArray = new Object[classesArray.length];
					List<ParameterType> pts = opEntry.coperation.getParameterTypes();

					int size = (paramsJA == null ? 0 : paramsJA.size());
					for (int i = 0; i < size; i++) {
						ParameterType pt = pts.get(i);
						Object value = BasyxResourcesUtil.fromM2MArrayElementValue(paramsJA.get(i).toString(),
								pt.getDataType());
						objectArray[i] = value;
					}

					try {
						Object retVal = opEntry.method.invoke(opEntry.object, objectArray);
						if (retVal == null) {
							retVal = new String();
						}
						// send result
						try {
							Cin cinResp = new Cin();
							cinResp.setCon(retVal);
							cinResp.getLbl().add("rel:" + cin.getRi());
							ResourceResult<Cin> rrResp = this.resourceManager
									.createContentInstance(opEntry.coperation.getM2MRiRESP(), cinResp, false);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (propEntry != null) {

					Object rep = notification.getNev().getRep();
					if (propEntry.cproperty.isCollection()) {
						if (rep == null) {
							return;
						}
						if (rep instanceof Cnt) {
							// new entry
							Cnt cntNewEntry = (Cnt) rep;

							// fetch value of new Entry
							ResourceResult<Cin> rrNewEntry = null;
							try {
								rrNewEntry = this.resourceManager.retrieveContentInstance(cntNewEntry.getLa(), false);
								Cin cinNewEntry = rrNewEntry.getResource();
								Object value = BasyxResourcesUtil.fromM2MValue(cinNewEntry.getCon().toString(),
										propEntry.cproperty.getDataType());
								if (propEntry.method != null) { // set
									Object[] objectArray = { cntNewEntry.getRn(), value };
									try {
										propEntry.method.invoke(propEntry.object, objectArray);
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								} else { // set property directly
									Field field = propEntry.field;
									field.setAccessible(true);
									try {
										Map<String, Object> map = (Map<String, Object>) field.get(propEntry.object);
										map.put(cntNewEntry.getRn(), value);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							// create subscription for new entry
							String handleNewEntry = null;
							try {
								handleNewEntry = this.resourceManager.addHandledSubscription(cntNewEntry.getRi(), this);
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (handleNewEntry != null) {
								CollectionPropertyValueEntry cpve = new CollectionPropertyValueEntry();
								cpve.key = cntNewEntry.getRn();
								cpve.cproperty = propEntry.cproperty;
								cpve.object = propEntry.object;
								if (propEntry.method != null) {
									cpve.method = propEntry.method;
								} else {
									cpve.field = propEntry.field;
								}
								this.handledValues.put(handleNewEntry, cpve);
							}
						} else {
							// nothing to do?
						}
					} else {
						if (rep == null || !(rep instanceof Cin)) {
							return;
						}
						Cin cin = (Cin) rep;

						if (aasId != null && cin.getCr().equals(this.aasId)) {
							return;
						}
						// send proc
						Object value = BasyxResourcesUtil.fromM2MValue(cin.getCon().toString(),
								propEntry.cproperty.getDataType());

						if (propEntry.method != null) { // set
							Object[] objectArray = { value };
							try {
								propEntry.method.invoke(propEntry.object, objectArray);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} else { // set property directly

							Field field = propEntry.field;
							field.setAccessible(true);
							try {
								field.set(propEntry.object, value);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
				} else if (valueEntry != null) {
					Object rep = notification.getNev().getRep();
					if (rep == null || !(rep instanceof Cin)) {
						return;
					}
					Cin cin = (Cin) rep;

					if (aasId != null && cin.getCr().equals(this.aasId)) {
						return;
					}

					Object value = BasyxResourcesUtil.fromM2MValue(cin.getCon().toString(),
							valueEntry.cproperty.getDataType());
					if (valueEntry.method != null) { // set
						Object[] objectArray = { valueEntry.key, value };
						try {
							valueEntry.method.invoke(valueEntry.object, objectArray);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} else { // set property directly
						Field field = valueEntry.field;
						field.setAccessible(true);
						try {
							Map<String, Object> map = (Map<String, Object>) field.get(valueEntry.object);
							map.put(valueEntry.key, value);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

}
