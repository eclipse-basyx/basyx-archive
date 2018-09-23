package org.eclipse.basyx.aas.impl.resources.basic;

import java.lang.reflect.Method;

public class ReflectionOperation extends Operation {
	private Object obj;
	private Method m;

	public ReflectionOperation(Object obj, Method m) {
		super();
		this.obj = obj;
		this.m = m;
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		return m.invoke(obj, params);
	}
}
