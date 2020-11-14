package clasesBase;
import java.awt.Color;
import java.util.ArrayList;

public class Texto extends Sprite {
	
	private String contenido;

	
	public Texto(int x, int y, int xV, int yV, Color color, int tamanyo, ArrayList<String> sprites, int sprite,
			double collisionRad, boolean simetriaActivada, boolean simetriaHorizontal, String contenido) {
		super(x, y, xV, yV, color, tamanyo, sprites, sprite, collisionRad, simetriaActivada, simetriaHorizontal);
		this.contenido = contenido;
	}
	
	

	@Override
	public String toString() {
		return "Texto [contenido=" + contenido + ", getX()=" + getX() + ", getY()=" + getY() + ", getSprites()="
				+ getSprites() + ", getSprite()=" + getSprite() + ", getCollisionRad()=" + getCollisionRad()
				+ ", getxV()=" + getxV() + ", getyV()=" + getyV() + ", getColor()=" + getColor() + ", getTamanyo()="
				+ getTamanyo() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
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
