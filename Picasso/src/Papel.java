import java.awt.GridLayout;

import javax.swing.JFrame;

public class Papel extends JFrame {

	ComponentePapel compPapel;


	public Papel() {
		setTitle("Picasso");
		setSize(600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));

		compPapel = new ComponentePapel();
		add(compPapel);

		setVisible(true);
	} 
} 