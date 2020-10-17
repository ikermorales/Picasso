import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class ComponentePapel extends JComponent {


	private Image imagen;
	private Graphics2D graficos;
	private int xActual;
	private int yActual; 
	private int xVieja; 
	private int yVieja;
	private static Color colorPincel;
	private static int pincel;

	public ComponentePapel() {
		setDoubleBuffered(false);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xVieja = e.getX();
				yVieja = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				xActual = e.getX();
				yActual = e.getY();

				if (graficos != null) {
					graficos.setPaint(colorPincel);
					if (pincel == 0) {
						System.out.println("Boli");
						graficos.drawLine(xVieja, yVieja, xActual, yActual); //Pincel Bolígrafo
						repaint();
						xVieja = xActual;
						yVieja = yActual; 
					}
					else if(pincel == 1) {
						System.out.println("Pixel");
						graficos.drawRect(xActual, yActual, 30, 30); //Pincel Pixelado
						
						repaint();
						xVieja = xActual;
						yVieja = yActual; 
					} else {
						System.out.println("No hay mas pinceles");
					}


				}
			}
		});
	}


	protected void paintComponent(Graphics g) {
		if (imagen == null) {
			imagen = createImage(getSize().width, getSize().height);
			graficos = (Graphics2D) imagen.getGraphics();
			graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(imagen, 0, 0, null);
	}


	//TODO METODO Pintar
	public void clear() {
		graficos.setPaint(new Color(255,255,255));
		graficos.fillRect(0, 0, getSize().width, getSize().height);
		graficos.setPaint(colorPincel);
		repaint();
	}


	public Image getImagen() {
		return imagen;
	}


	public Graphics2D getGraficos() {
		return graficos;
	}


	public int getxActual() {
		return xActual;
	}


	public int getyActual() {
		return yActual;
	}


	public int getxVieja() {
		return xVieja;
	}


	public int getyVieja() {
		return yVieja;
	}


	public Color getColorPincel() {
		return colorPincel;
	}


	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}


	public void setGraficos(Graphics2D graficos) {
		this.graficos = graficos;
	}


	public void setxActual(int xActual) {
		this.xActual = xActual;
	}


	public void setyActual(int yActual) {
		this.yActual = yActual;
	}


	public void setxVieja(int xVieja) {
		this.xVieja = xVieja;
	}


	public void setyVieja(int yVieja) {
		this.yVieja = yVieja;
	}


	public void setColorPincel(Color colorPincel) {
		this.colorPincel = colorPincel;
	}


	public int getPincel() {
		return pincel;
	}


	public void setPincel(int pincel) {
		this.pincel = pincel;
	}


}