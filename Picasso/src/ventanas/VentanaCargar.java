package ventanas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clasesBase.ComponentePapel;
import clasesBase.Sprite;

public class VentanaCargar extends JFrame implements Serializable {
	private JButton confirmar;
	private JTextField cajaNombre;

	public VentanaCargar(String usuarioEscogido,  HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos, ComponentePapel cp) {
		setTitle("Cargar");
		setSize(350,100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		cajaNombre = new JTextField();
		add(cajaNombre, BorderLayout.NORTH);

		confirmar = new JButton("Confirmar", new ImageIcon("iconos/load.png"));

		confirmar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cargarDibujo(usuarioEscogido, hashDibujos, cp);
				dispose();

			}
		});

		add(confirmar, BorderLayout.SOUTH);
	}


	public void cargarDibujo(String usuarioEscogido,  HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujos, ComponentePapel cp) {


		try {
			HashMap<Integer, ArrayList<ArrayList<Sprite>>> hashDibujosNuevos;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes/" + usuarioEscogido + "/galeria/" + cajaNombre.getText() + ".bin"));
			hashDibujosNuevos = (HashMap<Integer, ArrayList<ArrayList<Sprite>>>) ois.readObject();
			cp.setHashDibujos(hashDibujosNuevos);
			cp.setDibujosGrandes(hashDibujosNuevos.get(Collections.max(hashDibujosNuevos.keySet())));
			cp.setContadorImagen(Collections.max(hashDibujosNuevos.keySet()));
			cp.forRepaint();
	        ois.close();
	        
	        File dondeEstaban = new File("clientes/" + usuarioEscogido + "/galeria/" + cajaNombre.getText() + "/");
	        
	        for (File file : cp.getCarpetaProcesoAnterior().listFiles()) {
				if(!file.getName().equals("0.jpg")) {
					file.delete();
				}
			}
	        
	        for (File file : dondeEstaban.listFiles()) {
				file.renameTo(new File("proceso/" + file.getName()));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Tipo de archivo no válido.");
			e.printStackTrace();
		} catch(NullPointerException e) {
			dispose();
		}
	}

}