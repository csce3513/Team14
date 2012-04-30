package com.team14;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle
{
	private static final int MAX_OBSTACLES = 3;
    private Texture destroySheet;
	private Animation destroyAnimation;
	private boolean isDestroyed = false;
    private TextureRegion currentFrame;
    private float dieTime = 0.0f;
	private Music explosionSound;

    
	public Body body;
	private Texture texture;
	private TextureRegion textureRegion;
	
	public Obstacle(World world, float xpos, float ypos)
	{
		int num = getRandomNum(MAX_OBSTACLES);
		texture = new Texture(Gdx.files.internal("assets/obstacle" + num + ".png"));
		textureRegion = new TextureRegion(texture, 0, 0, 73, 73);
		
		destroySheet = new Texture(Gdx.files.internal("assets/obstacleDestroy.png"));
		destroyAnimation = new Animation(0.05f,
	                new TextureRegion(destroySheet, 0, 0, 64, 64),
	                new TextureRegion(destroySheet, 64, 0, 64, 64),
	                new TextureRegion(destroySheet, 128, 0, 64, 64),
	                new TextureRegion(destroySheet, 192, 0, 64, 64),
	                new TextureRegion(destroySheet, 256, 0, 64, 64),
	                new TextureRegion(destroySheet, 0, 64, 64, 64),
	                new TextureRegion(destroySheet, 64, 64, 64, 64),
	                new TextureRegion(destroySheet, 128, 64, 64, 64),
	                new TextureRegion(destroySheet, 192, 64, 64, 64),
	                new TextureRegion(destroySheet, 256, 64, 64, 64),
	                new TextureRegion(destroySheet, 0, 128, 64, 64),
	                new TextureRegion(destroySheet, 64, 128, 64, 64),
	                new TextureRegion(destroySheet, 128, 128, 64, 64),
	                new TextureRegion(destroySheet, 192, 128, 64, 64),
	                new TextureRegion(destroySheet, 256, 128, 64, 64),
	                new TextureRegion(destroySheet, 0, 192, 64, 64),
	                new TextureRegion(destroySheet, 64, 192, 64, 64),
	                new TextureRegion(destroySheet, 128, 192, 64, 64),
	                new TextureRegion(destroySheet, 192, 192, 64, 64),
	                new TextureRegion(destroySheet, 256, 192, 64, 64),
	                new TextureRegion(destroySheet, 0, 256, 64, 64),
	                new TextureRegion(destroySheet, 64, 256, 64, 64),
	                new TextureRegion(destroySheet, 128, 256, 64, 64),
	                new TextureRegion(destroySheet, 192, 256, 64, 64),
	                new TextureRegion(destroySheet, 256, 256, 64, 64));
	        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xpos / Utils.PIXELS_PER_METER, ypos / Utils.PIXELS_PER_METER);
        bodyDef.active = true;
		body = world.createBody(bodyDef);
		
		System.out.println("Obstacle pos: " + xpos + ", " + ypos);
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(73 / Utils.PIXELS_PER_METER, 0);
		vertices[2] = new Vector2(73 / Utils.PIXELS_PER_METER, 73 / Utils.PIXELS_PER_METER);
		vertices[3] = new Vector2(0, 73 / Utils.PIXELS_PER_METER);

		ChainShape chain = new ChainShape();
		chain.createLoop(vertices);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		System.out.println("creating fixture for obstacle");
		body.setUserData(this);
		chain.dispose();
        explosionSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("assets/music/explosion.mp3", FileType.Internal));
        explosionSound.setLooping(false);
        explosionSound.setVolume(0.5f);		// jump.wav is pretty loud!
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		if (!isDestroyed)
		{
			spriteBatch.draw(textureRegion, Utils.PIXELS_PER_METER * body.getPosition().x /* / 2 */, Utils.PIXELS_PER_METER * body.getPosition().y/* / 2 */); 
		}
		else
		{
        	currentFrame = destroyAnimation.getKeyFrame(dieTime, false);
        	dieTime += Gdx.graphics.getDeltaTime();
			spriteBatch.draw(currentFrame, Utils.PIXELS_PER_METER * body.getPosition().x /* / 2 */, Utils.PIXELS_PER_METER * body.getPosition().y/* / 2 */);
		}
	}
	
	private int getRandomNum(int MAX)
	{
	    Random random = new Random();
	    return random.nextInt(MAX) + 1;
	}
	
	public void setDestroyed()
	{
		if (!isDestroyed)
		{
			isDestroyed = true;
			explosionSound.play();
		}
	}
	
	public boolean isDestroyed()
	{
		return isDestroyed;
	}
	
	@Override
	public String toString()
	{
		return "OBSTACLE";
	}
}
