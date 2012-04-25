/**
 * @author Nathan Hyatt
 * File: TestDash.java
 * Purpose: Test dash functionality
 */
package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TestDash
{
	/**
	 * testVelocity(): Assert x velocity of the razorback is
	 * increased during dash mode and returned to original
	 * velocity afterwards.
	 */
	@Test
	public void testVelocity()
	{
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);
	
        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }

		// Get initial velocity, then dash
		float i = game.gameScreen.razorback.getXVelocity();
		game.gameScreen.razorback.dash();

		// Wait until we're in dash, get new velocity
		try { Thread.sleep(100); } catch(InterruptedException e) { }
		float j = game.gameScreen.razorback.getXVelocity();
		System.out.println("initial: " + i + ", dash: " + j);

		// Make the assertion
		assertTrue(j > i);

		// Wait another quarter second to complete dash
		try { Thread.sleep(1000); } catch(InterruptedException e) { }
		j = game.gameScreen.razorback.getXVelocity();
		System.out.println("initial: " + i + ", final: " + j);
		
		// Make the assertion, last field is an allowable delta
		assertEquals((double) i, (double) j, 0.01f);
	}
}
