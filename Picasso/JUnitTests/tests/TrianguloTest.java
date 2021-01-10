package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import formaStuff.Triangulo;

public class TrianguloTest {

	@Test
	public void testGetBase() {
		Triangulo t = new Triangulo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		assertEquals(9.0, t.getBase(), 0.001);
	}
	
	@Test
	public void testSetBase() {
		Triangulo t = new Triangulo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		t.setBase(29.0);
		
		assertEquals(29.0, t.getBase(), 0.001);
	}
	
	@Test
	public void testGetAltura() {
		Triangulo t = new Triangulo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		assertEquals(9.0, t.getAltura(), 0.001);
	}
	
	@Test
	public void testSetAltura() {
		Triangulo t = new Triangulo(1,1,1,1,Color.red, 255, 9, null, 0,9, true, false, false, 23, 9.0, 9.0);
		
		t.setAltura(29.0);
		
		assertEquals(29.0, t.getAltura(), 0.001);
	}
	
	@Test
	public void testTrianuloVacio() {
		Triangulo t = new Triangulo();
		
		assertEquals(0.0, t.getAltura(), 0.001);
	}
	
	

}
