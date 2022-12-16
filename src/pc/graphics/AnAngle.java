package pc.graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import bus.uigen.shapes.ALineModel;
import bus.uigen.shapes.OEShapeModel;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.models.PropertyListenerRegisterer;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({
	"LeftLine",
	"RightLine"
})
@EditablePropertyNames({
	
})
public class AnAngle implements Angle {
	int INIT_X = 50;
	int INIT_Y = 50;
	int INIT_Radius = 50;
	int LeftRotate = 2;
	//int RightRotate = -5;
	int RightRotate = 8;
	
	
	Line LeftLine, RightLine;
	//OEShapeModel LeftLine, RightLine;
	
	public AnAngle() {
		LeftLine = new ALine(INIT_X, INIT_Y, INIT_Radius, LeftRotate);
		RightLine = new ALine(INIT_X, INIT_Y, INIT_Radius, RightRotate);
//		LeftLine = new ALineModel();
//		RightLine = new ALineModel();
//		LeftLine.setX(INIT_X);
//		LeftLine.setY(INIT_Y);
//		LeftLine.setRadius(INIT_Radius);
//		LeftLine.setAngle(LeftRotate);
		
//		RightLine.setX(INIT_X);
//		RightLine.setY(INIT_Y);
//		RightLine.setRadius(INIT_Radius);
//		RightLine.setAngle(RightRotate);
	}
	
	@Override
	public Line getLeftLine() {
		return LeftLine;
	}

	@Override
	public Line getRightLine() {
		return RightLine;
	}

	@Override
	public void move(int MoveX, int MoveY) {
		LeftLine.setX(LeftLine.getX()+MoveX);
		LeftLine.setY(LeftLine.getY()+MoveY);
		RightLine.setX(RightLine.getX()+MoveX);
		RightLine.setY(RightLine.getY()+MoveY);
	}
	
	public void moveTo(int newX, int newY) {
		LeftLine.setX(newX);
		RightLine.setX(newX);
		LeftLine.setY(newY);
		RightLine.setY(newY);
	}
	
	@Override
	public void moveX(int NewX) {
		LeftLine.setX(NewX);
		RightLine.setX(NewX);
	}
	
	@Override
	public void moveY(int NewY) {
		LeftLine.setY(NewY);
		RightLine.setY(NewY);
	}
	
	@Override
	public void removeArms() {
//		LeftLine.setHeight(0);
//		LeftLine.setWidth(0);
//		LeftLine.setRadius(0);
		RightLine.setHeight(0);
		RightLine.setWidth(0);
		RightLine.setRadius(0);
	}
	
	@Override
	public void addPropertyChangeListener(Angle angleParameter, PropertyChangeListener aListener) {
		angleParameter.getLeftLine().addPropertyChangeListener(aListener);
		angleParameter.getRightLine().addPropertyChangeListener(aListener);
	}
}
