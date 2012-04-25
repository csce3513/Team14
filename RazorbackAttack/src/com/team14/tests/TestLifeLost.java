/**
 * File: TestLifeLost.java
 * Purpose: Test losing a life
 */
package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class TestLifeLost {

	/**
	 * By default, three lives are given. Testmap will cause a 
	 * pause in forward velocity without interaction, which should cause
	 * a life to be lost.
	 * 
	 * At this point, the game continues when a life is lost, so all lives
	 * should be lost and gameOver status assigned in about 10 seconds.
	 * 
	 * THIS WILL NEED TO BE CHANGED ONCE WE HAVE RANDOM PLATFORMS.
	 */
	@Test
	public void test() {
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);

		int beginningLives = game.gameScreen.info.lives();
		try 
		{
			Thread.sleep(10000); // Waiting six seconds for ultimate death...
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("start: " + beginningLives + ", end: " + game.gameScreen.info.lives());
		assertTrue(game.gameScreen.info.lives() < beginningLives);
	}
}
