package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

/**
 * Interface for DataSpecification
 *
 * @author rajashek
 *
 */
public interface IDataSpecificationIEC61360 extends IDataSpecificationContent {
	public LangStrings getPreferredName();
	public String getShortName();
	public String getUnit();
	public IReference getUnitId();
	public LangStrings getSourceOfDefinition();
	public String getSymbol();
	public String getDataType();
	public LangStrings getDefinition();
	public String getValueFormat();
	public Object getValueList();
	public Object getCode();
}
