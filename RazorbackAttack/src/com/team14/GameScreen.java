package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen, InputProcessor
{	
	/* The time the last frame was rendered, used for throttling framerate */
	private long lastRender;

	/* The libgdx SpriteBatch -- used to optimize sprite drawing. */
	private SpriteBatch worldBatch;

	/* The libgdx SpriteBatch for the heads up display (score+lives) */
	private SpriteBatch hudBatch;

	/**
	 * This is the main box2d "container" object. All bodies will be loaded in
	 * this object and will be simulated through calls to this object.
	 */
	private World world;

	/* This is the player character. It will be created as a dynamic object. */
	public Razorback razorback;
	public Platforms platforms;
	private RAContactListener contactListener;
	
	/**
	 * The screen's width and height. This may not match that computed by
	 * libgdx's gdx.graphics.getWidth() / getHeight() on devices that make use
	 * of on-screen menu buttons.
	 */
	private int screenWidth;
	private int screenHeight;
	
	private Game game;
	public GameInfo info;
	private boolean initialized = false;
	public LifeLostScreen lifeLostScreen;
	public GameOverScreen gameOverScreen;
	public HelpScreen pauseScreen;
	private int score = 0;
	private Music music;
	private Music jumpSound;
	private Music dashSound;
	private Music deathSound;
	private Music motorSound;
	private Texture iconTexture;
	private TextureRegion iconRegion1, iconRegion2;
	private float lastXpos = 0.0f; // for score tracking

	/**
	 * The camera responsible for showing the score and lives above the acutal game
	 */
	private HUDCamera hudCamera;
	private BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

	public GameScreen(Game g, GameInfo i, Music m)
	{
		info = i;
		game = g;
		music = m;
		
		screenWidth = -1;	// Defer until create() when Gdx is initialized.
		screenHeight = -1;
		
		gameOverScreen = new GameOverScreen(game, info, music, this);
		lifeLostScreen = new LifeLostScreen(game, info, music, this);
		pauseScreen = new HelpScreen(game, this, music, true);
	}
	
	public void updateWorld()
	{
		score += (razorback.getXPosition() - lastXpos) * Utils.PIXELS_PER_METER;
		lastXpos = razorback.getXPosition();
		

		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		// Clear the screen and show a nice blue backdrop.
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.2421875f, 0.43359375f, 0.73046875f, 0);

		platforms.removeOldAndAddNew(razorback.getXPosition());
		
		// Offset the camera by 300 so Razorback is near left edge of screen
		platforms.getCamera().position.x = Utils.PIXELS_PER_METER * razorback.getXPosition() + 150;
		platforms.getCamera().position.y = Utils.PIXELS_PER_METER * razorback.getYPosition();
		
		if (platforms.getCamera().position.x < Gdx.graphics.getWidth() / 2)
		{
			platforms.getCamera().position.x = Gdx.graphics.getWidth() / 2;
		}		
		platforms.getCamera().update();
		platforms.render();
		worldBatch.setProjectionMatrix(platforms.getCamera().combined);
	}
	
	/**
	 * Get new score and lives, update on HUD camera
	 */
	public void updateHUD()
	{
		hudCamera.getCamera().update();
		hudCamera.render();
		hudBatch.setProjectionMatrix(hudCamera.getCamera().combined);
	}

	@Override
	public void render(float delta)
	{
		long now = System.nanoTime();

		/**
		 * First we update the world and razorback. Then we display them. 
		 */ 
		updateWorld();		// Update world + camera position, HUD camera
		worldBatch.begin();	// Prepare the world and razorback SpriteBatch for drawing.
		platforms.draw(worldBatch);
		razorback.move(worldBatch);
		worldBatch.end();	// "Flush" the sprites to screen.

		/**
		 * Now we update the score and lives, then display them.
		 */
		updateHUD();
		hudBatch.begin();
		for (int i = 0; i <= GameInfo.MAXLIVES; i++)
		{
			if (info.life() <= i)
				hudBatch.draw(iconRegion1, 32 + (i * 64), 550);
			else
				hudBatch.draw(iconRegion2, 32 + (i * 64), 550);
		}
		
		CharSequence str = "" + getScore();
		font.setScale(0.2f);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(hudBatch, str, 350, 550);
		str = "[X] PAUSE";
		font.draw(hudBatch, str, 580, 550);
		hudBatch.end();
		
		/**
		 * Check for collision or falling between platforms
		 */
		if ((razorback.getXVelocity() < 1.0f) || ((razorback.getYPosition() * Utils.PIXELS_PER_METER) < -1000))
		{
			// Tell the GameInfo object that we're dead, and wish to record a new score
			razorback.setState(Razorback.DIE);
			if (!deathSound.isPlaying())
				deathSound.play();
			if (razorback.isDead())
			{
				info.loseLife(getScore());
			
				world = null;
				platforms = null;
				razorback = null;
				hudBatch = null;
				worldBatch = null;
				
				if (info.gameOver())
					game.setScreen(gameOverScreen);
				else
					game.setScreen(lifeLostScreen);
			}
		}
		
		/**
		 * Update render time
		 */
		now = System.nanoTime();
		if (now - lastRender < 30000000) // 30 ms, ~33FPS
		{
			try
			{
				Thread.sleep(30 - (now - lastRender) / 1000000);
			} catch (InterruptedException e)
			{
			}
		}
		lastRender = now;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this);
		/**
		 * Only initialize on first call
		 */
		if (!initialized)
		{
			/**
			 * If the viewport's size is not yet known, determine it here.
			 */
		
			if (screenWidth == -1) {
				screenWidth = Gdx.graphics.getWidth();
				screenHeight = Gdx.graphics.getHeight();
			}

			worldBatch = new SpriteBatch();

			/**
			 * You can set the world's gravity in its constructor. Here, the gravity
			 * is negative in the y direction (as in, pulling things down).
			 */
			world = new World(new Vector2(0.0f, -10.0f), true);

			
			/**
			 * Work from background to foreground
			 */
			razorback = new Razorback(world, info);
			platforms = new Platforms(world);
			font = new BitmapFont();

			lastRender = System.nanoTime();
			Gdx.input.setInputProcessor(this);

			/**
			 * Initialize objects needed to draw score and lives
			 */
			hudCamera = new HUDCamera();
			hudCamera.prepareCamera(screenWidth, screenHeight);
			hudBatch = new SpriteBatch();
			
			/**
			 * This is fun. This will tell us if two bodies collide.
			 */
			contactListener = new RAContactListener(world, this);
			world.setContactListener(contactListener);
			
			/**
			 * Initialize objects needed to play sounds
			 */
	        jumpSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/jump.wav", FileType.Internal));
	        jumpSound.setLooping(false);
	        jumpSound.setVolume(0.2f);	// jump.wav is pretty loud!

	        dashSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/dash.mp3", FileType.Internal));
	        dashSound.setLooping(false);
	        dashSound.setVolume(0.9f);

	        deathSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/death.wav", FileType.Internal));
	        deathSound.setLooping(false);
	        deathSound.setVolume(0.9f);

	        motorSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/motor.wav", FileType.Internal));
	        motorSound.setLooping(true);
	        motorSound.setVolume(0.9f);

	        iconTexture = new Texture(Gdx.files.internal("assets/icons.png"));
	        iconRegion1 = new TextureRegion(iconTexture, 0, 0, 64, 32);
	        iconRegion2 = new TextureRegion(iconTexture, 64, 0, 64, 32);
	        
			font = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("assets/dlxfont.ttf"), FONT_CHARACTERS, 7.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//			try { Thread.sleep(500); } catch(InterruptedException e) { }
			razorback.setXVelocity(Razorback.normalXVelocity);
			initialized = true;
		}
		
		/**
		 * Starts playing music after initialization. If we're already initialized
		 * and are coming back to this screen, will play music from pause point.
		 */
		if (music != null)
		{
			if (!music.isPlaying())
				music.play();
			if (info.motorcycleMode())
				if (!motorSound.isPlaying())
					motorSound.play();
		}
	}

	@Override public void hide() { 
		if (info.motorcycleMode())
			if (motorSound.isPlaying())
				motorSound.pause();
	}
	@Override public void pause() { }
	@Override public void resume() { 
		if (info.motorcycleMode())
			if (!motorSound.isPlaying())
				motorSound.play();
	}
	@Override public void dispose() { 
		worldBatch.dispose();
		hudBatch.dispose();
		if (!music.isPlaying())
				music.dispose();
		jumpSound.dispose();
		dashSound.dispose();
		deathSound.dispose();
		motorSound.dispose();
		font.dispose();
		iconTexture.dispose();
		razorback.dispose();
		razorback = null;
		pauseScreen.dispose();
		lifeLostScreen.dispose();
		gameOverScreen.dispose();
	}
	
	/**
	 * InputProcessor methods
	 */
	@Override
	public boolean keyDown(int keycode)
	{
		boolean down = true;
		switch (keycode)
		{
			case (Keys.DPAD_UP):
				if (razorback != null)
				{
					if (razorback.jump())
						jumpSound.play();
				}
				break;
			case (Keys.CONTROL_LEFT):
				if (razorback != null)
				{
					razorback.dash();
					if (razorback.isDashing())
						dashSound.play();
				}
				break;
			case (Keys.X):
				game.setScreen(pauseScreen);
				break;
			default:
				down = false;
				break;
		}
		return down;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addPoints(int points)
	{
		score += points;
	}
	
	public int getScore()
	{
		return score;
	}
}
