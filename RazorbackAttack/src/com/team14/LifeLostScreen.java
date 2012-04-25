package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  

public class LifeLostScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public GameScreen gameScreen, oldGameScreen;
	Game game;
	GameInfo info;
	Music music;
	private boolean didShow = false; // For testing.
	
	public LifeLostScreen(Game g, GameInfo i, Music m, GameScreen gs)
	{
		game = g;
		info = i;
		music = m;
		oldGameScreen = gs;
	}
	
	@Override  
	public void show()
	{  
		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/EndLife.png"));
		didShow = true;
		gameScreen = null;
		System.out.println("in lifelostscreen");
		if (!music.isPlaying())
			music.play();
	}  
      
	public void render (float delta)
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			oldGameScreen = null;
			gameScreen = new GameScreen(game, info, music);

			System.out.println("Going to new gamescreen...");
			game.setScreen(gameScreen);
		}
	}

	public boolean didShow()
	{
		return didShow;
	}
	
	public void resize (int width, int height) { }  
	public void pause () { }  
	public void resume () { }  
	public void dispose () { }  
	public void hide()
	{ 
		didShow = false;
	}  
}  