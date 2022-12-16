package pc.animations;
import bus.uigen.shapes.OEShapeModel;
import pc.graphics.Plate;

public class AMovingPlateCommand implements Runnable {

	MovingPlateAnimator animator;
	Plate plate;
	int pause;
	int x;
	int y;
	
	public AMovingPlateCommand(MovingPlateAnimator thisAnimator, Plate thisPlate, int pauseTime, int destX, int destY) {
		animator = thisAnimator;
		plate = thisPlate;
		pause = pauseTime;
		x = destX;
		y = destY;
	}

	@Override
	public void run() {
		//System.out.println("Made it inside the run method of a moving plate!!!");
		animator.animateMovingPlate(plate, pause, x, y);
	}
	
	
	
}
