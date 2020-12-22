package formaStuff;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

import clasesBase.Sprite;

public class Forma extends Sprite {
	private boolean estanRellenas;
	private Integer botonteclado;


	public Forma(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal,
			boolean estanRellenas, Integer botonteclado) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal);
		this.estanRellenas = estanRellenas;
		this.botonteclado = botonteclado;
	}


	public Forma() {
		super();
		this.estanRellenas = false;
		this.botonteclado = 0;
	}


	public boolean isEstanRellenas() {
		return estanRellenas;
	}


	public void setEstanRellenas(boolean estanRellenas) {
		this.estanRellenas = estanRellenas;
	}
	

	public Integer getBotonteclado() {
		return botonteclado;
	}

	public void setBotonteclado(Integer botonteclado) {
		this.botonteclado = botonteclado;
	}
	
	
}

