import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Object {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;           //a boolean to denote if the hero is alive or dead
    public Rectangle rec;


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Object(String pName, int pXpos, int pYpos) { // Astronaut constructor
        name = pName;
        xpos = pXpos;
        //xpos = (int)(Math.random()*400+100);
        ypos = pYpos;
        //ypos = (int)(Math.random()*200+100);
        dx = -5;
        dy = 0;
        width = 60;
        height = 60;
        isAlive = true;
        rec = new Rectangle(xpos,ypos,width,height);
 
    } // end Astronaut constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;

    } // end move

    public void bounce() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos >= 700-width) {
            dx = -dx;
        }
        if (xpos <= 0) {
            dx = -dx;
        }
        if(ypos >= 500-height) {
            dy = -dy;
        }
        if(ypos <= 0) {
            dy = -dy;
        }
        rec = new Rectangle(xpos,ypos,width,height);
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