/**
 * Platform.java - Loads resources for a new platform
 * 
 * Very important: When defining the body vertices in each textfile, the biggest
 * body should be the first one. This is the body on which we'll determine where
 * to place the platform on the screen. The second body vertices should be of the
 * body that is smaller, creating an obstacle or passage.
 * 
 * Platform shapes are LibGDX ChainShapes, using the createLoop() method.
 * 
 * File format
 * -----------
 * First line : Dimensions of texture region within texture file, "x y" format (no quotes)
 * Second line: Position on platform which to place obstacle, "x y" format (no quotes)
 * Remaining lines: Vertices (end points) of collision lines for each body, "x y" format (no quotes)
 */
package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Random;

public class Platform
{
	private Body body;
    private Texture texture;
	private Obstacle obstacle;
    public TextureRegion tr;
    private World world;
    
    private static int xSize, ySize;		// Dimensions of the platform texture region

	public static final float PIXELS_PER_METER = 46.6f; // Conversion factor for body<->pixels
	public static final int MAX_PLATFORMS = 8; // Total platforms, must be updated when new platform is added

	/**
	 * 
	 * @param w - World in which to place platforms, passed from Platforms instance
	 * @param num - Number of platform resource to load, 0 for randomized selection
	 * @param xpos - Pixel-based x-coordinate to place upper left corner of platform
	 * @param ypos - Same, for y-coordinate
	 */
	public Platform(World w, int num, FixtureDef[] fixtureDef, Vector2 dimensions, Vector2 obstaclePos, float xpos, float ypos)
	{
		world = w;
		
		texture = new Texture(Gdx.files.internal("assets/platforms/platform" + num + ".png"));
		xSize = (int) dimensions.x;
		ySize = (int) dimensions.y;
		tr = new TextureRegion(texture, 0, 0, xSize, ySize);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		float y = (ypos - ySize + (getRandomNum(100) * getRandomSign()));
		bodyDef.position.set(xpos / PIXELS_PER_METER, y / PIXELS_PER_METER);

		body = world.createBody(bodyDef);
		
		for (int i = 0; i < fixtureDef.length; i++)
			if (fixtureDef[i] != null)
				body.createFixture(fixtureDef[i]);
		System.out.println("Obstacle position: " + obstaclePos.x + ", " + obstaclePos.y);
		System.out.println("Platform position: " + xpos + ", " + y);
		System.out.println("=============================================================");
		if (obstaclePos.x != 0.0)
			obstacle = new Obstacle(world, (obstaclePos.x + xpos), y + obstaclePos.y + ySize);	

		body.setUserData(this);
	}
	
	/**
	 * getRandomNum(): Returns a general purpose random number.
	 */
	public static int getRandomNum(int MAX)
	{
	    Random random = new Random();
	    return random.nextInt(MAX) + 1;
	}
	
	/**
	 * getRandomSign(): Returns a random positive or negative 1. Used for random platform placement.
	 */
	private int getRandomSign()
	{
		Random random = new Random();
		int i = random.nextInt(2);
		if (i == 0)
			i = -1;
		return i;
	}
		
	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(tr, PIXELS_PER_METER * body.getPosition().x, PIXELS_PER_METER * body.getPosition().y);
		if (obstacle != null)
			obstacle.draw(spriteBatch);
	}
	
	/* Return x coordinate of left end of platform */
	public float getStart()
	{
		return body.getPosition().x * PIXELS_PER_METER;
	}
	
	/* Return x coordinate of right end of platform */
	public float getEnd()
	{
		return body.getPosition().x * PIXELS_PER_METER + xSize;
	}
	
	@Override
	public String toString()
	{
		return "PLATFORM";
	}
}
