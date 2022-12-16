package pc.graphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.OVAL_PATTERN)
@PropertyNames({ "X", "Y", "Width", "Height", "Color", "ZIndex", "Filled", "PropertyChangeListeners" })
public class ANewTable extends ABoundedShape implements NewTable {
	
	public ANewTable(int initX, int initY, int initWidth, int initHeight) {
		super(initX, initY, initWidth, initHeight);
	}
	public Color getColor() {
		return Color.DARK_GRAY;
	}
	public boolean isFilled() {
		return true;
	}
	// doesn't work yet
	public Stroke getStroke() {
		return new BasicStroke(20.0F);
	}
}
