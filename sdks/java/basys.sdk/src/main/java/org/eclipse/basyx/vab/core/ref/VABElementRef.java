package org.eclipse.basyx.vab.core.ref;

/**
 * VAB reference to a remote property. This reference also contains RTTI
 * information
 * 
 * @author kuhn
 *
 */
public class VABElementRef {

	/**
	 * Store server path to element. This path may contain optimized handles and is
	 * used internally by the SDK.
	 */
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public VABElementRef(String path) {
		super();
		this.path = path;
	}

	public VABElementRef() {
	}
}
