//class used to sort scores from different players
public class ScoreTally {
	private String username;
	private int score;
	public ScoreTally (int s, String u)
	{
		username =u;
		score =s;
	}
	//returns username of the scoreholder
	public String getPlayer()
	{
		return username;
	}
	//returns points of the score
	public int getPoints()
	{
		return score;
	}
	//compares two ScoreTallies by score points
	public int compareTo(ScoreTally sc)
	{
		return score-sc.getPoints();
	}
	//converts the ScoreTally to String format
	public String toString()
	{
	return score+"- "+username;
	}
}
