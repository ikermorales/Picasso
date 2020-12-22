package ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import clasesBase.ComponentePapel;
import formaStuff.Circulo;
import formaStuff.Forma;
import formaStuff.*;

public class VentanaEdicionFormas extends JInternalFrame{
	
	private JPanel panelBotonera;
	
	private JSpinner spinnerAlto;
	private JPanel panelAlto;
	
	private JSpinner spinnerAncho;
	private JPanel panelAncho;
	
	private JButton recolor;
	private JPanel panelRecolor;
	
	private JCheckBox relleno;
	private JPanel panelRelleno;
	private boolean rellenado;
	
	private JPanel panelConfirmar;
	private JButton confirmar;
	
	public VentanaEdicionFormas(Forma f, ComponentePapel cp, Papel p) {
		setTitle("Edicion de Forma");
		setSize(new Dimension(230, 230));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout());
		
		
		panelBotonera = new JPanel();
		add(panelBotonera);
		panelBotonera.setLayout(new GridLayout(5,1));
		panelBotonera.setBackground(Color.WHITE);
	
		panelRelleno = new JPanel();
		panelRelleno.setBackground(Color.WHITE);
		panelBotonera.add(panelRelleno);		
		panelRelleno.add(new JLabel("Relleno: "));
		relleno = new JCheckBox();
		if(f.isEstanRellenas()) {
			relleno.setSelected(true);
		}
		panelRelleno.add(relleno);
		relleno.setBackground(Color.WHITE);
		relleno.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(relleno.isSelected()) {
					rellenado = true;
				} else {
					rellenado = false;
				}
				
			}
		});
		rellenado = f.isEstanRellenas();
		
		
		panelRecolor = new JPanel();
		panelRecolor.setBackground(Color.WHITE);
		panelBotonera.add(panelRecolor);
		panelRecolor.add(new JLabel("Recolor: "));
		recolor = new JButton();
		recolor.setBackground(f.getColor());
		recolor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PaletaForma pF = new PaletaForma(cp, recolor);
				
			}
		});
		panelRecolor.add(recolor);
		
		
		
		if(f.getClass().getName().contains("Circulo")) {
		
			panelAlto = new JPanel();
			panelBotonera.add(panelAlto);
			panelAlto.add(new JLabel("Radio: "));
			spinnerAlto = new JSpinner(new SpinnerNumberModel(((Circulo) f).getRadio(), 1, 100, 1));
			panelAlto.add(spinnerAlto);
			
			panelConfirmar = new JPanel();
			panelBotonera.add(panelConfirmar);
			confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
			panelConfirmar.add(confirmar);
			
			panelAlto.setBackground(Color.WHITE);
			panelConfirmar.setBackground(Color.WHITE);
			confirmar.setBackground(new Color(111, 195, 179));
			
			spinnerAlto.setEditor(new JSpinner.DefaultEditor(spinnerAlto));
			
			confirmar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					((Circulo) f).setRadio((double) spinnerAlto.getValue());
					((Circulo) f).setEstanRellenas(rellenado);
					((Circulo) f).setColor(recolor.getBackground());
					cp.forRepaint();
					cp.generarEstado(p);
					dispose();
					
				}
			});
			
			
		} else if(f.getClass().getName().contains("Cuadrado")) {
		
			panelAlto = new JPanel();
			panelBotonera.add(panelAlto);
			panelAlto.add(new JLabel("Altura: "));
			spinnerAlto = new JSpinner(new SpinnerNumberModel(((Cuadrado) f).getAlto(), 1, 100, 1));
			panelAlto.add(spinnerAlto);
			
			panelAncho = new JPanel();
			panelBotonera.add(panelAncho);
			panelAncho.add(new JLabel("Anchura: "));
			spinnerAncho = new JSpinner(new SpinnerNumberModel(((Cuadrado) f).getAncho(), 1, 100, 1));
			panelAncho.add(spinnerAncho);
			
			panelConfirmar = new JPanel();
			panelBotonera.add(panelConfirmar);
			confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
			panelConfirmar.add(confirmar);
			
			panelAlto.setBackground(Color.WHITE);
			panelAncho.setBackground(Color.WHITE);
			panelConfirmar.setBackground(Color.WHITE);
			confirmar.setBackground(new Color(111, 195, 179));
			
			spinnerAlto.setEditor(new JSpinner.DefaultEditor(spinnerAlto));
			spinnerAncho.setEditor(new JSpinner.DefaultEditor(spinnerAncho));
			
			confirmar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					((Cuadrado) f).setAlto((double) spinnerAlto.getValue());
					((Cuadrado) f).setAncho((double) spinnerAncho.getValue());
					((Cuadrado) f).setEstanRellenas(rellenado);
					((Cuadrado) f).setColor(recolor.getBackground());
					cp.forRepaint();
					dispose();
					
				}
			});
			
			
		} else if(f.getClass().getName().contains("Ovalo")) {
			
			panelAlto = new JPanel();
			panelBotonera.add(panelAlto);
			panelAlto.add(new JLabel("Altura: "));
			spinnerAlto = new JSpinner(new SpinnerNumberModel(((Ovalo) f).getAltura(), 1, 100, 1));
			panelAlto.add(spinnerAlto);
			
			panelAncho = new JPanel();
			panelBotonera.add(panelAncho);
			panelAncho.add(new JLabel("Anchura: "));
			spinnerAncho = new JSpinner(new SpinnerNumberModel(((Ovalo) f).getAnchura(), 1, 100, 1));
			panelAncho.add(spinnerAncho);
			
			panelConfirmar = new JPanel();
			panelBotonera.add(panelConfirmar);
			confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
			panelConfirmar.add(confirmar);
			

			panelAlto.setBackground(Color.WHITE);
			panelAncho.setBackground(Color.WHITE);
			panelConfirmar.setBackground(Color.WHITE);
			confirmar.setBackground(new Color(111, 195, 179));
			
			spinnerAlto.setEditor(new JSpinner.DefaultEditor(spinnerAlto));
			spinnerAncho.setEditor(new JSpinner.DefaultEditor(spinnerAncho));	
			
			confirmar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					((Ovalo) f).setAltura((double) spinnerAlto.getValue());
					((Ovalo) f).setAnchura((double) spinnerAncho.getValue());
					((Ovalo) f).setEstanRellenas(rellenado);
					((Ovalo) f).setColor(recolor.getBackground());
					cp.forRepaint();
					cp.generarEstado(p);
					dispose();
					
				}
			});
			
		} else if(f.getClass().getName().contains("Triangulo")) {
		
			panelAlto = new JPanel();
			panelBotonera.add(panelAlto);
			panelAlto.add(new JLabel("Altura: "));
			spinnerAlto = new JSpinner(new SpinnerNumberModel(((Triangulo) f).getAltura(), 1, 100, 1));
			panelAlto.add(spinnerAlto);
			
			panelAncho = new JPanel();
			panelBotonera.add(panelAncho);
			panelAncho.add(new JLabel("Anchura: "));
			spinnerAncho = new JSpinner(new SpinnerNumberModel(((Triangulo) f).getBase(), 1, 100, 1));
			panelAncho.add(spinnerAncho);
			
			panelConfirmar = new JPanel();
			panelBotonera.add(panelConfirmar);
			confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
			panelConfirmar.add(confirmar);
			

			panelAlto.setBackground(Color.WHITE);
			panelAncho.setBackground(Color.WHITE);
			panelConfirmar.setBackground(Color.WHITE);
			confirmar.setBackground(new Color(111, 195, 179));
			
			spinnerAlto.setEditor(new JSpinner.DefaultEditor(spinnerAlto));
			spinnerAncho.setEditor(new JSpinner.DefaultEditor(spinnerAncho));			
			
			confirmar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					((Triangulo) f).setAltura((double) spinnerAlto.getValue());
					((Triangulo) f).setBase((double) spinnerAncho.getValue());
					((Triangulo) f).setEstanRellenas(rellenado);
					((Triangulo) f).setColor(recolor.getBackground());
					cp.forRepaint();
					cp.generarEstado(p);
					dispose();
				}
			});
			
			
		}
		
		setVisible(true);		
	}
	
}
