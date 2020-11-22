package ventanas;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;


public class PaletaRecolor extends JFrame{
	private JColorChooser paletaC;

	public PaletaRecolor(ArrayList<Sprite> sprites, ComponentePapel cp, Papel p, Logger logger) {
		setTitle("Paleta de colores");
		setSize(660,275);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		paletaC = new JColorChooser(new ColorSelectionModel() {
			@Override
			public void setSelectedColor(Color color) {
				for (Iterator<Sprite> spriteIterador = sprites.iterator(); spriteIterador.hasNext();) {
					Sprite sprite = spriteIterador.next();
					sprite.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), cp.getOpacidad()));
				}
				dispose();
				p.toFront();
				p.repaint();
				cp.forRepaint();
				cp.generarEstado(p);
				logger.log(Level.INFO, "Se ha cambaido el color del dibujo a : " + color);
			}
			@Override
			public void removeChangeListener(javax.swing.event.ChangeListener listener) {				
			}
			@Override
			public Color getSelectedColor() {
				return null;
			}
			@Override
			public void addChangeListener(javax.swing.event.ChangeListener listener) {
			}
		});
		setResizable(false);
		paletaC.setPreviewPanel(new JPanel());
		add(paletaC);		
	}

	
}
