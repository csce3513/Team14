package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/*
 * Razorback.java
 * 
 * Create an instance with getInstance() instead of constructor.
 * Using singleton only as an excuse for another JUnit test.
 *
 * TODO
 * ----
 * Set constant forward velocity for normal and dash modes (gdx.math.Vector2)
 * Set constant downward acceleration (gravity) (gdx.math.Vector2)
 * Implement timer for dash mode
 * 
 */
public class Razorback {
	private static Razorback instance = null;

	// Razorback states
	static final int RUNNING = 0;
	static final int JUMP = 1;
	static final int DOUBLEJUMP = 2;
	static final int DASH = 3;
	static final int DEAD = 4;
	
	int state = RUNNING;
	boolean grounded = true;
	
	private Razorback() {}

	/*
	 * TODO:
	 * add parameter for map in which we are placed
	 * add parameters for initial x and y coordinates
	 * 
	 * pass these parameters to constructor
	 */
	public static Razorback getInstance()
	{
		if (instance == null)
		{
			instance = new Razorback();
		}
		return instance;
	}

	// Performs a jump, if possible
	public void jump()
	{
		if (state == JUMP)
			state = DOUBLEJUMP;
		else
			state = JUMP;
		grounded = false;
		// Rest of jump logic here - or do it in move()?
		// set upward acceleration
	}
	
	public void dash()
	{
		state = DASH;
		// Rest of dash logic here - or do it in move()?
		// set timer - 1.5 seconds?
		// set forward velocity vector
		// revert to original velocity vector
	}
	
	public void move()
	{
		// check for collisions
			// if we have a collision: game over, man. game over.
			// if we collide with ground
				// set y acceleration to zero
				// grounded = true;

		// if jumping or double jumping
			// update y position based on y-axis acceleration vector
			// reverse y acceleration vector at zero or sign change - watch for double negative
			// if double jumping, set new upward acceleration
		// if falling
			// update y position based on y-axis acceleration vector

		// update x position based on x-axis velocity vector
	}
	
	public void processInput()
	{
		// TODO: These are our Android touch input variables.
		//       Fix these once we get a working game.
		boolean jumpButton = false;
		boolean dashButton = false;
		boolean pauseButton = false;
		
		if ((Gdx.input.isKeyPressed(Keys.SPACE) || jumpButton) && state != DOUBLEJUMP)
			jump();
		
		if ((Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT) || dashButton) && state != DASH)
			dash();

		/* 
		 * Pausing, not used in first release 
		
		if ((Gdx.input.isKeyPressed(Keys.P) || pauseButton))
			game.pause();
		*
		*/
	}
}
