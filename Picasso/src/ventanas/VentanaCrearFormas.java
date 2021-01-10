package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.*;

import clasesBase.ComponentePapel;
import formaStuff.Circulo;
import formaStuff.Cuadrado;
import formaStuff.Forma;
import formaStuff.Ovalo;
import formaStuff.Triangulo;

public class VentanaCrearFormas extends JFrame{
	private JComboBox<String> tipo;
	private JPanel centroCreador;
	private Thread hiloColor;
	private Thread hiloActualizador;
	private boolean relleno;


	public VentanaCrearFormas(ComponentePapel cp, Logger logger, ArrayList<Forma> formasCreadas,
			HashMap<Integer, Forma> hashFormasTecleadas) {
		setTitle("Taller");
		setSize(240,330);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		tipo = new JComboBox<>();
		tipo.addItem("Cuadrado");
		tipo.addItem("Triangulo");
		tipo.addItem("Circulo");
		tipo.addItem("Ovalo");
		add(tipo, BorderLayout.NORTH);
		centroCreador = new JPanel();
		centroCreador.setBackground(Color.WHITE);
		add(centroCreador, BorderLayout.CENTER);

		JPanel rellenoPanel = new JPanel();
		rellenoPanel.setBackground(Color.WHITE);
		JCheckBox formarelleno = new JCheckBox("Relleno");
		formarelleno.setBackground(Color.WHITE);
		formarelleno.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(formarelleno.isSelected()) {
					relleno = true;
				} else {
					relleno = false;
				}

			}
		});


		JPanel colorPanel= new JPanel();
		colorPanel.add(new JLabel("Color:"));
		colorPanel.setBackground(Color.WHITE);
		JButton colorF = new JButton();
		colorF.setBackground(cp.getGraphics().getColor());
		colorF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaletaForma p = new PaletaForma(cp, colorF);

			}
		});
		colorPanel.add(colorF);

		
		JPanel panelTecla = new JPanel();
		panelTecla.setLayout(new GridBagLayout());
		panelTecla.setBackground(Color.WHITE);
		panelTecla.add(new JLabel("Tecla:"));
		JComboBox<Character> comboTecla = new JComboBox<>(); 
		HashMap<Character, Integer> hashCharacter = new HashMap<>();
		hashCharacter.put('a', KeyEvent.VK_A);
		hashCharacter.put('b', KeyEvent.VK_B);
		hashCharacter.put('c', KeyEvent.VK_C);
		hashCharacter.put('d', KeyEvent.VK_D);
		hashCharacter.put('e', KeyEvent.VK_E);
		hashCharacter.put('f', KeyEvent.VK_F);
		hashCharacter.put('g', KeyEvent.VK_G);
		hashCharacter.put('h', KeyEvent.VK_H);
		hashCharacter.put('i', KeyEvent.VK_I);
		hashCharacter.put('j', KeyEvent.VK_J);
		hashCharacter.put('k', KeyEvent.VK_K);
		hashCharacter.put('l', KeyEvent.VK_L);
		hashCharacter.put('m', KeyEvent.VK_M);
		hashCharacter.put('n', KeyEvent.VK_N);
		hashCharacter.put('o', KeyEvent.VK_O);
		hashCharacter.put('p', KeyEvent.VK_P);
		hashCharacter.put('q', KeyEvent.VK_Q);
		hashCharacter.put('r', KeyEvent.VK_R);
		hashCharacter.put('s', KeyEvent.VK_S);
		hashCharacter.put('t', KeyEvent.VK_T);
		hashCharacter.put('u', KeyEvent.VK_U);
		hashCharacter.put('v', KeyEvent.VK_V);
		hashCharacter.put('w', KeyEvent.VK_W);
		hashCharacter.put('x', KeyEvent.VK_X);
		hashCharacter.put('y', KeyEvent.VK_Y);
		hashCharacter.put('z', KeyEvent.VK_Z);

		String abc = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < abc.length(); i++) {
			comboTecla.addItem(abc.charAt(i));	
		}		
		comboTecla.setSelectedIndex(1);


		tipo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {


				if(tipo.getSelectedItem() == "Cuadrado") {
					JPanel cuadradoCreador = new JPanel();
					cuadradoCreador.setBackground(Color.WHITE);
					cuadradoCreador.setLayout(new GridLayout(7,1));
					cuadradoCreador.add(new JLabel(new ImageIcon("iconos/cuadrado.png")));

					JPanel altoPanel = new JPanel();
					altoPanel.add(new JLabel("Alto:"));
					altoPanel.setBackground(Color.WHITE);
					JSpinner altoSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					altoSpinner.setPreferredSize(new Dimension(40, 20));
					altoSpinner.setMinimumSize(new Dimension(40, 20));
					altoSpinner.setEditor(new JSpinner.DefaultEditor(altoSpinner));
					altoPanel.add(altoSpinner); 
					
					JPanel anchoPanel = new JPanel();
					anchoPanel.add(new JLabel("Ancho:"));
					anchoPanel.setBackground(Color.WHITE);
					JSpinner anchoSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					anchoSpinner.setPreferredSize(new Dimension(40, 20));
					anchoSpinner.setMinimumSize(new Dimension(40, 20));
					anchoSpinner.setEditor(new JSpinner.DefaultEditor(anchoSpinner));
					anchoPanel.add(anchoSpinner);
					
					
					cuadradoCreador.add(altoPanel);
					cuadradoCreador.add(anchoPanel);
					
					rellenoPanel.add(formarelleno);
					cuadradoCreador.add(rellenoPanel);
					
					cuadradoCreador.add(colorPanel);
					
					panelTecla.add(comboTecla);
					cuadradoCreador.add(panelTecla);
					
					
					JButton confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
					confirmar.setBackground(new Color(111, 195, 179));
					confirmar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Cuadrado c = new Cuadrado();
							c.setColor(colorF.getBackground());
							c.setEstanRellenas(relleno);
							c.setBotonteclado(hashCharacter.get(comboTecla.getSelectedItem()));
							c.setAlto((Integer) altoSpinner.getValue());
							c.setAncho((Integer)anchoSpinner.getValue());
							Integer ancho = (Integer) anchoSpinner.getValue();
							double colision = ancho;
							c.setCollisionRad(colision);
							
							formasCreadas.add(c);
							if(hashFormasTecleadas.keySet().contains(comboTecla.getSelectedItem())) {
								hashFormasTecleadas.replace(hashCharacter.get(comboTecla.getSelectedItem()), c);
							} else {
								hashFormasTecleadas.put(hashCharacter.get(comboTecla.getSelectedItem()), c);
							}
							dispose();
						}
					});
					cuadradoCreador.add(confirmar);
					try{
						centroCreador.removeAll();
						centroCreador.add(cuadradoCreador);
						validate();	
					} catch(NullPointerException e2) {
						centroCreador.add(cuadradoCreador);
						validate();
					}	





				} else if(tipo.getSelectedItem() == "Circulo") {
					JPanel circuloCreador = new JPanel();
					circuloCreador.setBackground(Color.WHITE);
					circuloCreador.add(new JLabel(new ImageIcon("iconos/circulo.png")));
					circuloCreador.setLayout(new GridLayout(7,1));

					JPanel radioPanel = new JPanel();
					radioPanel.setBackground(Color.WHITE);
					radioPanel.add(new JLabel("Radio:"));
					JSpinner radioSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					radioSpinner.setPreferredSize(new Dimension(40, 20));
					radioSpinner.setMinimumSize(new Dimension(40, 20));
					radioSpinner.setEditor(new JSpinner.DefaultEditor(radioSpinner));
					
					radioPanel.add(radioSpinner);
					circuloCreador.add(radioPanel);


					rellenoPanel.add(formarelleno);
					circuloCreador.add(rellenoPanel);


					circuloCreador.add(colorPanel);
					
					
					circuloCreador.add(panelTecla);
					panelTecla.add(comboTecla);

					
					JButton confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
					confirmar.setBackground(new Color(111, 195, 179));
					confirmar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Circulo ci = new Circulo();
							
							ci.setColor(colorF.getBackground());                         
							ci.setEstanRellenas(relleno);       
							ci.setBotonteclado(hashCharacter.get(comboTecla.getSelectedItem()));
							ci.setRadio((Integer) radioSpinner.getValue());  
							Integer radio = (Integer) radioSpinner.getValue();
							double colision = radio;
							ci.setCollisionRad(colision);
							
							formasCreadas.add(ci);
							if(hashFormasTecleadas.keySet().contains(comboTecla.getSelectedItem())) {
								hashFormasTecleadas.replace(hashCharacter.get(comboTecla.getSelectedItem()), ci);
							} else {
								hashFormasTecleadas.put(hashCharacter.get(comboTecla.getSelectedItem()), ci);
							}							
							dispose();
						}
					});
					circuloCreador.add(confirmar);

					try{
						centroCreador.removeAll();
						centroCreador.add(circuloCreador);
						validate();
					} catch(NullPointerException e2) {
						centroCreador.add(circuloCreador);
						validate();
					}





				} else if(tipo.getSelectedItem() == "Ovalo") {
					JPanel ovaloCreador = new JPanel();
					ovaloCreador.setBackground(Color.WHITE);
					ovaloCreador.add(new JLabel(new ImageIcon("iconos/ovalo.png")));
					ovaloCreador.setLayout(new GridLayout(7,1));

					JPanel anchuraPanel = new JPanel();
					anchuraPanel.setBackground(Color.WHITE);
					anchuraPanel.add(new JLabel("Anchura:"));
					JSpinner anchuraSpinner = new JSpinner(new SpinnerNumberModel(5,2,100,1));
					anchuraSpinner.setPreferredSize(new Dimension(40, 20));
					anchuraSpinner.setMinimumSize(new Dimension(40, 20));
					anchuraSpinner.setEditor(new JSpinner.DefaultEditor(anchuraSpinner));
					anchuraPanel.add(anchuraSpinner); 
					ovaloCreador.add(anchuraPanel);


					JPanel alturaPanel = new JPanel();
					alturaPanel.add(new JLabel("Altura:"));
					alturaPanel.setBackground(Color.WHITE);
					JSpinner alturaSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					alturaSpinner.setPreferredSize(new Dimension(40, 20));
					alturaSpinner.setMinimumSize(new Dimension(40, 20));
					alturaSpinner.setEditor(new JSpinner.DefaultEditor(alturaSpinner));
					
								
					
					alturaPanel.add(alturaSpinner); 
					ovaloCreador.add(alturaPanel);


					rellenoPanel.add(formarelleno);
					ovaloCreador.add(rellenoPanel);

					ovaloCreador.add(colorPanel);
					
					ovaloCreador.add(panelTecla);
					panelTecla.add(comboTecla);
					

					JButton confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
					confirmar.setBackground(new Color(111, 195, 179));
					confirmar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Ovalo o = new Ovalo();
							
							o.setColor(colorF.getBackground());                         
							o.setEstanRellenas(relleno);                                     
							o.setBotonteclado(hashCharacter.get(comboTecla.getSelectedItem()));
							o.setAltura((Integer) alturaSpinner.getValue());                
							o.setAnchura((Integer) anchuraSpinner.getValue());
							Integer ancho = (Integer) anchuraSpinner.getValue();
							double colision = ancho;
							o.setCollisionRad(colision);
							                                                               
							
							formasCreadas.add(o);
							if(hashFormasTecleadas.keySet().contains(comboTecla.getSelectedItem())) {
								hashFormasTecleadas.replace(hashCharacter.get(comboTecla.getSelectedItem()), o);
							} else {
								hashFormasTecleadas.put(hashCharacter.get(comboTecla.getSelectedItem()), o);
							}							dispose();
						}
					});
					ovaloCreador.add(confirmar);

					try{
						centroCreador.removeAll();
						centroCreador.add(ovaloCreador);
						validate();
					} catch(NullPointerException e2) {
						centroCreador.add(ovaloCreador);
						validate();
					}







				} else if(tipo.getSelectedItem() == "Triangulo") {
					JPanel trainguloCreador = new JPanel();
					trainguloCreador.setBackground(Color.WHITE);
					trainguloCreador.add(new JLabel(new ImageIcon("iconos/triangulo.png")));
					trainguloCreador.setLayout(new GridLayout(7,1));

					JPanel basePanel = new JPanel();
					basePanel.setBackground(Color.WHITE);
					basePanel.add(new JLabel("Base:"));
					JSpinner baseSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					baseSpinner.setPreferredSize(new Dimension(40, 20));
					baseSpinner.setMinimumSize(new Dimension(40, 20));
					baseSpinner.setEditor(new JSpinner.DefaultEditor(baseSpinner));
					basePanel.add(baseSpinner); 
					trainguloCreador.add(basePanel);

					JPanel altoPanel = new JPanel();
					altoPanel.setBackground(Color.WHITE);
					altoPanel.add(new JLabel("Altura:"));
					JSpinner altoSpinner = new JSpinner(new SpinnerNumberModel(7,1,100,1));
					altoSpinner.setPreferredSize(new Dimension(40, 20));
					altoSpinner.setMinimumSize(new Dimension(40, 20));
					altoSpinner.setEditor(new JSpinner.DefaultEditor(altoSpinner));
					altoPanel.add(altoSpinner); 			
					
					
					trainguloCreador.add(altoPanel);


					rellenoPanel.add(formarelleno);
					trainguloCreador.add(rellenoPanel);

					trainguloCreador.add(colorPanel);
					
					trainguloCreador.add(panelTecla);
					panelTecla.add(comboTecla);

					JButton confirmar = new JButton("Confirmar", new ImageIcon("iconos/confirmar.png"));
					confirmar.setBackground(new Color(111, 195, 179));
					confirmar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Triangulo t = new Triangulo();
							
							t.setColor(colorF.getBackground());                         
							t.setEstanRellenas(relleno);                                     
							t.setBotonteclado(hashCharacter.get(comboTecla.getSelectedItem()));
							t.setAltura((Integer) altoSpinner.getValue());                
							t.setBase((Integer) (Integer) baseSpinner.getValue());
							Integer alto = (Integer) altoSpinner.getValue();
							double colision = alto;
							t.setCollisionRad(colision);
							
							formasCreadas.add(t);
							if(hashFormasTecleadas.keySet().contains(comboTecla.getSelectedItem())) {
								hashFormasTecleadas.replace(hashCharacter.get(comboTecla.getSelectedItem()), t);
							} else {
								hashFormasTecleadas.put(hashCharacter.get(comboTecla.getSelectedItem()), t);
							}
							dispose();
						}
					});
					trainguloCreador.add(confirmar);

					try{
						centroCreador.removeAll();
						centroCreador.add(trainguloCreador);
						validate();
					} catch(NullPointerException e2) {
						centroCreador.add(trainguloCreador);
						validate();
					}


				}

			}
		});


		tipo.setSelectedIndex(1);
		setVisible(true);
	}
}

