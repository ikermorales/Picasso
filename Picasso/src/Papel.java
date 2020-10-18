import java.awt.GridLayout;
import javax.swing.JFrame;

public class Papel extends JFrame {

	private ComponentePapel compPapel;
	private int anchura = 600;
	private int altura = 800;

	public Papel() {
		setTitle("Picasso");
		setSize(anchura, altura);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1,1));

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
	
	


	
	

	
	
} 