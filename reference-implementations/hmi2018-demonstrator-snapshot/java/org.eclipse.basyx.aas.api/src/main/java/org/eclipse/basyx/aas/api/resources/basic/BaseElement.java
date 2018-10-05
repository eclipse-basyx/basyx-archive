package org.eclipse.basyx.aas.api.resources.basic;

public class BaseElement {

	protected BaseElement parent;	
	protected String id;
	protected String name;

	
	public BaseElement getParent() {
		return parent;
	}
	public void setParent(BaseElement parent) {
		this.parent = parent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
