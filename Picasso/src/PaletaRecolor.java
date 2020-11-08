
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;


public class PaletaRecolor extends JFrame{
	private JColorChooser paletaC;

	public PaletaRecolor(ArrayList<Sprite> sprites, ComponentePapel cp, Papel p) {
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
					sprite.setColor(color);
				}
				dispose();
				p.toFront();
				p.repaint();
				cp.forRepaint();
				cp.generarEstado(p);
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
