package com.team14.tests;

import static org.junit.Assert.*;
import com.team14.Razorback;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;



import org.junit.Test;

public class TestJumpDash {

	@Test
	public void testjump() {
		GdxNativesLoader.load();
		World world= new World(new Vector2(0.0f, -10.0f), true);
		Razorback R = Razorback.getInstance(world, 0);
		assertTrue(R.jump());
		
	}
}