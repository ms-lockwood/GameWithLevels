import java.awt.*;

public class Meteor {

    //variable declaration section
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public Image pic;
    //movement booleans
    public boolean rightPressed;
    public boolean leftPressed;
    public boolean upPressed;
    public boolean downPressed;
    // Gravity component
    public static final double GRAVITY = 0.1; // You can adjust this value as needed


    //DO NOW: ADD SPEED as parameters in this constructor
    public Meteor(int pXpos, int pYpos, int pDx, int pDy){
        xpos=pXpos;
        ypos=pYpos;
        dx=pDx;
        dy=pDy;
        width=30;
        height=30;
        isAlive=true;
        rec = new Rectangle(xpos, ypos, width,height);
    }
    public Meteor(){
        xpos=100;
        ypos=200;
        dx=2;
        dy=3;
        width=60;
        height=80;
        isAlive=true;
    }

    public void killRock(){
        isAlive=false;
        dx=0;
        dy=0;
        ypos=-1000;
    }

    public void bouncingMove(){
        //4 seperate if statements.
        if(xpos>(1100-width) ||xpos<-100){
            dx=-dx;
        }

        if(ypos>(800-height)){
            dy=-dy;
        }
        if(ypos<-100){
            dy=-dy;
        }

        //the two lines of code below actually update the position
        //this is what makes the object move.
        xpos=xpos+ dx;
        ypos= ypos+ dy;
        rec = new Rectangle(xpos, ypos, width,height);

    }
    public void wrappingMove(){
        //4 seperate if statements, one for each wall
        if(xpos>1000){
            xpos=0;
        }
        if(xpos<0){
            xpos=1000;
        }
        if(ypos>700){
            ypos=0;
        }
        if (ypos<0){
            ypos=1000;
        }
        //the two lines of code below actually update the position
        //this is what makes the object move.
        xpos=xpos+ dx;
        ypos= ypos+ dy;
        //this updates the rectangle location
        rec = new Rectangle(xpos, ypos, width,height);

    }


    //make a printInfo() method
    public void printInfo(){
        //System.out.println("X position: " + xpos);
        //OR
        System.out.println("(x,y): ("+xpos+", "+ypos+")" );
        System.out.println("x speed: "+ dx);
        System.out.println("y speed: "+ dy);
        System.out.println("width: "+ width);
        System.out.println("height: "+height);
        System.out.println("isAlive: "+ isAlive);
    }




}
