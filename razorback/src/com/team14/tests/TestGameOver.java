package com.team14.tests;

import static org.junit.Assert.*;
import com.team14.Game;

import org.junit.Test;

public class TestGameOver {

	// Test that gameOver status is able to be set and read correctly.
	@Test
	public void test() {
		boolean gameOver = true;
		
		Game testGame = new Game();
		
		// Setting gameOver status true
		testGame.setGameOver(true);
		boolean g = testGame.isGameOver();
		assertSame( gameOver, g);
		
		// Setting gameOver status false
		gameOver = false;
		testGame.setGameOver(false);
		g = testGame.isGameOver();
		assertSame( gameOver, g);
	}

}
