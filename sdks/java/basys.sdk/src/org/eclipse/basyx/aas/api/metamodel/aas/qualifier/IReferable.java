package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;
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
	
	public Object getParent();
	
	public void setIdshort(String idShort);
	
	public void setCategory( String category);
	
	public void setDescription(String description);
	
	public void setParent(Object obj);
	
	
	
	
	
	

}
