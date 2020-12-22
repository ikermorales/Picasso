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

import formaStuff.*;
import ventanas.Papel;

public class Sprite implements Serializable{
	protected int x;
	protected int y;
	private int xV;
	private int yV;
	private Color color;
	private int opacidad;
	private int tamanyo;
	private ArrayList<String> sprites;
	private int sprite;
	private double collisionRad;
	private boolean simetriaActivada;
	private boolean simetriaHorizontal;



	public Sprite(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal) {
		super();
		this.x = x;
		this.y = y;
		this.xV = xV;
		this.yV = yV;
		this.color = color;
		this.opacidad = opacidad;
		this.tamanyo = tamanyo;
		this.sprites = sprites;
		this.sprite = sprite;
		this.collisionRad = collisionRad;
		this.simetriaActivada = simetriaActivada;
		this.simetriaHorizontal = simetriaHorizontal;
	}


	public Sprite() {
		super();
		this.x = 0;
		this.y = 0;
		this.xV = 0;
		this.yV = 0;
		this.color = null;
		this.opacidad = 0;
		this.tamanyo = 0;
		this.sprites = null;
		this.sprite = 0;
		this.collisionRad = 0;
		this.simetriaActivada = false;
		this.simetriaHorizontal = false;
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
	public int getOpacidad() {
		return opacidad;
	}
	public void setOpacidad(int opacidad) {
		this.opacidad = opacidad;
	}

	public void pintar(ComponentePapel cp) {
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

	public void pintarForma(ComponentePapel cp, Forma f, int x2, int y2) {
		Color actual = cp.getGraficos().getColor();

		if(f.getClass().getName().contains("Circulo")) {
			Circulo circuloNuevo = new Circulo();
			circuloNuevo = (Circulo) f;

			cp.getGraficos().setColor(f.getColor());

			if(f.isEstanRellenas()) {
				cp.getGraficos().fillOval(x2, y2, (int) circuloNuevo.getRadio(), (int) circuloNuevo.getRadio());
			} else {
				cp.getGraficos().drawOval(x2, y2, (int) circuloNuevo.getRadio(), (int) circuloNuevo.getRadio());
			}

		} else if(f.getClass().getName().contains("Cuadrado")) {
			
			Cuadrado cuadradoNuevo = (Cuadrado) f;
			
			cp.getGraficos().setColor(f.getColor());

			if(f.isEstanRellenas()) {
				cp.getGraficos().fillRect(x2, y2, (int) cuadradoNuevo.getAncho(), (int) cuadradoNuevo.getAncho());
			} else {
				cp.getGraficos().drawRect(x2, y2, (int) cuadradoNuevo.getAncho(), (int) cuadradoNuevo.getAncho());
			}



		} else if(f.getClass().getName().contains("Triangulo")) {
			Triangulo trianguloNuevo = (Triangulo) f;

			cp.getGraficos().setColor(f.getColor());

			int[] xTriangulo = new int[3];
			int[] yTriangulo = new int [3];
			Polygon triangulo;

			xTriangulo[0] = x2;
			yTriangulo[0] = y2;

			xTriangulo[1] = (int) (x2 + trianguloNuevo.getBase()/2);
			yTriangulo[1] = (int) (y2 + trianguloNuevo.getAltura());

			xTriangulo[2] = (int) (x2 - trianguloNuevo.getBase()/2);
			yTriangulo[2] = (int) (y2 + trianguloNuevo.getAltura());

			triangulo = new Polygon(xTriangulo, yTriangulo, 3);

			if(trianguloNuevo.isEstanRellenas()) {
				cp.getGraficos().fill(triangulo);
				cp.getGraficos().draw(triangulo);
			} else {
				cp.getGraficos().draw(triangulo);
			}

		} else if(f.getClass().getName().contains("Ovalo")) {

			Ovalo ovaloNuevo = (Ovalo) f;

			cp.getGraficos().setColor(f.getColor());

			if(f.isEstanRellenas()) {
				cp.getGraficos().fillOval(x2, y2, (int) ovaloNuevo.getAnchura(), (int) ovaloNuevo.getAltura());
			} else {
				cp.getGraficos().drawOval(x2, y2, (int) ovaloNuevo.getAnchura(), (int) ovaloNuevo.getAltura());
			}


		}
		cp.setColorActual(actual);
		cp.getGraficos().setColor(actual);
	}



}


