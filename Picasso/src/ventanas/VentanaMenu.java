package ventanas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clasesBase.ComponentePapel;
import clasesBase.Musica;
import formaStuff.Forma;


public class VentanaMenu extends JFrame {

	private JPanel panelPinceles;
	private JComboBox<String> pincelesCombo;
	private JLabel pincelPng;

	private JButton botonPaleta;
	private Paleta paleta;

	private JButton botonProceso;
	private VentanaProceso proceso;

	private JButton botonArcoiris;

	private JButton botonBorrarTodo;

	private JButton botonTexto;
	private VentanaTexto ventanaTexto;

	private JPanel panelTamanyo;
	private JSlider sliderTamanyo;
	private JLabel tamanyoPng;

	private JPanel panelPuedePintar;
	private JCheckBox checkPuedePintar;
	private Thread hiloCheck;

	private JMenu menuArchivo;

	private JMenuItem guardarArchivo;
	private VentanaGuardar ventanaGuardar;

	private JMenuItem cargarArchivo;
	private VentanaCargar ventanaCargar;

	private JMenuBar barra;

	private JMenuItem stats;

	private JMenu menuMusica;
	private JMenuItem stop;
	private static Musica m = new Musica();

	private JMenu menuGaleria;

	private JPanel transparenciaPanel;
	private JSlider barratransparencia;

	private JPanel simetriaPanel;
	private JCheckBox simetria;
	private JButton botonSimetria;
	private int tipoSimetria = 0;

	private Border emptyBorder = BorderFactory.createEmptyBorder();
	
	private ArrayList<Forma> formas = new ArrayList<>();
	private JButton botonFormas;
	
	private VentanaCrearFormas vCF;
	private JButton botonCrearFormas;
	
	private JPanel panelInformacion;


	public VentanaMenu(ComponentePapel cp, Papel p, String usuario, Logger logger) {
		setTitle("Menu");
		setSize(new Dimension(310, 820));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(11,1)); 
		setResizable(false);



