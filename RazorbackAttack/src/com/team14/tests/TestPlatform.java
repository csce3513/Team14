package com.team14.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.team14.Platform;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPlatform
{
	World world;
	
	@Test
	public void test()
	{
		initialize();
		while (true)
		{
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		SpriteBatch spriteBatch = new SpriteBatch();
		Platform platform = new Platform (world, 1, 100, 100);
		
		spriteBatch.begin();
		spriteBatch.draw(platform.tr, 100, 100);
		spriteBatch.end();
		}
//		fail("Not yet implemented");
	}

	public void initialize()
	{
		new LwjglApplication(new PlatformTest(), "platform test", 800, 600, false);
		world = new World(new Vector2(0.0f, -10.0f), true);
	}

	public class PlatformTest extends Game
	{
		@Override
		public void create () { }
	}
}