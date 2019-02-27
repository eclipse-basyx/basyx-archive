package org.eclipse.basyx.aas.impl.onem2m.connected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Operation;
import org.eclipse.basyx.aas.api.resources.basic.ParameterType;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.IOperationCall;
import org.eclipse.basyx.aas.impl.onem2m.BasyxResourcesUtil;
import org.eclipse.basyx.onem2m.manager.IOneM2MNotificationHandler;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.OneM2MSubscriptionHandler;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;

import com.google.gson.Gson;


public class OneM2MConnectedOperation extends ConnectedOperation implements IOneM2MNotificationHandler {

	class SynchronousOperationCallEntry {
		Cin cin;
		Condition condition;
		Object result;
	}
	
	protected Lock lock = new ReentrantLock();
	
	protected Map<String, SynchronousOperationCallEntry> syncCalls = new HashMap<>();	
	protected OneM2MResourceManager resourceManager;
	protected String m2m_ri;
    protected String m2m_ri_REQ;
    protected String m2m_ri_PROC;
    protected String m2m_ri_RESP;
	protected boolean hierarchical;

	public OneM2MConnectedOperation(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_REQ, String m2m_ri_PROC, String m2m_ri_RESP) {
		this(resourceManager, m2m_ri, m2m_ri_REQ, m2m_ri_PROC, m2m_ri_RESP, false);
	}
	
	public OneM2MConnectedOperation(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_REQ, String m2m_ri_PROC, String m2m_ri_RESP, boolean hierarchical) {
		this.resourceManager = resourceManager;
		this.m2m_ri = m2m_ri;	
		this.m2m_ri_REQ = m2m_ri_REQ;	
		this.m2m_ri_PROC = m2m_ri_PROC;
		this.m2m_ri_RESP = m2m_ri_RESP;	
		this.hierarchical = hierarchical;
	}
	
	

	public OneM2MConnectedOperation(OneM2MResourceManager resourceManager, String m2m_ri, String m2m_ri_REQ, String m2m_ri_PROC, String m2m_ri_RESP, Operation operation) {
		this(resourceManager, m2m_ri, m2m_ri_REQ, m2m_ri_PROC, m2m_ri_RESP);
		this.setId(operation.getId());
		this.setName(operation.getName());
		this.setParameterTypes(new ArrayList<ParameterType>(operation.getParameterTypes()));
		this.setReturnDataType(operation.getReturnDataType());
	}

	
	@Override
	public void call(Object[] parameters, int timeout, IOperationCall oc) throws Exception {
		throw new NotImplementedException("call");
	}


	@Override
	public Object call(Object[] parameters, int timeout) throws Exception {
		String json = new Gson().toJson(parameters);
		String handle = this.resourceManager.addHandledSubscription(this.m2m_ri_RESP, this, this.hierarchical);
		SynchronousOperationCallEntry entry = new SynchronousOperationCallEntry();
		entry.condition = lock.newCondition();
		try {
			lock.lock();
			this.syncCalls.put(handle, entry);
			String cinValue = BasyxResourcesUtil.toM2MValue(json, null);
			entry.cin = this.resourceManager.createContentInstance01(this.m2m_ri_REQ, cinValue, this.hierarchical).getResource();
			boolean ret;
			if (timeout == 0) {
				entry.condition.await();
				ret = true;
			} else {
				ret = entry.condition.await(timeout, TimeUnit.MILLISECONDS);
			}
			
			if (ret) {
				entry = this.syncCalls.remove(handle);			
				if (entry == null) {
					throw new IllegalStateException();
				}
				
			} else {
				throw new TimeoutException("Call did timeout");
			}
		} finally {
			lock.unlock();
		}
		return entry.result;
	}

	public String getM2MRi() {
		return m2m_ri;
	}

	public String getM2MRiREQ() {
		return m2m_ri_REQ;
	}

	public String getM2MRiPROC() {
		return m2m_ri_PROC;
	}

	public String getM2MRiRESP() {
		return m2m_ri_RESP;
	}

	@Override
	public void incomingNotification(OneM2MSubscriptionHandler subscriptionHandler, Sub subscription, String handle,
			Notification notification) {
		if (notification.getSur() != null) {
			String sur = notification.getSur();
			if (notification.getNev() != null || notification.getNev().getRep() != null) { // TODO CIN must be taken into account
				Object rep = notification.getNev().getRep();
				Cin cin = (Cin) rep;
				try {
					lock.lock();
					if (this.syncCalls.get(sur) != null) { // synchronous call
						SynchronousOperationCallEntry entry = this.syncCalls.get(sur);
						Object value = BasyxResourcesUtil.fromM2MValue(cin.getCon().toString(), (this.returnDataType==null ? DataType.STRING : this.returnDataType) );
						entry.result = value;
						try {
							this.resourceManager.deleteResource(sur, false);
						} catch (Exception e) {						
							e.printStackTrace();
						}
						entry.condition.signal();
					} 
				} finally {
					lock.unlock();					
				}
			}
		}
		
	}

	
	
	
	
	

}
