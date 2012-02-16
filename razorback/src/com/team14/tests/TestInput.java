/**
 * 
 * @author Jeremy Barr
 * 
 * File: TestInput.java
 * Purpose: Tests mock input against key constants in LibGDX
 *
 * TODO: Extend to cover all used keys (dash, menu selection, etc)
 * 
 */
package com.team14.tests;

//import static org.junit.Assert.*; - not needed?
import java.io.*;
import java.util.Scanner;
import junit.framework.TestCase;
import org.junit.Test;
import com.badlogic.gdx.Input.Keys;

public class TestInput extends TestCase
{
    @Test                
    public void test() throws IOException{
    	System.out.println("here");

    	Scanner input = null;
    	input = new Scanner(new BufferedReader(new FileReader("testinput.txt")));

    	// Reads input from file to mock user input.
    	int	tmp	= 0;
    	//Test for jump input to gdx jump key.
    	tmp = input.nextInt();
    	assertSame(Keys.DPAD_UP, tmp);
    	//Test for dash input to gdx dash key.
    	tmp=input.nextInt();
    	System.out.println(tmp);
    	assertTrue(Keys.CONTROL_LEFT==tmp);
    	// Close the streams
    	input.close();
    }
}