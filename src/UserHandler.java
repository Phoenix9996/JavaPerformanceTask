import java.io.*;
import java.util.*;
public class UserHandler 
{
	private Map<String,String> loginInfo;
	private Set<user> users;
	private File f;
	private user u;
	public UserHandler(File file)
	{
		f=file;
		loginInfo = new HashMap<String,String>();
		users = new HashSet<user>();
	}
	//returns wether or not a user has been logged in
	public boolean hasUser()
	{
		return(u!=null);
	}
	//loads users from the userdata textfile
	public void loadUsers() throws IOException
	{
		Scanner s = new Scanner(f);
		while(s.hasNextLine())
		{
			String line = s.nextLine();
			int userPos = line.indexOf("$");
			String un = line.substring(0,userPos);
			String pass = line.substring(userPos+1, line.indexOf("!"));
			loginInfo.put(un,pass);
			user u = new user(un);
			String scoreList = line.substring(1+line.indexOf("!"),line.length());
			String[] score = scoreList.split(" ");
			if(!score[0].equals(""))
			{
			for(String aScore:score)
			{
				
				Integer scoreValue = Integer.parseInt(aScore);
				u.addScore(scoreValue);
			}
			users.add(u);
			}
		}
	
		
	}
	//returns the user logged in
	public user getUser()
	{
		return u;
	}
	//finds the account associated with the user
	private user findAccount(String u)
	{
		Iterator i = users.iterator();
		while(i.hasNext())
		{
			
			user acc = (user) i.next();
			
			if(acc.getUsername().equals(u))
			return acc;
		}
		return null;
	}
	//logs into a user returns true if the login was successful and false if it failed
	public boolean findUser(String user, String pass)
	{
	if(loginInfo.containsKey(user))
	if(loginInfo.get(user).equals(pass))
	{
		u = findAccount(user);
		return true;
	}
	return false;
	}
	//returns wether or not a user exists
	public boolean hasUser(String user)
	{
		return loginInfo.containsKey(user);
	}
	//returns the top 10 local leaderboard 
	//uses linkedlist when gathering the top 10 as it can find the index of elements for removal

	public ArrayList<ScoreTally> getBest()
	{
		LinkedList<ScoreTally> total = new LinkedList<ScoreTally> ();
		ArrayList<ScoreTally> dabest = new ArrayList<ScoreTally>();
		for(user u:users)
		{
			Stack<Integer> stack =u.getTop10();
			if(stack!=null)
			{
				while(!stack.isEmpty())
				{
					total.add(new ScoreTally(stack.pop(), u.getUsername()));
				
				}
			}
			
		}
		for(int i =0;i<10;i++)
		{
			if(!total.isEmpty())
			{
				ScoreTally max= new ScoreTally(0, "JohnDoe");
				for(int j=0;j<total.size();j++)
				{
					if(max.compareTo(total.get(j))<0)
					{
						max = total.get(j);
					}
					
				}
				dabest.add(max);
				total.remove(total.indexOf(max));
			}
			
		}
		return dabest;
	}
	//creates a user and writes to userdata
	public void createUser(String username, String password)throws IOException
	{
		loginInfo.put(username, password);
		users.add(new user(username));
		Scanner sc = new Scanner(f);
		StringBuffer buffer = new StringBuffer();
		while(sc.hasNextLine())
		{
			buffer.append(sc.nextLine()+System.lineSeparator());
		}
		buffer.append(username+"$"+password+"!"+System.lineSeparator());
		String fileContents = buffer.toString();
		
		sc.close();
	
		FileWriter writer = new FileWriter(f);
		writer.write(fileContents);
		writer.flush();
	}
	//logs the player out and empties the user instance field.
	public void logOut()
	{
		u = null;
	}
	//updates userdata
	//stores accounts made in a set to ensure there are no duplicates
	public void update(user u) throws IOException
	{
		String oldLine= "user not found";
		String newLine;
		Scanner sc = new Scanner(f);
		StringBuffer buffer = new StringBuffer();
		while(sc.hasNextLine())
		{
			String nextLine = sc.nextLine();
			
			if(nextLine.substring(0,u.getUsername().length()+1).equals(u.getUsername()+"$"))
			{
				oldLine = nextLine;
				String newScores="";
				Stack<Integer> stack = u.getTop10();
				
				while(!stack.isEmpty())
				{
					if(newScores.length()<1)
					{
						newScores = newScores+stack.pop();
					}
					else
					{
						newScores = newScores+" "+stack.pop();
					}
				}
				
				newLine=oldLine.substring(0,oldLine.indexOf("!")+1)+newScores;
				buffer.append(newLine+System.lineSeparator());
			}
			else buffer.append(nextLine+System.lineSeparator());
		}
		
		String fileContents = buffer.toString();
		
		sc.close();
	
		FileWriter writer = new FileWriter(f);
		writer.write(fileContents);
		writer.flush();
	}
}
