package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import formaStuff.Cuadrado;

public class CuadradoTest {

	@Test
	public void testGetAncho() {
		Cuadrado c = new Cuadrado(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
	
		assertEquals(9.0, c.getAncho(), 0.001);		
	}
	
	@Test
	public void testSetAncho() {
		Cuadrado c = new Cuadrado(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
	
		c.setAncho(29.0);
		
		assertEquals(29.0, c.getAncho(), 0.001);		
	}
	
	@Test
	public void testGetAlto() {
		Cuadrado c = new Cuadrado(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		assertEquals(9.0, c.getAlto(), 0.001);		
	}
	
	@Test
	public void testSetAlto() {
		Cuadrado c = new Cuadrado(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		c.setAlto(29.0);
		
		assertEquals(29.0, c.getAlto(), 0.001);		
	}
	
	
	
	
	

}
