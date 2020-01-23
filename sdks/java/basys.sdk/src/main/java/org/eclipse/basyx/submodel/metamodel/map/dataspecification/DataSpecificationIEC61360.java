package org.eclipse.basyx.submodel.metamodel.map.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.dataspecification.DataSpecificationIEC61360Facade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * DataSpecification class
 * 
 * @author elsheikh
 *
 */
public class DataSpecificationIEC61360 extends DataSpecificationContent implements IDataSpecificationIEC61360 {

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
	public DataSpecificationIEC61360() {
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

	public DataSpecificationIEC61360(LangStrings preferredName, String shortName, String uni, Reference unitId, LangStrings sourceOfDefinition, String symbol, String dataType, LangStrings definition, String valueFormat/* , Valuelist valueList, Code code */) {
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
	public LangStrings getPreferredName() {
	return new DataSpecificationIEC61360Facade(this).getPreferredName();
	}

	@Override
	public String getShortName() {
		return new DataSpecificationIEC61360Facade(this).getShortName();
	}

	@Override
	public String getUnit() {
		return new DataSpecificationIEC61360Facade(this).getUnit();
	}

	@Override
	public IReference getUnitId() {
		return new DataSpecificationIEC61360Facade(this).getUnitId();
	}

	@Override
	public LangStrings getSourceOfDefinition() {
		return new DataSpecificationIEC61360Facade(this).getSourceOfDefinition();
	}

	@Override
	public String getSymbol() {
		return new DataSpecificationIEC61360Facade(this).getSymbol();
	}

	@Override
	public String getDataType() {
		return new DataSpecificationIEC61360Facade(this).getDataType();
	}

	@Override
	public LangStrings getDefinition() {
		return new DataSpecificationIEC61360Facade(this).getDefinition();
	}

	@Override
	public String getValueFormat() {
		return new DataSpecificationIEC61360Facade(this).getValueFormat();
	}

	@Override
	public Object getValueList() {
		return new DataSpecificationIEC61360Facade(this).getValueList();
	}

	@Override
	public Object getCode() {
		return new DataSpecificationIEC61360Facade(this).getCode();
	}


	public void setPreferredName(LangStrings preferredName) {
		new DataSpecificationIEC61360Facade(this).setPreferredName(preferredName);
		
	}


	public void setShortName(String shortName) {
		new DataSpecificationIEC61360Facade(this).setShortName(shortName);
		
	}


	public void setUnit(String uni) {
		new DataSpecificationIEC61360Facade(this).setUnit(uni);
		
	}


	public void setUnitId(IReference unitId) {
		new DataSpecificationIEC61360Facade(this).setUnitId(unitId);
		
	}


	public void setSourceOfDefinition(LangStrings sourceOfDefinition) {
		new DataSpecificationIEC61360Facade(this).setSourceOfDefinition(sourceOfDefinition);
		
	}


	public void setSymbol(String symbol) {
		new DataSpecificationIEC61360Facade(this).setSymbol(symbol);
		
	}


	public void setDataType(String dataType) {
		new DataSpecificationIEC61360Facade(this).setDataType(dataType);
		
	}


	public void setDefinition(LangStrings definition) {
		new DataSpecificationIEC61360Facade(this).setDefinition(definition);
		
	}


	public void setValueFormat(String valueFormat) {
		new DataSpecificationIEC61360Facade(this).setValueFormat(valueFormat);
		
	}


	public void setValueList(Object obj) {
		new DataSpecificationIEC61360Facade(this).setValueList(obj);
		
	}


	public void setCode(Object obj) {
		new DataSpecificationIEC61360Facade(this).setCode(obj);
		
	}


}
