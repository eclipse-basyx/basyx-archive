package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums.DataTypeIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums.LevelType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

/**
 * Interface for DataSpecification
 * In DAAS, this element is named DataSpecificationIEC61360, but for clarity "Content" is added to the interface name
 *
 * @author rajashek, espen
 *
 */
public interface IDataSpecificationIEC61360Content extends IDataSpecificationContent {
	public LangStrings getPreferredName();
	public LangStrings getShortName();
	public String getUnit();
	public IReference getUnitId();
	public String getSourceOfDefinition();
	public String getSymbol();
	public DataTypeIEC61360 getDataType();
	public LangStrings getDefinition();
	public String getValueFormat();
	public Collection<IValueReferencePair> getValueList();
	public String getValue();
	public IReference getValueId();
	public LevelType getLevelType();
}
