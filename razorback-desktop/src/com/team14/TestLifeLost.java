/**
 * 
 * File: TestLifeLost.java
 * Purpose: Test losing a life
 * 
 */
package com.team14;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.team14.Game;

public class TestLifeLost {

	/**
	 * By default, three lives are given. Testmap will cause a 
	 * pause in forward velocity without interaction, which should cause
	 * a life to be lost.
	 * 
	 * At this point, the game continues when a life is lost, so all lives
	 * should be lost and gameOver status assigned in about 6 seconds.
	 */
	@Test
	public void test() {
		Game game = new Game();	
        new LwjglApplication(game, "Game", 800, 600, false);
		try 
		{
			Thread.sleep(6000); // Waiting six seconds for collision...
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		assertTrue(game.isGameOver());
	}
}
