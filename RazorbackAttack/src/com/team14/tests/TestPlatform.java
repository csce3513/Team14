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
        
        // SECOND TEST
        // =========================================================================
        // Assert that all new platforms are placed after the current one.
        // This test will increase the Razorback's position by large increments
        // quickly and check to see that new platforms are a
        for (int i = (int) game.gameScreen.razorback.getXPosition(); i < 1000000; i++)
        {
        	int currPlatform = game.gameScreen.platforms.getCurrentPlatform(game.gameScreen.razorback.getXPosition());
        	
        	if (currPlatform == -1)
        	{
        		System.out.println("skipping");
        		continue;
        	}
        	float currPlatformX = game.gameScreen.platforms.platformList.get(currPlatform).getStart();
        	float nextPlatformX = game.gameScreen.platforms.platformList.get(currPlatform+1).getStart();
        	assertTrue(nextPlatformX > currPlatformX);
        	i += 1000;
            try { Thread.sleep(500); } catch(InterruptedException e) { }
        	game.gameScreen.razorback.setXPosition(i * 46.6f);        	
        }
	}
}
