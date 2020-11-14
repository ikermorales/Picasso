package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import clasesBase.Texto;

public class TextoTest {

	@Test
	public void testGetContenido() {
		Texto txt = new Texto(0, 0, 0, 0, null, 0, null, 0, 0, false, false, "Boton");
		assertEquals("boton", txt.getContenido());
	}
	@Test
	public void testSetContenido() {
		Texto txt = new Texto(0, 0, 0, 0, null, 0, null, 0, 0, false, false, "Boton");
		txt.setContenido("Patata");
		assertEquals("Patata", txt.getContenido());
	}

}
