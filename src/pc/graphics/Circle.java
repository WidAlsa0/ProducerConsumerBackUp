package pc.graphics;

import java.awt.Color;

public interface Circle extends BoundedShape {

//	public int getHeight();
//	public int getWidth();
//	public void setHeight();
//	public void setWidth();
	public int getX();
	public int getY();
	public void setX(int newX);
	public void setY(int newY);
	public Color getColor();
	public boolean isFilled();
}
