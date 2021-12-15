import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;

public class GamePage extends JFrame{
private user u; 
private UserHandler uh;
	GamePage(user u, UserHandler uh){
			this.uh = uh;
			this.u=u;
			Image icon = Toolkit.getDefaultToolkit().getImage("snek.png");  
			this.setIconImage(icon);  
			GamePanel gp = new GamePanel(this);
		this.add(gp);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		boolean game = true;
		
	

		
	}
	//gets the endpage
	public void getEnd(int i)
	{
		
		this.dispose();
		new EndScreen(uh,u,i);
	}
	//updates the user's score with it's new addition.
	public void addScore(int s)
	{
		u.addScore(s);
	}
}