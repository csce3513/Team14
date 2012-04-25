package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.team14.GameInfo;

/**
 * @author Jennifer Marti
 * Tests GameInfo class for tracking lives left, score per life, and total score.
 */
public class TestLivesAndScores {
	@Test
	public void test() {
		GameInfo gameinfo = new GameInfo();

		gameinfo.loseLife(6100);
		gameinfo.loseLife(5000);
		gameinfo.loseLife(7200);
		
		assertEquals(gameinfo.life(), 3);
		assertEquals(gameinfo.getTotalScore(), 18300);
		assertTrue(gameinfo.gameOver());
	}

}
