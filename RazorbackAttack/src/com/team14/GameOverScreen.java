package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;

public class GameOverScreen implements Screen
{  
	private SpriteBatch batch;  
	private Texture splashTexture;
	public IntroScreen introScreen;
	Game game;
	GameInfo info;
	Music music;
	private boolean didShow = false; // For testing.
	BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	
	
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
		font = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("assets/StarForce.ttf"), FONT_CHARACTERS, 7.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.setScale(.4f);
		didShow = true;
		//gameScreen = new GameScreen(game, info);
		introScreen = new IntroScreen(game);
		if (!music.isPlaying())
			music.play();
	}  
      
	public void render (float delta)
	{
		int R = (int) (Math.random( )*256);
		int G = (int)(Math.random( )*256);
		int B= (int)(Math.random( )*256);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.  
		batch.begin();  
		batch.draw(splashTexture, 0, 0);  
		displayScore();
		font.setColor(R, G, B, 1);

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
	private void displayScore()
	{
		for (int life =0 ;life<=info.MAXLIVES ; life++){
			CharSequence str= "DEATH " + (life+1)  +" "+ info.getScore(life);
			if (info.getScore(life)>0)
			{
				font.draw(batch, str, 150, 550-(life*40));
			}
		}
		CharSequence str= "FINAL    "+info.getTotalScore();
		font.draw(batch, str, 150, 550-((info.MAXLIVES+1)*40));
	}
}  