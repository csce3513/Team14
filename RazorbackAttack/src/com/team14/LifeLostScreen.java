package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.stbtt.*;

public class LifeLostScreen implements Screen, InputProcessor
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public GameScreen gameScreen, oldGameScreen;
	Game game;
	GameInfo info;
	Music music;
	float scale = 0.480f;
	int multiplier = 1;
	private boolean didShow = false; // For testing.
	BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"�`'<>";
	
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
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();  
		splashTexture = new Texture(Gdx.files.internal("assets/EndLife.png"));
		font = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("assets/dlxfont.ttf"), FONT_CHARACTERS, 7.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.setScale(.4f);
		didShow = true;
		gameScreen = null;
		if (music != null)	// Null check for doing unit testing, music not loaded then
			if (!music.isPlaying())
				music.play();
	}  
      
	public void render (float delta)
	{
		CharSequence str = "";
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  

		int R = (int) (Math.random( )*256);
		int G = (int) (Math.random( )*256);
		int B = (int) (Math.random( )*256);

		if ((scale >= .500) || (scale <= .460))
		{
			multiplier = -1 * multiplier;
		}
		scale += multiplier * .001;
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		for (int life = 0; life < info.life(); life++)
		{
			str = "" + info.getScore(life);
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
					font.setScale(0.460f);
				}
				font.draw(batch, str, 515, 537 - (life * 48));
			}
		}
		batch.end();
	}

	public boolean didShow()
	{
		return didShow;
	}
	
	public void resize (int width, int height) { }  
	public void pause () { }  
	public void resume () { }  
	public void dispose ()
	{
		batch.dispose();
		music.dispose();
		font.dispose();
		splashTexture.dispose();
	}  
	
	public void hide()
	{ 
		didShow = false;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		boolean down = true;
		switch (keycode)
		{
			case (Keys.ESCAPE):
				gameScreen = new GameScreen(game, info, music);
				game.setScreen(gameScreen);
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
}
