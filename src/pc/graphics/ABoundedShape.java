package pc.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.models.PropertyListenerRegisterer;

@PropertyNames({
	"x",
	"y",
	"width",
	"height",
	"ZIndex"
	})
@EditablePropertyNames({
	"width",
	"height",
	"x",
	"y",
	"ZIndex"
})
public class ABoundedShape extends ALocatable implements BoundedShape, PropertyListenerRegisterer {

	int width, height;
	int zIndex;
	
	public ABoundedShape() {
		
	}
		
	public ABoundedShape(int initX, int initY, int initWidth, int initHeight) {
		x = initX;
		y = initY;
		width = initWidth;
		height = initHeight;
	}
	
	@Override
	public int getZIndex() {
		return zIndex;
	}
	
	@Override
	public void setZIndex(int newZ) {
		int oldZ = zIndex;
		zIndex = newZ;
		
		propertySupport.notifyAllListeners(new PropertyChangeEvent(this, "ZIndex", oldZ, zIndex));
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public synchronized void setHeight(int NewHeight) {
		int oldHeight = height;
		height = NewHeight;
		
		propertySupport.notifyAllListeners(new PropertyChangeEvent(this, "height", oldHeight, height));
	}

	@Override
	public synchronized void setWidth(int NewWidth) {
		int oldWidth = width;
		width = NewWidth;
		
		propertySupport.notifyAllListeners(new PropertyChangeEvent(this, "width", oldWidth, width));
	}
	
}
