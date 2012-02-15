package com.team14;

/**
 * TODO
 * 
 * - Splash, high score, and instruction screens
 * - Fix so camera doesn't change frame of reference, ie razorback appears to
 *   	stay in same x coordinate 
 * - Implement dash timer
 * - Implement sprite animation,
 *   	tutorial: http://code.google.com/p/libgdx/wiki/SpriteAnimation
 * - New test map with platforms and gaps to test for losing a life at bottom
 * - Add overlaid killable objects where collidable tiles are, or change
 *   	collision detection for vertical surfaces to notify of life lost?
 *
 *   ... OR OR OR ...
 *   
 * - Detect forward velocity on render. If we go slower than the original
 *   	velocity (or dash velocity in dash mode), we die.
 *      I don't know why I didn't think of this before.
 *
 * - Create class and sprite for breakable object
 * - If we do premapped levels instead of a neverending random level, 
 * 		determine when we reach end of level for victory. 
 * - Tests tests tests tests tests tests
 *
 * - Eventually: Music + sound effects
 * - Eventually: create a decent level map, at least 1000 tiles long?
 */

/**
 *   Copyright 2011 David Kirchner dpk@dpk.net
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * Source:
 * http://dpk.net/2011/05/08/libgdx-box2d-tiled-maps-full-working-example-part-2/
 */

