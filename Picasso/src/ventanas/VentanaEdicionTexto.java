package ventanas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;
import clasesBase.Texto;

public class VentanaEdicionTexto extends JFrame{

	private JButton botonEliminar;
	private JButton botonRecolorear;
	private JButton cancelar;
	private Color recolor;
	private PaletaRecolor paletaRecolor;
	private JButton botonString;

	public VentanaEdicionTexto(ComponentePapel cp, ArrayList<Sprite> sprites, Papel p){
		setTitle("Editar Texto");
		setSize(200, 67);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setLayout(new GridLayout(4,1));
		setLocation(MouseInfo.getPointerInfo().getLocation());

		botonEliminar = new JButton("Eliminar");
		botonEliminar.setHorizontalAlignment(SwingConstants.LEFT);
		botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (Iterator<Sprite> spriteIterador = sprites.iterator(); spriteIterador.hasNext();) {
					Sprite sprite = spriteIterador.next();
					spriteIterador.remove();
					cp.forRepaint();
				}
				cp.forRepaint();
				dispose();
			}
		});
		add(botonEliminar);


		botonRecolorear = new JButton("Recolor");
		botonRecolorear.setHorizontalAlignment(SwingConstants.LEFT);
		botonRecolorear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paletaRecolor = new PaletaRecolor(sprites, cp, p);
				dispose();
			}
		});
		add(botonRecolorear);

		botonString = new JButton("Cambiar contenido");
		botonString.setHorizontalAlignment(SwingConstants.LEFT);
		botonString.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String contenidoNuevo = JOptionPane.showInputDialog("Insertar nuevo texto: ");

					if(!contenidoNuevo.isEmpty() || contenidoNuevo != null) {
						for (Iterator<Sprite> spriteIterador = sprites.iterator(); spriteIterador.hasNext();) {
							Texto sprite = (Texto) spriteIterador.next();
							sprite.setContenido(contenidoNuevo);
							System.out.println("Contador:" + cp.getContadorImagen());
							cp.forRepaint();
						}
						dispose();
						p.toFront();
						p.repaint();
						cp.forRepaint();
						cp.generarEstado(p);
					} else {
						dispose();
					}

				} catch(NullPointerException e1) {
					dispose();
				}

			}
		});

		add(botonString);

		cancelar = new JButton("Cancelar");
		cancelar.setHorizontalAlignment(SwingConstants.LEFT);
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(cancelar);



		setVisible(true);
	}
}
