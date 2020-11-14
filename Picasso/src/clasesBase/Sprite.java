package clasesBase;
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
	private boolean simetriaActivada;
	private boolean simetriaHorizontal;
	
	
	public Sprite(int x, int y, int xV, int yV, Color color, int tamanyo, ArrayList<String> sprites, int sprite,
			double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal) {
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
		this.simetriaActivada = simetriaActivada;
		this.simetriaHorizontal = simetriaHorizontal;
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
	public boolean isSimetriaActivada() {
		return simetriaActivada;
	}
	public void setSimetriaActivada(boolean simetriaActivada) {
		this.simetriaActivada = simetriaActivada;
	}
	public void setyV(int yV) {
		this.yV = yV;
	}
	public boolean isSimetriaHorizontal() {
		return simetriaHorizontal;
	}
	public void setSimetriaHorizontal(boolean simetriaHorizontal) {
		this.simetriaHorizontal = simetriaHorizontal;
	}


	public void pintar(ComponentePapel cp) {
		//cp.getGraficos().drawImage(img, x, y, tamanyo, tamanyo, color, null);
		if(sprite == 0) {
			cp.getGraficos().drawLine(xV, yV, x, y); 
			if(simetriaActivada && simetriaHorizontal) {
				cp.getGraficos().drawLine(cp.getWidth() - xV, cp.getHeight() - yV , cp.getWidth() - x, cp.getHeight() - y); 
			} else if(simetriaActivada && !simetriaHorizontal) {
				cp.getGraficos().drawLine(cp.getWidth() - xV, yV , cp.getWidth() - x, y); 
			}
		} else if(sprite == 1) {
			cp.getGraficos().fillOval(x, y, tamanyo, tamanyo);
			if(simetriaActivada && simetriaHorizontal) {
				cp.getGraficos().fillOval(cp.getWidth() - x, cp.getHeight() - y, tamanyo, tamanyo); 
			} else if(simetriaActivada && !simetriaHorizontal) {
				cp.getGraficos().fillOval(cp.getWidth() - x, y, tamanyo, tamanyo); 
			}
		} else if(sprite == 2) {
			cp.getGraficos().fillRect(x, y, tamanyo, tamanyo);
			if(simetriaActivada && simetriaHorizontal) {
				cp.getGraficos().fillRect(cp.getWidth() - x, cp.getHeight() - y, tamanyo, tamanyo); 
			} else if(simetriaActivada && !simetriaHorizontal) {
				cp.getGraficos().fillRect(cp.getWidth() - x, y, tamanyo, tamanyo); 
			}
		}
		//cp.setxActual(cp.getxVieja());
		//cp.setyActual(cp.getyVieja());
	}


}


