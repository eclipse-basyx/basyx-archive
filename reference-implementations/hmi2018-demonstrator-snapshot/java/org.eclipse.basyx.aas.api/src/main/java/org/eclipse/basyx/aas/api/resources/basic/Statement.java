package org.eclipse.basyx.aas.api.resources.basic;

import org.eclipse.basyx.aas.api.resources.ExpressionLogicEnum;
import org.eclipse.basyx.aas.api.resources.ExpressionTypeEnum;

public class Statement {	
	
	protected String name;	
	protected Object value;
	protected String unit;	
	protected ExpressionLogicEnum expressionLogic;
	protected ExpressionTypeEnum expressionType;
	protected DataType dataType;
	
	
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public ExpressionLogicEnum getExpressionLogic() {
		return expressionLogic;
	}
	public void setExpressionLogic(ExpressionLogicEnum expressionLogic) {
		this.expressionLogic = expressionLogic;
	}
	public ExpressionTypeEnum getExpressionType() {
		return expressionType;
	}
	public void setExpressionType(ExpressionTypeEnum expressionType) {
		this.expressionType = expressionType;
	}
	
	
}
