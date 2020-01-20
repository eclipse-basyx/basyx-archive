package org.eclipse.basyx.submodel.metamodel.facade.dataspecification;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

/**
 * Facade providing access to a map containing the DataSpecification structure
 * 
 * @author rajashek
 *
 */
public class DataSpecificationIEC61360Facade implements IDataSpecificationIEC61360 {

	private Map<String, Object> map;

	public DataSpecificationIEC61360Facade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public LangStrings getPreferredName() {
		if (map.get(DataSpecificationIEC61360.PREFERREDNAME) == null)
			return null;

		return (LangStrings) map.get(DataSpecificationIEC61360.PREFERREDNAME);
	}

	@Override
	public String getShortName() {
		if (map.get(DataSpecificationIEC61360.SHORTNAME) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.SHORTNAME);
	}

	@Override
	public String getUnit() {
		if (map.get(DataSpecificationIEC61360.UNIT) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.UNIT);
	}

	@Override
	public IReference getUnitId() {
		if (map.get(DataSpecificationIEC61360.UNITID) == null)
			return null;
		return (IReference) map.get(DataSpecificationIEC61360.UNITID);
	}

	@Override
	public LangStrings getSourceOfDefinition() {
		if (map.get(DataSpecificationIEC61360.SOURCEOFDEFINITION) == null)
			return null;
		return (LangStrings) map.get(DataSpecificationIEC61360.SOURCEOFDEFINITION);
	}

	@Override
	public String getSymbol() {
		if (map.get(DataSpecificationIEC61360.SYMBOL) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.SYMBOL);
	}

	@Override
	public String getDataType() {
		if (map.get(DataSpecificationIEC61360.DATATYPE) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.DATATYPE);
	}

	@Override
	public LangStrings getDefinition() {
		if (map.get(DataSpecificationIEC61360.DEFINITION) == null)
			return null;
		return (LangStrings) map.get(DataSpecificationIEC61360.DEFINITION);
	}

	@Override
	public String getValueFormat() {
		if (map.get(DataSpecificationIEC61360.VALUEFORMAT) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.VALUEFORMAT);
	}

	@Override
	public String getValueList() {
		if (map.get(DataSpecificationIEC61360.VALUELIST) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.VALUELIST);
	}

	@Override
	public String getCode() {
		if (map.get(DataSpecificationIEC61360.CODE) == null)
			return null;
		return (String) map.get(DataSpecificationIEC61360.CODE);
	}

	public void setPreferredName(LangStrings preferredName) {
		map.put(DataSpecificationIEC61360.PREFERREDNAME, preferredName);
	}

	public void setShortName(String shortName) {
		map.put(DataSpecificationIEC61360.SHORTNAME, shortName);
	}

	public void setUnit(String uni) {
		map.put(DataSpecificationIEC61360.UNIT, uni);
	}

	public void setUnitId(IReference unitId) {
		map.put(DataSpecificationIEC61360.UNITID, unitId);
	}

	public void setSourceOfDefinition(LangStrings sourceOfDefinition) {
		map.put(DataSpecificationIEC61360.SOURCEOFDEFINITION, sourceOfDefinition);
	}

	public void setSymbol(String symbol) {
		map.put(DataSpecificationIEC61360.SYMBOL, symbol);
	}

	public void setDataType(String dataType) {
		map.put(DataSpecificationIEC61360.DATATYPE, dataType);
	}

	public void setDefinition(LangStrings definition) {
		map.put(DataSpecificationIEC61360.DEFINITION, definition);
	}

	public void setValueFormat(String valueFormat) {
		map.put(DataSpecificationIEC61360.VALUEFORMAT, valueFormat);
	}

	public void setValueList(Object obj) {
		map.put(DataSpecificationIEC61360.VALUELIST, obj);
	}

	public void setCode(Object obj) {
		map.put(DataSpecificationIEC61360.CODE, obj);
	}

}
