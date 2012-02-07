package com.team14.tests;

import com.team14.Razorback;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestRazorbackSingleton {

	// Test that only one instance of a Razorback is allowed.
	@Test
	public void test() {
		assertSame(Razorback.getInstance(), Razorback.getInstance());
	}

}
