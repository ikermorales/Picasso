package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import clasesBase.Sprite;

public class SpriteTest {


	@Test
	public void testGetX() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED,0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getX());
	}
	@Test
	public void testSetX() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setX(23);
		assertEquals(23, ty.getX());
	}



	@Test
	public void testGetY() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getY());
	}
	@Test
	public void testSetY() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setY(23);
		assertEquals(23, ty.getY());
	}


	@Test
	public void testGetXV() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getxV());
	}
	@Test
	public void testSetXV() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setxV(2);
		assertEquals(2, ty.getxV());
	}


	@Test
	public void testGetYV() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getyV());
	}
	@Test
	public void testSetYV() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setyV(23);
		assertEquals(23, ty.getyV());
	}



	@Test
	public void testGetColor() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(Color.RED, ty.getColor());
	}
	@Test
	public void testSetColor() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setColor(Color.BLACK);
		assertEquals(Color.BLACK, ty.getColor());
	}


	@Test
	public void testGetTamanyo() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(160, ty.getTamanyo());
	}
	@Test
	public void testSetTamanyo() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setTamanyo(159);
		assertEquals(159, ty.getTamanyo());
	}
	
	
	@Test
	public void testGetCollisionRad() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160.2, false, false);
		assertEquals(160.2, ty.getCollisionRad(), 0);
	}
	@Test
	public void testSetCollisionRad() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setCollisionRad(23.2);
		assertEquals(23.2, ty.getCollisionRad(), 0);
	}



	@Test
	public void testGetSprites() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(null, ty.getSprites());
	}
	@Test
	public void testSetSprites() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ArrayList<String> prueba = new ArrayList<>();
		ty.setSprites(prueba);
		assertEquals(prueba, ty.getSprites());
	}



	@Test
	public void testGetSprite() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getSprite());
	}
	@Test
	public void testSetSprite() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setSprite(23);
		assertEquals(23, ty.getSprite());
	}
	
	@Test
	public void testGetOpacidad() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		assertEquals(0, ty.getSprite());
	}
	
	@Test
	public void testSetOpacidad() {
		Sprite ty = new Sprite(0, 0, 0, 0, Color.RED, 0, 160 , null, 0, 160, false, false);
		ty.setOpacidad(23);
		assertEquals(23, ty.getOpacidad());
	}
	
	
	


}
