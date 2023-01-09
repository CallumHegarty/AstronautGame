import java.awt.*;

/**
 * Created by chegarty on 12/12/22.
 */
public class Cleat {

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

    public boolean right;
    public boolean down;
    public boolean up;
    public boolean left;


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Cleat(String pName, int pXpos, int pYpos) { // Cleat constructor
        name = pName;
        xpos = pXpos;
        //xpos = (int)(Math.random()*400+100);
        ypos = pYpos;
        //ypos = (int)(Math.random()*200+100);
        dx = 4;
        dy = 4;
        width = 90;
        height = 60;
        rec = new Rectangle(xpos,ypos,width,height);

    } // end Cleat constructor

    public void move() {

        if (right) {
            xpos = xpos + dx;
            if (xpos > 700 - width) {
                xpos = 700 - width;
            }
        }

        if (down) {
            ypos = ypos + dy;
            if (ypos > 500 - height) {
                ypos = 500 - height;
            }
        }

        if (up) {
            ypos = ypos - dy;
            if (ypos < 0) {
                ypos = 0;
            }
        }

        if (left) {
            xpos = xpos - dx;
            if (xpos < 0) {
                xpos = 0;
            }
        }
    }

    //used for green cleat
    //bounces on left side of field
    public void bounceLeft() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos >= 350-width) {
            dx = -dx;
        }
        if (xpos <= 0) {
            dx = -dx;
        }
        if(ypos >= 500-height && dy > 0) {
            dy = -dy;
        }
        if(ypos <= 0 && dy < 0) {
            dy = -dy;
        }
        rec = new Rectangle(xpos,ypos,width,height);

        //System.out.println("Green "+xpos + ", "+ ypos + "dx:"+ dx+" dy:" +dy);
    }

    //used for black cleat
    //bounces on right half of field
    public void bounceRight() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos >= 700-width) {
            dx = -dx;
        }
        if (xpos <= 350) {
            dx = -dx;
        }
        if(ypos >= 500-height && dy > 0) {
            dy = -dy;
        }
        if(ypos <= 0 && dy < 0) {
            dy = -dy;
        }
        rec = new Rectangle(xpos,ypos,width,height);

        //System.out.println("Black "+xpos + ", "+ ypos + "dx:"+ dx+" dy:" +dy);
    }

    public void wrap(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos>500+height && dy > 0){
            ypos = 0;
        }
        if (ypos<0-height && dy < 0){
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
