/**
 * 
 * @author Jennifer Marti Regier
 * 
 * File: TestScore.java
 * Purpose: Test to see if score is set by how far razorback travels
 * 
 */
package com.team14.tests;


import com.team14.Game;
import com.team14.Razorback;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestScore
{
	@Test
	public void test() {
		/* 
		 * Test to see if score is kept based on distance the razorback travels
		 */

		// create a new instance of the game
		Game game = new Game(1, 3);
		boolean b = false;
		
		if(game.PIXELS_PER_METER > 0)
		{
			if(game.getScore() > 0)
				b = true;
		}
		else
		{
			if(game.getScore() == 0)
				b = true;
		}
	}

}
