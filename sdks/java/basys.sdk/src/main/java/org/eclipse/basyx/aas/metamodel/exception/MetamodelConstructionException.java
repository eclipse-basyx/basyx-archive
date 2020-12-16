package org.eclipse.basyx.aas.metamodel.exception;

import java.util.Map;

/**
 * This class is used to throw exception when
 * metamodel's createAsFacade from map does not work
 * due to absence of mandatory fields
 * 
 * @author haque
 *
 */
public class MetamodelConstructionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public MetamodelConstructionException(Class<?> clazz , Map<String, Object> map) {
		super("Could not construct meta model element " + clazz.getName() + ". Passed argument was " + map.toString());
	}
}
