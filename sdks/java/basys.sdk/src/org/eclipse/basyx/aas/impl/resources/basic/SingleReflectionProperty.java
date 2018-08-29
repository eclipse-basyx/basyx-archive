package org.eclipse.basyx.aas.impl.resources.basic;

import java.lang.reflect.Field;

import org.eclipse.basyx.aas.api.exception.ServerException;

/**
 * Provides reflective access to a simple value based on a property
 * @author schnicke
 *
 */
public class SingleReflectionProperty extends SingleProperty{
	private Field f;
	private Object o;
	public SingleReflectionProperty(Field f, Object o) {
		super(null);
		this.f = f;
		this.o = o;
		f.setAccessible(true);
	}
	
	@Override
	public Object get() throws Exception {
		return f.get(o);
	}
	
	@Override
	public void set(Object newValue) throws ServerException {
		try {
			f.set(o, newValue);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
