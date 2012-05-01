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
	 * 
	 * Updated: Now with random platforms, we have to wait a bit before
	 * the razorback dies. Setting this to eleven seconds gives the
	 * razorback time to reach the end of the platform, fall between
	 * platforms, and go through the death animation.
	 */
	@Test
	public void test() {
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);

		int beginningLives = game.gameScreen.info.lives();
		try 
		{
			Thread.sleep(11000); // Waiting eleven seconds for ultimate death...
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("start: " + beginningLives + ", end: " + game.gameScreen.info.lives());
		assertTrue(game.gameScreen.info.lives() < beginningLives);
	}
}
