package formaStuff;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;
import ventanas.Papel;

public class PaletaForma extends JFrame{
	private JColorChooser paletaC;

	public PaletaForma(ComponentePapel cp, JButton boton) {
		setTitle("Paleta de colores");
		setSize(660,275);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		paletaC = new JColorChooser(new ColorSelectionModel() {
			@Override
			public void setSelectedColor(Color color) {
				boton.setBackground(color);
				setBackground(color);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					dispose();
				}
				dispose();
				
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
