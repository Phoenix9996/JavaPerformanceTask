import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import java.awt.image.*;
import java.io.IOException;
public class LoginPage implements ActionListener{
	
	private JFrame frame = new JFrame();
	private JButton loginButton = new JButton("Login");
	private  JButton resetButton = new JButton("Reset");
	private JTextField userIDField = new JTextField();
	private JPasswordField userPasswordField = new JPasswordField();
	private JLabel userIDLabel = new JLabel("username:");
	private JLabel userPasswordLabel = new JLabel("password:");
	private JLabel messageLabel = new JLabel();
	private JButton registerButton = new JButton("Register");
	private JTextField userIDField2 = new JTextField();
	private JPasswordField userPasswordField2 = new JPasswordField();
	private  JButton resetButton2 = new JButton("Reset");
	private JLabel userIDLabel2 = new JLabel("username:");
	private JLabel userPasswordLabel2 = new JLabel("password:");
	private UserHandler uh;
	private user u;
	
	LoginPage(UserHandler uh){
		this.uh = uh;
		Image icon = Toolkit.getDefaultToolkit().getImage("snek.png");  
		frame.setIconImage(icon);  
		userIDLabel.setBounds(50,50,75,25);
		userPasswordLabel.setBounds(50,100,75,25);
		
		messageLabel.setBounds(125,350,250,20);
	
		
		userIDField.setBounds(125,50,200,25);
		userPasswordField.setBounds(125,100,200,25);
		
		loginButton.setBounds(125,150,100,25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		resetButton.setBounds(225,150,100,25);
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
		userIDLabel2.setBounds(50,200,75,25);
		userPasswordLabel2.setBounds(50,250,75,25);
		userIDField2.setBounds(125,200,200,25);
		userPasswordField2.setBounds(125,250,200,25);
		registerButton.setBounds(125,300,100,25);
		resetButton2.setBounds(225,300,100,25);
		resetButton2.setFocusable(false);
		resetButton2.addActionListener(this);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		frame.add(userIDField2);
		frame.add(userPasswordField2);
		frame.add(registerButton);
		frame.add(resetButton2);
		frame.add(userIDLabel);
		frame.add(userIDLabel2);
		frame.add(userPasswordLabel);
		frame.add(userPasswordLabel2);
		frame.add(messageLabel);
		frame.add(userIDField);
		frame.add(userPasswordField);
		frame.add(loginButton);
		frame.add(resetButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
		
		
	}
//checks if a string has a special character
	 public static boolean hasSpecialCharacter(String str)
		    {
		 
		 Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		 Matcher m = p.matcher(str);
		 boolean b = m.find();
		 return b;
		    }
		 
	@Override
	//gives controls for all the buttons
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==resetButton) {
			
			userIDField.setText("");
			userPasswordField.setText("");
		}
		
		else if(e.getSource()==loginButton) {
			
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			
			if(uh.hasUser(userID)) {
				if(uh.findUser(userID, password)) {
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");
					frame.dispose();
					GamePage welcomePage = new GamePage(uh.getUser(),uh);
					
				}
				else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}

			}
			else {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("username not found");
			}
		}
	
		else if(e.getSource()==registerButton)
		{
			
			
			String userID2 = userIDField2.getText();
			String password2 = String.valueOf(userPasswordField2.getPassword());
			if(!LoginPage.hasSpecialCharacter(password2)&&!LoginPage.hasSpecialCharacter(userID2)&&!uh.hasUser(userID2)&&(userID2.indexOf(" ")==-1)&&(password2.indexOf(" ")==-1))
			{
				try {
					uh.createUser(userID2, password2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				messageLabel.setForeground(Color.green);
				messageLabel.setText("Account sucessfully created, you may login");
				
			}
			else messageLabel.setForeground(Color.red);
			messageLabel.setText("Your username or password contains a special character or a space. Please choose another username/password");
		}
		else if(e.getSource()==resetButton2) {
			
			userIDField2.setText("");
			userPasswordField2.setText("");
		
		}
	}	
}