package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import formaStuff.Forma;

public class FormaTest {

	@Test
	public void testIsEstanRellnas() {
		Forma f = new Forma(1,1,1,1,null,29,2,null,29,20,false,true,false,23);
		
		assertEquals(false, f.isEstanRellenas());
	}
	
	@Test
	public void testSetEstanRellnas() {
		Forma f = new Forma(1,1,1,1,null,29,2,null,29,20,false,true,false,23);
		
		f.setEstanRellenas(true);
		
		assertEquals(true, f.isEstanRellenas());
	}
	
	@Test
	public void testGetBotonTeclado() {
		Forma f = new Forma(1,1,1,1,null,29,2,null,29,20,false,true,false,23);
			
		assertEquals((Integer) 23, (Integer) f.getBotonteclado());
	}
	
	@Test
	public void testSetBotonTeclado() {
		Forma f = new Forma(1,1,1,1,null,29,2,null,29,20,false,true,false,23);
		
		f.setBotonteclado(230);
		
		assertEquals((Integer) 230, (Integer) f.getBotonteclado());
	}
	
	
	

}
