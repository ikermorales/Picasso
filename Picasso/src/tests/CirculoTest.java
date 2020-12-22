package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import formaStuff.Circulo;

public class CirculoTest {

	@Test
	public void testGetRadio() {
		Circulo c = new Circulo(1,1,1,1,null,20,20,null,2,2,false,false,false,34,20.0);
		
		assertEquals(20.0, c.getRadio(),0.001);
		
	}
	
	@Test
	public void testSetRadio() {
		Circulo c = new Circulo(1,1,1,1,null,20,20,null,2,2,false,false,false,34,20.0);
		
		c.setRadio(10.0);
		
		assertEquals(10.0, c.getRadio(),0.001);
		
	}
	

}
