package org.eclipse.basyx.aas.impl.resources.basic;

import java.lang.reflect.Field;


/**
 * Base class for properties relying on reflections
 * @author schnicke
 *
 */
public abstract class AbstractReflectionProperty extends Property {
	Field f;
	Object o;

	public AbstractReflectionProperty(Field f, Object o) {
		super();
		this.f = f;
		this.o = o;
	}

	protected Field getField() {
		return f;
	}

	protected Object getObject() {
		return o;
	}
}
