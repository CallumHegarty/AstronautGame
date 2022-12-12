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

	//Sets the width and height of the program window
	final int WIDTH = 700;
	final int HEIGHT = 500;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
	public JPanel panel;

	public BufferStrategy bufferStrategy;

	public Image ballPic;
	public Image cleat1Pic;
	public Image cleat2Pic;
	public Image backgroundPic;
	public Image crownPic;

	//Declare the objects used in the program
	public Object ball;
	public Cleat cleat1;
	public Cleat cleat2;

	//sound Effects
	public SoundFile goalSound;
	public SoundFile crowdCheering;
	public SoundFile kickSound;

	// Used in goal() method to add to and display score
	public Rectangle greenGoalRect;
	public Rectangle blackGoalRect;
	int greenScore = 0;
	int blackScore = 0;

	public boolean gameOver = false;
	public String gameWinner;

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
		ball = new Object("ball",(int)((Math.random()*400)+100),200); //construct the astronaut

		cleat1Pic = Toolkit.getDefaultToolkit().getImage("soccerCleat.png");
		cleat1 = new Cleat("cleat1",35,220);

		cleat2Pic = Toolkit.getDefaultToolkit().getImage("soccerCleatBlack.png");
		cleat2 = new Cleat("cleat2",400,400);
		cleat2.width = 80;

		backgroundPic = Toolkit.getDefaultToolkit().getImage("soccerField.jpg");

		crownPic = Toolkit.getDefaultToolkit().getImage("crown.png");

		goalSound = new SoundFile("Doooh Reaction Male.wav");
		crowdCheering = new SoundFile("Crowd Laughing 02.wav");
		kickSound = new SoundFile("Flashlight Turned On 01.wav");

	} // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

	// main thread
	// this is the code that plays the game after you set things up
	public void run() {

		//loops forever
		while (true) {
			if(gameOver == false) {
				moveThings(); //move all the game objects
				kick(); //bounces happen
				goal();    //keeping track of goals and score
				gameEnd();
			}
			render();  //paint the graphics
			pause(20);	//sleep for 10 ms

		}

		//if(gameOver == true)
	}

	public void moveThings() {
		//calls the bounce codes in the objects
		ball.bounce();
		cleat1.bounceLeft();
		cleat2.bounceRight();
	}

	public void kick() {

		// make the cleats shoot the ball
		if(ball.rec.intersects(cleat1.rec) && cleat1.isCrashing == false){
			kickSound.play();
			cleat1.isCrashing = true;
			ball.dx = -ball.dx;
			//cleat1.dx = -cleat1.dx;
		}
		if(!ball.rec.intersects(cleat1.rec)){
			cleat1.isCrashing = false;
		}

		if(ball.rec.intersects(cleat2.rec) && cleat2.isCrashing == false){
			kickSound.play();
			cleat2.isCrashing = true;
			ball.dx = -ball.dx;
			//cleat2.dx = -cleat2.dx;
		}
		if(!ball.rec.intersects(cleat2.rec)){
			cleat2.isCrashing = false;
		}


		// all these fix some moments where the objects would bounce repeatedly and crazy

		if(ball.rec.intersects(cleat2.rec)&&(cleat2.dy<0)&&(ball.dy>0)){
			ball.dy = -ball.dy;
			cleat2.dy = -cleat2.dy;
		}
		if(ball.rec.intersects(cleat2.rec)&&(cleat2.dy>0)&&(ball.dy<0)){
			ball.dy = -ball.dy;
			cleat2.dy = -cleat2.dy;
		}
		if(ball.rec.intersects(cleat1.rec)&&(cleat1.dy>0)&&(ball.dy<0)){
			ball.dy = -ball.dy;
			cleat1.dy = -cleat1.dy;
		}
		if(ball.rec.intersects(cleat1.rec)&&(cleat1.dy<0)&&(ball.dy>0)){
			ball.dy = -ball.dy;
			cleat1.dy = -cleat1.dy;
		}
	}

	public void goal(){

		// Draws goal hitboxes
		greenGoalRect= new Rectangle(35,220,22,60);
		blackGoalRect= new Rectangle(645,219,22,61);

		//Goals for Black Team
		if(ball.rec.intersects(greenGoalRect)&&(ball.isCrashing == false)){
			goalSound.play();
			crowdCheering.play();
			ball.isCrashing = true;
			System.out.println("Black Goal!");
			blackScore = blackScore + 1;
			System.out.println("Black " + blackScore + " - " + greenScore + " Green");
			ball.xpos = 350 - ball.width/2;
			ball.ypos = 250 - ball.height/2;
		}

		//Goals for Green Team
		if(ball.rec.intersects(blackGoalRect)&&(ball.isCrashing == false)){
			goalSound.play();
			crowdCheering.play();
			ball.isCrashing = true;
			System.out.println("Green Goal!");
			greenScore = greenScore + 1;
			System.out.println("Black " + blackScore + " - " + greenScore + " Green");
			ball.xpos = 350 - ball.width/2;
			ball.ypos = 250 - ball.height/2;
		}

		//isCrashing management, stops the things from bouncing repeatedly and crazily every collision
		if(!ball.rec.intersects(greenGoalRect)&&!ball.rec.intersects(blackGoalRect)){
			ball.isCrashing = false;
		}
	}

	public void gameEnd(){
		//Win messages and reset cleats
		gameWinner = "null";
		if(blackScore >= 3){
			gameWinner = "black";
			gameOver = true;
			cleat1.xpos = 200;
			cleat1.ypos = 250;
			cleat2.xpos = 500-cleat2.width;
			cleat2.ypos = 250;
			System.out.println("BLACK WINS");
		}
		if(greenScore >= 3){
			gameWinner = "green";
			gameOver = true;
			cleat1.xpos = 200;
			cleat1.ypos = 250;
			cleat2.xpos = 500-cleat2.width;
			cleat2.ypos = 250;
			System.out.println("GREEN WINS");
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

		//draws background
		g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);


		//draws the images of the characters

		g.drawImage(ballPic, ball.xpos, ball.ypos, ball.width, ball.height, null);
		//g.drawRect(ball.rec.x, ball.rec.y, ball.rec.width, ball.rec.height);

		g.drawImage(cleat1Pic, cleat1.xpos, cleat1.ypos, cleat1.width, cleat1.height, null);
		//g.drawRect(cleat1.rec.x, cleat1.rec.y, cleat1.rec.width, cleat1.rec.height);

		g.drawImage(cleat2Pic, cleat2.xpos, cleat2.ypos, cleat2.width, cleat2.height, null);
		//g.drawRect(cleat2.rec.x, cleat2.rec.y, cleat2.rec.width, cleat2.rec.height);

		//goals:
		//g.drawRect(35,220,22,60);
		//g.drawRect(645,219,22,61);

		Font scoreFont = new Font("Courier", Font.BOLD, 30);
		g.setFont(scoreFont);
		g.setColor(Color.WHITE);
		//displays score
		g.drawString(greenScore + "  --  " + blackScore, 305, 70);

		if(gameWinner == "black"){
			g.drawImage(crownPic, cleat2.xpos, cleat2.ypos-40, 100, 50, null);
		}

		if(gameWinner == "green"){
			g.drawImage(crownPic, cleat1.xpos, cleat1.ypos-40, 100, 50, null);
		}


		g.dispose();
		bufferStrategy.show();
	}
}