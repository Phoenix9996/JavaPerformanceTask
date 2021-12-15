import java.io.*;
import java.util.*;
public class Main 
{
	public static void main(String[] args)
	{
		//makes graphics run more effficiently
		System.setProperty("sun.java2d.opengl", "true");
		UserHandler uh = new UserHandler(new File("userdata"));
		try{
			uh.loadUsers();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		LoginPage lp = new LoginPage(uh);
		
	}
	
}
