package ventanas;

import java.awt.Color; 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;

public class VentanaEdicion extends JFrame{

	private JButton botonEliminar;
	private JButton botonRecolorear;
	private JButton cancelar;
	private Color recolor;
	private PaletaRecolor paletaRecolor;

	public VentanaEdicion(ComponentePapel cp, ArrayList<Sprite> sprites, Papel p, Logger logger){
		setTitle("Editar dibujo");
		setSize(200, 50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setLayout(new GridLayout(3,1));
		setLocation(MouseInfo.getPointerInfo().getLocation());

		botonEliminar = new JButton("Eliminar");
		botonEliminar.setBackground(new Color(111, 195, 179));
		botonEliminar.setForeground(Color.WHITE);
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
		botonRecolorear.setBackground(new Color(111, 195, 179));
		botonRecolorear.setForeground(Color.WHITE);
		botonRecolorear.setHorizontalAlignment(SwingConstants.LEFT);
		botonRecolorear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paletaRecolor = new PaletaRecolor(sprites, cp, p, logger);
				logger.log(Level.INFO, "Se ha abierto la paleta para recolorear el dibujo.");
				dispose();
			}
		});
		add(botonRecolorear);

		cancelar = new JButton("Cancelar");
		cancelar.setBackground(new Color(111, 195, 179));
		cancelar.setForeground(Color.WHITE);
		cancelar.setHorizontalAlignment(SwingConstants.LEFT);
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				logger.log(Level.INFO, "Se ha cerrado la ventana para editar dibujo.");
			}
		});
		add(cancelar);


		setVisible(true);
	}
}