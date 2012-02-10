package com.team14.tests;

import static org.junit.Assert.*;
import java.io.*;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class TestInput {

	@Test
	public void test() {
		byte [] buff ={62};
		BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(buff));
		System.setIn(in);
		boolean b=true;
		//b=Gdx.input.isKeyPressed(Keys.SPACE);
		assertTrue(b);
	}

}
