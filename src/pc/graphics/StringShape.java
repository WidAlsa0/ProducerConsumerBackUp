package pc.graphics;

public interface StringShape extends Locatable {

	void setX(int NewX);
	void setY(int NewY);
	String getText();
	void setText(String NewText);
	void move(int newX, int newY);
}
