package formaStuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Circulo extends Forma{
	private double radio;

	
	public Circulo(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal,
			boolean estanRellenas, Integer botonteclado, double radio) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal, estanRellenas, botonteclado);
		this.radio = radio;
	}
	
	public Circulo() {
		super();
		this.radio = 0;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	@Override
	public String toString() {
		return "Circulo [radio=" + radio + ", getColor()=" + getColor() + ", isEstanRellenas()=" + isEstanRellenas()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}	
	
}
