package formaStuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;

public class Ovalo extends Forma {
	private double anchura;
	private double altura;
	
		
	public Ovalo(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal,
			boolean estanRellenas, Integer botonteclado, double anchura, double altura) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal, estanRellenas, botonteclado);
		this.anchura = anchura;
		this.altura = altura;
	}
	
	public Ovalo() {
		super();
		this.anchura = 0;
		this.altura = 0;
	}
	
	
	
	public double getAnchura() {
		return anchura;
	}
	public void setAnchura(double anchura) {
		this.anchura = anchura;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
}
