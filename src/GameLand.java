//Game Example
//Lockwood Version 2023-24
// Learning goals:
/**Use booleans to determine game start, game over and level screen
 * This also has a function user control and array collisions example
 * This also has a functional timer
 * STEP 0 declare booleans for each screen/level
 * */

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable, KeyListener {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    //Declare the objects and arrays used in the program below
    public Hero astro;
    public Meteor[] rocks;

    public Image astroPic;
    public Image backgroundPic1, backgroundPic2;
    public Image rockPic;

    /**STEP 0 Declare booleans for start screen, levels and game over */
    public boolean startScreen1=true;
    public boolean level1;
    public boolean startScreen2;
    public boolean level2;
    public boolean gameOver;

    public int score;

    //declare time variables
    public long startTime;
    public long currentTime;
    public long elapsedTime;

    // Main method definition: PSVM
    public static void main(String[] args) {
        GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method
/** In order to avoid having our game start immeadiatly upon pressing run,
 * take out the object constructors, and put them in the relevant
 * startLevel1() etcs... methods*/
        //declare images
        astroPic= Toolkit.getDefaultToolkit().getImage("astroPic.png");
        rockPic = Toolkit.getDefaultToolkit().getImage("rock.png");
        backgroundPic1 = Toolkit.getDefaultToolkit().getImage( "spacePic.jpeg");
        backgroundPic2 = Toolkit.getDefaultToolkit().getImage("spacePic2.png");

    }// GameLand()

    //*******************************************************************************
//User Method Section
// put your code and methods to do things here.
    public void runCorrectLevel(){
        /** This method gets called when you click the space bar
         * It will DECIDE WHICH LEVEL METHODS TO CALL BASED ON BOOLEANS */
        if(startScreen1){ // transition from startscreen to level 1
            startScreen1=false;
            level1=true;
            startLevel1();
           // System.out.println("level 1 has started");
        }
        if(level1 && score>14){ // transition from level 1 to pause screen
            level1=false;
            startScreen2=true;
        }
        if (startScreen2){//transition from pause screen to level 2
            startScreen2=false;
            level2=true;
            startLevel2();
           // System.out.println("level 2 has started");
       }
        if(gameOver){ //restart the game transition from game over to level 1
            gameOver=false;
            level1=true;
            startLevel1();
        }
    }
    public void startLevel1(){
        /**create (construct) the objects needed for level 1 below*/
        astro = new Hero(400, 500, 2, 0);
        System.out.println("i made astro");
        //construct my array of meteors
        rocks = new Meteor[28];
        for (int i = 0; i < rocks.length; i = i + 1) {
            int randX = (int) (Math.random() * 1000);
            int randY = (int) (Math.random() * 700);
            int randDx = (int) (Math.random()*5+1);
            int randDy = (int) (Math.random()*5+1);
            rocks[i] = new Meteor(randX, randY, randDx, randDy);
        }
        System.out.println("i made my rocks");
        //reset start time
        startTime=System.currentTimeMillis();
        //reset score
        score=0;
    }
    public void startLevel2(){
        /** create (construct) the objects needed for level 2 below */
        //construct new array of rocks by writing over the old array
        rocks = new Meteor[40];
        for (int i = 0; i < rocks.length; i = i + 1) {
            int randX = (int) (Math.random() * 1000);
            int randY = (int) (Math.random() * 700);
            int randDx= (int) (Math.random() * 6)-3;
            int randDy= (int) (Math.random() * 6)-3;
            rocks[i] = new Meteor(randX, randY, randDx, randDy);
            rocks[i].height=60;
            rocks[i].width=60;
        }
        System.out.println("i made my BIG rocks");
        //reset start time
        startTime=System.currentTimeMillis();
        //reset score
        score=0;
    }
    public void timer(){
        //get the current time
        currentTime = System.currentTimeMillis();
        //calculate the elapsed time, convert it to seconds and cast as an int
        elapsedTime=(int)((currentTime-startTime)*.001); // *.001 to convert to seconds

    }
    public void moveThings() {
        //call the move() method code from your object class
        /**Avoid Null pointer exceptions*/
        if(astro!=null){
            astro.move();}
        if(rocks!=null) {
            for (int i = 0; i < rocks.length; i = i + 1) {
                rocks[i].bouncingMove();
            }
        }
    }
    public void collisions(){
        if(astro!=null && rocks!=null) {
            for(int i=0;i< rocks.length; i=i+1) {
                if (rocks[i].rec.intersects(astro.rec)) {
                    rocks[i].killRock();
                    score=score+1;
                }
            }
        }
        if(level1 && score>14){
            level1=false;
            startScreen2=true;
        }
        if(level2 && score>30){
            level2=false;
            gameOver=true;
            // startScreen3=true;
        }

    }



    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        //Anything that happens continuously throughout the game is called here
        while (true) {
            timer();
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
       g.clearRect(0, 0, WIDTH, HEIGHT);

       /** USE IF CONDITIONS AROUND WHAT IMAGES TO RENDER */
       if (startScreen1){
           //include start screen here
           g.drawString("press space bar to begin", 400, 300);
       }

       if(level1) {
           /**draw background and images and words for level 1
            * in order to avoid Null Point errors, you need to include
            * if (objects!= null) conditionals around objects */

           g.drawImage(backgroundPic1, 0, 0, WIDTH, HEIGHT, null);
            g.setColor(Color.WHITE);
           g.drawString("score: "+ score, 100, 60);
           g.drawString("time: " + elapsedTime, 900, 60);
           //draw the image of your objects below:
           if (astro != null) {
               g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
           }
           if (rocks!=null) {
               for (int i = 0; i < rocks.length; i = i + 1) {
                   if(rocks[i].isAlive==true) {
                       g.drawImage(rockPic, rocks[i].xpos, rocks[i].ypos, rocks[i].width, rocks[i].height, null);
                   }
               }
           }
       }
       if(startScreen2){
           //draw image for pause screen between levels
           g.drawString("press space bar for level 2", 400, 350);
       }
       if (level2){
           /**draw new background and new objects if applicable
            * again, include if( objects != null) conditionals */
           g.drawImage(backgroundPic2, 0, 0, WIDTH, HEIGHT, null);
           g.setColor(Color.WHITE);
           g.drawString("score: "+ score, 100, 60);
           g.drawString("time: " + elapsedTime, 900, 60);
           //draw the image of your objects below:
           if (astro != null) {
               g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
           }
           if (rocks!=null) {
               for (int i = 0; i < rocks.length; i = i + 1) {
                   g.drawImage(rockPic, rocks[i].xpos, rocks[i].ypos, rocks[i].width, rocks[i].height, null);
               }
           }
       }
       if(gameOver){
           //draw cool game over screen image here
           g.drawString("the game is over", 400,350);
           g.drawString("press space bar to restart", 400,400);
       }

       //these two lines have to stay at the bottom of the render!
        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();
        bufferStrategy.show();
    }


    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);
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


    @Override
    public void keyTyped(KeyEvent e) {
        //probably will stay empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key: "+ key+ ", KeyCode: "+ keyCode);


        //USER CONTROLS BELOW
        if(keyCode==68){// d is 68 // right movement
            astro.rightPressed=true;
        }
        if (keyCode==65){//a is 65
            astro.leftPressed=true;
        }
        if(keyCode==87){ //w is 87
            astro.upPressed=true;
        }
        if(keyCode==83){ //s is 83
            astro.downPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
       /**CONNECT SPACE BAR KEY TO OUR LEVEL BOOLEAN TRANSITIONS */
        if(keyCode==32){ //32 is space bar
          runCorrectLevel();
        }

       //USER CONTROL BELOW
        if(keyCode==68){// d is 68 // right movement
            astro.rightPressed=false;
        }
        if (keyCode==65){
            astro.leftPressed=false;
        }
        if(keyCode==87){
            astro.upPressed=false;
        }
        if(keyCode==83){
            astro.downPressed=false;
        }

    }
}