		botonPaleta = new JButton("Paleta", new ImageIcon("iconos/paleta.png"));
		botonPaleta.setBackground(Color.WHITE);
		botonPaleta.setBorder(emptyBorder);
		//botonPaleta.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonPaleta.setForeground(new Color(111, 195, 179));
		botonPaleta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paleta = new Paleta(cp, logger);
				logger.info( "Se ha abierto la paleta de colores.");
			}
		});
		add(botonPaleta);


		botonProceso = new JButton("Historial", new ImageIcon("iconos/historial.png"));
		botonProceso.setBackground(Color.WHITE);    
		botonProceso.setBorder(emptyBorder);
		botonProceso.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonProceso.setForeground(new Color(111, 195, 179));
		botonProceso.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(cp.getContadorImagen() <= 1)) {
					proceso = new VentanaProceso(cp, p, logger);
					logger.info( "Se ha abierto el Historial.");
				} else {
					JOptionPane.showMessageDialog(null, "Dibuja algo para acceder al historial.");
				}


			}
		});
		add(botonProceso);


		botonArcoiris = new JButton("Arco�ris", new ImageIcon("iconos/arcoiris.png"));
		botonArcoiris.setBackground(Color.WHITE);      
		botonArcoiris.setBorder(emptyBorder);
		//botonArcoiris.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonArcoiris.setForeground(new Color(111, 195, 179));
		botonArcoiris.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!cp.isRainbowActivado()) {
					cp.setRainbowActivado(true);
					cp.dibujarRainbow();
					logger.info( "Pintura Arcoiris activado.");
					cp.getHiloArcoiris().start();
				}
			}
		});
		add(botonArcoiris);


		botonBorrarTodo = new JButton("Nuevo dibujo", new ImageIcon("iconos/papel.png"));
		botonBorrarTodo.setBackground(Color.WHITE);  
		botonBorrarTodo.setBorder(emptyBorder);
		botonBorrarTodo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonBorrarTodo.setForeground(new Color(111, 195, 179));
		botonBorrarTodo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cp.borrarTodo();
				logger.info( "Se ha borrado todo el dibujo.");
			}
		});
		add(botonBorrarTodo);


		botonTexto = new JButton("Texto", new ImageIcon("iconos/texto.png"));
		botonTexto.setBackground(Color.WHITE);    
		botonTexto.setBorder(emptyBorder);
		//botonTexto.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonTexto.setForeground(new Color(111, 195, 179));
		botonTexto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaTexto = new VentanaTexto(cp, p, logger);
				logger.info( "Se ha abierto la ventana para insertar texto.");

			}
		});
		add(botonTexto);


		panelPinceles = new JPanel();
		panelPinceles.setBackground(Color.WHITE);
		panelPinceles.setLayout(new GridBagLayout());
		pincelPng = new JLabel();
		pincelPng.setIcon(new ImageIcon("iconos/caligrafico.png"));
		pincelesCombo = new JComboBox<>();
		pincelesCombo.setBackground(Color.WHITE);
		pincelesCombo.setForeground(new Color(111, 195, 179));
		pincelesCombo.addItem("Caligrafico");
		pincelesCombo.addItem("Suave");
		pincelesCombo.addItem("Pixelado");

		int sizePincelesCombo = pincelesCombo.getItemCount();
		for (int i = 0; i < sizePincelesCombo; i++) {
			String item = pincelesCombo.getItemAt(i);
			cp.getPinceles().add(item);
		}

		pincelesCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cp.setSelectorSprite(pincelesCombo.getSelectedIndex());
				pincelPng.setIcon(new ImageIcon("iconos/" + pincelesCombo.getSelectedItem() + ".png"));
				logger.info( "Se ha cambiado de pincel.");
				if(pincelesCombo.getSelectedIndex() == 0) {
					sliderTamanyo.disable();
				} else {
					sliderTamanyo.enable();
				}
			}
		});
		panelPinceles.add(pincelesCombo);
		panelPinceles.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelPinceles.add(pincelPng);
		add(panelPinceles);


		sliderTamanyo = new JSlider(); 
		sliderTamanyo.setBackground(Color.WHITE);
		sliderTamanyo.setForeground(Color.white);
		sliderTamanyo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		sliderTamanyo.setPreferredSize(new Dimension(100, 40));
		TitledBorder tituloTamanyo = new TitledBorder("Tama�o: " + cp.getTamanyo());
		sliderTamanyo.setBorder(tituloTamanyo);
		tituloTamanyo.setTitleColor(new Color(111, 195, 179));
		sliderTamanyo.setValue(7);
		sliderTamanyo.setPaintTicks(false);
		sliderTamanyo.setMajorTickSpacing(20);
		sliderTamanyo.setMajorTickSpacing(5);
		sliderTamanyo.setEnabled(false);
		sliderTamanyo.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				cp.setTamanyo(sliderTamanyo.getValue());
				TitledBorder tituloTamanyo = new TitledBorder("Tama�o: " + sliderTamanyo.getValue());
				tituloTamanyo.setTitleColor(new Color(111, 195, 179));
				sliderTamanyo.setBorder(tituloTamanyo);

			}
		});
		panelTamanyo = new JPanel();
		panelTamanyo.setBackground(Color.white);
		panelTamanyo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelTamanyo.setLayout(new GridBagLayout());
		tamanyoPng = new JLabel();
		tamanyoPng.setIcon(new ImageIcon("iconos/size.png"));
		panelTamanyo.getMinimumSize();
		panelTamanyo.add(new JLabel(new ImageIcon()));
		panelTamanyo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); //TOC
		panelTamanyo.add(sliderTamanyo);
		panelTamanyo.add(tamanyoPng);
		add(panelTamanyo);


		barra = new JMenuBar();
		setJMenuBar(barra);
		menuArchivo= new JMenu("Archivo");
		menuArchivo.setIcon(new ImageIcon("iconos/archivo.png"));
		guardarArchivo = new JMenuItem("Guardar", new ImageIcon("iconos/guardar.png"));
		guardarArchivo.setBackground(Color.WHITE);
		guardarArchivo.setForeground(new Color(111, 195, 179));
		cargarArchivo = new JMenuItem("Cargar", new ImageIcon("iconos/load.png"));
		cargarArchivo.setBackground(Color.WHITE);
		cargarArchivo.setForeground(new Color(111, 195, 179));

		guardarArchivo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaGuardar = new VentanaGuardar(usuario, cp.getHashDibujos(), cp, logger);
				logger.info( "Se ha abierto la ventana para guardar archivos.");
			}
		});

		cargarArchivo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaCargar = new VentanaCargar(usuario, cp.getHashDibujos(), cp, logger);
				logger.info( "Se ha abierto la ventana para cargar archivos.");

			}
		});

		menuArchivo.add(guardarArchivo);
		menuArchivo.add(cargarArchivo);
		barra.add(menuArchivo);



		barratransparencia = new JSlider(0, 255); 
		barratransparencia.setBackground(Color.WHITE);
		barratransparencia.setPreferredSize(new Dimension(140,40));
		barratransparencia.setSize(getMinimumSize());
		barratransparencia.setValue(255);
		barratransparencia.setMajorTickSpacing(100);
		barratransparencia.setMajorTickSpacing(20);
		TitledBorder tituloTransparencia = new TitledBorder("Opacidad: " + barratransparencia.getValue());
		tituloTransparencia.setTitleColor(new Color(111, 195, 179));
		barratransparencia.setBorder(tituloTransparencia); 
		barratransparencia.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				TitledBorder tituloTransparencia = new TitledBorder("Opacidad: " + barratransparencia.getValue());
				tituloTransparencia.setTitleColor(new Color(111, 195, 179));
				barratransparencia.setBorder(tituloTransparencia); 

				cp.setOpacidad(barratransparencia.getValue());
			}
		});

		transparenciaPanel = new JPanel();
		transparenciaPanel.setBackground(Color.white);
		transparenciaPanel.setLayout(new GridBagLayout());
		transparenciaPanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,0)); //TOC
		transparenciaPanel.add(barratransparencia);
		transparenciaPanel.add(new JLabel(new ImageIcon("iconos/opacidad.png")));
		transparenciaPanel.getMinimumSize();
		transparenciaPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		add(transparenciaPanel);


		menuMusica = new JMenu("Fonoteca");
		menuMusica.setIcon(new ImageIcon("iconos/disco.png"));
		barra.add(menuMusica);

		stop = new JMenuItem("Stop", new ImageIcon("iconos/mute.png"));
		stop.setBackground(Color.WHITE);
		stop.setForeground(new Color(111, 195, 179));
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				m.setActivado(false);
				m.playear(".wav");
				logger.info( "Se ha parado la m�sica.");
			}
		});
		menuMusica.add(stop);

		ArrayList<String> canciones = new ArrayList<>();
		canciones.add("Railroad");
		canciones.add("Devil");
		canciones.add("Hydra");
		canciones.add("Floral");
		canciones.add("Hell");
		canciones.add("Fanatic");
		canciones.add("Diamond");
		canciones.add("Perdition");

		for (String cancion : canciones) {
			JMenuItem cancionItem = new JMenuItem(cancion, new ImageIcon("iconos/" + cancion + ".png"));
			cancionItem.setBackground(Color.WHITE);
			cancionItem.setForeground(new Color(111, 195, 179));
			cancionItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					m.setActivado(true);
					m.playear("musica/" + cancion + ".wav");
					logger.info( "Funcion de musica activada: " + cancion + "escogida.");
				}
			});
			menuMusica.add(cancionItem);
		}




		simetria = new JCheckBox("Simetr�a");
		simetria.setForeground(new Color(111, 195, 179));
		simetria.setBackground(Color.WHITE);
		botonSimetria = new JButton(new ImageIcon("iconos/simetria.png"));
		//botonSimetria.setBackground(new Color(111, 195, 179));
		botonSimetria.setBackground(Color.WHITE);
		botonSimetria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tipoSimetria++;
				logger.info( "Se ha cambiado el tipo de simetr�a.");
				if(tipoSimetria % 2 == 0) {
					botonSimetria.setIcon(new ImageIcon("iconos/simetria.png"));
					cp.setSimetriaHorizontal(false);
				} else {
					botonSimetria.setIcon(new ImageIcon("iconos/simetria2.png"));
					cp.setSimetriaHorizontal(true);
				}
			}
		});

		simetria.setSelected(false);
		simetria.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(simetria.isSelected()) {
					cp.setSimetriaActivada(true);
					logger.info( "Simetria activada.");
				}else {
					cp.setSimetriaActivada(false);
					logger.info( "Simetria desactivada.");
				}}
		});

		simetriaPanel = new JPanel();
		simetriaPanel.setBorder(new LineBorder(Color.lightGray));
		simetriaPanel.setBackground(Color.WHITE);
		JPanel panelsimetriaBoton = new JPanel();
		panelsimetriaBoton.setBackground(Color.WHITE);
		panelsimetriaBoton.add(botonSimetria);
		simetriaPanel.setLayout(new GridLayout(1,2));
		simetriaPanel.add(panelsimetriaBoton);
		simetriaPanel.add(simetria);
		simetriaPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); //TOC
		simetriaPanel.getMinimumSize();
		add(simetriaPanel);


		menuGaleria = new JMenu("Galer�a");		
		menuGaleria.setIcon(new ImageIcon("iconos/galeria.png"));
		barra.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				menuGaleria.removeAll();
				File carpetaDibujos = new File("clientes/" + usuario + "/galeria/");
				File[] dibujos = carpetaDibujos.listFiles();
				for (File archivo : dibujos) {
					if(archivo.getName().contains(".bin")) {
						String nombre = archivo.getName();
						nombre = nombre.substring(0, nombre.length() - 4); //Le quitamos el .bin para que quede mas bonito
						JMenuItem dibujo = new JMenuItem(nombre, new ImageIcon("iconos/dibujo.png"));
						dibujo.setBackground(Color.WHITE);              
						dibujo.setForeground(new Color(111, 195, 179)); 
						dibujo.addActionListener(new ActionListener() {
	
							@Override
							public void actionPerformed(ActionEvent e) {
							cp.cargarDibujoAutomatico(usuario, archivo.getName());
								
							}
						});
						menuGaleria.add(dibujo);
						
					}
				}

			}


		});
		barra.add(menuGaleria);
		
		
		botonCrearFormas = new JButton("Taller de Formas", new ImageIcon("iconos/taller.png"));
		botonCrearFormas.setBackground(Color.WHITE);  
		botonCrearFormas.setBorder(emptyBorder);
		botonCrearFormas.setBorder(new LineBorder(Color.LIGHT_GRAY));
		botonCrearFormas.setForeground(new Color(111, 195, 179));
		botonCrearFormas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vCF = new VentanaCrearFormas(cp, logger, formas, cp.getHashFormasTecleadas());
				
			}
		});
		add(botonCrearFormas);


		panelInformacion = new JPanel();
		TitledBorder titulo = new TitledBorder("Informaci�n: ");
		panelInformacion.setBorder(titulo);
		panelInformacion.setLayout(new GridLayout(1,2));
		
		JPanel panelVacio = new JPanel();
		
		JLabel xLabel = new JLabel("X: ");
		JLabel yLabel = new JLabel("Y: ");
		JPanel panelPosicion = new JPanel();
		panelPosicion.setLayout(new GridLayout(2,1));
		panelPosicion.add(xLabel);
		panelPosicion.add(yLabel);
		
		JPanel panelColorInfo = new JPanel();
		JButton botonColorInfo = new JButton();
		panelColorInfo.setLayout(new GridBagLayout());
		panelColorInfo.add(botonColorInfo);
		
		JPanel panelPincel = new JPanel();
		JLabel labelPincel = new JLabel();
		panelPincel.add(labelPincel);
		
		JPanel panelDise�oInfo = new JPanel();
		panelDise�oInfo.add(panelColorInfo);
		panelDise�oInfo.add(panelPincel);
		
		panelInformacion.add(panelDise�oInfo);
		panelInformacion.add(panelPosicion);
		
		panelColorInfo.setBackground(Color.white);
		panelDise�oInfo.setBackground(Color.WHITE);
		panelPincel.setBackground(Color.WHITE);
		panelPosicion.setBackground(Color.WHITE);
		panelInformacion.setBackground(Color.WHITE);
		titulo.setTitleColor(new Color(111, 195, 179));
		titulo.setBorder(new LineBorder(new Color(111, 195, 179)));
	
	
		
		
		Thread hiloInformacion = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					xLabel.setText("X: " + cp.getxConstante());
					yLabel.setText("Y: " + cp.getyConstante());
					try {
						botonColorInfo.setBackground(cp.getGraficos().getColor());
					} catch(Exception e) {
						botonColorInfo.setBackground(Color.black);
					}
					labelPincel.removeAll();
					labelPincel.setIcon(new ImageIcon("iconos/" + pincelesCombo.getSelectedItem() + ".png"));
					
					repaint();
					validate();
				}
				
			}
		});
		hiloInformacion.start();
		add(panelInformacion);
		
		
		
		setVisible(true);
		logger.info("Menu inicializado correctamente.");
	}


	public JCheckBox getCheckPuedePintar() {
		return checkPuedePintar;
	}
	public void setCheckPuedePintar(JCheckBox checkPuedePintar) {
		this.checkPuedePintar = checkPuedePintar;
	}




}
