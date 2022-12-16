package pc.graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bus.uigen.shapes.AnImageModel;
import bus.uigen.shapes.OEShapeModel;
import pc.animations.AMovingPlateAnimator;
import pc.animations.AMovingPlateCommand;
import pc.animations.APlateAnimationCommand;
import pc.animations.APlateAnimator;
import pc.animations.MovingPlateAnimator;
import pc.animations.PlateAnimator;
import pc.controls.AController;
import pc.controls.ConsoleSceneView;
import pc.controls.Controller;
import pc.factories.AFactory;
import util.annotations.EditablePropertyNames;
import util.annotations.Label;
import util.annotations.ObserverRegisterer;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;
import util.misc.ThreadSupport;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({
	"patronList",
	"chefList",
	"buffer",
	"centerTable",
	"servedQueue"
})
@EditablePropertyNames({
})
@Label("Producer-Consumer Scene")
public class AProducerConsumerScene implements ProducerConsumerScene {
	List<APatron> patronList;
	List<AChef> chefList;
	BoundedBufferView bufferView;
	List<APlate> plateList;
	Queue<Integer> bufferQueue = new LinkedList<Integer>();
	Queue<Integer> chefQueue = new LinkedList<Integer>();
	Queue<Integer> patronQueue = new LinkedList<Integer>();
	Queue<Integer> serveOrder = new LinkedList<Integer>();
	
	
	//NewTable centerTable;
	
	int resetPatronNumber;
	int resetChefNumber;
	
	int maxAmountofAvatars;
	
	int Patron_X = 1000;
	int Chef_X = 50;
	int AvatarA_Y = 50;
	int AvatarB_Y = 200;
	int AvatarC_Y = 320;
	int AvatarD_Y = 450;
	
	String[] chefImages = {"images/0.png","images/1.png","images/4.png","images/3.png"};
	String[] patronImages = {"images/6.png", "images/7.png", "images/8.png", "", "", ""};
	String[] plateImages = {"images/12.png", "images/13.png", "images/14.png", "images/15.png", "images/16.png", "images/17.png", "images/18.png", "images/19.png", "images/20.png"};
	int[] chefXValues = {150,50,50,150};
	int[] chefYValues = {50,150,350,450};
	int[] patronXValues = {1000,1100,1100,1000};
	int[] patronYValues = {50,150,350,450};
	
	char zero = '0';
	char one = '1';
	char two = '2';
	char three = '3';
	char four = '4';
	char five = '5';
	char six = '6';
	char seven = '7';
	char eight = '8';
	char nine = '9';
	
	OEShapeModel testPlate;
	
	APlateAnimationCommand command;
	PlateAnimator animator;
	
	AMovingPlateCommand movingPlateCommand;
	MovingPlateAnimator movingPlateAnimator;
	
	boolean[] bufferOccupied = {false, false, false, false, false, false, false, false};
	
	int[] yValues = {AvatarA_Y, AvatarB_Y, AvatarC_Y, AvatarD_Y};
	
	List<PropertyChangeListener> propertyChangeListeners = new ArrayList<PropertyChangeListener>();
	
	public AProducerConsumerScene(int patronAmount, int chefAmount) {
	
		initScene(patronAmount, chefAmount);
		
	}
	
	public AProducerConsumerScene() {
		bufferView = new ABoundedBufferView();
		//centerTable = new ANewTable(420, 200, 450, 150);
		patronList = new ArrayList<APatron>();
		patronList.add(0, new APatron());
		patronList.get(0).move(patronXValues[0], patronYValues[0]);
		patronList.get(0).patron.getHead().setImageFileName(patronImages[0]);
		chefList = new ArrayList<AChef>();
		chefList.add(0, new AChef());
		chefList.get(0).move(chefXValues[0], chefYValues[0]);
		chefList.get(0).chef.getHead().setImageFileName(chefImages[0]);
		//testPlate = new AnImageModel("images/12.png");
		animator = new APlateAnimator();
		movingPlateAnimator = new AMovingPlateAnimator();
		//command = new APlateAnimationCommand(animator, chefList.get(0).chef.getPlate(), 39, 10);
	}
	
