package com.team14.tests;

import static org.junit.Assert.*;
import java.io.*;
import java.util.Scanner;
import junit.framework.TestCase;
import org.junit.Test;
import com.badlogic.gdx.Input.Keys;



public class TestInput extends TestCase{

    @Test                
    public void test ()throws IOException{
	    System.out.println("here");

    	Scanner input=null;
    	input =new Scanner(new BufferedReader(new FileReader("testinput.txt")));

	// Reads input from file to mock user input and dump it to the console.
	int	tmp	= 0;
	while (input.hasNextInt()){
	
		tmp =input.nextInt();
	    assertSame(Keys.UP,tmp);
	    System.out.println("here");
	}
	// Close the streams.

	  input.close();

    }
}