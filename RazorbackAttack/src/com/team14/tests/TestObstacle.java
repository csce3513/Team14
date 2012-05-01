package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.team14.Obstacle;

public class TestObstacle {

	@Test
	public void test()
	{
		GameStart game = new GameStart();
		new LwjglApplication(game, "Unit Test", 800, 600, false);
        
        // Wait 2s for game to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }

		int currPlatform = game.gameScreen.platforms.getCurrentPlatform(game.gameScreen.razorback.getXPosition());
        Obstacle obstacle = game.gameScreen.platforms.platformList.get(currPlatform + 2).obstacle;
        assertFalse(obstacle.isDestroyed());
        try { Thread.sleep(10000); } catch(InterruptedException e) { }
        
        // YOU HAD BETTER STAY ALIVE AND DESTROY THE OBSTACLE, OR THIS WILL FAIL.
        assertTrue(obstacle.isDestroyed());
	}

}
