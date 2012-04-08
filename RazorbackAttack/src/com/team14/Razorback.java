package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Razorback.java
 * 
 * Create an instance with getInstance() instead of constructor.
 * Using singleton only as an excuse for another JUnit test.
 */
public class Razorback
{
    private Body body;

    /* Razorback sprite animation variables */
    private Animation walkAnimation;
    private Animation jumpAnimation;
    private Animation dashAnimation;
    private Animation deathAnimation;
    private Texture walkSheet;
    private Texture dashSheet;
    private TextureRegion currentFrame;
    private float stateTime;

	/* Razorback states */
	public static final int RUNNING = 0;
	public static final int JUMP = 1;
	public static final int DOUBLEJUMP = 2;
	public static final int DASH = 3;
	public static final int DEAD = 4;
	private int state = RUNNING;
	private int prevState = RUNNING;
	
    private boolean dash = false;

    private static final float normalXVelocity = 8.0f;
    private static final float dashXVelocity = 13.0f;
	public static final float PIXELS_PER_METER = 60.0f;

    private Timer dashTimer = new Timer();

	protected Razorback(World world)
    {
        super();

        /**
         * Load up the texture sheet, create sprites from it.
         */
        walkSheet = new Texture(Gdx.files.internal("assets/animation_sheet.png"));
        walkAnimation = new Animation(0.075f, //25f,
                new TextureRegion(walkSheet, 0, 0, 69, 56),
                new TextureRegion(walkSheet, 70, 0, 69, 56),
                new TextureRegion(walkSheet, 139, 0, 69, 56),
                new TextureRegion(walkSheet, 208, 0, 69, 56),
                new TextureRegion(walkSheet, 277, 0, 65, 56));
        jumpAnimation = new Animation(0.075f, //25f,
                new TextureRegion(walkSheet, 0, 0, 69, 56),
                new TextureRegion(walkSheet, 70, 0, 69, 56),
                new TextureRegion(walkSheet, 139, 0, 69, 56),
                new TextureRegion(walkSheet, 208, 0, 69, 56),
                new TextureRegion(walkSheet, 277, 0, 65, 56));
        dashSheet = new Texture(Gdx.files.internal("assets/dash_sheet.png"));
        dashAnimation = new Animation(0.075f, //25f,
                new TextureRegion(dashSheet, 0, 0, 60, 70),
                new TextureRegion(dashSheet, 60, 0, 68, 70),
                new TextureRegion(dashSheet, 128, 0, 68, 70),
                new TextureRegion(dashSheet, 196, 0, 70, 72));
        deathAnimation = new Animation(0.075f, //25f,
                new TextureRegion(walkSheet, 0, 0, 69, 56),
                new TextureRegion(walkSheet, 70, 0, 138, 56),
                new TextureRegion(walkSheet, 139, 0, 207, 56),
                new TextureRegion(walkSheet, 208, 0, 276, 56),
                new TextureRegion(walkSheet, 277, 0, 341, 56));
        stateTime = 0f;
        
        /* Now initialize */ 
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        
        /* Default start position */
        bodyDef.position.set(1.0f, 7.0f);

        body = world.createBody(bodyDef);

        /**
         * Boxes are defined by their "half width" and "half height", hence the 2 multiplier.
         */
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(69 / (2 * PIXELS_PER_METER), 56 / (2 * PIXELS_PER_METER));
        System.out.println("6");

        /**
         * The character should not ever spin around on impact.
         */
        body.setFixedRotation(true);

		/**
		 * The density and friction of the jumper were found experimentally.
		 * Play with the numbers and watch how the character moves faster or
		 * slower.
		 */
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.8f;
		fixtureDef.friction = 0.0f; // always moving on ground

		body.createFixture(fixtureDef);
		shape.dispose();
        System.out.println("7");

        /* Set default start velocity */
        setXVelocity(normalXVelocity);
	}


	public void move(SpriteBatch spriteBatch)
	{
        if (grounded())
            state = RUNNING;
        stateTime += Gdx.graphics.getDeltaTime();

        /* Determine animation based on Razorback state */
        switch (state)
        {
        	case DASH:
        		currentFrame = dashAnimation.getKeyFrame(stateTime, true);
        		break;
        	case RUNNING:
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);
                break;
        	case JUMP:
        	case DOUBLEJUMP:
        		currentFrame = jumpAnimation.getKeyFrame(stateTime, true);
        		break;
        	
        	case DEAD:
        		currentFrame = deathAnimation.getKeyFrame(stateTime, true);
        		break;
        	default:
        		break;
        }
        spriteBatch.draw(currentFrame, PIXELS_PER_METER * body.getPosition().x	- 69 / 2,
        		PIXELS_PER_METER * body.getPosition().y - 56 / 2);
        System.out.println(getXVelocity());
    }
	
	/**
	 * jump(): Attempts to perform a jump or double jump.
	 */
    public boolean jump()
    {
    	boolean didJump = false;
        if (grounded())
            state = RUNNING;

        switch (state)
        {
        	case RUNNING: 
        	case JUMP:
        		state++;
        		setYVelocity(7.0f);
        		didJump = true;
        		break;
        	case DASH:
        		/**
        		 * Here we want to be able to jump out of a dash
        		 */
        		if (prevState != DOUBLEJUMP)
        		{
            		setYVelocity(7.0f);
            		didJump = true;        			
        		}
        		break;
        	case DOUBLEJUMP:
        	default:
        		break;
        }
        return didJump;
    }

    /**
     * dash(): Attempts to perform a dash. A dash is 0.5 seconds long.
     *         Ensure that we're not already dashing.
     *         TODO: Method to return dash time left?
     */
    public boolean dash()
    {
        if (!dash)
        {
        	setXVelocity(dashXVelocity);
        	dash = true;
        	prevState = state;
        	state = DASH;
        	dashTimer = new Timer();
        	dashTimer.schedule(new DashTimerTask(), 1000);
        }
        return dash;
    }

    /**
     * grounded(): Returns true if y velocity is below and unchanging below
     *             an accepted threshold.
     */
    public boolean grounded()
    {
        if (Math.abs(body.getLinearVelocity().y) < 1e-9)
            return true;
        else
            return false;
    }

    /**
     * Methods to get and set X and Y velocity
     */
    public float getXVelocity()
    {
    	return body.getLinearVelocity().x;
    }

    public float getYVelocity()
    {
    	return body.getLinearVelocity().y;
    }

    public void setXVelocity(float xvel)
    {
    	body.setLinearVelocity(new Vector2(xvel, /*0.0f */getYVelocity()));
    }

    public void setYVelocity(float yvel)
    {
    	body.setLinearVelocity(new Vector2 (getXVelocity(), yvel));
    }
    
    /**
     * Methods to get and set X and Y position
     */
    public float getXPosition()
    {
    	return body.getPosition().x;
    }

    public float getYPosition()
    {
    	return body.getPosition().y;
    }
  
	public int getState()
	{
		return state;
	}
	
	class DashTimerTask extends TimerTask
	{
		public void run()
		{
			setXVelocity(normalXVelocity);
			dash = false;
			state = prevState;
			dashTimer.cancel();
		}
	}
}
