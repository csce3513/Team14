package com.team14;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
		
	public static void main (String[] args) {
		new LwjglApplication(new RazorbackAttack(), "Razorback Attack!", 800, 600, false);
	}
}