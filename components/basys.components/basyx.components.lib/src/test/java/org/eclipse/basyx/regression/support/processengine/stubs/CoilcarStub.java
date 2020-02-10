package org.eclipse.basyx.regression.support.processengine.stubs;


public class CoilcarStub implements ICoilcar {
	
	private String serviceCalled;
	private Object parameter;

	@Override
	public int moveTo(int position) {
		serviceCalled = "moveTo";
		parameter = position;
		return position;
	}

	@Override
	public int liftTo(int position) {
		serviceCalled = "liftTo";
		parameter = position;
		return position;
	}

	public String getServiceCalled() {
		return serviceCalled;
	}

	public Object getParameter() {
		return parameter;
	}
}
