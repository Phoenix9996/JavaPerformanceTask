import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import java.util.Random;
import java.util.Stack;

public class GamePanel extends JPanel implements ActionListener{
	private String binaryValue;
	private static final int SCREEN_WIDTH = 192;
	private static final int SCREEN_HEIGHT = 192;
	private static final int UNIT_SIZE = 16;
	private static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	private static final int DELAY = 170;
	private final int x[] = new int[GAME_UNITS];
	private final int y[] = new int[GAME_UNITS];
	private Image apple;
	private Image zero;
	private Image one;
	private int bodyParts = 1;
	private int applesEaten;
	private int appleX;
	private int appleY;
	private boolean running = false;
	private Timer timer;
	private Random random;
	private GamePage gp;
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	private boolean moved;
	GamePanel(GamePage gp){
		applesEaten=1;
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		apple = Toolkit.getDefaultToolkit().getImage("apple.png"); 
		zero = Toolkit.getDefaultToolkit().getImage("zero.png"); 
		one = Toolkit.getDefaultToolkit().getImage("one.png"); 
		binaryValue="1";
		this.gp=gp;
		startGame();
		
	}
	//checks if the game is running
	public boolean isRunning()
	{
		return running;
	}
	//converts a number in base 10 to binary using stacks
	 public String toBinary(int num)
	  {
	    if(num==0) return "0";
	    Stack<Integer> d = new Stack<Integer>();
	    
	     while (num != 0)
	    {
	      int n = num % 2;
	      d.push(n);
	      num /= 2;
	    } 
	    String s = "";
	    while(!d.isEmpty())
	    {
	      s = s+d.pop();
	    }
	    return s;
	  
	  }
	 //runs the game
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	//paints game
	public void draw(Graphics g) {
		
		if(running) {
			
			
			
			g.drawImage(apple,appleX,appleY,null);
			
			
			
			for(int i = 0; i< bodyParts;i++) {
			String number = binaryValue.substring(i,i+1);
			if(number.equals("1"))
			{
				g.drawImage(one,x[i],y[i],null);
			}
			else g.drawImage(zero,x[i],y[i],null);
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			
			 Toolkit.getDefaultToolkit().sync();
		}
		else
		{
			gp.addScore(applesEaten);
			gp.getEnd(applesEaten);

		}
		
	}
	//creates a new apple
	public void newApple(){
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	//moves the snake
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if(up) y[0] = y[0] - UNIT_SIZE;
		if(down) y[0] = y[0] + UNIT_SIZE;
		if(left) x[0] = x[0] - UNIT_SIZE;
		if(right) x[0] = x[0] + UNIT_SIZE;
		moved = true;
		
		
	}
	//checks if apple has been eaten
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			
			applesEaten++;
			if(toBinary(applesEaten).length()>binaryValue.length())
			{
				bodyParts++;
			}
			binaryValue = toBinary(applesEaten);
			newApple();
		}
	}
	//checks if snake has killed itself
	public void checkCollisions() {
	
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])&&(i!=1)) {
				running = false;
				
			}
		}
	
		if(x[0] < 0) {
			running = false;
		}

		if(x[0] > SCREEN_WIDTH-UNIT_SIZE) {
			running = false;
		}
	
		if(y[0] < 0) {
			running = false;
		}
		
		if(y[0] > SCREEN_HEIGHT-UNIT_SIZE) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	//checks keyboard input
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			 int key = e.getKeyCode();

	        if(moved||bodyParts==1)
	        {
	            if ((key == KeyEvent.VK_LEFT) &&( (!right)||bodyParts==1)) {
	                left = true;
	                up = false;
	                down = false;
	            }

	            if ((key == KeyEvent.VK_RIGHT) && ( (!left)||bodyParts==1))  {
	                right = true;
	                up= false;
	                down= false;
	            }

	            if ((key == KeyEvent.VK_UP) && ( (!down)||bodyParts==1))  {
	                up = true;
	                right= false;
	                left = false;
	            }

	            if ((key == KeyEvent.VK_DOWN) && ( (!up)||bodyParts==1)) {
	                down = true;
	                right = false;
	                left = false;
	            }
	            moved= false;
	        }
		}
	}
}