package main.vol1_chlee.ch6.learningtest.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {
	
	@Test
	public void invokeMethod() throws Exception {
		String name = "Spring";
		
		// length()
		assertEquals(name.length(), 6);
		
		// invoke()
		Method lengthMethod = String.class.getMethod("length");
		assertEquals(lengthMethod.invoke(name), 6);
		
		// charAt()
		assertEquals(name.charAt(0), 'S');
		
		// invoke()
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertEquals(charAtMethod.invoke(name, 0), 'S');
		
		
	}
}
