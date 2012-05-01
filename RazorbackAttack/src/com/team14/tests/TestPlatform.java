/**
 * @author Nathan Hyatt
 * File: TestDash.java
 * Purpose: Test dash functionality
 */
package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TestPlatform
{
	/**
	 * testVelocity(): Assert x velocity of the razorback is
	 * increased during dash mode and returned to original
	 * velocity afterwards.
	 */
	@Test
	public void testNumberOfPlatformsEqualsThree()
	{
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);
	
        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }

        // You have to play the game in these tests. AND STAY ALIVE. It sucks, I know.
        
        // FIRST TEST
        // =========================================================================
        // Test that the number of platforms is always three. 
        Integer size = game.gameScreen.platforms.platformList.size();
        assertEquals(size, Integer.valueOf(3));
        
        // Wait another five seconds to cycle through some platforms...
        try { Thread.sleep(5000); } catch(InterruptedException e) { }
        size = game.gameScreen.platforms.platformList.size();
        assertEquals(size, Integer.valueOf(3));     
        // Wait another five seconds to cycle through some platforms...
        try { Thread.sleep(5000); } catch(InterruptedException e) { }
        size = game.gameScreen.platforms.platformList.size();
        assertEquals(size, Integer.valueOf(3));     
        
        // SECOND TEST
        // =========================================================================
        // Assert that all new platforms are placed after the current one.
        int currPlatform = game.gameScreen.platforms.getCurrentPlatform(game.gameScreen.razorback.getXPosition());
    	float currPlatformX = game.gameScreen.platforms.platformList.get(currPlatform).getStart();
    	float nextPlatformX = game.gameScreen.platforms.platformList.get(currPlatform+1).getStart();
    	float lastPlatformX = game.gameScreen.platforms.platformList.get(currPlatform+2).getStart();
    	assertTrue(lastPlatformX > nextPlatformX);
    	assertTrue(nextPlatformX > currPlatformX);
    	
    	// THIRD TEST
    	// =========================================================================
    	// Test that the platforms aren't placed so high that the razorback can't 
    	// reach them.
    	float runningHeight = game.gameScreen.razorback.getYPosition();
    	float jumpApexHeight = 0.0f;
    	game.gameScreen.razorback.jump();
    	while (game.gameScreen.razorback.isJumping())
    	{
    		if (jumpApexHeight < game.gameScreen.razorback.getYPosition())
    			jumpApexHeight = game.gameScreen.razorback.getYPosition();
    	}
    	float jumpHeight = jumpApexHeight - runningHeight;
    	float currPlatformY = game.gameScreen.platforms.platformList.get(currPlatform).getY();
    	float nextPlatformY = game.gameScreen.platforms.platformList.get(currPlatform+1).getY();
    	float lastPlatformY = game.gameScreen.platforms.platformList.get(currPlatform+2).getY();
    	assertTrue((nextPlatformY - currPlatformY) < jumpHeight);
    	assertTrue((lastPlatformY - nextPlatformY) < jumpHeight);
	}
}
