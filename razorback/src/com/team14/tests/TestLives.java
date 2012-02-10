package com.team14.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import com.team14.Razorback;

public class TestLives {

	@Test
	public void test() {
		
		// Razorback starts with three lives. Take away threee, test
		
		Razorback piggie = Razorback.getInstance();
		
		piggie.loseLife();
		piggie.loseLife();
		piggie.loseLife();
		
		assertSame(0, piggie.getLives());
		
	
	}

}
