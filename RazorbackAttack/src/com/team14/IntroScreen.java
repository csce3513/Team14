package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  

public class IntroScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public GameScreen gameScreen;
	Game game;
	private GameInfo info;
	private Music music;
	private Music gameMusic;
	private boolean didShow = false; // For testing.
	
	public IntroScreen(Game g)
	{
		game = g;
	}
      
	public Screen getGameScreen()
	{
		return gameScreen;
	}
	
	@Override  
	public void show()
	{
		music = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/start.mp3", FileType.Internal));
        music.setLooping(true);
        music.play();
        
        gameMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/level.mp3", FileType.Internal));
        gameMusic.setLooping(true);

		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/SplashScreen.png"));
		didShow = true;
		info = new GameInfo();
		gameScreen = new GameScreen(game, info, gameMusic);
	}  
      
	public void render (float delta)
	{
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			game.setScreen(gameScreen);
			System.out.println("Going to GameScreen...");
		}
		if (Gdx.input.isKeyPressed(Keys.H))
		{
			game.setScreen(new HelpScreen(game, this, music));
			System.out.println("Going to HelpScreen...");
		}
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			info.setMotorcycleMode();
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		batch.end();  
	}

	public boolean didShow()
	{
		return didShow;
	}

	public void hide()
	{
		music.pause();
	}

	public void resize (int width, int height) { }  
	public void pause () { }  
	public void resume () { }  
	public void dispose () { }  
}  