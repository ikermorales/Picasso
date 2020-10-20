import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;


public class Paleta extends JFrame{
	private JColorChooser paletaC;

	public Paleta(ComponentePapel cp) {
		setTitle("Paleta de colores");
		setSize(660,275);
		setVisible(true);
		
		
		paletaC = new JColorChooser(new ColorSelectionModel() {
			@Override
			public void setSelectedColor(Color color) {
				cp.setColorPincel(color);
				paletaC.setBackground(color); //Mostramos el color escogido.
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
		add(paletaC);
	}


	
}
