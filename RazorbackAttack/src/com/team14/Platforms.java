package com.team14;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Platforms
{
    private World world;
	private OrthographicCamera camera;
	Box2DDebugRenderer renderer;
//	private int lastYPos;
	LinkedList<Platform> platformList;

	public static final float PIXELS_PER_METER = 46.6f;

	public Platforms(World w)
	{
		camera = new OrthographicCamera(800, 600);
		camera.position.set(0, 0, 0);
		renderer = new Box2DDebugRenderer(true, false, false);

		world = w;
		platformList = new LinkedList<Platform>();
		platformList.add(getNewPlatform(1, 0, -200));
		platformList.add(getNewPlatform(2, 1400, -200));
		platformList.add(getNewPlatform(3 , 2300, -200));
	}

	private Platform getNewPlatform(int num, float xpos, float ypos)
	{
		Platform p = new Platform(world, num, xpos, ypos);
		return p;
	}
	
	/**
	 * removeOldAndAddNew(): Iterates the PlatformList to find any platforms that have left
	 *                       the screen. When this happens, this platform is deleted and a
	 *                       new platform is added to the tail of the linked list. 
	 * @param currentPos: The current X position of the Razorback's Box2D body. This number
	 * 					  needs to be multiplied by PIXELS_PER_METER to get the screen position
	 *                    of the Razorback.
	 */
	public void removeOldAndAddNew(float currentPos)
	{
		int currPlatform = -1;

		/**
		 * Walk the list until we find the platform the Razorback currently occupies.
		 */
		for (Platform p: platformList)
		{
			if ((p.getStart() <= currentPos * PIXELS_PER_METER) && (p.getEnd() >= currentPos * PIXELS_PER_METER))
				currPlatform = platformList.indexOf(p);
		}

		/**
		 * Remove any platforms before the current platform, add a new random platform to tail.
		 */
		for (int i = 0; i < currPlatform; i++)
		{
			platformList.remove(i);
			platformList.add(getNewPlatform(0, (platformList.getLast().getEnd() + 300), -200));
		}
	}
	
	public void render()
	{
		Vector3 tmp = new Vector3();
		tmp.set(0, 0, 0);
		camera.unproject(tmp);
	}
	
	public void update(SpriteBatch spriteBatch, float currentPos)
	{
		removeOldAndAddNew(currentPos);
		for (Platform p: platformList)
			p.render(spriteBatch);
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
