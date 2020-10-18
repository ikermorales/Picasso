import java.awt.Graphics;

public class Picasso {
	
	private static Papel papel;
	private static ComponentePapel componente = new ComponentePapel(papel);
	private static VentanaMenu menu;
	private static Graphics graficos;

	public static void main(String[] args) {

		papel = new Papel();
		menu = new VentanaMenu(componente, papel, graficos);

	}
}
