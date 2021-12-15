import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.IOException;
public class EndScreen implements ActionListener{
	private JButton logout;
	private JButton playAgain;
	private JFrame frame;
	private UserHandler uh;
	private user u;
	EndScreen(UserHandler uh, user u, int s){
		this.uh = uh;
		this.u =u;
		
		try {
			uh.update(u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel username = new JLabel(u.getUsername());
		username.setBounds(20, 10, 100, 30);
		JLabel pr = new JLabel("Personal Records");
		JLabel lb = new JLabel("Leaderboard");
		pr.setBounds(120,10,150,30);
		lb.setBounds(300,10,100,30);
		
		frame = new JFrame();
		playAgain = new JButton("Play Again!");
		Image icon = Toolkit.getDefaultToolkit().getImage("snek.png");  
		frame.setIconImage(icon);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);

		playAgain.setBounds(55,240,100,25);
		playAgain.setFocusable(false);
playAgain.addActionListener(this);
logout = new JButton("logout");
logout.setBounds(215,240,100,25);
logout.setFocusable(false);
logout.addActionListener(this);
		JLabel newscore = new JLabel("Score: " +String.valueOf(s));
		newscore.setBounds(19,25,100,30);
		frame.add(newscore);
		frame.add(logout);
		frame.add(playAgain);
		frame.add(username);
		frame.add(lb);
		frame.add(pr);
		int count=1;
		Stack<Integer> pscores = u.getTop10();
		while(!pscores.isEmpty())
		{
			JLabel lable = new JLabel(pscores.pop().toString()+" - "+u.getUsername());
			lable.setBounds(120,15+15*(count),100,20);
			frame.add(lable);
			count++;
		}
		count =1;
		ArrayList<ScoreTally> leaderbaord = uh.getBest();
		for(ScoreTally st:uh.getBest())
		{
			JLabel lable = new JLabel(st.toString());
			lable.setBounds(300, 15+15*(count),100,20);
			frame.add(lable);
			count++;
		}
	}
//allows the player to play again or logout of the game
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		if(e.getSource()==playAgain) {
			
			frame.dispose();
			new GamePage(u, uh);
		}
		if(e.getSource()==logout)
		{
			frame.dispose();
			uh.logOut();
			new LoginPage(uh);
		}
			}	
}