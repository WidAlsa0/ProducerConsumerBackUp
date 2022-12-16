package pc.animations;

import pc.graphics.Plate;
import util.misc.ThreadSupport;

public class AMovingPlateAnimator implements MovingPlateAnimator {
	
	public AMovingPlateAnimator() {
		
	}
	
	public void animateMovingPlate(Plate aPlate, int pauseTime, int destX, int destY) {
		
		boolean alt = false;
		
		if (aPlate.getY() < destY) {
			
			while (aPlate.getX()!=destX || aPlate.getY()!=destY) {
				
				if (aPlate.getX()!=destX) {
					aPlate.setX(aPlate.getX()+1);
				}
				
				if (aPlate.getY()!=destY && alt == true) {
					aPlate.setY(aPlate.getY()+1);
				}
				
				ThreadSupport.sleep(pauseTime);
				alt = !alt;
			}
			
		} else {
			
			while (aPlate.getX()!=destX || aPlate.getY()!=destY) {
				
				if (aPlate.getX()!=destX) {
					aPlate.setX(aPlate.getX()+1);
				}
				
				if (aPlate.getY()!=destY && alt == true) {
					aPlate.setY(aPlate.getY()-1);
				}
				
				ThreadSupport.sleep(pauseTime);
				alt = !alt;
			}
			
			
		}

		
		
	}

}
