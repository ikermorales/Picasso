package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import formaStuff.Ovalo;

public class OvaloTest {

	@Test
	public void testGetAnchura() {
		Ovalo o = new Ovalo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 2.99, 2.99);
		
		assertEquals(2.99, o.getAnchura(), 0.001);
	}

	@Test
	public void testSetAnchura() {
		Ovalo o = new Ovalo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 2, 2);
		
		o.setAnchura(9.0);
		
		assertEquals(9.0, o.getAnchura(), 0.001);
	}
	
	@Test
	public void testGetAltura() {
		Ovalo o = new Ovalo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		assertEquals(9.0, o.getAltura(), 0.001);
	}
	
	@Test
	public void testSetAltura() {
		Ovalo o = new Ovalo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		o.setAltura(12.0);
		
		assertEquals(12.0, o.getAltura(), 0.001);
	}
	
	@Test
	public void testOvaloVacio() {
		Ovalo o = new Ovalo();
		
		assertEquals(0.0, o.getAltura(), 0.001);
	}
	
	
	

}
