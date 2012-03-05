package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class RazorbackAttack extends Game
{
        boolean firstTimeCreate = true;
        public Screen introScreen;
        
        public RazorbackAttack()
        {
        	introScreen = new IntroScreen(this);
        }

        public Screen getStartScreen () {
        	// return new IntroScreen(this);
        	return introScreen;
        }

        @Override
        public void create () {
        	// this.setScreen(new IntroScreen(this));
        	this.setScreen(introScreen);
        }
}