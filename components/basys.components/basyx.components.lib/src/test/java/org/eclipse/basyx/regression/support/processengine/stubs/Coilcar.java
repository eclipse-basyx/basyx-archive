package org.eclipse.basyx.regression.support.processengine.stubs;


public class Coilcar implements ICoilcar {
	private int currentPosition = 0;
	private int currentLifterPosition = 0;
	
	
	@Override
	public int moveTo(int position) {
		System.out.printf("#submodel# invoke service +MoveTo+ with parameter: %d \n\n", position);
		Double steps[] =  generateCurve(currentPosition,  position);
		for(Double step : steps) {
			System.out.println(step);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentPosition = position;
		return currentPosition;
	}

	@Override
	public int liftTo(int position) {
		System.out.printf("#submodel# Call service LiftTo with Parameter: %d \n\n", position);
		Double steps[] =  generateCurve(currentLifterPosition,  position);
		for(Double step : steps) {
			System.out.println(step);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		currentLifterPosition = position;
		return currentLifterPosition;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public int getCurrentLifterPosition() {
		return currentLifterPosition;
	}
	
	
	private Double[] generateCurve(double current, double goal){
		Double stepList[] = new Double[20];
		double delta = (goal-current)/20;
		for(int i= 0; i< 20; i++) {
			stepList[i]= current+delta*(i+1);
		}
		return stepList;
	}
	
}
