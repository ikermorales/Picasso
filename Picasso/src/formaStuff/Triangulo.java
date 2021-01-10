package formaStuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;

public class Triangulo extends Forma {
	private double altura;
	private double base;
	
	
	public Triangulo(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal,
			boolean estanRellenas, Integer botonteclado, double altura, double base) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal, estanRellenas, botonteclado);
		this.altura = altura;
		this.base = base;
	}
	
	public Triangulo() {
		super();
		this.altura = 0;
		this.base = 0;
	}
	
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getBase() {
		return base;
	}
	public void setBase(double base) {
		this.base = base;
	}	
}
