package tests;

import static org.junit.Assert.*; 

import javax.sound.sampled.AudioInputStream;

import org.junit.Test;

import clasesBase.Musica;

public class MusicaTest {

	@Test
	public void testGetAis() {
		Musica musica = new Musica(null, null, false);
		assertEquals(null, musica.getAis());
	}
	@Test
	public void testSetAis() {
		Musica musica = new Musica(null, null, false);
		AudioInputStream ais2 = null;
		musica.setAis(ais2);
		assertEquals(ais2, musica.getAis());
	}
	
	
	@Test
	public void testGetClip() {
		Musica musica = new Musica(null, null, false);
		assertEquals(null, musica.getClip());
	}
	@Test
	public void testSetClip() {
		Musica musica = new Musica(null, null, false);
		musica.setClip(null);
		assertEquals(null, musica.getClip());
	}
	
	@Test
	public void testIsActivado() {
		Musica musica = new Musica(null, null, false);
		assertEquals(false, musica.isActivado());
	}
	@Test
	public void testSetActivado() {
		Musica musica = new Musica(null, null, false);
		musica.setActivado(true);;
		assertEquals(true, musica.isActivado());
	}
	

}
