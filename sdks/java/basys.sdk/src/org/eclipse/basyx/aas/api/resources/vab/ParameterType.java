package org.eclipse.basyx.aas.api.resources.vab;

/**
 * Store a parameter type
 * 
 * @author schoeffler
 *
 */
public class ParameterType {

	
	/**
	 * Parameter index
	 * - FIXME: Where do we start counting?
	 */
	protected int index;
	
	
	/**
	 * Parameter data type
	 */
	protected DataType dataType;
	
	
	/**
	 * Parameter name
	 */
	protected String name;	
	
	
	
	
	/**
	 * Default constructor
	 */
	public ParameterType() {
		super();
	}
	
	
	/**
	 * Constructor 
	 * 
	 * @param index Index of parameter
	 * @param dataType Data type of parameter
	 * @param name Name of parameter
	 */
	public ParameterType(int index, DataType dataType, String name) {
		// Delegate to default constructor
		this();
		
		// Set parameter values
		this.setIndex(index);
		this.setDataType(dataType);
		this.setName(name);
	}

	
	/**
	 * Return parameter index
	 * 
	 * @return Parameter index
	 */
	public int getIndex() {
		return index;
	}
	
	
	/**
	 * Set parameter index
	 * 
	 * @param index New/updated index of parameter
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	/**
	 * Get parameter data type
	 * 
	 * @return Parameter data type
	 */
	public DataType getDataType() {
		return dataType;
	}
	
	/**
	 * Set parameter data type
	 * 
	 * @param dataType New/updated parameter data type
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	
	/**
	 * Get parameter name
	 * 
	 * @return Parameter name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Set parameter name
	 * 
	 * @param name New/updated name of parameter
	 */
	public void setName(String name) {
		this.name = name;
	}
}

