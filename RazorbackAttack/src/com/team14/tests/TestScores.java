package com.team14.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.team14.GameInfo;


public class TestScores {

	@Test
	public void test() {
		GameInfo gameinfo = new GameInfo();
//		gameinfo.setScore(6100);
		gameinfo.loseLife(6100);
		gameinfo.loseLife(5000);
		gameinfo.loseLife(7200);
		
		assertEquals(gameinfo.life(), 3);
		assertEquals(gameinfo.getTotalScore(), 18300);
		assertTrue(gameinfo.gameOver());
	}

}
