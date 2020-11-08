import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.Line;

public class Sprite implements Serializable{
	protected int x;
	protected int y;
	private int xV;
	private int yV;
	private Color color;
	private int tamanyo;
	private ArrayList<String> sprites;
	private int sprite;
	private double collisionRad;
	
	
	public Sprite(int x, int y, int xV, int yV, Color color, int tamanyo, ArrayList<String> sprites, int sprite,
			double collisionRad) {
		super();
		this.x = x;
		this.y = y;
		this.xV = xV;
		this.yV = yV;
		this.color = color;
		this.tamanyo = tamanyo;
		this.sprites = sprites;
		this.sprite = sprite;
		this.collisionRad = collisionRad;
	}
	
	public Sprite() {
		super();
		this.x = 0;
		this.y = 0;
		this.xV = 0;
		this.yV = 0;
		this.color = null;
		this.tamanyo = 0;
		this.sprites = null;
		this.sprite = 0;
		this.collisionRad = 0;
	}
	
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ArrayList<String> getSprites() {
		return sprites;
	}
	public void setSprites(ArrayList<String> sprites) {
		this.sprites = sprites;
	}
	public int getSprite() {
		return sprite;
	}
	public void setSprite(int sprite) {
		this.sprite = sprite;
	}
	public double getCollisionRad() {
		return collisionRad;
	}
	public void setCollisionRad(double collisionRad) {
		this.collisionRad = collisionRad;
	}	
	public int getxV() {
		return xV;
	}
	public void setxV(int xV) {
		this.xV = xV;
	}
	public int getyV() {
		return yV;
	}
	public void setxY(int yV) {
		this.yV = yV;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getTamanyo() {
		return tamanyo;
	}
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}


	
	@Override
	public String toString() {
		return "Sprite [x=" + x + ", y=" + y + ", xV=" + xV + ", yV=" + yV + ", color=" + color + ", tamanyo=" + tamanyo
				+ ", sprites=" + sprites + ", sprite=" + sprite + ", collisionRad=" + collisionRad + "]";
	}
	

	public void pintar(ComponentePapel cp, Image img) {
		//cp.getGraficos().drawImage(img, x, y, tamanyo, tamanyo, color, null);
		if(sprite == 0) {
			cp.getGraficos().drawLine(xV, yV, x, y); 
		} else if(sprite == 1) {
			cp.getGraficos().fillOval(x, y, tamanyo, tamanyo);
		} else if(sprite == 2) {
			cp.getGraficos().fillRect(x, y, tamanyo, tamanyo);
		}
		//cp.setxActual(cp.getxVieja());
		//cp.setyActual(cp.getyVieja());
	}
	
	
}


