package ventanas;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import clasesBase.ComponentePapel;


public class Paleta extends JFrame{
	private JColorChooser paletaC;
	private Color colorEscogido;

	public Paleta(ComponentePapel cp) {
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
