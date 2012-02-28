//Jonathan Whitmore

package com.team14;

/** Splash screen generation class
 * 		right now this is only for testing compilation
 * todo:
 * 1) generate background
 * 2) generate start button
 * 3) generate rendering
 * 		-action inputs
 * 
 */
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;



public class Splash implements Screen {
	
	private int screenWidth;
	private int screenHeight;
	private SpriteBatch spriteBatch;
	private Texture mySplash;
	//Game myGame = new Game();
	private boolean gameStart = false;
	
	public Splash(){
		screenWidth = -1;
		screenHeight = -1;
	}
	public void create(){
		if (screenWidth == -1) {
			screenWidth = 600; //Gdx.graphics.getWidth();
			screenHeight = 500; //Gdx.graphics.getHeight();
		}
	}
	public void render(){
		
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(mySplash, 0, 0);
        spriteBatch.end();
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            gameStart = true;    
        	//myGame.setScreen(new Game());
        		
	}
	public void showSplash()
    {
            spriteBatch = new SpriteBatch();
            mySplash = new Texture(Gdx.files.internal("We need a screen still"));
    }
	public int getWidth(){
		return screenWidth;
	}
	public int getHeight(){
		return screenHeight;
	}
	public boolean isGameStart(){
		return gameStart;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
}
