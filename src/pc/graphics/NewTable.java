package pc.graphics;
import java.awt.Color;
import java.awt.Stroke;
public interface NewTable extends BoundedShape {
	public Color getColor();
	public boolean isFilled();
	public Stroke getStroke();
	
}
