package org.eclipse.basyx.aas.metamodel.builder;

import java.util.Map;

public class ElementBuilder {

	Map<String, Object> target;

	public ElementBuilder(Map<String, Object> target) {
		this.target = target;
	}

	public Object build() {
		return target;
	}

	public ElementBuilder set(String key, Object value) {
		target.put(key, value);
		return this;
	}
}
