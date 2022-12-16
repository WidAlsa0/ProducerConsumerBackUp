package pc.animations;

import pc.graphics.Plate;

public interface MovingPlateAnimator {

	public void animateMovingPlate(Plate aPlate, int pauseTime, int destX, int destY);
}
