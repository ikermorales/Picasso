import java.awt.GridLayout;  
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Papel extends JFrame {

	private static ComponentePapel compPapel;
	private int anchura = 600;
	private int altura = 800;


	public Papel() {
		setTitle("Picasso");
		setSize(anchura, altura);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setResizable(false);
		setLocationRelativeTo(null);

		compPapel = new ComponentePapel(this);
		add(compPapel);
		
		
		setVisible(true);
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