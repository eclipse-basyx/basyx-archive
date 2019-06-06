package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for Referable 
 * the function names are self explanatory 
 * @author rajashek
 *
*/
public interface IReferable {
	

	public String getIdshort();
	
	public String getCategory();
	
	public String getDescription();
	
	public IReference  getParent();
	
	public void setIdshort(String idShort);
	
	public void setCategory( String category);
	
	public void setDescription(String description);
	
	public void setParent(IReference  obj);
	
	
	
	
	
	

}
