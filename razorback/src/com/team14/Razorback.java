package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/*
 * Create an instance with getInstance() instead of constructor.
 * Using singleton only as an excuse for another JUnit test.
 */
public class Razorback {
	// Go on about the evils of singletons, but this provides us with
	// an excuse to do a JUnit test on it.
	private static Razorback instance = null;

	// Razorback states
	static final int RUNNING = 0;
	static final int JUMP = 1;
	static final int DOUBLEJUMP = 2;
	static final int DASH = 3;
	static final int DEAD = 4;
	
	int state = RUNNING;
	boolean grounded = true;
	
	/*
	 * Can't use default constructor. Use getInstance().
	 */
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
		// if grounded, we are running
			// update position based on forward velocity vector

		// if not grounded, we are jumping or falling
		
		// if jumping
			// update position based on y-axis acceleration vector
			// reverse y acceleration vector at zero or sign change - watch for double negative 
		// if falling
			// update position based on y-axis acceleration vector

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
