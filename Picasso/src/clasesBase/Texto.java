package clasesBase;
import java.awt.Color;
import java.util.ArrayList;

public class Texto extends Sprite {
	
	private String contenido;

	public Texto(int x, int y, int xV, int yV, Color color, int opacidad, int tamanyo, ArrayList<String> sprites,
			int sprite, double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal, String contenido) {
		super(x, y, xV, yV, color, opacidad, tamanyo, sprites, sprite, collisionRad, simetriaActivada,
				simetriaHorizontal);
		this.contenido = contenido;
	}


	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public void pintarString(ComponentePapel cp) {
		cp.getGraficos().drawString(contenido, x, y);
	}
	
	
}
