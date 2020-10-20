import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

public class ComponentePapel extends JComponent {


	private Image imagen;
	private Graphics2D graficos;
	private int xActual;
	private int yActual; 
	private int xVieja; 
	private int yVieja;
	private static Color colorPincel;
	private static int pincel;
	private int tamanyo = 8; //cambiarr
	private int[] xcuadrado = new int[4];
	private int[] ycuadrado = new int [4];
	private Polygon cuadrado;
	private int[] xcirculo = new int[4];
	private int[] ycirculo = new int [4];
	private Polygon circulo;
	private static int contadorImagen = 0;
	private KeyListener listener;


	public ComponentePapel(Papel p) {
		setDoubleBuffered(false);
		setFocusable(true);
		requestFocus();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xVieja = e.getX();
				yVieja = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				generarEstado();
			}

		});


		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				xActual = e.getX();
				yActual = e.getY();

				if (graficos != null) {
					graficos.setPaint(colorPincel);
					if (pincel == 0) {	//Pincel Bolígrafo
						graficos.drawLine(xVieja, yVieja, xActual, yActual); 
						repaint();
						xVieja = xActual;
						yVieja = yActual; 
					}
					else if(pincel == 1) { //Pincel Pixelado
						xcuadrado[0] = xActual;
						ycuadrado[0] = yActual;


						xcuadrado[1] = xActual + tamanyo;
						ycuadrado[1] = yActual;


						xcuadrado[2] = xActual + tamanyo;
						ycuadrado[2] = yActual + tamanyo;

						xcuadrado[3] = xActual;
						ycuadrado[3] = yActual + tamanyo;

						cuadrado = new Polygon(xcuadrado, ycuadrado, 4);

						graficos.fill(cuadrado);
						graficos.draw(cuadrado);

						repaint();
						xVieja = xActual;
						yVieja = yActual; 
						
					} 
					else if(pincel== 2) {
						graficos.fillOval(xActual, yActual, tamanyo, tamanyo);
						repaint();
						xVieja = xActual;
						yVieja = yActual;
					}

					else {
						System.out.println("no hay mas pinceles");
					}
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					cargarImagenAnterior(graficos);
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


	//TODO METODO Borrar
	public void clear() {
		graficos.setPaint(new Color(255,255,255));
		graficos.fillRect(0, 0, getSize().width, getSize().height);
		graficos.setPaint(colorPincel);
		repaint();
	}

	public void generarEstado() {
		Dimension d = getSize();
		BufferedImage imagen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = imagen.createGraphics();
		this.print(g2d);
		g2d.dispose();
		try {
			File file = new File("proceso/" + contadorImagen + ".jpg");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(imagen, "png", file);
			contadorImagen++;
			out.close();
		} catch (IOException e) {
			System.out.println("No ha funcionado el generador de estados.");
		}
	}
	

	public void cargarImagenAnterior(Graphics g) {
		if(contadorImagen >= 2) {
			Dimension d = getSize();
			BufferedImage imagen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
			contadorImagen = contadorImagen - 2;
			File imagenAnterior = new File("proceso/" + contadorImagen + ".jpg");
			try {
				imagen = ImageIO.read(imagenAnterior);
				g.drawImage(imagen, 0, 0, getWidth(), getHeight(), Color.BLACK, null);
				g = imagen.createGraphics();
				contadorImagen = contadorImagen + 1;
				repaint();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	public int getTamanyo() {
		return tamanyo;
	}
	public int[] getXcuadrado() {
		return xcuadrado;
	}
	public int[] getYcuadrado() {
		return ycuadrado;
	}
	public Polygon getCuadrado() {
		return cuadrado;
	}
	public int[] getXcirculo() {
		return xcirculo;
	}
	public int[] getYcirculo() {
		return ycirculo;
	}
	public Polygon getCirculo() {
		return circulo;
	}
	public int getContadorImagen() {
		return contadorImagen;
	}
	public KeyListener getListener() {
		return listener;
	}
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	public void setXcuadrado(int[] xcuadrado) {
		this.xcuadrado = xcuadrado;
	}
	public void setYcuadrado(int[] ycuadrado) {
		this.ycuadrado = ycuadrado;
	}
	public void setCuadrado(Polygon cuadrado) {
		this.cuadrado = cuadrado;
	}
	public void setXcirculo(int[] xcirculo) {
		this.xcirculo = xcirculo;
	}
	public void setYcirculo(int[] ycirculo) {
		this.ycirculo = ycirculo;
	}
	public void setCirculo(Polygon circulo) {
		this.circulo = circulo;
	}
	public static void setContadorImagen(int contadorImagen) {
		ComponentePapel.contadorImagen = contadorImagen;
	}
	public void setListener(KeyListener listener) {
		this.listener = listener;
	}
}