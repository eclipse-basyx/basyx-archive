package org.eclipse.basyx.aas.impl.provider.javahandler.genericsm;

import java.util.function.BiFunction;

import org.eclipse.basyx.aas.api.resources.basic.ISubModel;


/**
 * A generic operation class for the generic handler sub model class
 * 
 * @author kuhn
 *
 */
public abstract class GenericHandlerOperation implements BiFunction<ISubModel, Object[], Object> {

	
	/**
	 * Execute operation
	 */
	@Override
	public abstract Object apply(ISubModel instance, Object[] parameter);
}

