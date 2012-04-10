package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelpScreen implements Screen
{
	private SpriteBatch batch;  
	private Texture helpTexture;
	Game game;
	Screen prevScreen; 
	private Music music;
	
	public HelpScreen(Game g, Screen s)
	{
		game = g;
		prevScreen = s;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();  
		helpTexture = new Texture(Gdx.files.internal("assets/HelpScreen.png"));
        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/pause.mp3", FileType.Internal));
        if (music != null)
        {
        	music.setLooping(true);
        	music.play();
        }
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); // Clear screen  
		batch.begin();  
		batch.draw(helpTexture, 0, 0);  
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			System.out.println("Going to previous screen...");
			game.setScreen(prevScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		if (music != null)
			music.pause();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		music.dispose();
	}

}
