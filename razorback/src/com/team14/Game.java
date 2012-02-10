package com.team14;

import com.badlogic.gdx.ApplicationListener;

public class Game implements ApplicationListener {

	private boolean gameOver = false;
	
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