	@Label("Init Scene")
	public void initScene(int patronAmount, int chefAmount) {
		
		patronList.clear();
		chefList.clear();
		resetPatronNumber = patronAmount;
		resetChefNumber = chefAmount;
		for (int i=0; i<patronAmount; i++) { 
			APatron patron = new APatron();
			patronList.add(i, patron);
			patron.move(patronXValues[i], patronYValues[i]);
			patron.patron.getHead().setImageFileName(patronImages[i]);
			patron.patron.getArms().removeArms();
		}
		for (int i=0; i<chefAmount; i++) {
			AChef chef = new AChef();
			chefList.add(i, chef);
			chef.move(chefXValues[i], chefYValues[i]);
			chef.chef.getHead().setImageFileName(chefImages[i]);
		}
		
		ConsoleSceneView console = AFactory.ConsoleSceneViewFactoryMethod(chefAmount, patronAmount);
		Controller control = new AController(console);
	
	} 
	
//	@Override
//	public NewTable getCenterTable() {
//		return centerTable;
//	}
	
//	@Override
//	public StringShape getServedQueue() {
//		return servedQueue;
//	}
	
	@Override
	public List<APatron> getPatronList() {
		return patronList;
	}


	@Override
	public List<AChef> getChefList() {
		return chefList;
	}

	@Override
	public BoundedBufferView getBuffer() {
		return bufferView;
	}
	
	private int randomizeDish (int dish) {
		int min = 0;
		int max = 8;
		dish = (int) Math.floor(Math.random()*(max-min+1)+min);
		return dish;
	}
	
	@Override
	public synchronized void cook(Head aHead) {
		if (aHead.getImageFileName().charAt(7) == zero) {
			chefList.get(0).chef.getPlate().setImageFileName("images/12.png");
			chefList.get(0).chef.getPlate().setX(135);
			chefList.get(0).chef.getPlate().setY(85);
			chefList.get(0).chef.getPlate().setWidth(10);
			chefList.get(0).chef.getPlate().setHeight(10);
			command = new APlateAnimationCommand(animator, chefList.get(0).getChef().getPlate(), 39, 30);
			Thread thread = new Thread (command);
			thread.start();
			
		} else if (aHead.getImageFileName().charAt(7) == one) {
			chefList.get(1).chef.getPlate().setImageFileName("images/13.png");
			chefList.get(1).chef.getPlate().setX(36);
			chefList.get(1).chef.getPlate().setY(182);
			chefList.get(1).chef.getPlate().setWidth(10);
			chefList.get(1).chef.getPlate().setHeight(10);
			command = new APlateAnimationCommand(animator, chefList.get(1).getChef().getPlate(), 39, 30);
			Thread thread = new Thread (command);
			thread.start();
			
		} else if (aHead.getImageFileName().charAt(7) == four) {
			chefList.get(2).chef.getPlate().setImageFileName("images/15.png");
			chefList.get(2).chef.getPlate().setX(36);
			chefList.get(2).chef.getPlate().setY(390);
			chefList.get(2).chef.getPlate().setWidth(10);
			chefList.get(2).chef.getPlate().setHeight(10);
			command = new APlateAnimationCommand(animator, chefList.get(2).getChef().getPlate(), 39, 30);
			Thread thread = new Thread (command);
			thread.start();
			
		} else if (aHead.getImageFileName().charAt(7) == three) {
			chefList.get(3).chef.getPlate().setImageFileName("images/16.png");
			chefList.get(3).chef.getPlate().setX(137);
			chefList.get(3).chef.getPlate().setY(485);
			chefList.get(3).chef.getPlate().setWidth(10);
			chefList.get(3).chef.getPlate().setHeight(10);
			command = new APlateAnimationCommand(animator, chefList.get(3).getChef().getPlate(), 39, 30);
			Thread thread = new Thread (command);
			thread.start();
			
		}
	}
	
