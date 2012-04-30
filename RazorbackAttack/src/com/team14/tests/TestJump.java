/**
 * @author Jeremy Barr and Nathan Hyatt
 * File: TestJump.java
 * Purpose: Test jump functionality
 */
package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TestJump
{
	/**
	 * testVelocity(): Assert x velocity of the razorback is
	 * increased during dash mode and returned to original
	 * velocity afterwards.
	 */
	@Test
	public void test()
	{
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);
	
        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }

        /**
         * Test first jump
         */
		float i = game.gameScreen.razorback.getYVelocity();
		game.gameScreen.razorback.jump();

		// Wait until we're in jump, get new vertical velocity
		float j = game.gameScreen.razorback.getYVelocity();
		System.out.println("First jump - initial vel: " + i + ", jump vel: " + j);
		
		// Make the assertion
		assertTrue(j > i);
		
		/**
		 *  Now test double jump
		 */
		// Wait for Y velocity to change a bit
		try { Thread.sleep(100); } catch(InterruptedException e) { }
		
		// Get existing velocity
		i = game.gameScreen.razorback.getYPosition();

		// Jump again
		game.gameScreen.razorback.jump();
		j = game.gameScreen.razorback.getXPosition();
		
		System.out.println("Second jump - initial vel: " + i + ", jump vel: " + j);
		
		// Make the assertion
		assertTrue(Math.abs(j) > i);
		
		/**
		 * Test that we can't jump more than twice.
		 */
		
		// Get existing velocity
		i = game.gameScreen.razorback.getYVelocity();
				
		// Wait for Y velocity to change a bit
		try { Thread.sleep(100); } catch(InterruptedException e) { }

		// Try to jump again
		game.gameScreen.razorback.jump();
		j = game.gameScreen.razorback.getYVelocity();
		System.out.println("Third jump - initial vel: " + i + ", jump vel: " + j);

		/**
		 * This time, j should be less than i since we've already used up
		 * our double jump before hitting the ground.
		 */
		assertTrue(j < i);		
	}
}
