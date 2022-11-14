//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

	//Variable Definition Section
	//Declare the variables used in the program
	//You can set their initial values too

	//Sets the width and height of the program window
	final int WIDTH = 700;
	final int HEIGHT = 500;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
	public JPanel panel;

	public BufferStrategy bufferStrategy;

	public Image ballPic;
	public Image cleatPic;

	//Declare the objects used in the program
	//These are things that are made up of more than one variable type
	public Object ball;
	public Object cleat;

	// Main method definition
	// This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


	// This section is the setup portion of the program
	// Initialize your variables and construct your program objects here.
	public BasicGameApp() { // BasicGameApp constructor

		setUpGraphics();

		//variable and objects
		//create (construct) the objects needed for the game and load up
		ballPic = Toolkit.getDefaultToolkit().getImage("soccerBall.png"); //load the picture
		ball = new Object("ball",100,400); //construct the astronaut

		cleatPic = Toolkit.getDefaultToolkit().getImage("soccerCleat.png");
		cleat = new Object("cleat",400,250);

	} // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

	// main thread
	// this is the code that plays the game after you set things up
	public void run() {

		//for the moment we will loop things forever.
		while (true) {
			moveThings(); //move all the game objects
			crash();
			render();  //paint the graphics
			pause(20); //sleep for 10 ms
		}
	}

	public void moveThings() {
		//calls the move( ) code in the objects
		ball.bounce();
		cleat.wrap();
	}

	public void crash() {
		if(ball.rec.intersects(cleat.rec)){
			ball.dx = cleat.dx;
			ball.dy = cleat.dy;
			ball.xpos = cleat.xpos+ball.width;

		}
	}

	//Pauses or sleeps the computer for the amount specified in milliseconds
	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	//Graphics setup method
	private void setUpGraphics() {
		frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

		panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
		panel.setLayout(null);   //set the layout

		// creates a canvas which is a blank rectangular area of the screen onto which the application can draw
		// and trap input events (Mouse and Keyboard events)
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);  // adds the canvas to the panel.

		// frame operations
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
		frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
		frame.setResizable(false);   //makes it so the frame cannot be resized
		frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

		// sets up things so the screen displays images nicely.
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		System.out.println("DONE graphic setup");
	}

	//Paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		//draw the image of the astronaut
		if(ball.isAlive == true) {
			g.drawImage(ballPic, ball.xpos, ball.ypos, ball.width, ball.height, null);
			g.drawRect(ball.rec.x, ball.rec.y, ball.rec.width, ball.rec.height);
		}
		g.drawImage(cleatPic, cleat.xpos, cleat.ypos, cleat.width, cleat.height, null);

		g.drawRect(cleat.rec.x, cleat.rec.y, cleat.rec.width, cleat.rec.height);

		g.dispose();
		bufferStrategy.show();
	}
}