package com.team14.tests;

import static org.junit.Assert.*;

import com.team14.Game;

import org.junit.Test;

public class TestGameOver {

	/**
	 * Test getters and setters of Game class.
	 */
	@Test
	public void test()
	{
		boolean gameOver = true;
		
		Game testGame = new Game();
		
		// Should not be equal. Lives have not been lost.
		testGame.setGameOver(true);
		boolean g = testGame.isGameOver();
		assertNotSame( gameOver, g);
		
		// Setting gameOver status false
		gameOver = false;
		testGame.setGameOver(false);
		g = testGame.isGameOver();
		assertSame( gameOver, g);
	}
	
	/*
	 * Testing gameOver being set once all lives are used up. 
	 */
	@Test
	public void testGameOverAfterLosingLives()
	{
		Game testGame = new Game();
		
		// new game defaults to 3 lives (0-2)
		testGame.loseLife();
		testGame.loseLife();
		testGame.loseLife();
		
		assertTrue(testGame.isGameOver());
	}

}
