package com.team14;

import java.util.Random;

/**
 * A class chock full of static methods and constants 
 * @author nate
 */
public class Utils
{
	/**
	 * This is the conversion factor from Box2D bodies to their actual on-screen
	 * sizes. This number was calculated based on the rough size of our sprite
	 * and the average size of a full-grown razorback listed on Wikipedia.
	 *
	 * Box2d works best with small values. If you use pixels directly you will
	 * get weird results -- speeds and accelerations not feeling quite right.
	 * Common practice is to use a constant to convert pixels to and from
	 * "meters".
	 */
	public static final float PIXELS_PER_METER = 46.6f;
	
	/**
	 * getRandomNum(): Returns a general purpose random number.
	 */
	public static int getRandomNum(int MAX)
	{
	    Random random = new Random();
	    return random.nextInt(MAX) + 1;
	}
	
	/**
	 * getRandomSign(): Returns a random positive or negative 1. Used for random platform placement.
	 */
	public static int getRandomSign()
	{
		Random random = new Random();
		int i = random.nextInt(2);
		if (i == 0)
			i = -1;
		return i;
	}
}
