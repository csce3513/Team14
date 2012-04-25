/*
 * Keeps track of lives remaining and scores for each separate life
 * Gives total score for all lives
 */

package com.team14;

public class GameInfo {
	private int livesRemaining;
	private int[] scores = new int[3];
	private int currentLife = 0;
	static final int MAXLIVES = 2; // Zero offset
	
	public GameInfo()
	{
		// livesRemaining does not include the currentLife
		livesRemaining = MAXLIVES;// - 1;
		// array of integers to keep track of score for each life
		for (int i = 0; i < MAXLIVES; i++)
			scores[i] = 0;
	}
	
	public void setScore(int score)
	{
		// error checking to make sure the currentLife is valid
		// manually be able to set the score of the currentLife
		if ((currentLife > MAXLIVES) || (currentLife < 0))
		{
			System.out.println("Error: Invalid array index");
		}
		else
			scores[currentLife] = score;
	}
	
	public int getScore(int life)
	{
		// returns the score of a specific life
		if ((life >= MAXLIVES) || (life < 0))
		{
			System.out.println("Error: Invalid array index");
			return -1;
		}
		else
			return scores[life];
	}
	
	public int getTotalScore()
	{
		// returns total score of all three lives combined
		return (scores[0] + scores[1] + scores[2]);
	}
	
	public int lives()
	{
		//returns the number of lives remaining
		return livesRemaining;
	}

	public int life()
	{
		// returns the life you are currently on
		return currentLife;
	}
	
	public void loseLife(int score)
	{
		// sets the score for the life just lost
		setScore(score);
		// livesRemaining decrements
		livesRemaining--;
		// the life you are currently on increments
		currentLife++;
	}

	public boolean gameOver()
	{
		// checks to see if you are out of lives
		return (livesRemaining < 0);
	}
}