	@Override
	public synchronized void animatePlate(int avatar) {
//		int sleep = 500;
//		Thread a = new Thread();
//		a.start();
//		try {
//			a.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (int i=0; i<39; i++) {
//			chefList.get(avatar).chef.getPlate().setHeight(i);
//			chefList.get(avatar).chef.getPlate().setWidth(i);
//			ThreadSupport.sleep(sleep);
//		}
//		Thread.yield();
	}
	
	private void platePass(Plate aPlate) {
		
	}
	
	@Override
	@Label("Chef to the table")
	public void chefToTable(Head aHead) {
		int min = 0;
		int max = 8;
		int buffer = bufferView.getBoundedBuffer().get();
		int dish = (int) Math.floor(Math.random()*(max-min+1)+min);
		System.out.println(buffer);
		
		if (buffer == 0 && bufferOccupied[0] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 1 && bufferOccupied[1] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 2 && bufferOccupied[2] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 3 && bufferOccupied[3] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 4 && bufferOccupied[4] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 5 && bufferOccupied[5] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 6 && bufferOccupied[6] == true) {
			System.out.println("Table already occupied!");
		} else if (buffer == 7 && bufferOccupied[7] == true) {
			System.out.println("Table already occupied!");
		}
		
		if (buffer == 0 && bufferOccupied[0] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,520,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[0] = true;
				bufferQueue.add(0);
				chefQueue.add(0);
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,520,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[0] = true;
				bufferQueue.add(0);
				chefQueue.add(1);
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,520,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[0] = true;
				bufferQueue.add(0);
				chefQueue.add(2);
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,520,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[0] = true;
				bufferQueue.add(0);
				chefQueue.add(3);
			}
			bufferView.getBoundedBuffer().put(0);
			
		} else if (buffer == 1 && bufferOccupied[1] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,620,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[1] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,620,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[1] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,620,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[1] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,620,200);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[1] = true;
			}
			bufferView.getBoundedBuffer().put(1);
			
		} else if (buffer == 2 && bufferOccupied[2] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,695,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[2] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,695,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[2] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,695,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[2] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,695,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[2] = true;
			}
			bufferView.getBoundedBuffer().put(2);
			
		} else if (buffer == 3 && bufferOccupied[3] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,695,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[3] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,695,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[3] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,695,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[3] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,695,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[3] = true;
			}
			bufferView.getBoundedBuffer().put(3);
			
		} else if (buffer == 4 && bufferOccupied[4] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,620,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[4] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,620,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[4] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,620,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[4] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,620,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[4] = true;
			}
			bufferView.getBoundedBuffer().put(4);
			
		} else if (buffer == 5 && bufferOccupied[5] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,520,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[5] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,520,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[5] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,520,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[5] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,520,430);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[5] = true;
			}
			bufferView.getBoundedBuffer().put(5);
			
		} else if (buffer == 6 && bufferOccupied[6] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,450,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[6] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,450,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[6] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,450,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[6] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,450,380);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[6] = true;
			}
			bufferView.getBoundedBuffer().put(6);
			
		} else if (buffer == 7 && bufferOccupied[7] == false) {
			
			if (aHead.getImageFileName().charAt(7) == zero) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(0).getChef().getPlate(),4,450,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[7] = true;
			} else if (aHead.getImageFileName().charAt(7) == one) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(1).getChef().getPlate(),4,450,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[7] = true;
			} else if (aHead.getImageFileName().charAt(7) == four) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(2).getChef().getPlate(),4,450,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[7] = true;
			} else if (aHead.getImageFileName().charAt(7) == three) {
				movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,chefList.get(3).getChef().getPlate(),4,450,270);
				Thread thread = new Thread (movingPlateCommand);
				thread.start();
				bufferOccupied[7] = true;
			}
			bufferView.getBoundedBuffer().put(7);
			
		}
		
	}
	
	
	
	@Override
	public void serveDish(Plate aPlate) {
			int[] destX = {1070, 1170, 1180};
			int[] destY = {92, 190, 380};
			int chefNumber = chefQueue.peek();
			int patronNumber = patronQueue.peek();
			patronNumber = patronNumber-6;
			//System.out.println("This is the patronNumber: " + patronNumber);
			
			patronList.get(patronNumber).getPatron().getPlate().setHeight(aPlate.getHeight());
			patronList.get(patronNumber).getPatron().getPlate().setWidth(aPlate.getWidth());
			patronList.get(patronNumber).getPatron().getPlate().setX(aPlate.getX());
			patronList.get(patronNumber).getPatron().getPlate().setY(aPlate.getY());
			patronList.get(patronNumber).getPatron().getPlate().setImageFileName(aPlate.getImageFileName());
			
			chefList.get(chefNumber).getChef().getPlate().setHeight(0);
			chefList.get(chefNumber).getChef().getPlate().setWidth(0);
			
			movingPlateCommand = new AMovingPlateCommand(movingPlateAnimator,patronList.get(patronNumber).getPatron().getPlate(),4,destX[patronNumber],destY[patronNumber]);
			Thread thread = new Thread (movingPlateCommand);
			thread.start();
			patronQueue.poll();
	}

	public void order(Head aHead) {
		if (aHead.getImageFileName().charAt(7) == six) {
			int size = patronQueue.size();
			patronQueue.add(6);
			//size++;
			serveOrder.add(size);
			size++;
			String sizeString = String.valueOf(size);
			getPatronList().get(0).patron.getText().setText(sizeString);
		} else if (aHead.getImageFileName().charAt(7) == seven) {
			int size = patronQueue.size();
			patronQueue.add(7);
			//size++;
			serveOrder.add(size);
			size++;
			String sizeString = String.valueOf(size);
			getPatronList().get(1).patron.getText().setText(sizeString);
		} else if (aHead.getImageFileName().charAt(7) == eight) {
			int size = patronQueue.size();
			patronQueue.add(8);
			//size++;
			serveOrder.add(size);
			size++;
			String sizeString = String.valueOf(size);
			getPatronList().get(2).patron.getText().setText(sizeString);
		}
	}
	
	
	public void eat(Head aHead) {
		if (aHead.getImageFileName().charAt(7) == six) {
			command = new APlateAnimationCommand(animator, patronList.get(0).getPatron().getPlate(), -1, 30);
			Thread thread = new Thread (command);
			thread.start();
		}
	}
	public boolean preProcessFirstBufferChef() {
		return true;
	}
	
	public void processHeads(Head aHead) {
		System.out.println("Do head" + aHead.getImageFileName());
	}
	
	public boolean preProcessHeads() {
		return true;
	}
	
	@Override
	@Label("Reset")
	public void reset() {
		patronList.clear();
		chefList.clear();
		for (int i=0; i<resetPatronNumber; i++) { 
			APatron patron = new APatron();
			patronList.add(i, patron);
			patron.move(patronXValues[i], patronYValues[i]);
			patron.patron.getHead().setImageFileName(patronImages[i]);
			patron.patron.getArms().removeArms();
		}
		for (int i=0; i<resetChefNumber; i++) {
			AChef chef = new AChef();
			chefList.add(i, chef);
			chef.move(chefXValues[i], chefYValues[i]);
			chef.chef.getHead().setImageFileName(chefImages[i]);
		}
		for (int i=0; i<bufferOccupied.length; i++) {
			bufferOccupied[i] = false;
		}
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		for (int i = 0; i<patronList.size(); i++) {
			patronList.get(i).addPropertyChangeListener(patronList.get(i), arg0);
		}
		for (int i = 0; i<chefList.size(); i++) {
			chefList.get(i).addPropertyChangeListener(chefList.get(i), arg0);
		}
		//bufferView.addPropertyChangeListener(arg0);
	}

	
	
	
	
}
