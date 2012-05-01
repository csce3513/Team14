package com.team14;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class RAContactListener implements ContactListener
{
	World world;
	GameScreen screen;
	
	public RAContactListener(World w, GameScreen s)
	{
		super();
		world = w;
		screen = s;
	}
	
	@Override
	public void beginContact(Contact contact)
	{
		Razorback razorback;
		Obstacle obstacle;
		
		Body razorbackBody, colliderBody;
		
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		
		/**
		 * All elements in the game are static except for the razorback, so
		 * one of the bodies in contact will always be the razorback!
		 */
		if (bodyA.getUserData().toString() == "RAZORBACK")
		{
			razorbackBody = bodyA;
			colliderBody = bodyB;
		}
		else
		{
			razorbackBody = bodyB;
			colliderBody = bodyA;
		}
		
		razorback = (Razorback) razorbackBody.getUserData();

		if (colliderBody.getUserData().toString() == "PLATFORM")
		{
			razorback = (Razorback) razorbackBody.getUserData();
			if (!razorback.isSlowing())
			{
				razorback.endJump();
				if (razorback.isDashing())
					razorback.setXVelocity(Razorback.dashXVelocity);
				else if (razorback.isDying())
					razorback.setXVelocity(0.0f);
				else
					razorback.setXVelocity(Razorback.normalXVelocity);
			}
		}
		else if (colliderBody.getUserData().toString() == "OBSTACLE")
		{
			obstacle = (Obstacle) colliderBody.getUserData();
			if (!obstacle.isDestroyed())
			{
				System.out.println("Collided with obstacle, brah");
				if (!razorback.isDashing())
					razorback.setState(Razorback.DIE);
				else
				{
					obstacle.setDestroyed();
					screen.addPoints(5000);
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
