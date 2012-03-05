//Jonathan Whitmore

package com.team14.tests;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;
import com.badlogic.gdx.Input.Keys;
import com.team14.Splash;

public class TestSplashScreen {

	@Test
	public void test() {
		//creates new splash screen instance
		Splash splash = new Splash();
		
		//should just have stand in variables of -1 for each upon instantiation. testing for this
		assertEquals(-1, splash.getWidth());
		assertEquals(-1, splash.getHeight());
		
		//get rid of stand in variables. right now just changes them to specified sizes. will alter later
		splash.create();
		
		//just testing for change right now
		//will change to needed dimensions once coding begins.
		assertNotSame(-1, splash.getWidth());
		assertNotSame(-1, splash.getHeight());
		
		//want to have user start game by pressing start.
		//for now, we will just assume that once render is ran that the user presses space to begin game immediately. 
		assertFalse(splash.isGameStart());
		splash.render();
		assertTrue(splash.isGameStart());
		
		
	}

}
