package org.eclipse.basyx.submodel.metamodel.map.dataspecification;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
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

	public static final String PREFERREDNAME = "preferredName";

	public static final String SHORTNAME = "shortName";

	public static final String UNIT = "unit";

	public static final String UNITID = "unitId";

	public static final String SOURCEOFDEFINITION = "sourceOfDefinition";

	public static final String SYMBOL = "symbol";

	public static final String DATATYPE = "dataType";

	public static final String DEFINITION = "definition";

	public static final String VALUEFORMAT = "valueFormat";

	public static final String VALUELIST = "valueList";

	public static final String CODE = "code";

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

	public DataSpecificationIEC61360(LangStrings preferredName, String shortName, String uni, Reference unitId, LangStrings sourceOfDefinition, String symbol, String dataType, LangStrings definition,
			String valueFormat/* , Valuelist valueList, Code code */) {
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

	/**
	 * Creates a DataSpecificationIEC61360 object from a map
	 * 
	 * @param obj
	 *            a DataSpecificationIEC61360 object as raw map
	 * @return a DataSpecificationIEC61360 object, that behaves like a facade for
	 *         the given map
	 */
	public static DataSpecificationIEC61360 createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		DataSpecificationIEC61360 ret = new DataSpecificationIEC61360();
		ret.putAll(map);
		return ret;
	}

	@Override
	public LangStrings getPreferredName() {
		if (get(DataSpecificationIEC61360.PREFERREDNAME) == null)
			return null;

		return (LangStrings) get(DataSpecificationIEC61360.PREFERREDNAME);
	}

	@Override
	public String getShortName() {
		if (get(DataSpecificationIEC61360.SHORTNAME) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.SHORTNAME);
	}

	@Override
	public String getUnit() {
		if (get(DataSpecificationIEC61360.UNIT) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.UNIT);
	}

	@Override
	public IReference getUnitId() {
		if (get(DataSpecificationIEC61360.UNITID) == null)
			return null;
		return (IReference) get(DataSpecificationIEC61360.UNITID);
	}

	@Override
	public LangStrings getSourceOfDefinition() {
		if (get(DataSpecificationIEC61360.SOURCEOFDEFINITION) == null)
			return null;
		return (LangStrings) get(DataSpecificationIEC61360.SOURCEOFDEFINITION);
	}

	@Override
	public String getSymbol() {
		if (get(DataSpecificationIEC61360.SYMBOL) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.SYMBOL);
	}

	@Override
	public String getDataType() {
		if (get(DataSpecificationIEC61360.DATATYPE) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.DATATYPE);
	}

	@Override
	public LangStrings getDefinition() {
		if (get(DataSpecificationIEC61360.DEFINITION) == null)
			return null;
		return (LangStrings) get(DataSpecificationIEC61360.DEFINITION);
	}

	@Override
	public String getValueFormat() {
		if (get(DataSpecificationIEC61360.VALUEFORMAT) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.VALUEFORMAT);
	}

	@Override
	public String getValueList() {
		if (get(DataSpecificationIEC61360.VALUELIST) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.VALUELIST);
	}

	@Override
	public String getCode() {
		if (get(DataSpecificationIEC61360.CODE) == null)
			return null;
		return (String) get(DataSpecificationIEC61360.CODE);
	}

	public void setPreferredName(LangStrings preferredName) {
		put(DataSpecificationIEC61360.PREFERREDNAME, preferredName);
	}

	public void setShortName(String shortName) {
		put(DataSpecificationIEC61360.SHORTNAME, shortName);
	}

	public void setUnit(String uni) {
		put(DataSpecificationIEC61360.UNIT, uni);
	}

	public void setUnitId(IReference unitId) {
		put(DataSpecificationIEC61360.UNITID, unitId);
	}

	public void setSourceOfDefinition(LangStrings sourceOfDefinition) {
		put(DataSpecificationIEC61360.SOURCEOFDEFINITION, sourceOfDefinition);
	}

	public void setSymbol(String symbol) {
		put(DataSpecificationIEC61360.SYMBOL, symbol);
	}

	public void setDataType(String dataType) {
		put(DataSpecificationIEC61360.DATATYPE, dataType);
	}

	public void setDefinition(LangStrings definition) {
		put(DataSpecificationIEC61360.DEFINITION, definition);
	}

	public void setValueFormat(String valueFormat) {
		put(DataSpecificationIEC61360.VALUEFORMAT, valueFormat);
	}

	public void setValueList(Object obj) {
		put(DataSpecificationIEC61360.VALUELIST, obj);
	}

	public void setCode(Object obj) {
		put(DataSpecificationIEC61360.CODE, obj);
	}

}
