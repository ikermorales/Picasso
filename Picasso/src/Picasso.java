
public class Picasso {
	
	private static ComponentePapel componente = new ComponentePapel();
	private static Papel papel;
	private static VentanaMenu menu;

	public static void main(String[] args) {

		papel = new Papel();
		menu = new VentanaMenu(componente);

	}
}
