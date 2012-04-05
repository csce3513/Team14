package com.team14;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class HUDCamera
{
	OrthographicCamera camera;
	
	public void prepareCamera(int screenWidth, int screenHeight)
	{
		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.position.set(400, 300, 0);
	}
	
	/**
	 * Returns the camera object created for showing lives and score
	 * 
	 * @return OrthographicCamera
	 */
	public OrthographicCamera getCamera()
	{
		if (camera == null)
		{
			throw new IllegalStateException(
					"getCamera() called out of sequence");
		}
		return camera;
	}
	
	/**
	 * Renders the part of the map that should be visible to the user.
	 */
	public void render()
	{
	}
}
