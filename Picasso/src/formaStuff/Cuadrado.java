package formaStuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;

public class Cuadrado extends Forma {
	private double alto;
	private double ancho;
	
	
	public Cuadrado(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal,
			boolean estanRellenas, Integer botonteclado, double alto, double ancho) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal, estanRellenas, botonteclado);
		this.alto = alto;
		this.ancho = ancho;
	}
	
	

	public Cuadrado() {
		super();
		this.alto = 0;
		this.ancho = 0;
	}
	
	
	public double getAlto() {
		return alto;
	}
	public void setAlto(double alto) {
		this.alto = alto;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	
}
