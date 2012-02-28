/**
 * @author Nathan Hyatt
 * File: TestDash.java
 * Purpose: Test dash functionality
 */
package com.team14;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.team14.Game;

public class TestDash {
	/**
	 * testVelocity(): Assert x velocity of the razorback is
	 * increased during dash mode and returned to original
	 * velocity afterwards.
	 */
	@Test
	public void test() {
		Game game = new Game(1,3);	
        new LwjglApplication(game, "Game", 800, 600, false);

        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }

		// Get initial velocity, then dash
		float i = game.razorback.getXVelocity();
		game.razorback.dash();

		// Wait until we're in dash, get new velocity
		try { Thread.sleep(500); } catch(InterruptedException e) { }
		float j = game.razorback.getXVelocity();
		System.out.println("initial: " + i + ", dash: " + j);

		// Make the assertion
		assertTrue(j > i);

		// Wait another second to complete dash
		try { Thread.sleep(1000); } catch(InterruptedException e) { }
		j = game.razorback.getXVelocity();
		System.out.println("initial: " + i + ", final: " + j);
		
		// Make the assertion, last field is an allowable delta
		assertEquals((double) i, (double) j, 0.01f);
	}
}
