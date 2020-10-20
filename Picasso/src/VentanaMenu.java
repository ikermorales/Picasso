import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class VentanaMenu extends JFrame {
	
	private JPanel panelPinceles;
	private JComboBox<String> pincelesCombo;
	
	private JButton botonPaleta;
	private Paleta paleta;
	
	private JButton botonProceso;
	private VentanaProceso proceso;
	
	public VentanaMenu(ComponentePapel cp, Papel p, Graphics g) {
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
		pincelesCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cp.setPincel(pincelesCombo.getSelectedIndex());
				System.out.println(cp.getPincel());
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
				proceso = new VentanaProceso(cp);
				
				
			}
		});
		add(botonProceso);
		
		
		setVisible(true);
	}
	
}
