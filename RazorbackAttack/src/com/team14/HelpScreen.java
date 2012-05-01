package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelpScreen implements Screen, InputProcessor
{
	private SpriteBatch batch;  
	private Texture helpTexture;
	Game game;
	Screen prevScreen; 
	private Music music, oldMusic;
	boolean pause;
	
	public HelpScreen(Game g, Screen s, Music m, boolean p)
	{
		game = g;
		prevScreen = s;
		oldMusic = m;
		pause = p;
	}

	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		if (pause)
			helpTexture = new Texture(Gdx.files.internal("assets/PauseScreen.png"));
		else
			helpTexture = new Texture(Gdx.files.internal("assets/HelpScreen.png"));
        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/pause.mp3", FileType.Internal));
        if (music != null)
        {
        	oldMusic.pause();
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
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
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
	public void dispose()
	{
		batch.dispose();
		music.dispose();
		helpTexture.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		boolean down = true;
		switch (keycode)
		{
			case (Keys.ESCAPE):
				game.setScreen(prevScreen);
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
