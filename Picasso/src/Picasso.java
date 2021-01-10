import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ventanas.VentanaIniciarSesion;

public class Picasso {

	private static VentanaIniciarSesion ventanaInicial;
	private static final Logger LOGGER = Logger.getLogger(Picasso.class.getName());

	public static void main(String[] args) {

		try {
			FileHandler fH = new FileHandler("MyLogFile.log", 8096, 1, false);
			for (Handler handler : LOGGER.getHandlers()) {
				LOGGER.removeHandler(handler);
			}
			SimpleFormatter formatter = new SimpleFormatter();  
			fH.setFormatter(formatter);
			LOGGER.addHandler(fH);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.log(Level.INFO, "Programa Inicializado");



		ventanaInicial = new VentanaIniciarSesion(LOGGER);


	}
}