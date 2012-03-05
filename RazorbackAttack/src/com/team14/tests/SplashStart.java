package com.team14.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.team14.IntroScreen;

public class SplashStart extends Game
{
	public IntroScreen introScreen;
//	boolean firstTimeCreate = true;
        
	public SplashStart()
	{
		introScreen = new IntroScreen(this);
	}

	public Screen getStartScreen()
	{
		return introScreen;
	}

	@Override
	public void create () 
	{
		System.out.println("Going to IntroScreen...");
		this.setScreen(introScreen);
	}
}