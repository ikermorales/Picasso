package ventanas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;


public class VentanaGuardar extends JFrame implements Serializable {
	private JButton confirmar;
	private JTextField cajaNombre;

	public VentanaGuardar(String usuarioEscogido,  HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos,
			ComponentePapel cp, Logger logger) {
		setTitle("Guardar");
		setSize(350,100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		cajaNombre = new JTextField();
		add(cajaNombre, BorderLayout.NORTH);

		confirmar = new JButton("Confirmar", new ImageIcon("iconos/guardar.png"));

		confirmar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guardarDibujo(usuarioEscogido, hashDibujos, cp);
				logger.log(Level.INFO, "Se ha guardado del dibujo en la carpeta de : " + usuarioEscogido);
				dispose();

			}
		});

		add(confirmar, BorderLayout.SOUTH);
	}


	public void guardarDibujo(String usuarioEscogido, HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos, ComponentePapel cp) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("clientes/" + usuarioEscogido + "/galeria/" + cajaNombre.getText() + ".bin"));
			os.writeObject(hashDibujos);
			os.flush();
			os.close();

			File destino = new File("clientes/" + usuarioEscogido + "/galeria/" + cajaNombre.getText() + "/" );
			destino.mkdirs();
			for (File file : cp.getCarpetaProcesoAnterior().listFiles()) {
				if(!file.getName().equals("0.jpg")) {
					file.renameTo(new File("clientes/" + usuarioEscogido + "/galeria/" + cajaNombre.getText() + "/" + file.getName()));
				}
			}
			
			cp.setHashDibujos(new HashMap<>());
			cp.setDibujosGrandes(new ArrayList<>());
			cp.setDibujos(new ArrayList<>());
			cp.setContadorImagen(0);
			cp.forRepaint();

			JOptionPane.showMessageDialog(null, cajaNombre.getText() + " guardado con éxito.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
