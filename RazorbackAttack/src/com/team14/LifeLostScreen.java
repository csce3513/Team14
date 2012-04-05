package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
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
	private boolean didShow = false; // For testing.
	
	public LifeLostScreen(Game g, GameInfo i, GameScreen gs)
	{
		game = g;
		info = i;
		oldGameScreen = gs;
//		gameScreen = new GameScreen(game);
	}
	
	@Override  
	public void show()
	{  
		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/SplashScreen.png"));
		didShow = true;
		oldGameScreen = null;
		gameScreen = new GameScreen(game, info);
		System.out.println("in lifelostscreen");
		System.out.println(gameScreen.toString());
	}  
      
	public void render (float delta)
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
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