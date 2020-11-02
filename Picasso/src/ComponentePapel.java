import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class ComponentePapel extends JComponent {


	private static Image imagen;
	private static Graphics2D graficos;

	private static BufferedImage imagenBuff;
	private Thread hiloBuff;

	private int xActual;
	private int yActual; 
	private int xVieja; 
	private int yVieja;

	private static int pincel;

	private static int tamanyo = 8;

	private int[] xcuadrado = new int[4];
	private int[] ycuadrado = new int [4];
	private Polygon cuadrado;

	private static int contadorImagen = 1;
	private boolean deshaciendo = false;
	private static ArrayList<Integer> contadorImagenMaximo = new ArrayList<>();

	private int[] xTriangulo = new int[3];
	private int[] yTriangulo = new int [3];
	private Polygon triangulo;

	private File carpetaProcesoAnterior = new File("proceso/");
	private File[] procesosAnteriores = carpetaProcesoAnterior.listFiles();

	private Thread hiloArcoiris;
	private boolean rainbowActivado;
	private ArrayList<Color> colores;

	private int[] corazonTriangulo1 = new int[3];
	private int[] corazonTriangulo2 = new int [3];
	private Polygon corazon;

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
				generarEstado(p);
			}

		});


		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				xActual = e.getX();
				yActual = e.getY();

				File imagenEterea = new File("proceso/" + (contadorImagen) + ".jpg");
				if (imagenEterea.exists() && !deshaciendo) {
					Collections.sort(contadorImagenMaximo);
					Collections.reverse(contadorImagenMaximo);
					for (int i = contadorImagen; i <= contadorImagenMaximo.get(0) ; i++) {
						File imagenDesviada = new File("proceso/" + i + ".jpg");
						imagenDesviada.delete();
					}
				}

				deshaciendo = false;

				if (graficos != null) {

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
					}else if(pincel == 3) {
						xTriangulo[0] = xActual;
						yTriangulo[0] = yActual;

						xTriangulo[1] = xActual + tamanyo/2;
						yTriangulo[1] = yActual + tamanyo;

						xTriangulo[2] = xActual - tamanyo/2;
						yTriangulo[2] = yActual + tamanyo;

						triangulo = new Polygon(xTriangulo, yTriangulo, 3);
						graficos.fill(triangulo);
						graficos.draw(triangulo);
						repaint();
						xVieja = xActual;
						yVieja = yActual; 


					} else if(pincel == 4){ //Pincel Corazón
						corazonTriangulo1[0] = xActual - 2*tamanyo/34;
						corazonTriangulo1[1] = xActual + tamanyo + 2*tamanyo/34;
						corazonTriangulo1[2] = (xActual - 2*tamanyo/34 + xActual + tamanyo + 2*tamanyo/34)/2;

						corazonTriangulo2[0] = yActual + tamanyo - 2*tamanyo/3;
						corazonTriangulo2[1] = yActual + tamanyo - 2*tamanyo/3;
						corazonTriangulo2[2] = yActual + tamanyo;

						graficos.fillOval(xActual - tamanyo/12, yActual, tamanyo/2 + tamanyo/6, tamanyo/2); 
						graficos.fillOval(xActual + tamanyo/2 - tamanyo/12,	yActual, tamanyo/2 + tamanyo/6, tamanyo/2);

						corazon = new Polygon(corazonTriangulo1, corazonTriangulo2, corazonTriangulo1.length);
						graficos.fill(corazon);
						graficos.draw(corazon);
						repaint();
						xVieja = xActual;
						yVieja = yActual; 


					} else {
						System.out.println("no hay mas pinceles");
					}
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					cargarImagenAnterior(p);

				}	else if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					cargarImagenSiguiente(p);
				}		
			}
		});

		//INIT
		for (File file : procesosAnteriores) {
			if (!file.getName().equals("0.jpg")) {
				file.delete();
			}
		}

		
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

	

	public void clear() {
		Color color = (Color) graficos.getPaint();
		graficos.setPaint(new Color(255,255,255));
		graficos.fillRect(0, 0, getSize().width, getSize().height);
		graficos.setPaint(color);
		repaint();
	}

	
	
	public void generarEstado(Papel p) {
		BufferedImage imagen = new BufferedImage((p.getAnchura() - 16), (p.getAltura() - 39), BufferedImage.TYPE_INT_RGB);
		this.paint(imagen.createGraphics());

		try {
			File file = new File("proceso/" + contadorImagen + ".jpg");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(imagen, "png", file);	
			contadorImagen++;
			if (!contadorImagenMaximo.contains(contadorImagen)) {
				contadorImagenMaximo.add(contadorImagen);
			}
			out.close();

		} catch (IOException e) {
			System.out.println("No ha funcionado el generador de estados");
		}
	}

	

	public void cargarImagenAnterior(Papel p) {
		if(contadorImagen >= 2) {
			BufferedImage imagen = new BufferedImage((p.getAnchura() - 16), (p.getAltura() - 39), BufferedImage.TYPE_INT_RGB);
			contadorImagen = contadorImagen - 2;
			File imagenAnterior = new File("proceso/" + contadorImagen + ".jpg");
			try {
				imagen = ImageIO.read(imagenAnterior);
				graficos.drawImage(imagen, 0, 0, (p.getAnchura() - 16), (p.getAltura() - 39), Color.BLACK, null);
				this.paint(imagen.createGraphics());
				deshaciendo = true;
				contadorImagen++;
				if (!contadorImagenMaximo.contains(contadorImagen)) {
					contadorImagenMaximo.add(contadorImagen);
				}

				repaint();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public void cargarImagenSiguiente(Papel p) {
		BufferedImage imagen = new BufferedImage((p.getAnchura() - 16), (p.getAltura() - 39), BufferedImage.TYPE_INT_RGB);
		File imagenEterea = new File("proceso/" + (contadorImagen) + ".jpg");
		if (imagenEterea.exists() && deshaciendo) {
			try {
				imagen = ImageIO.read(imagenEterea);
				graficos.drawImage(imagen, 0, 0, (p.getAnchura() - 16), (p.getAltura() - 39), Color.BLACK, null);
				this.paint(imagen.createGraphics());
				contadorImagen = contadorImagen + 1;
				repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}

	
	
	public void cargarImagenConcreta(Integer i, Papel p) {
		BufferedImage imagen = new BufferedImage((p.getAnchura() - 16), (p.getAltura() - 39), BufferedImage.TYPE_INT_RGB);
		File imagenEterea = new File("proceso/" + (i) + ".jpg");
		if (imagenEterea.exists()) {
			try {
				imagen = ImageIO.read(imagenEterea);
				graficos.drawImage(imagen, 0, 0, (p.getAnchura() - 16), (p.getAltura() - 39), Color.BLACK, null);
				this.paint(imagen.createGraphics());
				contadorImagen = i + 1;
				deshaciendo = true;
				repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}

	
	
	public void dibujarRainbow() {	
		ArrayList<Color> colores = new ArrayList<>();

		if(colores.isEmpty()) {
			colores.add(Color.red);
			colores.add(Color.orange);
			colores.add(Color.yellow);
			colores.add(Color.green);
			colores.add(Color.cyan);
			colores.add(Color.blue);
			colores.add(Color.magenta);
		}

		hiloArcoiris = new Thread(new Runnable() {
			@Override
			public void run() {

				while(rainbowActivado == true) {

					for (int i = 0; i < colores.size(); i++) {
						graficos.setPaint(colores.get(i));
						try {
							hiloArcoiris.sleep(75);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(null, "Hubo un problema con el Thread del Arcoiris");
						}
					}
				}
			}});
	}


	
	public void borrarTodo(Papel p){
		Color color = (Color) graficos.getPaint();
		graficos.setColor(Color.white);
		graficos.fillRect(0, 0, p.getAnchura(), p.getAltura());
		p.repaint();
		FileInputStream is;
		FileOutputStream os;
		try {
			is = new FileInputStream(new File("proceso/0.jpg"));
			contadorImagen++;
			os = new FileOutputStream(new File("proceso/" + (contadorImagen-1) + ".jpg"));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			} 
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Hubo un problema con el estado al borrar todo.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.repaint();
		graficos.setPaint(color);;
	}


	
	public void guardarDibujo(Papel p, String usuarioEscogido, String nombreDibujo) {
		imagenBuff = new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = imagenBuff.createGraphics();
		this.print(g2d);
		g2d.dispose();
		try {
			File file = new File("proceso/" + (contadorImagen - 1) + ".jpg");
			imagenBuff = ImageIO.read(file);
			file = new File(usuarioEscogido + "/galeria/" + nombreDibujo + ".jpg");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(imagenBuff, "png", file);	
			JOptionPane.showMessageDialog(null, nombreDibujo + ".jpg guardado correctamente en la galería de " + usuarioEscogido);
			out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen.");
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

	public int getContadorImagen() {
		return contadorImagen;
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

	public static void setContadorImagen(int contadorImagen) {
		ComponentePapel.contadorImagen = contadorImagen;
	}


	public static ArrayList<Integer> getContadorImagenMaximo() {
		return contadorImagenMaximo;
	}


	public static void setContadorImagenMaximo(ArrayList<Integer> contadorImagenMaximo) {
		ComponentePapel.contadorImagenMaximo = contadorImagenMaximo;
	}


	public Thread getHiloArcoiris() {
		return hiloArcoiris;
	}


	public void setHiloArcoiris(Thread hiloArcoiris) {
		this.hiloArcoiris = hiloArcoiris;
	}


	public boolean isRainbowActivado() {
		return rainbowActivado;
	}


	public void setRainbowActivado(boolean rainbowActivado) {
		this.rainbowActivado = rainbowActivado;
	}

}