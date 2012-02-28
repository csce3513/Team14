package com.team14.tests;

import static org.junit.Assert.*;
import com.team14.Razorback;
import com.badlogic.gdx.*;

import org.junit.Test;

public class TestJumpDash{

	@Test
	public void testjump() {
		Razorback R = Razorback.getInstance(null, 0);
		assertTrue(R.jump());
		
	}

}
