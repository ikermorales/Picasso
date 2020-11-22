package ventanas;
import java.awt.GridLayout;  
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clasesBase.ComponentePapel;

public class Papel extends JFrame {

	private static ComponentePapel compPapel;
	private int anchura = 600;
	private int altura = 800;


	public Papel(Logger logger) {
		setTitle("Picasso");
		setSize(anchura, altura);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setResizable(false);
		setLocationRelativeTo(null);

		compPapel = new ComponentePapel(this, logger);
		add(compPapel);
		
		
		setVisible(true);
		logger.log(Level.INFO, "Papel creado correctamente.");

	}
	
	
	public int getAnchura() {
		return anchura;
	}
	public void setAnchura(int anchura) {
		this.anchura = anchura;
	}

	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public ComponentePapel getCompPapel() {
		return compPapel;
	}
	public void setCompPapel(ComponentePapel compPapel) {
		this.compPapel = compPapel;
	}
	
} 