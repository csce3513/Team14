package com.team14;

import static org.junit.Assert.*;
import com.team14.Razorback;
import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.team14.Game;



import org.junit.Test;

public class TestJumpDash {

	@Test
	public void testjump() {
		Game game = new Game(1,3);	
        new LwjglApplication(game, "Game", 800, 600, false);
		try 
		{
			Thread.sleep(3000); // Waiting six seconds for collision...
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		assertTrue(game.razorback.jump());
		
	}
}