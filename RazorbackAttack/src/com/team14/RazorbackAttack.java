package com.team14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

//import Game;
//import MainMenuScreen;
//import Screen;

public class RazorbackAttack extends Game {
        boolean firstTimeCreate = true;

        public Screen getStartScreen () {
                return new IntroScreen(this);
        }

        @Override
        public void create () {
//                Settings.load();
//                Assets.load();
//                super.create();
                this.setScreen(new IntroScreen(this));
        }
}