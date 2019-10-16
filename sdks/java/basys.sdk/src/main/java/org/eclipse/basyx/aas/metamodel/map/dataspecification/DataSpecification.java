package org.eclipse.basyx.aas.metamodel.map.dataspecification;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.api.dataspecification.IDataSpecification;
import org.eclipse.basyx.aas.metamodel.facade.dataspecification.DataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * DataSpecification class
 * 
 * @author elsheikh
 *
 */
public class DataSpecification extends HashMap<String, Object> implements IDataSpecification {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PREFERREDNAME="preferredName";
	
	public static final String SHORTNAME="shortName";

	public static final String UNIT= "unit";

	public static final String UNITID="unitId";

	public static final String SOURCEOFDEFINITION="sourceOfDefinition";

	public static final String SYMBOL="symbol";

	public static final String DATATYPE="dataType";

	public static final String DEFINITION="definition";

	public static final String VALUEFORMAT="valueFormat";

	public static final String VALUELIST="valueList";

	public static final String CODE="code";


	/**
	 * Constructor
	 */
	public DataSpecification() {
		// Add Identifiable class
		putAll(new Identifiable());

		// Default values
		put(PREFERREDNAME, null);
		put(SHORTNAME, null);
		put(UNIT, null);
		put(UNITID, null);
		put(SOURCEOFDEFINITION, null);
		put(SYMBOL, null);
		put(DATATYPE, null);
		put(DEFINITION, null);
		put(VALUEFORMAT, null);
		put(VALUELIST, null);
		put(CODE, null);
	}

	public DataSpecification(String preferredName, String shortName, String uni, Reference unitId, String sourceOfDefinition, String symbol, String dataType, String definition, String valueFormat/* , Valuelist valueList, Code code */) {
		// Add Identifiable class
		putAll(new Identifiable());

		// Default values
		put(PREFERREDNAME, preferredName);
		put(SHORTNAME, shortName);
		put(UNIT, uni);
		put(UNITID, null);
		put(SOURCEOFDEFINITION, sourceOfDefinition);
		put(SYMBOL, symbol);
		put(DATATYPE, dataType);
		put(DEFINITION, definition);
		put(VALUEFORMAT, valueFormat);
		put(VALUELIST, null);
		put(CODE, null);
	}

	@Override
	public String getPreferredName() {
	return new DataSpecificationFacade(this).getPreferredName();
	}

	@Override
	public String getShortName() {
		return new DataSpecificationFacade(this).getShortName();
	}

	@Override
	public String getUnit() {
		return new DataSpecificationFacade(this).getUnit();
	}

	@Override
	public IReference getUnitId() {
		return new DataSpecificationFacade(this).getUnitId();
	}

	@Override
	public String getSourceOfDefinition() {
		return new DataSpecificationFacade(this).getSourceOfDefinition();
	}

	@Override
	public String getSymbol() {
		return new DataSpecificationFacade(this).getSymbol();
	}

	@Override
	public String getDataType() {
		return new DataSpecificationFacade(this).getDataType();
	}

	@Override
	public String getDefinition() {
		return new DataSpecificationFacade(this).getDefinition();
	}

	@Override
	public String getValueFormat() {
		return new DataSpecificationFacade(this).getValueFormat();
	}

	@Override
	public String getValueList() {
		return new DataSpecificationFacade(this).getValueList();
	}

	@Override
	public String getCode() {
		return new DataSpecificationFacade(this).getCode();
	}


	public void setPreferredName(String preferredName) {
		new DataSpecificationFacade(this).setPreferredName(preferredName);
		
	}


	public void setShortName(String shortName) {
		new DataSpecificationFacade(this).setShortName(shortName);
		
	}


	public void setUnit(String uni) {
		new DataSpecificationFacade(this).setUnit(uni);
		
	}


	public void setUnitId(IReference unitId) {
		new DataSpecificationFacade(this).setUnitId(unitId);
		
	}


	public void setSourceOfDefinition(String sourceOfDefinition) {
		new DataSpecificationFacade(this).setSourceOfDefinition(sourceOfDefinition);
		
	}


	public void setSymbol(String symbol) {
		new DataSpecificationFacade(this).setSymbol(symbol);
		
	}


	public void setDataType(String dataType) {
		new DataSpecificationFacade(this).setDataType(dataType);
		
	}


	public void setDefinition(String definition) {
		new DataSpecificationFacade(this).setDefinition(definition);
		
	}


	public void setValueFormat(String valueFormat) {
		new DataSpecificationFacade(this).setValueFormat(valueFormat);
		
	}


	public void setValueList(Object obj) {
		new DataSpecificationFacade(this).setValueList(obj);
		
	}


	public void setCode(Object obj) {
		new DataSpecificationFacade(this).setCode(obj);
		
	}


}
