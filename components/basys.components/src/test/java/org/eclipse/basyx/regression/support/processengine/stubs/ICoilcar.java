package org.eclipse.basyx.regression.support.processengine.stubs;

public interface ICoilcar {
	/**
	 * a service that moves the coil-car to the expected position
	 * @param position expected position
	 * */
	public int moveTo(int position);
	
	/**
	 * a service that rises the lifter of the coil-car to the expected position
	 * @param position expected position
	 * */
	public int liftTo(int position);
}
