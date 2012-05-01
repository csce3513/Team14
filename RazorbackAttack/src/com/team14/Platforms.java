package com.team14;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;

public class Platforms
{
    private World world;
    private OrthographicCamera camera;
	Box2DDebugRenderer renderer;
	public LinkedList<Platform> platformList;
	
	FixtureDef fixtureDef[][];
	Vector2 dimensions[];
	Vector2 obstacles[];
	
	public Platforms(World w)
	{
		camera = new OrthographicCamera(800, 600);
		camera.position.set(0, 0, 0);
		renderer = new Box2DDebugRenderer(true, false, false);
		
		world = w;
		
		/**
		 * These platforms are set the same each time for the test cases.
		 */
		platformList = new LinkedList<Platform>();
		platformList.add(getNewPlatform(1, 0, -100));
		platformList.add(getNewPlatform(2, 1700, -100));
		platformList.add(getNewPlatform(7, 2800, -200));
	}

	public Platform getNewPlatform(int num, float xpos, float ypos)
	{
		float yRandomized = Utils.getRandomNum(75) * Utils.getRandomSign();
		Platform p = new Platform(world, num, xpos, ypos + yRandomized);
		return p;
	}
	
	/**
	 * removeOldAndAddNew(): Iterates the PlatformList to find any platforms that have left
	 *                       the screen. When this happens, this platform is deleted and a
	 *                       new platform is added to the tail of the linked list. 
	 * @param currentPos: The current X position of the Razorback's Box2D body. This number
	 * 					  is passed on to getCurrentPlatform().
	 */
	public void removeOldAndAddNew(float currentPos)
	{
		/**
		 * Remove any platforms before the current platform, add a new random platform to tail.
		 */
		for (int i = 0; i < getCurrentPlatform(currentPos); i++)
		{
			platformList.set(i, null);
			platformList.remove(i);
			int num = Utils.getRandomNum(Platform.MAX_PLATFORMS);
			platformList.add(getNewPlatform(num, (platformList.getLast().getEnd() + 300), -200));
		}
	}

	/**
	 * getCurrentPlatform(): Which platform in the list is the Razorback currently on/over?
	 * @param currentPos: The current X position of the Razorback's Box2D body. This number
	 * 					  needs to be multiplied by PIXELS_PER_METER to get the screen position
	 *                    of the Razorback.
	 * @return The element position of the platformList over which the Razorback resides.
	 * 		   Returns -1 if not currently over a platform.
	 */
	public int getCurrentPlatform(float currentPos)
	{
		int currPlatform = -1;

		/**
		 * Walk the list until we find the platform the Razorback currently occupies.
		 */
		for (Platform p: platformList)
		{
			if ((p.getStart() <= currentPos * Utils.PIXELS_PER_METER) && (p.getEnd() >= currentPos * Utils.PIXELS_PER_METER))
				currPlatform = platformList.indexOf(p);
		}
		return currPlatform;
	}
	
	public void render()
	{
		Vector3 tmp = new Vector3();
		tmp.set(0, 0, 0);
		camera.unproject(tmp);
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		for (Platform p: platformList)
		{
			p.render(spriteBatch);

			/**
			 * For debugging, uncomment this line. It will display a miniature outline
			 * of the world, switching to full size when you approach an obstacle.
			 * 
			 * I take no credit for this, it just works that way.
			 */
//			renderer.render(world, camera.projection);
		}
	}
	
	/**
	 * Returns the camera object created for viewing the loaded map.
	 * 
	 * @return OrthographicCamera
	 */
	public OrthographicCamera getCamera()
	{
		if (camera == null) 
		{
			throw new IllegalStateException(
					"getCamera() called out of sequence");
		}
		return camera;
	}
}
