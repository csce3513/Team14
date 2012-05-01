package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  

public class IntroScreen implements Screen, InputProcessor
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
		Gdx.input.setInputProcessor(this);
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
	public void dispose ()
	{
		batch.dispose();
		music.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		boolean down = true;
		switch (keycode)
		{
			case (Keys.SPACE):
				game.setScreen(gameScreen);
				break;
			case (Keys.H):
				game.setScreen(new HelpScreen(game, this, music, false));
				break;
			case (Keys.D):
				info.setMotorcycleMode();
				break;
			case (Keys.X):
				Gdx.app.exit();
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