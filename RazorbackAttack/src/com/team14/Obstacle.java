package com.team14;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	private static final int MAX_OBSTACLES = 1;
	private static final float PIXELS_PER_METER = 46.6f;
	
	public Body body;
	private Texture texture;
	private TextureRegion textureRegion;
	
	public Obstacle(World world, float xpos, float ypos)
	{
		int num = getRandomNum(MAX_OBSTACLES);
		texture = new Texture(Gdx.files.internal("assets/obstacle" + 1 + ".png"));
		textureRegion = new TextureRegion(texture, 0, 0, 74, 74);
		
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xpos / PIXELS_PER_METER, ypos / PIXELS_PER_METER);
		body = world.createBody(bodyDef);
		
		System.out.println("Obstacle pos: " + xpos + ", " + ypos);
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(0, 0);
		vertices[1] = new Vector2(73 / PIXELS_PER_METER, 0);
		vertices[2] = new Vector2(73 / PIXELS_PER_METER, 73 / PIXELS_PER_METER);
		vertices[3] = new Vector2(0, 73 / PIXELS_PER_METER);

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
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(textureRegion, PIXELS_PER_METER * body.getPosition().x /* / 2 */, PIXELS_PER_METER * body.getPosition().y/* / 2 */); 
	}
	
	private int getRandomNum(int MAX)
	{
	    Random random = new Random();
	    return random.nextInt(MAX) + 1;
	}
	
	@Override
	public String toString()
	{
		return "OBSTACLE";
	}
}
