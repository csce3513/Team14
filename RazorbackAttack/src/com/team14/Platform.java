/**
 * Platform.java - Loads resources for a new platform
 * 
 * Platform shapes are LibGDX ChainShapes, using the createLoop() method.
 * File format
 * -----------
 * First line: Dimensions of texture region within texture file, "x y" format (no quotes)
 * Remaining lines: Vertices (end points) of collision lines, "x y" format (no quotes)
 * 
 * TODO:
 * - Add multiple physical bodies per platform
 * - New platform textfile format:
 * 		First line: Dimensions of texture region
 * 		Second line: Position on platform to place obstacle, if desired
 * 		Third line: Number of bodies defined - up to two
 * 		Fourth - Mth line: Vertices of body one
 * 		--- blank line ---
 * 		Mth+2 - Nth line: Vertices of body two 
 */
package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Random;

public class Platform
{
    private Body body;
    private Texture texture;
    public TextureRegion tr;
    private World world;
    
    private int xSize, ySize;		// Dimensions of the platform texture region

	public static final float PIXELS_PER_METER = 46.6f; // Conversion factor for body<->pixels
	private static final int MAX_PLATFORMS = 3; // Total platforms, must be updated when new platform is added

	/**
	 * 
	 * @param w - World in which to place platforms, passed from Platforms instance
	 * @param num - Number of platform resource to load, 0 for randomized selection
	 * @param xpos - Pixel-based x-coordinate to place upper left corner of platform
	 * @param ypos - Same, for y-coordinate
	 */
	public Platform(World w, int num, float xpos, float ypos)
	{
		world = w;
		if (num == 0)	// Get a random number if we weren't provided one.
			num = this.getRandomNum(MAX_PLATFORMS);

		texture = new Texture(Gdx.files.internal("assets/platforms/platform" + num + ".png"));

		String collisionsFile = "assets/platforms/platform" + num + ".txt";
		FileHandle fh = Gdx.files.internal(collisionsFile);
		String collisionFile = fh.readString();
		String lines[] = collisionFile.split("\\r?\\n");

		/**
		 * All textures used by OpenGL v1 are required to have dimensions based on
		 * powers of two. So, we first need to define the size of the region in 
		 * the texture we need - this is the first line in each platform text file.
		 */
		String dimensions[] = lines[0].split(" ");
		xSize = Integer.parseInt(dimensions[0]);
		ySize = Integer.parseInt(dimensions[1]);
		tr = new TextureRegion(texture, 0, 0, xSize, ySize);

		/**
		 * Each line after the first defines a vertex for the platform.
		 */
		Vector2[] vertices = new Vector2[lines.length - 1];
		for (int i = 1; i < lines.length; i++)
		{
			String cols[] = lines[i].split(" ");
			String xcoord = cols[0];
			String ycoord = cols[1];
			vertices[i - 1] = new Vector2(Integer.parseInt(xcoord) / PIXELS_PER_METER, (Integer.parseInt(ycoord) * -1 + ySize) / PIXELS_PER_METER);
		}
		
		/**
		 * Now initialize the Box2D physics body
		 */ 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
      
		/**
		 * TODO:
		 * The position needs to be set dynamically.
		 */
		bodyDef.position.set(xpos / PIXELS_PER_METER, (ypos - ySize + getRandomNum(160) * getRandomSign()) / PIXELS_PER_METER);
		body = world.createBody(bodyDef);

		ChainShape chain = new ChainShape();
		chain.createLoop(vertices);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 0.0f;
		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0.0f;
		body.createFixture(fixtureDef);
		chain.dispose();
	}
	
	/**
	 * getRandomNum(): Returns a general purpose random number.
	 */
	private int getRandomNum(int MAX)
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