import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Object {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;               //name
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //speed in the x direction
    public int dy;                    //speed in the y direction
    public int width;                 //the width of the image
    public int height;                //the height of the image
    public boolean isCrashing = false;//helps control collision repetition
    public Rectangle rec;             //hit box


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Object(String pName, int pXpos, int pYpos) { // Object constructor
        name = pName;
        xpos = pXpos;
        //xpos = (int)(Math.random()*400+100);
        ypos = pYpos;
        //ypos = (int)(Math.random()*200+100);
        dx = -10;
        dy = 5;
        width = 60;
        height = 60;
        rec = new Rectangle(xpos,ypos,width,height);
 
    } // end Object constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;

    } // end move

    //makes object bounce off walls
    public void bounce() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos >= 700-width) {
            dx = -dx;
        }
        if (xpos <= 0) {
            dx = -dx;
        }
        if(ypos > 500-height && dy > 0) {
            dy = -dy;
        }
        if(ypos < 0 && dy < 0) {
            dy = -dy;
        }
        rec = new Rectangle(xpos,ypos,width,height);
        //System.out.println("ball "+xpos + ", "+ ypos + " dx:"+ dx+" dy:" +dy);
    }

    public void wrap(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos>=500+height && dy > 0){
            ypos = 0;
        }
        if (ypos<=0-height && dy < 0){
            ypos = 500;
        }
        if (xpos>=700 && dx > 0){
            xpos = 0;
        }
        if (xpos<=0-width && dx < 0){
            xpos = 700;
        }
        rec = new Rectangle(xpos,ypos,width,height);
    }
}