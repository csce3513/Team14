/**
 * 
 * @author Nathan Hyatt
 * 
 * File: TestRazorbackSingleton.java
 * Purpose: Stupid simple test to ensure only one Razorback instance
 * 			can be created
 * 
 */
package com.team14.tests;

import com.team14.Razorback;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestRazorbackSingleton
{
	@Test
	public void test() {
		/**
		 * Attempt to make two instances, should both refer to first
		 */
		assertSame(Razorback.getInstance(), Razorback.getInstance());
	}

}
