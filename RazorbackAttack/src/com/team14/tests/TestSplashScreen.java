/**
 * @author Jonathan Whitmore
 * File: TestSplashScreen.java
 * Purpose: Test splash screen functionality
 */
package com.team14.tests;

import static org.junit.Assert.*;


import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class TestSplashScreen
{
	/**
	 * Test to ensure the splash screen(s) load and display properly.
	 * 
	 * As if appearing on screen wasn't enough proof.
	 */
	@Test
	public void test()
	{
		SplashStart splash = new SplashStart();
		
		// Make the assertion.
		assertFalse(splash.introScreen.didShow());
		
		// Now start the application, which will show the screen.
		new LwjglApplication(splash, "Razorback Attack!", 800, 600, false);

        // Wait 2s for splash screen to appear
        try { Thread.sleep(2000); } catch(InterruptedException e) { }
		
        // Make the assertion.
        assertTrue(splash.introScreen.didShow());
	}
}
