package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;

public class Platforms
{
    private World world;
    private OrthographicCamera camera;
	Box2DDebugRenderer renderer;
	LinkedList<Platform> platformList;
	public static final float PIXELS_PER_METER = 46.6f;
	
	//
	FixtureDef fixtureDef[][];
	Vector2 dimensions[];
	Vector2 obstacles[];
	//
	
	public Platforms(World w)
	{
		camera = new OrthographicCamera(800, 600);
		camera.position.set(0, 0, 0);
		renderer = new Box2DDebugRenderer(true, false, false);

		//
		fixtureDef = new FixtureDef[Platform.MAX_PLATFORMS][2];
		dimensions = new Vector2[Platform.MAX_PLATFORMS];
		obstacles = new Vector2[Platform.MAX_PLATFORMS];
		this.loadVertices();
		//
		
		world = w;
		
		platformList = new LinkedList<Platform>();
		platformList.add(getNewPlatform(1, 0, -200));
		platformList.add(getNewPlatform(2, 1400, -200));
		platformList.add(getNewPlatform(7, 2300, -200));
	}

	private Platform getNewPlatform(int num, float xpos, float ypos)
	{
		Platform p = new Platform(world, num, fixtureDef[num-1], dimensions[num-1], obstacles[num-1], xpos, ypos);
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
			int num = Platform.getRandomNum(Platform.MAX_PLATFORMS);
			platformList.add(getNewPlatform(num, (platformList.getLast().getEnd() + 300), -200));
//			platformList.add(getNewPlatform(0, (platformList.getLast().getEnd() + 300), -200));
		}
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
//			renderer.render(world, camera.combined);
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
	
	/**
	 * Precaches the vertices so we don't have to load them from a file each time
	 * we create a platform.
	 */
	public void loadVertices()
	{
		for (int h = 0; h < Platform.MAX_PLATFORMS; h++)
		{
			String collisionsFile = "assets/platforms/platform" + (h + 1) + ".txt";
			FileHandle fh = Gdx.files.internal(collisionsFile);
			String collisionFile = fh.readString();
			String lines[] = collisionFile.split("\\r?\\n");

			String dims[] = lines[0].split(" ");
			dimensions[h] = new Vector2(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
			
			String obs[] = lines[1].split(" ");
			obstacles[h] = new Vector2(Integer.parseInt(obs[0]), Integer.parseInt(obs[1]));
			
			String bodyVertices[] = lines[2].split(" ");
			int filePos = 3;	// for keeping track of where we are in the file

			for (int i = 0; i < bodyVertices.length; i++)
			{
				int numVertices = Integer.parseInt(bodyVertices[i]);
				
				Vector2[] vertices = new Vector2[numVertices];
				for (int j = 0; j < numVertices; j++)
				{
					String cols[] = lines[filePos + j].split(" ");
					String xcoord = cols[0];
					String ycoord = cols[1];
					vertices[j] = new Vector2(Integer.parseInt(xcoord) / PIXELS_PER_METER, (Integer.parseInt(ycoord) * -1 + Integer.parseInt(dims[1])) / PIXELS_PER_METER);
				}
				filePos += numVertices;
				ChainShape chain = new ChainShape();
				chain.createChain(vertices);
				fixtureDef[h][i] = new FixtureDef();
				fixtureDef[h][i].shape = chain;
				fixtureDef[h][i].density = 0.1f;
				fixtureDef[h][i].friction = 0.0f;
				fixtureDef[h][i].restitution = 0.0f;
//				chain.dispose();
			}
		}
	}
}
