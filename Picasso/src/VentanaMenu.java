import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	private JButton botonGuardar;
	private VentanaGuardar ventanaGuardar;
	
	private JPanel panelTamanyo;
	private JSlider sliderTamanyo;
	private JLabel tamanyoPng;
	
	private JPanel panelPuedePintar;
	private JCheckBox checkPuedePintar;
	private Thread hiloCheck;

	
	public VentanaMenu(ComponentePapel cp, Papel p, String usuario) {
		setTitle("Menu");
		setSize(new Dimension(310, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,1)); 
		
		panelPinceles = new JPanel();
		panelPinceles.setLayout(new GridBagLayout());
		pincelPng = new JLabel();
		pincelPng.setIcon(new ImageIcon("iconos/caligrafico.png"));
		panelPinceles.setBorder(new TitledBorder("Pinceles: "));
		pincelesCombo = new JComboBox<>();
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
			}
		});
		panelPinceles.add(pincelesCombo);
		panelPinceles.add(pincelPng);
		add(panelPinceles);
		
		
		botonPaleta = new JButton("Paleta", new ImageIcon("iconos/paleta.png"));
		botonPaleta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				paleta = new Paleta(cp);
			}
		});
		add(botonPaleta);
		
		
		botonProceso = new JButton("Historial", new ImageIcon("iconos/historial.png"));
		botonProceso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				proceso = new VentanaProceso(cp, p);
				
				
			}
		});
		add(botonProceso);
		

		botonArcoiris = new JButton("Arcoíris", new ImageIcon("iconos/arcoiris.png"));
		botonArcoiris.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!cp.isRainbowActivado()) {
					cp.setRainbowActivado(true);
					cp.dibujarRainbow();
					cp.getHiloArcoiris().start();
				}
			}
		});
		add(botonArcoiris);
		
		
		botonBorrarTodo = new JButton("Nuevo dibujo", new ImageIcon("iconos/papel.png"));
		botonBorrarTodo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.borrarTodo(p);	
				cp.setDibujosGrandes(new ArrayList<>());
			}
		});
		add(botonBorrarTodo);
		
		
		botonTexto = new JButton("Texto", new ImageIcon("iconos/texto.png"));
		botonTexto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaTexto = new VentanaTexto(cp, p);
	
			}
		});
		add(botonTexto);
		
		
		botonGuardar = new JButton("Guardar", new ImageIcon("iconos/guardar.png"));
		botonGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaGuardar = new VentanaGuardar(p, cp, usuario);
				
			}
		});
		add(botonGuardar);
		
		
		sliderTamanyo = new JSlider(); 
		sliderTamanyo.setPreferredSize(new Dimension(100, 40));
		sliderTamanyo.setBorder(new TitledBorder("Tamaño: " + cp.getTamanyo()));
		sliderTamanyo.setValue(7);
		sliderTamanyo.setPaintTicks(false);
		sliderTamanyo.setMajorTickSpacing(20);
		sliderTamanyo.setMajorTickSpacing(5);
		sliderTamanyo.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				cp.setTamanyo(sliderTamanyo.getValue());
				sliderTamanyo.setBorder(new TitledBorder("Tamaño: " + sliderTamanyo.getValue()));
		
			}
		});
		panelTamanyo = new JPanel();
		tamanyoPng = new JLabel();
		tamanyoPng.setIcon(new ImageIcon("iconos/size.png"));
		panelTamanyo.getMinimumSize();
		panelTamanyo.add(new JLabel(new ImageIcon()));
		panelTamanyo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); //TOC
		panelTamanyo.add(sliderTamanyo);
		panelTamanyo.add(tamanyoPng);
		add(panelTamanyo);
		

		
		setVisible(true);
	}


	public JCheckBox getCheckPuedePintar() {
		return checkPuedePintar;
	}
	public void setCheckPuedePintar(JCheckBox checkPuedePintar) {
		this.checkPuedePintar = checkPuedePintar;
	}
	
	
	
	
}
