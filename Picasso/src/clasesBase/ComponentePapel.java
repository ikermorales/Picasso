package clasesBase;
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent.KeyBinding;

import org.apache.commons.io.FileUtils;

import formaStuff.Circulo;
import formaStuff.Forma;
import ventanas.Papel;
import ventanas.VentanaEdicion;
import ventanas.VentanaEdicionFormas;
import ventanas.VentanaEdicionTexto;

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
	private Color colorActual;
	private int opacidad = 255;

	private static int tamanyo = 8;

	private static int contadorImagen = 0;

	private boolean deshaciendo = false;
	private static ArrayList<Integer> contadorImagenMaximo = new ArrayList<>();

	private File carpetaProcesoAnterior = new File("proceso/");
	private File[] procesosAnteriores = carpetaProcesoAnterior.listFiles();

	private Thread hiloArcoiris;
	private boolean rainbowActivado;
	private static ArrayList<Color> colores = new ArrayList<>();;

	private ArrayList<String> pinceles;
	private ArrayList<Sprite> dibujos;
	private ArrayList<ArrayList<Sprite>> dibujosGrandes;
	private Sprite pixelado;
	private int selectorSprite = 0;

	private Image img;
	private Thread hiloImagen;

	private int xConstante;
	private int yConstante;

	private VentanaEdicion ventanaEdicion;
	private VentanaEdicionTexto ventanaEdicionTexto;
	private VentanaEdicionFormas ventanaEdicionFormas;
	
	private static HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos;

	private boolean simetriaActivada;
	private boolean simetriaHorizontal;

	private HashMap<Integer, Forma> hashFormasTecleadas = new HashMap<>();
	
	private ArrayList<Sprite> formasUsadas = new ArrayList<>();


	public ComponentePapel(Papel p, Logger logger) {

		setDoubleBuffered(false);
		setFocusable(true);
		requestFocus();

		pinceles = new ArrayList<>();
		pinceles.add("punto.png");
		dibujos = new ArrayList<>();
		dibujosGrandes = new ArrayList<>();
		hashDibujos = new HashMap<>();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xVieja = e.getX();
				yVieja = e.getY();

			}

			public void mouseReleased(MouseEvent e) {		
				if(SwingUtilities.isLeftMouseButton(e)) {
					generarEstado(p);
				}
			}

			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 3) {

					for(Iterator<ArrayList<Sprite>> spritesIterador = dibujosGrandes.iterator(); spritesIterador.hasNext();) {
						ArrayList<Sprite> spritesTodos = spritesIterador.next();

						for (Iterator<Sprite> spriteIterador = spritesTodos.iterator(); spriteIterador.hasNext();) {
							Sprite sprite = spriteIterador.next();

							double dx = sprite.getX() - xConstante;
							double dy = sprite.getY() - yConstante;
							double distance = Math.sqrt(dx * dx + dy * dy);

							if(distance < sprite.getCollisionRad() && sprite.getClass().getName().contains("Texto")) {
								ventanaEdicionTexto = new VentanaEdicionTexto(ComponentePapel.this, spritesTodos, p, logger);
								forRepaint();
								break;
							} else if(distance < sprite.getCollisionRad() && sprite.getClass().getName().contains("Sprite")) {
								ventanaEdicion = new VentanaEdicion(ComponentePapel.this, spritesTodos, p, logger);
								forRepaint();
								break;
							} else if(distance < sprite.getCollisionRad() && (sprite.getClass().getName().contains("Cuadrado") || sprite.getClass().getName().contains("Circulo") || sprite.getClass().getName().contains("Triangulo") || sprite.getClass().getName().contains("Ovalo"))) {
								ventanaEdicionFormas = new VentanaEdicionFormas((Forma) sprite, ComponentePapel.this, p);
								add(ventanaEdicionFormas);
								forRepaint();
								break;
							}

						}
					}
				}
			}


		});


		addMouseMotionListener(new MouseMotionListener() {


			public void mouseMoved(MouseEvent e) {
				xConstante = e.getX();
				yConstante = e.getY();
			}

			public void mouseDragged(MouseEvent e) {

				if(SwingUtilities.isLeftMouseButton(e)) {

					procesosAnteriores = carpetaProcesoAnterior.listFiles();

					for (File file : procesosAnteriores) {
						if(contadorImagen < Integer.parseInt(file.getName().substring(0,1))) {
							file.delete();			
						}
					}

					try {
						for (Integer i : hashDibujos.keySet()) {
							if(i > contadorImagen) {
								hashDibujos.remove(i);
								System.out.println(contadorImagen + " ; " + hashDibujos.keySet());
							}
						}
					} catch (Exception e2) {
						//No hacer nada, por que lo hace bien aun as�
					}
					
					xConstante = e.getX();
					yConstante = e.getY();
					xActual = e.getX();
					yActual = e.getY();

					deshaciendo = false;

					Color colorActual = getGraficos().getColor();
					getGraficos().setColor(new Color(colorActual.getRed(), colorActual.getGreen(), colorActual.getBlue(), opacidad));

					pixelado = new Sprite(xActual, yActual, xVieja, yVieja, graficos.getColor(), opacidad, tamanyo, pinceles, selectorSprite, tamanyo, simetriaActivada, simetriaHorizontal);

					if(simetriaActivada && simetriaHorizontal) {
						Sprite pixeladoSimetrico = new Sprite(ComponentePapel.this.getWidth() - xActual, ComponentePapel.this.getHeight() - yActual, ComponentePapel.this.getWidth() - xVieja, ComponentePapel.this.getHeight() - yVieja, graficos.getColor(), opacidad, tamanyo, pinceles, selectorSprite, tamanyo, simetriaActivada, simetriaHorizontal);
						dibujos.add(pixeladoSimetrico);
					} else if (simetriaActivada && !simetriaHorizontal) {
						Sprite pixeladoSimetrico = new Sprite(ComponentePapel.this.getWidth() - xActual, yActual, ComponentePapel.this.getWidth() - xVieja, yVieja, graficos.getColor(), opacidad, tamanyo, pinceles, selectorSprite, tamanyo, simetriaActivada, simetriaHorizontal);
						dibujos.add(pixeladoSimetrico);
					}

					dibujos.add(pixelado);


					for(Iterator<ArrayList<Sprite>> spritesIterador = dibujosGrandes.iterator(); spritesIterador.hasNext();) {
						ArrayList<Sprite> spritesTodos = spritesIterador.next();

						if(spritesTodos.isEmpty() || spritesTodos == null) {
							spritesIterador.remove();
						}
					}

					xVieja = xActual;
					yVieja = yActual;

					forRepaint();
					deshaciendo = false;
				}

			}
		});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					for(Iterator<ArrayList<Sprite>> spritesIterador = dibujosGrandes.iterator(); spritesIterador.hasNext();) {
						ArrayList<Sprite> spritesTodos = spritesIterador.next();

						for (Iterator<Sprite> spriteIterador = spritesTodos.iterator(); spriteIterador.hasNext();) {
							Sprite sprite = spriteIterador.next();

							double dx = sprite.getX() - xConstante;
							double dy = sprite.getY() - yConstante;
							double distance = Math.sqrt(dx * dx + dy * dy);

							if(distance < sprite.getCollisionRad()) {
								try{
									spritesIterador.remove();
									forRepaint();
									break;
								} catch(IllegalStateException e2) {
									//NO HACER NADA
								}

							}	
						}
					}
					deshaciendo = false;
				}  else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) && contadorImagen > 1) {
					deshaciendo = true;  
					dibujosGrandes = new ArrayList<>();
					dibujos = new ArrayList<>();              
					forRepaint();             
					contadorImagen = contadorImagen - 2;
					setDibujosGrandes(getHashDibujos().get(contadorImagen));
					hashDibujos.replace(contadorImagen, dibujosGrandes);
					forRepaint();
					generarEstado(p);

					deshaciendo = true;
				} else if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
						&& deshaciendo && hashDibujos.containsKey(contadorImagen)) {
					dibujosGrandes = new ArrayList<>();
					dibujos = new ArrayList<>();                        
					forRepaint();                          
					try {
						setDibujosGrandes(getHashDibujos().get(contadorImagen));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					forRepaint();
					generarEstado(p);

				}  else {
					for (Integer i : hashFormasTecleadas.keySet()) {
						if(i == e.getKeyCode()) {
							Sprite forma = new Sprite();
							forma = hashFormasTecleadas.get(i);
							
							if(formasUsadas.contains(forma)) {
								forma.setX(xConstante);
								forma.setY(yConstante);
								generarEstado(p);
								forRepaint();
							} else {
								forma.setX(xConstante);
								forma.setY(yConstante);
								dibujos.add(forma);
								formasUsadas.add(forma);
								generarEstado(p);
								forRepaint();
							}
							

						}
					}
				}
			} 


			public void keyReleased (KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
					generarEstado(p);
				}
			}

		});

		//INIT
		for (File file : procesosAnteriores) {
			file.delete();

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


	public void forRepaint() {
		Color colorActual = graficos.getColor();
		clear();
		for (ArrayList<Sprite> spritesArray : dibujosGrandes) {
			for (Sprite sprite : spritesArray) {
				//sprite.pintar(ComponentePapel.this, img);
				graficos.setColor(sprite.getColor());
				if (sprite.getClass().getName().contains("Texto"))  {
					Texto text = (Texto) sprite;
					text.pintarString(this);
					repaint();
					
				} else if(sprite.getClass().getName().contains("Circulo")   || 
						  sprite.getClass().getName().contains("Triangulo") ||
						  sprite.getClass().getName().contains("Cuadrado")  ||
						  sprite.getClass().getName().contains("Ovalo"))    {
					Forma f = (Forma) sprite;
					f.pintarForma(this, f, f.getX(), f.getY());
					repaint();
				} else if(sprite.getClass().getName().contains("Sprite")) {
					sprite.pintar(this);
					repaint();
				} 
			}
		}
		graficos.setColor(colorActual);

	}



	public void generarEstado(Papel p) {
		BufferedImage imagen = new BufferedImage((p.getAnchura() - 16), (p.getAltura() - 39), BufferedImage.TYPE_INT_RGB);
		this.paint(imagen.createGraphics());

		try {
			File file = new File("proceso/" + contadorImagen + ".jpg");

			ArrayList<ArrayList<Sprite>> copiaDibujos = new ArrayList<>();
			copiaDibujos = (ArrayList<ArrayList<Sprite>>) dibujosGrandes.clone();
			dibujos = new ArrayList<>();
			dibujosGrandes.add(dibujos);

			if(hashDibujos.containsKey(contadorImagen)) {
				hashDibujos.replace(contadorImagen, copiaDibujos);
			} else {
				hashDibujos.put(contadorImagen, copiaDibujos);
			}

			OutputStream out = new FileOutputStream(file);
			ImageIO.write(imagen, "jpg", file);	
			contadorImagen++;
			if (!contadorImagenMaximo.contains(contadorImagen)) {
				contadorImagenMaximo.add(contadorImagen);
			}
			out.close();

		} catch (IOException e) {
			System.out.println("No ha funcionado el generador de estados");
		}
	}


	public void dibujarRainbow() {	

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
							hiloArcoiris.sleep(50);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(null, "Hubo un problema con el Thread del Arcoiris");
						}
					}
				}
			}});	
	}


	public void borrarTodo() {
		for(File file : carpetaProcesoAnterior.listFiles()) {
			if(!file.getName().equals("0.jpg")) {
				file.delete();
			}
		}
		hashDibujos = new HashMap<>();
		dibujosGrandes = new ArrayList<>();
		dibujos = new ArrayList<>();
		contadorImagen = 0;
		forRepaint();
	}


	public void cargarDibujoAutomatico(String usuarioEscogido, String dibujoEscogido) {	
		try {
			HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujosNuevos;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes/" + usuarioEscogido + "/galeria/" + dibujoEscogido));
			hashDibujosNuevos = (HashMap<Integer, ArrayList<ArrayList<Sprite>>>) ois.readObject();
			setHashDibujos(hashDibujosNuevos);                                               
			setDibujosGrandes(hashDibujosNuevos.get(Collections.max(hashDibujosNuevos.keySet())));
			setContadorImagen(Collections.max(hashDibujosNuevos.keySet()));                  
			forRepaint();
			ois.close();

			String carpetaDibujoEscogido = dibujoEscogido.substring(0, dibujoEscogido.length() - 4);
			File dondeEstaban = new File("clientes/" + usuarioEscogido + "/galeria/" + carpetaDibujoEscogido + "/");

			for (File file : getCarpetaProcesoAnterior().listFiles()) {
				if(!file.getName().equals("0.jpg")) {
					file.delete();
				}
			}

			for (File file : dondeEstaban.listFiles()) {
				FileUtils.copyFileToDirectory(file, getCarpetaProcesoAnterior());
			}


		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}






	public static Graphics2D getGraficos() {
		return graficos;
	}



	public int getxActual() {
		return xActual;
	}




	public void setxActual(int xActual) {
		this.xActual = xActual;
	}




	public int getyActual() {
		return yActual;
	}




	public void setyActual(int yActual) {
		this.yActual = yActual;
	}




	public int getxVieja() {
		return xVieja;
	}




	public void setxVieja(int xVieja) {
		this.xVieja = xVieja;
	}




	public int getyVieja() {
		return yVieja;
	}




	public void setyVieja(int yVieja) {
		this.yVieja = yVieja;
	}




	public static int getPincel() {
		return pincel;
	}




	public static void setPincel(int pincel) {
		ComponentePapel.pincel = pincel;
	}




	public Color getColorActual() {
		return colorActual;
	}




	public void setColorActual(Color colorActual) {
		this.colorActual = colorActual;
	}




	public static int getTamanyo() {
		return tamanyo;
	}




	public static void setTamanyo(int tamanyo) {
		ComponentePapel.tamanyo = tamanyo;
	}




	public static int getContadorImagen() {
		return contadorImagen;
	}




	public void setContadorImagen(int contadorImagen) {
		ComponentePapel.contadorImagen = contadorImagen;
	}



	public File getCarpetaProcesoAnterior() {
		return carpetaProcesoAnterior;
	}



	public File[] getProcesosAnteriores() {
		return procesosAnteriores;
	}



	public Thread getHiloArcoiris() {
		return hiloArcoiris;
	}



	public boolean isRainbowActivado() {
		return rainbowActivado;
	}




	public void setRainbowActivado(boolean rainbowActivado) {
		this.rainbowActivado = rainbowActivado;
	}



	public ArrayList<String> getPinceles() {
		return pinceles;
	}



	public ArrayList<Sprite> getDibujos() {
		return dibujos;
	}



	public void setDibujos(ArrayList<Sprite> dibujos) {
		this.dibujos = dibujos;
	}



	public ArrayList<ArrayList<Sprite>> getDibujosGrandes() {
		return dibujosGrandes;
	}




	public void setDibujosGrandes(ArrayList<ArrayList<Sprite>> dibujosGrandes) {
		this.dibujosGrandes = dibujosGrandes;
	}



	public int getSelectorSprite() {
		return selectorSprite;
	}




	public void setSelectorSprite(int selectorSprite) {
		this.selectorSprite = selectorSprite;
	}



	public static HashMap<Integer, ArrayList<ArrayList<Sprite>>> getHashDibujos() {
		return hashDibujos;
	}




	public static void setHashDibujos(HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos) {
		ComponentePapel.hashDibujos = hashDibujos;
	}




	public boolean isSimetriaActivada() {
		return simetriaActivada;
	}




	public void setSimetriaActivada(boolean simetriaActivada) {
		this.simetriaActivada = simetriaActivada;
	}




	public boolean isSimetriaHorizontal() {
		return simetriaHorizontal;
	}




	public void setSimetriaHorizontal(boolean simetriaHorizontal) {
		this.simetriaHorizontal = simetriaHorizontal;
	}




	public int getOpacidad() {
		return opacidad;
	}




	public void setOpacidad(int opacidad) {
		this.opacidad = opacidad;
	}




	public HashMap<Integer, Forma> getHashFormasTecleadas() {
		return hashFormasTecleadas;
	}




	public int getxConstante() {
		return xConstante;
	}




	public void setxConstante(int xConstante) {
		this.xConstante = xConstante;
	}




	public int getyConstante() {
		return yConstante;
	}




	public void setyConstante(int yConstante) {
		this.yConstante = yConstante;
	}
	
	





}