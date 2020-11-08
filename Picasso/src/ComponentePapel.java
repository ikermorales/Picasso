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
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent.KeyBinding;

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
	
	private static HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos;
	
	
	public ComponentePapel(Papel p) {

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
				generarEstado(p);
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
								ventanaEdicionTexto = new VentanaEdicionTexto(ComponentePapel.this, spritesTodos, p);
								forRepaint();
								break;
							} else if(distance < sprite.getCollisionRad() && sprite.getClass().getName().contains("Sprite")) {
								ventanaEdicion = new VentanaEdicion(ComponentePapel.this, spritesTodos, p);
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
					//No hacer nada, por que lo hace bien aun así
				}

				xActual = e.getX();
				yActual = e.getY();
				
				deshaciendo = false;

				pixelado = new Sprite(xActual, yActual, xVieja, yVieja, graficos.getColor(), tamanyo, pinceles, selectorSprite, tamanyo);
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
				} else if(sprite.getClass().getName().contains("Sprite")) {
					sprite.pintar(this, img);
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


	public void borrarTodo(Papel p){
		Color colorActual = graficos.getColor();
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
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un problema con el estado al borrar todo.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.repaint();
		graficos.setColor(colorActual);
	}


	public static Image getImagen() {
		return imagen;
	}




	public static void setImagen(Image imagen) {
		ComponentePapel.imagen = imagen;
	}




	public static Graphics2D getGraficos() {
		return graficos;
	}




	public static void setGraficos(Graphics2D graficos) {
		ComponentePapel.graficos = graficos;
	}




	public static BufferedImage getImagenBuff() {
		return imagenBuff;
	}




	public static void setImagenBuff(BufferedImage imagenBuff) {
		ComponentePapel.imagenBuff = imagenBuff;
	}




	public Thread getHiloBuff() {
		return hiloBuff;
	}




	public void setHiloBuff(Thread hiloBuff) {
		this.hiloBuff = hiloBuff;
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




	public boolean isDeshaciendo() {
		return deshaciendo;
	}




	public void setDeshaciendo(boolean deshaciendo) {
		this.deshaciendo = deshaciendo;
	}




	public static ArrayList<Integer> getContadorImagenMaximo() {
		return contadorImagenMaximo;
	}




	public static void setContadorImagenMaximo(ArrayList<Integer> contadorImagenMaximo) {
		ComponentePapel.contadorImagenMaximo = contadorImagenMaximo;
	}




	public File getCarpetaProcesoAnterior() {
		return carpetaProcesoAnterior;
	}




	public void setCarpetaProcesoAnterior(File carpetaProcesoAnterior) {
		this.carpetaProcesoAnterior = carpetaProcesoAnterior;
	}




	public File[] getProcesosAnteriores() {
		return procesosAnteriores;
	}




	public void setProcesosAnteriores(File[] procesosAnteriores) {
		this.procesosAnteriores = procesosAnteriores;
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




	public ArrayList<Color> getColores() {
		return colores;
	}




	public void setColores(ArrayList<Color> colores) {
		this.colores = colores;
	}
	
	

	public ArrayList<String> getPinceles() {
		return pinceles;
	}




	public void setPinceles(ArrayList<String> pinceles) {
		this.pinceles = pinceles;
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




	public Sprite getPixelado() {
		return pixelado;
	}




	public void setPixelado(Sprite pixelado) {
		this.pixelado = pixelado;
	}




	public int getSelectorSprite() {
		return selectorSprite;
	}




	public void setSelectorSprite(int selectorSprite) {
		this.selectorSprite = selectorSprite;
	}




	public Image getImg() {
		return img;
	}




	public void setImg(Image img) {
		this.img = img;
	}




	public Thread getHiloImagen() {
		return hiloImagen;
	}




	public void setHiloImagen(Thread hiloImagen) {
		this.hiloImagen = hiloImagen;
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




	public VentanaEdicion getVentanaEdicion() {
		return ventanaEdicion;
	}




	public void setVentanaEdicion(VentanaEdicion ventanaEdicion) {
		this.ventanaEdicion = ventanaEdicion;
	}




	public static HashMap<Integer, ArrayList<ArrayList<Sprite>>> getHashDibujos() {
		return hashDibujos;
	}




	public static void setHashDibujos(HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos) {
		ComponentePapel.hashDibujos = hashDibujos;
	}
	
	





}