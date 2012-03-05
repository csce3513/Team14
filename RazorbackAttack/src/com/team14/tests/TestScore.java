/**
 * 
 * @author Jennifer Marti Regier
 * 
 * File: TestScore.java
 * Purpose: Test to see if score is set by how far razorback travels
 * 
 */
package com.team14.tests;

import static org.junit.Assert.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import org.junit.Test;

public class TestScore
{
	/* 
	 * Test to see if score is kept based on distance the razorback travels
	 */
	@Test
	public void test()
	{
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);

        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }
		
		if (game.gameScreen.razorback.getXPosition() > 0)
			assertTrue(game.gameScreen.getScore() > 0);
	}

}
