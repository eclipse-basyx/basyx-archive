package org.eclipse.basyx.aas.api.resources.basic;

import java.util.HashMap;
import java.util.Map;

public class Property extends BaseElement {
		
	protected boolean readable;
	protected boolean writeable;
	protected boolean eventable;
	protected DataType dataType;
	
	protected boolean isCollection;
	
	protected HashMap<String, Statement> statements = new HashMap<>();
	
	
	public synchronized void addStatement(Statement statement) {
		if (statement.name == null || statement.name.isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.statements.put(statement.name, statement);
	}
	
	public Map<String, Statement> getStatements() {
		return this.statements;
	}	
		

	public boolean isCollection() {
		return isCollection;
	}
		
	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

	public boolean isReadable() {
		return readable;
	}
	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	public boolean isWriteable() {
		return writeable;
	}
	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
	}

	public boolean isEventable() {
		return eventable;
	}
	public void setEventable(boolean eventable) {
		this.eventable = eventable;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	
	
}
