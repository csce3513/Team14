package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  

public class IntroScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	Game game;
	
	public IntroScreen(Game g)
	{
		game = g;
	}
      
	@Override  
	public void show()
	{  
		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/SplashScreen.png"));
	}  
      
	public void render (float delta)
	{
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			game.setScreen(new GameScreen(game));
			System.out.println("Going to GameScreen...");
		}
		if (Gdx.input.isKeyPressed(Keys.H))
		{
			game.setScreen(new HelpScreen(game, this));
			System.out.println("Going to HelpScreen...");
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			
		}
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		batch.end();  
	}
      
	public void resize (int width, int height) { }  
	public void pause () { }  
	public void resume () { }  
	public void dispose () { }  
	public void hide() { }  
}  