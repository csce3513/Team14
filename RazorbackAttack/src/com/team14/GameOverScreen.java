package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  

public class GameOverScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public IntroScreen introScreen;
	Game game;
	GameInfo info;
	Music music;
	private boolean didShow = false; // For testing.
	
	public GameOverScreen(Game g, GameInfo i, Music m)
	{
		game = g;
		info = i;
		music = m;
//		gameScreen = new GameScreen(game);
	}
	
	@Override  
	public void show()
	{  
		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/EndGame.png"));
		didShow = true;
		//gameScreen = new GameScreen(game, info);
		introScreen = new IntroScreen(game);
		if (!music.isPlaying())
			music.play();
	}  
      
	public void render (float delta)
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			System.out.println("Going to previous screen...");
			game.setScreen(introScreen);
		}
	}

	public boolean didShow()
	{
		return didShow;
	}
	
	public void resize (int width, int height) { }  
	public void pause () { }  
	public void resume () { }  
	
	public void dispose () {
		music.dispose();
	}  
	public void hide()
	{
		music.pause();
		didShow = false;
	}  
}  