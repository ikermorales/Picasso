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

	public VentanaEdicion(ComponentePapel cp, ArrayList<Sprite> sprites, Papel p){
		setTitle("Editar dibujo");
		setSize(200, 50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setLayout(new GridLayout(3,1));
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