/**
 * TODO:
 * Make Razorback class extend Body class, for testing position setting 
 */
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game implements ApplicationListener {
	/**
	 * The time the last frame was rendered, used for throttling framerate
	 */
	private long lastRender;

	private TiledMapHelper tiledMapHelper;

	/**
	 * Holder of the texture for the various non-map sprites the game will have.
	 */
	private Texture overallTexture;

	/**
	 * As the name implies, this is the sprite for the razorback. This will need
	 * to be changed to allow for sprite animation, but it works for the demo.
	 */
	private Sprite razorbackSprite;

	/**
	 * The libgdx SpriteBatch -- used to optimize sprite drawing.
	 */
	private SpriteBatch spriteBatch;

	/**
	 * This is the main box2d "container" object. All bodies will be loaded in
	 * this object and will be simulated through calls to this object.
	 */
	private World world;

	/**
	 * This is the player character. It will be created as a dynamic object.
	 */
	private Body razorback;

	/**
	 * This box2d debug renderer comes from libgdx test code. It draws lines
	 * over all collision boundaries, so it is immensely useful for verifying
	 * that the world collisions are as you expect them to be. It is, however,
	 * slow, so only use it for testing.
	 */
	private Box2DDebugRenderer debugRenderer;

	/**
	 * Box2d works best with small values. If you use pixels directly you will
	 * get weird results -- speeds and accelerations not feeling quite right.
	 * Common practice is to use a constant to convert pixels to and from
	 * "meters".
	 */
	public static final float PIXELS_PER_METER = 60.0f;

	/**
	 * The screen's width and height. This may not match that computed by
	 * libgdx's gdx.graphics.getWidth() / getHeight() on devices that make use
	 * of on-screen menu buttons.
	 */
	private int screenWidth;
	private int screenHeight;
	private boolean gameOver = false;
	private int lives = 3;

	public Game() {
		super();

		// Defer until create() when Gdx is initialized.
		screenWidth = -1;
		screenHeight = -1;
	}

	public Game(int width, int height) {
		super();

		screenWidth = width;
		screenHeight = height;
	}

	@Override
	public void create() {
		/**
		 * If the viewport's size is not yet known, determine it here.
		 */
		if (screenWidth == -1) {
			screenWidth = Gdx.graphics.getWidth();
			screenHeight = Gdx.graphics.getHeight();
		}

		tiledMapHelper = new TiledMapHelper();

		tiledMapHelper = new TiledMapHelper();

		tiledMapHelper.setPackerDirectory("assets/tiles");

		tiledMapHelper.loadMap("assets/tiles/testmap.tmx");

		tiledMapHelper.prepareCamera(screenWidth, screenHeight);

		/**
		 * Load up the overall texture and chop it in to pieces. In this case,
		 * piece.
		 */
		overallTexture = new Texture(Gdx.files.internal("assets/razorback.png"));
		overallTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		/**
		 * This may need to change, especially when we start animating this guy.
		 */
		razorbackSprite = new Sprite(overallTexture, 0, 0, 99, 63);
		spriteBatch = new SpriteBatch();

		/**
		 * You can set the world's gravity in its constructor. Here, the gravity
		 * is negative in the y direction (as in, pulling things down).
		 */
		world = new World(new Vector2(0.0f, -10.0f), true);

		Razorback razorbackBodyDef = Razorback.getInstance();
		razorbackBodyDef.type = BodyDef.BodyType.DynamicBody;
		razorbackBodyDef.position.set(1.0f, 7.0f);

		razorback = world.createBody(razorbackBodyDef);

		/**
		 * Boxes are defined by their "half width" and "half height", hence the
		 * 2 multiplier.
		 */
		/**
		 * Might look at using PolygonShape.set(Vector2[] vertices), since our
		 * razorback is a rather odd shape for a sprite.
		 */
		PolygonShape razorbackShape = new PolygonShape();
		razorbackShape.setAsBox(razorbackSprite.getWidth() / (2 * PIXELS_PER_METER),
				razorbackSprite.getHeight() / (2 * PIXELS_PER_METER));

		/**
		 * The character should not ever spin around on impact.
		 */
		razorback.setFixedRotation(true);

		/**
		 * The density and friction of the jumper were found experimentally.
		 * Play with the numbers and watch how the character moves faster or
		 * slower.
		 */
		FixtureDef razorbackFixtureDef = new FixtureDef();
		razorbackFixtureDef.shape = razorbackShape;
		razorbackFixtureDef.density = 0.8f;
		razorbackFixtureDef.friction = 0.0f; // always moving on ground... // 5.0f;

		razorback.createFixture(razorbackFixtureDef);
		razorbackShape.dispose();

		tiledMapHelper.loadCollisions("assets/collisions.txt", world,
				PIXELS_PER_METER);

		debugRenderer = new Box2DDebugRenderer();

		lastRender = System.nanoTime();
		razorback.setLinearVelocity(new Vector2(10.0f, 0.0f));
	}

	@Override
	public void resume() {
	}

	@Override
	public void render() {
		long now = System.nanoTime();

		/**
		 * Detect requested motion.
		 */
		boolean doJump = false;
		boolean doDash = false;

//		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
//			moveRight = true;
//		} else {
//			for (int i = 0; i < 2; i++) {
//				if (Gdx.input.isTouched(i)
//						&& Gdx.input.getX() > Gdx.graphics.getWidth() * 0.80f) {
//					moveRight = true;
//				}
//			}
//		}
//
//		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
//			moveLeft = true;
//		} else {
//			for (int i = 0; i < 2; i++) {
//				if (Gdx.input.isTouched(i)
//						&& Gdx.input.getX() < Gdx.graphics.getWidth() * 0.20f) {
//					moveLeft = true;
//				}
//			}
//		}

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
			doJump = true;
		} else {
			for (int i = 0; i < 2; i++) {
				if (Gdx.input.isTouched(i)
						&& Gdx.input.getY() < Gdx.graphics.getHeight() * 0.20f) {
					doJump = true;
				}
			}
		}
		
		/**
		 * TODO: implement dash mode for touch screen.
		 */
		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
			doDash = true;
		
		/**
		 * The razorback can only jump while on the ground. There are better
		 * ways to detect ground contact, but for our purposes it is sufficient
		 * to test that the vertical velocity is zero (or close to it). As in
		 * the above code, the vertical figure here was found through
		 * experimentation. It's enough to get the guy off the ground.
		 * 
		 * As before, impulse is applied to the center of the razorback.
		 */
		/**
		 * TODO: DOUBLE JUMP MODE, ADD ME.
		 */
		if (doJump && Math.abs(razorback.getLinearVelocity().y) < 1e-9) {
			razorback.applyLinearImpulse(new Vector2(0.0f, 10.0f),
					razorback.getWorldCenter());
		}

		/**
		 * TODO: Implement a timer to do this for 1.5 seconds, and then
		 * 		 return to original forward velocity.
		 */
		if (doDash)
			razorback.setLinearVelocity(new Vector2(20.0f, 0.0f));
		
		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		/**
		 * A nice(?), blue backdrop.
		 */
		Gdx.gl.glClearColor(0, 0.5f, 0.9f, 0);

		/**
		 * The camera is now controlled primarily by the position of the main
		 * character, and secondarily by the map boundaries.
		 */

		tiledMapHelper.getCamera().position.x = PIXELS_PER_METER
				* razorback.getPosition().x + 300;
		
		/**
		 * Ensure that the camera is only showing the map, nothing outside.
		 */
		if (tiledMapHelper.getCamera().position.x < Gdx.graphics.getWidth() / 2) {
			tiledMapHelper.getCamera().position.x = Gdx.graphics.getWidth() / 2;
		}
		if (tiledMapHelper.getCamera().position.x >= tiledMapHelper.getWidth()
				- Gdx.graphics.getWidth() / 2) {
			tiledMapHelper.getCamera().position.x = tiledMapHelper.getWidth()
					- Gdx.graphics.getWidth() / 2;
		}

		if (tiledMapHelper.getCamera().position.y < Gdx.graphics.getHeight() / 2) {
			tiledMapHelper.getCamera().position.y = Gdx.graphics.getHeight() / 2;
		}
		if (tiledMapHelper.getCamera().position.y >= tiledMapHelper.getHeight()
				- Gdx.graphics.getHeight() / 2) {
			tiledMapHelper.getCamera().position.y = tiledMapHelper.getHeight()
					- Gdx.graphics.getHeight() / 2;
		}

		tiledMapHelper.getCamera().update();
		tiledMapHelper.render();

		/**
		 * Prepare the SpriteBatch for drawing.
		 */
		spriteBatch.setProjectionMatrix(tiledMapHelper.getCamera().combined);
		spriteBatch.begin();

		razorbackSprite.setPosition(
				PIXELS_PER_METER * razorback.getPosition().x
						- razorbackSprite.getWidth() / 2,
				PIXELS_PER_METER * razorback.getPosition().y
						- razorbackSprite.getHeight() / 2);
		razorbackSprite.draw(spriteBatch);

		/**
		 * "Flush" the sprites to screen.
		 */
		spriteBatch.end();

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		debugRenderer.render(world, tiledMapHelper.getCamera().combined.scale(
				Game.PIXELS_PER_METER,
				Game.PIXELS_PER_METER,
				Game.PIXELS_PER_METER));

		now = System.nanoTime();
		if (now - lastRender < 30000000) { // 30 ms, ~33FPS
			try {
				Thread.sleep(30 - (now - lastRender) / 1000000);
			} catch (InterruptedException e) {
			}
		}

		lastRender = now;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
	}

	public void loseLife()
	{
		lives--;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void setGameOver(boolean b) {
		gameOver = b;
	}

	public boolean isGameOver() {
		return (getLives() == 0);
	}
}

