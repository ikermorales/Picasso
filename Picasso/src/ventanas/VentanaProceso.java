package ventanas;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import clasesBase.ComponentePapel;


public class VentanaProceso extends JFrame{
	private JPanel panelProceso;
	private JLabel imagenProceso = new JLabel();
	private JPanel panelSeleccion;
	private JButton avanzar;
	private JButton retroceder;
	private JButton separador;
	private int pagina = 0;
	private HashMap<Integer, String> hashPaginas = new HashMap<>();


	public VentanaProceso(ComponentePapel cp, Papel p, Logger logger) {  
		setTitle("Proceso");
		setSize(240, 385);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());

		panelProceso = new JPanel();
		panelProceso.setPreferredSize(new Dimension(200,300));
		add(panelProceso);

		panelSeleccion = new JPanel();
		panelSeleccion.setPreferredSize(new Dimension(400, 37));
		panelSeleccion.setBorder(new LineBorder(Color.lightGray));
		add(panelSeleccion);

		for (int i = 0; i < cp.getContadorImagen(); i++) { 
			hashPaginas.put(i, "proceso/" + i + ".jpg");
		}
	

		avanzar = new JButton(">");
		avanzar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(pagina < cp.getContadorImagen() - 1) { 
					pagina++;
					ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
					Image imagen = ic.getImage(); 
					Image nuevaimagenreeslada = imagen.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH);
					ic = new ImageIcon(nuevaimagenreeslada);  
					imagenProceso.setIcon(ic);
					separador.setLabel(pagina + " / " + (cp.getContadorImagen() - 1) ); 
					repaint();
					validate();
				}

			}
		});
		retroceder = new JButton("<");
		retroceder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pagina >= 1) {
					pagina--;
					ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
					Image imagen = ic.getImage(); 
					Image nuevaimagenreeslada = imagen.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH);
					ic = new ImageIcon(nuevaimagenreeslada);  
					imagenProceso.setIcon(ic);
					separador.setLabel(pagina + " / " + (cp.getContadorImagen() - 1) ); 
					repaint();
					validate();
					System.out.println(pagina);
				}

			}
		});

		separador = new JButton(pagina + " /" + (cp.getContadorImagen() - 1) ); 
		panelSeleccion.add(retroceder);
		panelSeleccion.add(separador);
		panelSeleccion.add(avanzar);
		panelProceso.add(imagenProceso);
		separador.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cp.setDibujosGrandes(new ArrayList<>());
				cp.setDibujos(new ArrayList<>());
				cp.forRepaint();
				cp.setDibujosGrandes(cp.getHashDibujos().get(pagina));
				cp.forRepaint();
				
				cp.setContadorImagen(pagina + 1);
				dispose();
				p.toFront();
				p.repaint();
				cp.forRepaint();
				cp.generarEstado(p);
				
				logger.log(Level.INFO, "Se ha cargado un dibujo del historial.");
			}
		});
		
	
		//INIT
		ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
		Image imagen = ic.getImage(); 
		Image nuevaimagenreeslada = imagen.getScaledInstance(200, 300,  java.awt.Image.SCALE_SMOOTH);
		ic = new ImageIcon(nuevaimagenreeslada);  
		imagenProceso.setIcon(ic);


		setVisible(true);
	}

}
