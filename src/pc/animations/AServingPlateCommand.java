package pc.animations;

import pc.graphics.Plate;

public class AServingPlateCommand implements Runnable {

	ServingPlateAnimator animator;
	Plate plate;
	int pause;
	int x;
	int y;
	
	public AServingPlateCommand(ServingPlateAnimator thisAnimator, Plate thisPlate, int pauseTime, int destX, int destY) {
		animator = thisAnimator;
		plate = thisPlate;
		pause = pauseTime;
		x = destX;
		y = destY;
	}
	
	@Override
	public void run() {
		animator.animateServingPlate(plate, pause, x, y);
	}

	
}
