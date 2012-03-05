package com.team14.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.team14.GameScreen;

public class GameStart extends Game
{
	public GameScreen gameScreen;
	boolean firstTimeCreate = true;
	public Screen introScreen;
        
	public GameStart()
	{
		gameScreen = new GameScreen(this);
	}

	public Screen getStartScreen ()
	{
		return gameScreen;
	}

	@Override
	public void create () 
	{
		System.out.println("Going to GameScreen...");
		this.setScreen(gameScreen);
	}
}