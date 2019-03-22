package org.eclipse.basyx.aas.impl.onem2m;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Event;
import org.eclipse.basyx.aas.api.resources.basic.Operation;
import org.eclipse.basyx.aas.api.resources.basic.ParameterType;
import org.eclipse.basyx.aas.api.resources.basic.Property;
import org.eclipse.basyx.aas.api.resources.basic.Statement;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedCollectionProperty;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedEvent;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedProperty;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSingleProperty;
import org.eclipse.basyx.aas.api.resources.connected.IOperationCall;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedCollectionProperty;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedEvent;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedOperation;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedSingleProperty;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedSubModel;
import org.eclipse.basyx.onem2m.clients.IOneM2MClient;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceExplorer;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.ResourceResult;
import org.eclipse.basyx.onem2m.manager.ResourceResults;
import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.Ae;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.eclipse.basyx.onem2m.xml.protocols.FilterCriteria;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

import com.google.gson.Gson;

public class OneM2MAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	private OneM2MResourceManager resourceManager;
	private OneM2MResourceExplorer resourceExplorer;

	public OneM2MAssetAdministrationShellManager(OneM2MResourceManager resourceManager,
			OneM2MResourceExplorer resourceExplorer) {
		this.resourceManager = resourceManager;
		this.resourceExplorer = resourceExplorer;
	}

	private ConnectedProperty createProperty(String parentRi, Property prop) throws Exception {
		Cnt cntProp = new Cnt();
		cntProp.setRn(prop.getName());
		cntProp.getLbl().add("basys:prop");
		if (!(prop.getId() == null || prop.getId().isEmpty())) {
			cntProp.getLbl().add("id:" + prop.getId());
		}
		cntProp.getLbl().add("dty:" + BasyxResourcesUtil.convertDataTypeToStr(prop.getDataType()));
		if (prop.isReadable()) {
			cntProp.getLbl().add("rd");
		}
		if (prop.isWriteable()) {
			cntProp.getLbl().add("wr");
		}
		if (prop.isEventable()) {
			cntProp.getLbl().add("ev");
		}
		if (prop.isCollection()) {
			cntProp.getLbl().add("col:map");
		}

		ResourceResult<Cnt> rrProp = this.resourceManager.createContainer(parentRi, cntProp, false);

		// STATEMENTS
		Cnt cntPropStatements = new Cnt();
		cntPropStatements.setRn("STATEMENTS");
		ResourceResult<Cnt> rrPropStatements = this.resourceManager.createContainer(rrProp.getResource().getRi(),
				cntPropStatements, false);

		for (Statement st : prop.getStatements().values()) {
			Cnt cntSt = new Cnt();
			cntSt.setRn(st.getName());
			cntSt.getLbl().add("un:" + st.getUnit());
			cntSt.getLbl().add("dt:" + BasyxResourcesUtil.convertDataTypeToStr(st.getDataType()));
			cntSt.getLbl().add("el:" + st.getExpressionLogic());
			cntSt.getLbl().add("et:" + st.getExpressionType());
			ResourceResult<Cnt> rrPropStatement = this.resourceManager
					.createContainer(rrPropStatements.getResource().getRi(), cntSt, false);

			Cin cinValue = new Cin();
			cinValue.setCon(BasyxResourcesUtil.toM2MValue(st.getValue(), st.getDataType()));
			ResourceResult<Cin> rrVal = this.resourceManager
					.createContentInstance(rrPropStatement.getResource().getRi(), cinValue, false);
		}

		// DATA
		Cnt cntPropData = new Cnt();
		cntPropData.setRn("DATA");
		ResourceResult<Cnt> rrPropData = this.resourceManager.createContainer(rrProp.getResource().getRi(), cntPropData,
				false);

		ConnectedProperty cprop = null;
		if (prop.isCollection()) {
			cprop = new OneM2MConnectedCollectionProperty(resourceManager, rrProp.getResource().getRi(),
					rrPropData.getResource().getRi(), rrPropData.getResource().getLa(), prop);
		} else {
			cprop = new OneM2MConnectedSingleProperty(resourceManager, rrProp.getResource().getRi(),
					rrPropData.getResource().getRi(), rrPropData.getResource().getLa(), prop);
		}

		return cprop;
	}

	private OneM2MConnectedEvent createEvent(String parentRi, Event evt) throws Exception {
		Cnt cntEvt = new Cnt();
		cntEvt.setRn(evt.getName());
		cntEvt.getLbl().add("basys:evt");
		if (!(evt.getId() == null || evt.getId().isEmpty())) {
			cntEvt.getLbl().add("id:" + evt.getId());
		}
		cntEvt.getLbl().add("dty:" + BasyxResourcesUtil.convertDataTypeToStr(evt.getDataType()));
		ResourceResult<Cnt> rrEvt = this.resourceManager.createContainer(parentRi, cntEvt, false);
		Cnt cntEvtData = new Cnt();
		cntEvtData.setRn("DATA");
		ResourceResult<Cnt> rrEvtData = this.resourceManager.createContainer(rrEvt.getResource().getRi(), cntEvtData,
				false);
		OneM2MConnectedEvent cevt = new OneM2MConnectedEvent(resourceManager, rrEvt.getResource().getRi(),
				rrEvtData.getResource().getRi(), evt);
		return cevt;
	}

	private OneM2MConnectedOperation createOperation(String parentRi, Operation op) throws Exception {
		Cnt cntOp = new Cnt();
		cntOp.setRn(op.getName());
		cntOp.getLbl().add("basys:op");
		if (!(op.getId() == null || op.getId().isEmpty())) {
			cntOp.getLbl().add("id:" + op.getId());
		}

		cntOp.getLbl().add("rdty:" + BasyxResourcesUtil.convertDataTypeToStr(op.getReturnDataType()));
		cntOp.getLbl().add("par-len:" + op.getParameterTypes().size());
		for (int i = 0; i < op.getParameterTypes().size(); ++i) {
			ParameterType pt = op.getParameterTypes().get(i);
			cntOp.getLbl().add("par-" + pt.getIndex() + "-nm:" + pt.getName());
			cntOp.getLbl()
					.add("par-" + pt.getIndex() + "-dty:" + BasyxResourcesUtil.convertDataTypeToStr(pt.getDataType()));
		}

		ResourceResult<Cnt> rr = this.resourceManager.createContainer(parentRi, cntOp, false);
		Cnt cntREQ = new Cnt();
		cntREQ.setRn("REQ");
		ResourceResult<Cnt> rrREQ = this.resourceManager.createContainer(rr.getResource().getRi(), cntREQ, false);

		Cnt cntPROC = new Cnt();
		cntPROC.setRn("PROC");
		ResourceResult<Cnt> rrPROC = this.resourceManager.createContainer(rr.getResource().getRi(), cntPROC, false);

		Cnt cntRESP = new Cnt();
		cntRESP.setRn("RESP");
		ResourceResult<Cnt> rrRESP = this.resourceManager.createContainer(rr.getResource().getRi(), cntRESP, false);

		OneM2MConnectedOperation cop = new OneM2MConnectedOperation(resourceManager, rr.getResource().getRi(),
				rrREQ.getResource().getRi(), rrPROC.getResource().getRi(), rrRESP.getResource().getRi(), op);
		return cop;
	}

	private OneM2MConnectedSubModel createSubModel(String parentRi, SubModel sm) throws Exception {
		Cnt cntSm = new Cnt();
		cntSm.setRn(sm.getName());
		cntSm.getLbl().add("basys:sm");
		cntSm.getLbl().add("ak:" + BasyxResourcesUtil.convertAssetKindToStr(sm.getAssetKind()));
		cntSm.getLbl().add("td:" + sm.getTypeDefinition());
		if (sm.getId() != null) {
			cntSm.getLbl().add("id:" + sm.getId());
		}
		ResourceResult<Cnt> rrSm = this.resourceManager.createContainer(parentRi, cntSm, false);

		OneM2MConnectedSubModel csm = new OneM2MConnectedSubModel(this.resourceManager, rrSm.getResource().getRi(), sm);

		for (Property prop : sm.getProperties().values()) {
			ConnectedProperty cprop = this.createProperty(csm.getM2MRi(), prop);
			csm.addProperty(cprop);
		}

		for (Event evt : sm.getEvents().values()) {
			OneM2MConnectedEvent cevt = this.createEvent(csm.getM2MRi(), evt);
			csm.addEvent(cevt);
		}

		for (Operation op : sm.getOperations().values()) {
			OneM2MConnectedOperation cop = this.createOperation(csm.getM2MRi(), op);
			csm.addOperation(cop);
		}
		return csm;
	}

	private void checkValidity(AssetAdministrationShell aas) {
		if (!BasyxResourcesUtil.isValidBasyxIdentifier(aas.getId())) {
			throw new IllegalArgumentException("AAS id is not valid: " + aas.getId());
		}
		for (SubModel sm : aas.getSubModels().values()) {
			if (!BasyxResourcesUtil.isValidBasyxIdentifier(sm.getName())) {
				throw new IllegalArgumentException("Submodel name is not valid: " + sm.getName());
			}
			for (Property prop : sm.getProperties().values()) {
				if (!BasyxResourcesUtil.isValidBasyxIdentifier(prop.getName())) {
					throw new IllegalArgumentException("Property name is not valid: " + prop.getName());
				}
			}
			for (Event evt : sm.getEvents().values()) {
				if (!BasyxResourcesUtil.isValidBasyxIdentifier(evt.getName())) {
					throw new IllegalArgumentException("Event name is not valid: " + evt.getName());
				}
			}
			for (Operation op : sm.getOperations().values()) {
				if (!BasyxResourcesUtil.isValidBasyxIdentifier(op.getName())) {
					throw new IllegalArgumentException("Operation name is not valid: " + op.getName());
				}
			}
		}
	}

	@Override
	public ConnectedAssetAdministrationShell createAAS(AssetAdministrationShell aas) throws Exception {
		checkValidity(aas);
		Ae aeAAS = new Ae();
		aeAAS.setRn(aas.getId());
		aeAAS.setApi(aas.getId());
		aeAAS.setRr(false);

		aeAAS.getLbl().add("basys:aas");
		aeAAS.getLbl().add("id:" + aas.getId());
		aeAAS.getLbl().add("atd:" + aas.getAssetTypeDefinition());
		aeAAS.getLbl().add("dn:" + aas.getDisplayName());
		aeAAS.getLbl().add("ak:" + BasyxResourcesUtil.convertAssetKindToStr(aas.getAssetKind()));
		aeAAS.getLbl().add("aid:" + aas.getAssetId());

		ResourceResult<Ae> rrAAS = this.resourceManager.createApplicationEntity("", aeAAS, true);
		if (rrAAS.getResource() == null) {
			return null;
		}

		OneM2MConnectedAssetAdministrationShell caas = new OneM2MConnectedAssetAdministrationShell(this.resourceManager,
				rrAAS.getResource().getRi(), aas);
		for (SubModel sm : aas.getSubModels().values()) {
			OneM2MConnectedSubModel csm = this.createSubModel(caas.getM2MRi(), sm);
			caas.addSubModel(csm);
		}

		return caas;
	}

	private ConnectedProperty buildProperty(Cnt cnt) {
		Cnt cntData = findChildCnt(cnt, "DATA");
		ConnectedProperty cprop = null;
		String propMap = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "col");

		if ("map".equals(propMap)) {
			cprop = new OneM2MConnectedCollectionProperty(this.resourceManager, cnt.getRi(), cntData.getRi(),
					cntData.getLa());
			cprop.setCollection(true);
		} else {
			cprop = new OneM2MConnectedSingleProperty(this.resourceManager, cnt.getRi(), cntData.getRi(),
					cntData.getLa());
			cprop.setCollection(false);
		}

		String propDataType = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "dty");
		cprop.setDataType(BasyxResourcesUtil.convertStrToDataType(propDataType));
		cprop.setEventable(OneM2MResourceUtil.hasLabel(cnt.getLbl(), "ev"));
		String cpropid = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "id");
		cprop.setId(cpropid);
		String propName = cnt.getRn();
		cprop.setName(propName);
		cprop.setReadable(OneM2MResourceUtil.hasLabel(cnt.getLbl(), "rd"));
		cprop.setWriteable(OneM2MResourceUtil.hasLabel(cnt.getLbl(), "wr"));
		return cprop;
	}

	private OneM2MConnectedOperation buildOperation(Cnt cnt) {
		Cnt cntREQ = findChildCnt(cnt, "REQ");
		Cnt cntPROC = findChildCnt(cnt, "PROC");
		Cnt cntRESP = findChildCnt(cnt, "RESP");

		OneM2MConnectedOperation cop = new OneM2MConnectedOperation(resourceManager, cnt.getRi(), cntREQ.getRi(),
				cntPROC.getRi(), cntRESP.getRi());

		String copid = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "id");
		cop.setId(copid);
		cop.setName(cnt.getRn());

		int parLen = Integer.parseInt(OneM2MResourceUtil.extractLabel(cnt.getLbl(), "par-len"));
		List<ParameterType> pts = new ArrayList<>();
		for (int i = 0; i < parLen; ++i) {
			int index = i;
			String name = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "par-" + i + "-nm");
			String strDty = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "par-" + i + "-dty");
			DataType dty = BasyxResourcesUtil.convertStrToDataType(strDty);
			pts.add(new ParameterType(index, dty, name));
		}
		cop.setParameterTypes(pts);
		String opRetDt = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "rdty");
		cop.setReturnDataType(BasyxResourcesUtil.convertStrToDataType(opRetDt));
		return cop;
	}

	private OneM2MConnectedEvent buildEvent(Cnt cnt) {
		Cnt cntData = findChildCnt(cnt, "DATA");
		OneM2MConnectedEvent ce = new OneM2MConnectedEvent(resourceManager, cnt.getRi(), cntData.getRi());

		String eDataType = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "dty");
		ce.setDataType(BasyxResourcesUtil.convertStrToDataType(eDataType));

		String eid = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "id");
		ce.setId(eid);
		ce.setName(cnt.getRn());
		return ce;
	}

	private OneM2MConnectedSubModel buildSubModel(Cnt cntSm) {
		OneM2MConnectedSubModel csm = new OneM2MConnectedSubModel(this.resourceManager, cntSm.getRi());

		String smName = cntSm.getRn();
		csm.setName(smName);
		String smAssetKind = OneM2MResourceUtil.extractLabel(cntSm.getLbl(), "ak");
		csm.setAssetKind(BasyxResourcesUtil.convertStrToAssetKind(smAssetKind));
		String csmid = OneM2MResourceUtil.extractLabel(cntSm.getLbl(), "id");
		csm.setId(csmid);
		String smTypeDefinition = OneM2MResourceUtil.extractLabel(cntSm.getLbl(), "td");
		csm.setTypeDefinition(smTypeDefinition);

		for (Cnt cnt : cntSm.getCnt()) {
			String cnttype = OneM2MResourceUtil.extractLabel(cnt.getLbl(), "basys");
			if (cnttype != null && cnttype.equals("prop")) { // is property
				ConnectedProperty cprop = this.buildProperty(cnt);
				csm.addProperty(cprop);
			} else if (cnttype != null && cnttype.equals("op")) { // is operation
				ConnectedOperation cop = this.buildOperation(cnt);
				csm.addOperation(cop);
			} else if (cnttype != null && cnttype.equals("evt")) { // is event
				ConnectedEvent ce = this.buildEvent(cnt);
				csm.addEvent(ce);
			}
		}

		return csm;
	}

	@Override
	public ConnectedAssetAdministrationShell retrieveAAS(String id) throws Exception {

		ResourceResults<Resource> rr = this.resourceExplorer.retrieveResourceWithChildrenRecursive(id, true, 4);

		Ae aeAAS = (Ae) rr.getResource();
		if (aeAAS == null) {
			return null;
		}
		OneM2MConnectedAssetAdministrationShell caas = new OneM2MConnectedAssetAdministrationShell(this.resourceManager,
				aeAAS.getRi());

		String aasAssetId = OneM2MResourceUtil.extractLabel(aeAAS.getLbl(), "aid");
		caas.setAssetId(aasAssetId);

		String aasKind = OneM2MResourceUtil.extractLabel(aeAAS.getLbl(), "ak");
		caas.setAssetKind(BasyxResourcesUtil.convertStrToAssetKind(aasKind));

		String aasAssetTypeDefinition = OneM2MResourceUtil.extractLabel(aeAAS.getLbl(), "atd");
		caas.setAssetTypeDefinition(aasAssetTypeDefinition);

		String aasDisplayName = OneM2MResourceUtil.extractLabel(aeAAS.getLbl(), "dn");
		caas.setDisplayName(aasDisplayName);
		String aasid = OneM2MResourceUtil.extractLabel(aeAAS.getLbl(), "id");
		caas.setId(aasid);
		for (Cnt cntSm : aeAAS.getCnt()) { // submodels
			OneM2MConnectedSubModel csm = this.buildSubModel(cntSm);
			caas.addSubModel(csm);
		}
		return caas;
	}

	public boolean existsAAS(String id) throws Exception {
		ResourceResult<Ae> rr = this.resourceManager.retrieveApplicationEntity(id, true);
		Ae aeAAS = (Ae) rr.getResource();
		if (aeAAS == null) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<ConnectedAssetAdministrationShell> retrieveAASAll() {

		IOneM2MClient client = this.resourceManager.getClient();
		Rqp rqp = client.createDefaultRequest("", true);
		rqp.setOp(BigInteger.valueOf(2));
		rqp.setFc(new FilterCriteria());
		rqp.getFc().setFu(BigInteger.valueOf(1));
		rqp.getFc().getLbl().add("basys:aas");

		try {
			Rsp rsp = client.send(rqp);
			Collection<Object> urils = (Collection<Object>) rsp.getPc().getAnyOrAny().get(0);
			ArrayList<ConnectedAssetAdministrationShell> cshells = new ArrayList<ConnectedAssetAdministrationShell>();
			for (Object uril : urils) {
				String url = uril.toString();
				String[] splits = url.split("/", 4);
				String id = splits[3];
				try {
					ConnectedAssetAdministrationShell caas = this.retrieveAAS(id);
					cshells.add(caas);
				} catch (Exception e) {
					System.out.println("Did not retrieve: " + id); // TODO log
				}
			}
			return cshells;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void deleteAAS(String id) throws Exception {
		this.resourceManager.deleteResource(id, true);
	}

	private Cnt findChildCnt(Cnt parent, String rn) {
		for (Cnt child : parent.getCnt()) {
			if (rn.equals(child.getRn())) {
				return child;
			}
		}
		return null;
	}

	@Override
	public Object callOperation(String aasId, String subName, String operationName, Object parameters[], int timeout)
			throws Exception {
		ConnectedOperation co = this.retrieveOperation(aasId, subName, operationName);
		return co.call(parameters, timeout);
	}

	@Override
	public ConnectedOperation retrieveOperation(String aasId, String subName, String operationName) throws Exception {
		ResourceResults<Resource> rr = this.resourceExplorer
				.retrieveResourceWithChildrenRecursive(aasId + "/" + subName + "/" + operationName, true);
		Cnt cntOp = (Cnt) rr.getResource();
		return cntOp == null ? null : this.buildOperation(cntOp);
	}

	@Override
	public ConnectedProperty retrieveProperty(String aasId, String subName, String propertyName) throws Exception {
		ResourceResults<Resource> rr = this.resourceExplorer
				.retrieveResourceWithChildrenRecursive(aasId + "/" + subName + "/" + propertyName, true);
		Cnt cntProp = (Cnt) rr.getResource();
		return cntProp == null ? null : this.buildProperty(cntProp);
	}

	@Override
	public Object getSingleProperty(String aasId, String subName, String propertyName) throws Exception {
		ConnectedSingleProperty cp = (ConnectedSingleProperty) this.retrieveProperty(aasId, subName, propertyName);
		return cp.get();
	}

	@Override
	public void setSingleProperty(String aasId, String subName, String propertyName, Object value) throws Exception {
		ConnectedSingleProperty cp = (ConnectedSingleProperty) this.retrieveProperty(aasId, subName, propertyName);
		cp.set(value);
	}

	@Override
	public Object getCollectionProperty(String aasId, String subName, String propertyName, String key)
			throws Exception {
		ConnectedCollectionProperty cp = (ConnectedCollectionProperty) this.retrieveProperty(aasId, subName,
				propertyName);
		return cp.get(key);
	}

	@Override
	public void setCollectionProperty(String aasId, String subName, String propertyName, String key, Object value)
			throws Exception {
		ConnectedCollectionProperty cp = (ConnectedCollectionProperty) this.retrieveProperty(aasId, subName,
				propertyName);
		cp.set(key, value);
	}

}
