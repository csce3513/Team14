package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.stbtt.*;



public class LifeLostScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public GameScreen gameScreen, oldGameScreen;
	Game game;
	GameInfo info;
	Music music;
	float scale = 0.350f;
	int multiplier = 1;
	private boolean didShow = false; // For testing.
	BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	
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
		font = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("assets/StarForce.ttf"), FONT_CHARACTERS, 7.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.setScale(.4f);
		didShow = true;
		gameScreen = null;
		System.out.println("in lifelostscreen");
		if (music != null)	// Null check for doing unit testing, music not loaded then
			if (!music.isPlaying())
				music.play();
	}  
      
	public void render (float delta)
	{
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  

		int R = (int) (Math.random( )*256);
		int G = (int) (Math.random( )*256);
		int B = (int) (Math.random( )*256);

		if ((scale >= .400) || (scale <= .300))
		{
			multiplier = -1 * multiplier;
		}
		scale += multiplier * .001;
		System.out.println(scale);
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		for (int life = 0; life < info.life(); life++)
		{
			CharSequence str= "" + info.getScore(life);
			if (info.getScore(life) != -1)
			{
				if (life == info.life() - 1)
				{
					font.setColor(R, G, B, 1);
					font.setScale(scale);
				}
				else
				{
					font.setColor(Color.WHITE);
					font.setScale(0.3f);
				}
				font.draw(batch, str, 150, 550-(life*40));
			}
		}
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