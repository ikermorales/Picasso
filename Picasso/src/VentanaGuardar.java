import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;


public class VentanaGuardar extends JFrame {
	private JButton confirmar;
	private JTextField cajaNombre;
	
	public VentanaGuardar(Papel p,ComponentePapel cp, String usuario) {
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
				cp.guardarDibujo(p, usuario, cajaNombre.getText());
				dispose();

			}
		});

		add(confirmar, BorderLayout.SOUTH);
	}
	
}
