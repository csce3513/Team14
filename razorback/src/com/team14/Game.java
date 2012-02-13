package com.team14;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Game implements ApplicationListener {
	OrthographicCamera cam = new OrthographicCamera();
	Vector3 tmp = new Vector3();
    TileMapRenderer tileMapRenderer;
    SpriteBatch spriteBatch;
    
	private boolean gameOver = false;
	boolean automove = true;
	
	public void setGameOver(boolean status)
	{
		gameOver = status;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
//        TileMapRenderer tileMapRenderer;
        TiledMap map;
        TileAtlas atlas;
        // TiledLoader loader;
        spriteBatch = new SpriteBatch();
        // Load the tmx file into map
        map = TiledLoader.createMap(Gdx.files.internal("assets/tiles/testmap.tmx"));

        // Load the tiles into atlas
        atlas = new TileAtlas(map, Gdx.files.internal("assets/tiles/"));

        // Create the renderer
        tileMapRenderer = new TileMapRenderer(map, atlas, 32, 32, 5, 5);
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
	public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // if (automove) {
        //     updateCameraPosition();
        // }

        cam.zoom = 0.9f;
        cam.update();
        
        tileMapRenderer.render(cam);

        spriteBatch.begin();
        //font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        //font.draw(spriteBatch, "InitialCol, LastCol: " + tileMapRenderer.getInitialCol() + "," + tileMapRenderer.getLastCol(), 20,
         //       40);
        //font.draw(spriteBatch, "InitialRow, LastRow: " + tileMapRenderer.getInitialRow() + "," + tileMapRenderer.getLastRow(), 20,
         //       60);
        
        tmp.set(0, 0, 0);
        cam.unproject(tmp);
        //font.draw(spriteBatch, "Location: " + tmp.x + "," + tmp.y, 20, 80);
        spriteBatch.end();
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

}
