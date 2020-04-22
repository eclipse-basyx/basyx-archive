package org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.SubmodelElementXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses &lt;aas:operation&gt; and builds the Operation object from it <br>
 * Builds &lt;aas:operation&gt; form a given Operation object
 * 
 * @author conradi
 *
 */
public class OperationXMLConverter extends SubmodelElementXMLConverter {
	
	public static final String OPERATION = "aas:operation";
	public static final String INPUT_VARIABLE = "aas:inputVariable";
	public static final String OUTPUT_VARIABLE = "aas:outputVariable";
	public static final String INOUTPUT_VARIABLE = "aas:inoutputVariable";
	public static final String OPERATION_VARIABLE = "aas:operationVariable";
	

	/**
	 * Parses a Map containing the content of XML tag &lt;aas:operation&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:operation&gt;
	 * @return the parsed Operation
	 */
	@SuppressWarnings("unchecked")
	public static Operation parseOperation(Map<String, Object> xmlObject) {
		
		Map<String, Object> inObj = (Map<String, Object>) xmlObject.get(INPUT_VARIABLE);
		List<Map<String, Object>> xmlOpVars = XMLHelper.getList(inObj.get(OPERATION_VARIABLE));
		List<OperationVariable> inList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			inList.add(parseOperationVariable(map));
		}
		
		Map<String, Object> outObj = (Map<String, Object>) xmlObject.get(OUTPUT_VARIABLE);
		xmlOpVars = XMLHelper.getList(outObj.get(OPERATION_VARIABLE));
		List<OperationVariable> outList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			outList.add(parseOperationVariable(map));
		}
		
		Map<String, Object> inoutObj = (Map<String, Object>) xmlObject.get(INOUTPUT_VARIABLE);
		xmlOpVars = XMLHelper.getList(inoutObj.get(OPERATION_VARIABLE));
		List<OperationVariable> inoutList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			inoutList.add(parseOperationVariable(map));
		}
	
		Operation operation = new Operation(inList, outList, inoutList, null);
		populateSubmodelElement(xmlObject, operation);
		return operation;
	}
	

	/**
	 * Parses a Map containing the content of XML tag &lt;aas:operationVariable&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:operationVariable&gt;
	 * @return the parsed OperationVariable
	 */
	@SuppressWarnings("unchecked")
	private static OperationVariable parseOperationVariable(Map<String, Object> xmlObject) {
		SubmodelElement submodelElement = getSubmodelElement((Map<String, Object>) xmlObject.get(VALUE));
		OperationVariable operationVariable = new OperationVariable(submodelElement);
		return operationVariable;
	}
	
	
	
	
	/**
	 * Builds the &lt;aas:operation&gt; XML tag for an Operation
	 * 
	 * @param document the XML document
	 * @param operation the IOperation to build the XML for
	 * @return the &lt;aas:operation&gt; XML tag for the given Operation
	 */
	public static Element buildOperation(Document document, IOperation operation) {
		Element operationRoot = document.createElement(OPERATION);
		
		populateSubmodelElement(document, operationRoot, operation);
		
		Collection<IOperationVariable> in = operation.getInputVariables();
		if(in != null) {
			Element valueRoot = document.createElement(INPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: in) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}
		
		Collection<IOperationVariable> out = operation.getOutputVariables();
		if(out != null) {
			Element valueRoot = document.createElement(OUTPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: out) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}
		
		Collection<IOperationVariable> inout = operation.getInOutputVariables();
		if(out != null) {
			Element valueRoot = document.createElement(INOUTPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: inout) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}

		return operationRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:operationVariable&gt; XML tag for an OperationVariable
	 * 
	 * @param document the XML document
	 * @param operationVariable the IOperationVariable to build the XML for
	 * @return the &lt;aas:operationVariable&gt; XML tag for the given OperationVariable
	 */
	private static Element buildOperationVariable(Document document, IOperationVariable operationVariable) {
		Element operationVariableRoot = document.createElement(OPERATION_VARIABLE);
		
		ISubmodelElement value = operationVariable.getValue();
		
		if(value != null) {
			Element valueRoot = document.createElement(VALUE);
			valueRoot.appendChild(buildSubmodelElement(document, value));
			operationVariableRoot.appendChild(valueRoot);
		}

		return operationVariableRoot;
	}
}
