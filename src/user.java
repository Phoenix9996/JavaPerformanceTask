import java.util.*;
public class user {
	private PriorityQueue<Integer> scores;
	private String username;
	public user(String u)
	{
		username=u;
		scores = new PriorityQueue<Integer>();

	}
	//adds a new score
	//uses a priority queue to remove the smallest score from the leaderboard
	public void addScore(int i)
	{
		scores.add(i);
		if(scores.size()>10)
		{
			scores.remove();
		}
	
	}
	//returns username of the user
	public String getUsername()
	{
		return username;
	}
	//returns the personal records of the player
	//uses a stack as it reverses the order of the objects removed from a priority queue, 
	//changing the order from increasing to descending
	public Stack<Integer> getTop10()
	{
		PriorityQueue<Integer> temp = new PriorityQueue<Integer>();
		
		Stack<Integer> top10 = new Stack<Integer>();
		if(scores.size()>=10) 
		{
			for(int i = 0;i<10;i++)
			{
				int tempInt = scores.remove();
				temp.add(tempInt);
				top10.add(tempInt);
				
			}	
		}
		else
		{
			while(!scores.isEmpty())
			{
				int tempInt = scores.remove();
				temp.add(tempInt);
				top10.add(tempInt);
			}
		}
		scores = temp;
		return top10;
	}
	//overrides hascode method so it can be used in a hashset
	@Override
	public int hashCode()
	{	
		final int prime = 31;
		int hc= 7;
		hc = prime*hc+username.hashCode();
		hc = prime*hc+scores.hashCode();
		return hc;
	}
	//overrides equals to be used in a hashset
	@Override
	public boolean equals(Object o) 
	{
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		user other = (user)o;
		if(getUsername().equals(other.getUsername()))
		{
			return true;
		}
		return false;
	}
	//converts the user to a string format
	public String toString()
	{
		return username+getTop10().toString();
	}
	
}
