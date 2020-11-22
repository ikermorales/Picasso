package ventanas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import clasesBase.ComponentePapel;


public class Paleta extends JFrame{
	private JColorChooser paletaC;
	private Color colorEscogido;

	public Paleta(ComponentePapel cp, Logger logger) {
		setTitle("Paleta de colores");
		setSize(660,275);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		paletaC = new JColorChooser(new ColorSelectionModel() {
			@Override
			public void setSelectedColor(Color color) {
				cp.setRainbowActivado(false);
				cp.getGraficos().setPaint(color);
				logger.log(Level.INFO, "Se ha cambiado de color a : " + color);
				paletaC.setBackground(color);
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
