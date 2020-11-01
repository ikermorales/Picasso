import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VentanaMenu extends JFrame {

	private JPanel panelPinceles;
	private JComboBox<String> pincelesCombo;

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
	

	public VentanaMenu(ComponentePapel cp, Papel p, String usuario) {
		setTitle("Menu");
		setSize(new Dimension(310, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,1)); 

		panelPinceles = new JPanel();
		panelPinceles.setBorder(new TitledBorder("Pinceles: "));
		pincelesCombo = new JComboBox<>();
		pincelesCombo.addItem("Bolígrafo");
		pincelesCombo.addItem("Pixelado");
		pincelesCombo.addItem("Suave");
		pincelesCombo.addItem("Triangular");
		pincelesCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cp.setPincel(pincelesCombo.getSelectedIndex());

			}
		});
		panelPinceles.add(pincelesCombo);
		add(panelPinceles);
		botonPaleta = new JButton("Paleta");
		botonPaleta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paleta = new Paleta(cp);
			}
		});
		add(botonPaleta);

		botonProceso = new JButton("Proceso");
		botonProceso.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				proceso = new VentanaProceso(cp, p);


			}
		});
		add(botonProceso);
		
		botonArcoiris = new JButton("Arcoíris");
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
		
		
		botonBorrarTodo = new JButton("Nuevo dibujo");
		botonBorrarTodo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cp.borrarTodo(p);
				
			}
		});
		add(botonBorrarTodo);
		
		
		botonTexto = new JButton("Texto");
		botonTexto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaTexto = new VentanaTexto(cp, p);
	
			}
		});
		add(botonTexto);
		
		
		botonGuardar = new JButton("Guardar");
		botonGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaGuardar = new VentanaGuardar(p, cp, usuario);
				
			}
		});
		add(botonGuardar);


		sliderTamanyo = new JSlider(); 
		sliderTamanyo.setPreferredSize(new Dimension(120, 40));
		sliderTamanyo.setSize(getMinimumSize());
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
		panelTamanyo.getMinimumSize();
		panelTamanyo.add(new JLabel(new ImageIcon()));
		panelTamanyo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); //TOC
		panelTamanyo.add(sliderTamanyo);
		add(panelTamanyo);
		
		setVisible(true);
	}

}
