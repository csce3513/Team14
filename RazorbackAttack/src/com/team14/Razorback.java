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
import com.badlogic.gdx.physics.box2d.CircleShape;

import java.util.BitSet;
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
	private World world;
    private Body body;

    /* Razorback sprite animation variables */
    private Animation walkAnimation;
    private Animation jumpAnimation;
    private Animation dashAnimation;
    private Animation deathAnimation;
    private Texture walkSheet;
    private Texture dashSheet;
    private Texture deathSheet;
    private Texture jumpSheet;
    private Texture motorcycleTexture;
    private TextureRegion motorcycleTR;
    private TextureRegion currentFrame;
	private float scale = 1.0f;			// For enlarging the sprite upon dying!
	private float lastXVelocity;
	private GameInfo info;
	
    private float stateTime;
    private float dieTime = 0.0f;
    
	/* Razorback states */
    private BitSet state; 

	public static final int RUNNING = 0;
	public static final int JUMP = 1;
	public static final int DOUBLEJUMP = 2;
	public static final int DASH = 3;
	public static final int DIE = 4;
	public static final int DEAD = 5;

    public static final float normalXVelocity = 8.0f;
    public static final float dashXVelocity = 12.0f;
	public static final float PIXELS_PER_METER = 46.6f;

    private Timer dashTimer;
    
	protected Razorback(World w, GameInfo gi)
    {
        super();

        info = gi;
        
        /**
         * Load up the texture sheets, create sprites from it.
         */
        motorcycleTexture = new Texture(Gdx.files.internal("assets/motorcycle2.png"));
        motorcycleTR = new TextureRegion(motorcycleTexture, 0, 0, 160, 76);
        walkSheet = new Texture(Gdx.files.internal("assets/animation_sheet.png"));
        walkAnimation = new Animation(0.075f,
                new TextureRegion(walkSheet, 0, 0, 69, 56),
                new TextureRegion(walkSheet, 70, 0, 69, 56),
                new TextureRegion(walkSheet, 139, 0, 69, 56),
                new TextureRegion(walkSheet, 208, 0, 69, 56),
                new TextureRegion(walkSheet, 277, 0, 65, 56));
        jumpSheet = new Texture(Gdx.files.internal("assets/jump_sheet.png"));
        jumpAnimation = new Animation(.85f,
                new TextureRegion(jumpSheet, 0, 0, 95, 55),
                new TextureRegion(jumpSheet, 95, 0, 95, 55));
        dashSheet = new Texture(Gdx.files.internal("assets/dash_sheet.png"));
        dashAnimation = new Animation(0.150f,
                new TextureRegion(dashSheet, 0, 0, 60, 70),
                new TextureRegion(dashSheet, 60, 0, 68, 70),
                new TextureRegion(dashSheet, 128, 0, 68, 70),
                new TextureRegion(dashSheet, 196, 0, 70, 72));
        deathSheet = new Texture(Gdx.files.internal("assets/death_sheet.png"));
        deathAnimation = new Animation(0.09f,
                new TextureRegion(deathSheet, 0, 0, 64, 64),
                new TextureRegion(deathSheet, 64, 0, 64, 64),
                new TextureRegion(deathSheet, 128, 0, 64, 64),
                new TextureRegion(deathSheet, 192, 0, 64, 64),
                new TextureRegion(deathSheet, 256, 0, 64, 64),
                new TextureRegion(deathSheet, 0, 64, 64, 64),
                new TextureRegion(deathSheet, 64, 64, 64, 64),
                new TextureRegion(deathSheet, 128, 64, 64, 64),
                new TextureRegion(deathSheet, 192, 64, 64, 64),
                new TextureRegion(deathSheet, 256, 64, 64, 64),
                new TextureRegion(deathSheet, 0, 128, 64, 64),
                new TextureRegion(deathSheet, 64, 128, 64, 64),
                new TextureRegion(deathSheet, 128, 128, 64, 64),
                new TextureRegion(deathSheet, 192, 128, 64, 64),
                new TextureRegion(deathSheet, 256, 128, 64, 64),
                new TextureRegion(deathSheet, 0, 192, 64, 64),
                new TextureRegion(deathSheet, 64, 192, 64, 64),
                new TextureRegion(deathSheet, 128, 192, 64, 64),
                new TextureRegion(deathSheet, 192, 192, 64, 64),
                new TextureRegion(deathSheet, 256, 192, 64, 64),
                new TextureRegion(deathSheet, 0, 256, 64, 64),
                new TextureRegion(deathSheet, 64, 256, 64, 64),
                new TextureRegion(deathSheet, 128, 256, 64, 64),
                new TextureRegion(deathSheet, 192, 256, 64, 64),
                new TextureRegion(deathSheet, 262, 256, 64, 64));        
        
        stateTime = 0f;
        
        /* Now initialize */ 
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        
        /* Default start position */
        bodyDef.position.set(0.0f, 0.0f);

        body = w.createBody(bodyDef);

        /* Setting shape to be a circle, which is close enough for our needs */
        CircleShape shape = new CircleShape();
        shape.setRadius(0.6f);

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
		fixtureDef.density = 2.8f;
		fixtureDef.friction = 0.0f; // always moving on ground
		fixtureDef.restitution = 0.0f;
		body.createFixture(fixtureDef);
		body.setUserData(this);
		shape.dispose();
		world = w;
		state = new BitSet();
		state.set(RUNNING);
		
        dashTimer = new Timer();
        /* Set default start velocity */
        setXVelocity(normalXVelocity);
        lastXVelocity = normalXVelocity;
	}


	public void move(SpriteBatch spriteBatch)
	{
    	stateTime += Gdx.graphics.getDeltaTime();
    	
    	lastXVelocity = this.getXVelocity();
    	
    	/* Determine animation based on Razorback state */        
        if (state.get(DIE))
        {
        	this.setXVelocity(0.0f);
        	currentFrame = deathAnimation.getKeyFrame(dieTime, false);
        	dieTime += Gdx.graphics.getDeltaTime();

        	if (deathAnimation.isAnimationFinished(dieTime))
        	{
        		state.set(DEAD);
        	}
        }
        else if (state.get(DASH))
        {
        	if (info.motorcycleMode())
        		currentFrame = motorcycleTR;
        	else
        		currentFrame = dashAnimation.getKeyFrame(stateTime, true);
        }
        else if ((state.get(JUMP)) || (state.get(DOUBLEJUMP)))
        {
        	if (info.motorcycleMode())
        		currentFrame = motorcycleTR;
        	else
        		currentFrame = jumpAnimation.getKeyFrame(stateTime, true);
        }
        else if (state.get(RUNNING))
        {
        	if (info.motorcycleMode())
        		currentFrame = motorcycleTR;
        	else
        		currentFrame = walkAnimation.getKeyFrame(stateTime, true);        	
        }

        /**
         * Let's have some fun with this guy.
         */
        if (state.get(DIE))
        {
        	scale += 0.06f;
        	spriteBatch.draw(currentFrame, PIXELS_PER_METER * body.getPosition().x	- 69 / 2, PIXELS_PER_METER * body.getPosition().y - 56 / 2, (int) 0, (int) 0, (int) 69, (int)56, scale, scale, 0.0f);
        }
        else
        	spriteBatch.draw(currentFrame, PIXELS_PER_METER * body.getPosition().x	- 69 / 2,
        		PIXELS_PER_METER * body.getPosition().y - 56 / 2);
    }
	
	/**
	 * jump(): Attempts to perform a jump or double jump.
	 */
    public boolean jump()
    {
    	boolean didJump = false;
        
    	/* Handle the cases for each state */
    	if ((state.get(DOUBLEJUMP)) || (state.get(DIE)))
    	{
    		// Do nothing - we aren't allowed to jump again.
    	}
    	else if (state.get(JUMP))
    	{
    		if (state.get(DASH))
    			endDash();
    		state.set(DOUBLEJUMP);
    		setYVelocity(7.0f);
    		didJump = true;
    	}
    	else if (state.get(DASH))
    	{
    		if (!state.get(DOUBLEJUMP))
    		{
    			endDash();
    			state.set(DOUBLEJUMP);
    			setYVelocity(9.0f);
    			didJump = true;
    		}
    	}
    	else if (state.get(RUNNING))
        {
        	state.set(RUNNING, false);
        	state.set(JUMP);
    		setYVelocity(9.0f);
        	didJump = true;
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
    	boolean didDash = false;
    	
        if ((!state.get(DASH)) && (!state.get(DIE)))
        {
        	setXVelocity(dashXVelocity);
        	setYVelocity(0.0f);
        	world.setGravity(new Vector2(0.0f, 0.0f));
        	state.set(DASH);
        	dashTimer = new Timer();
        	dashTimer.schedule(new DashTimerTask(), 1000);
        	didDash = true;
        }
        return didDash;
    }

    /**
     * endDash(): called when dash timer is up or we wish to jump out of a dash
     */
    public void endDash()
    {
    	world.setGravity(new Vector2(0.0f, -10.0f));

    	this.setXVelocity(normalXVelocity);
    	state.set(DASH, false);
		dashTimer.cancel();
    }

    /**
     * grounded(): Returns true if y velocity is below and unchanging below
     *             an accepted threshold.
     */
    public boolean grounded()
    {
        if (Math.abs(body.getLinearVelocity().y) < 1e-3)
            return true;
        else
            return false;
    }
    
    /**
     * 
     */
    public boolean isSlowing()
    {
    	return (this.getXVelocity() < lastXVelocity);
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
  
	public BitSet getState()
	{
		return state;
	}
	public void setState(int i)
	{
		state.set(i);
	}
	
	public void setState(int i, boolean b)
	{
		state.set(i, b);
	}
	
	public boolean isDashing()
	{
		return state.get(DASH);
	}
	
	public boolean isDying()
	{
		return state.get(DIE);
	}
	
	public boolean isDead()
	{
		return state.get(DEAD);
	}
	
	class DashTimerTask extends TimerTask
	{
		public void run()
		{
			endDash();
		}
	}
	
	@Override
	public String toString()
	{
		return "RAZORBACK";
	}
}
