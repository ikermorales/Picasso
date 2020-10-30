import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class VentanaIniciarSesion extends JFrame {

	private JPanel panelUsuario;
	private JTextField usuario;
	private JLabel labelUsuario = new JLabel("        Usuario: ");

	private JPanel panelContraseña;
	private JPasswordField contraseña;
	private JLabel labelContraseña = new JLabel("  Contraseña: ");

	private JButton aceptar;
	private JButton crearUsuario;
	private JPanel panelBotonera;

	private JPanel panelDatos;

	private JPanel panelDecorativo;
	private JLabel labelDecorativo;
	
	private File file = new File("usuariosdata.txt");
	
	private static ComponentePapel componente;
	private static VentanaMenu menu;
	private static Papel papel;
	private static String usuarioEscogido;
	
	public VentanaIniciarSesion() {
		setTitle("Inicio");
		setSize(310,260);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setBackground(new Color(188, 245, 175));


		panelDecorativo = new JPanel();
		panelDecorativo.setLayout(new GridLayout(1,1));
		panelDecorativo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); //TOC
		labelDecorativo = new JLabel(new ImageIcon("login.png"));
		panelDecorativo.add(labelDecorativo);
		add(panelDecorativo);


		panelDatos = new JPanel();
		panelDatos.setLayout(new GridLayout(3,1));
		panelDatos.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelDatos.setSize(new Dimension(100,100));
		panelDatos.setBackground(new Color(188, 245, 175));
		panelDatos.setPreferredSize(new Dimension(280,100));
		add(panelDatos);

		panelUsuario = new JPanel();
		usuario = new JTextField();
		usuario.setPreferredSize(new Dimension(100,25));
		panelUsuario.add(labelUsuario);
		panelUsuario.add(usuario);
		panelUsuario.setBackground(new Color(188, 245, 175));
		panelDatos.add(panelUsuario);

		panelContraseña = new JPanel();
		contraseña = new JPasswordField();
		contraseña.setPreferredSize(new Dimension(100,25));
		panelContraseña.add(labelContraseña);
		panelContraseña.add(contraseña);
		panelContraseña.setBackground(new Color(188, 245, 175));
		panelDatos.add(panelContraseña);

		panelBotonera = new JPanel();
		aceptar = new JButton("Iniciar Sesión");
		crearUsuario = new JButton("Crear Nuevo Usuario");
		panelBotonera.add(aceptar);
		panelBotonera.add(crearUsuario);
		panelBotonera.setBackground(new Color(188, 245, 175));
		panelDatos.add(panelBotonera);

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comprobar()) {
					panelDatos.removeAll();
					JLabel bienvenidaLabel = new JLabel("Bienvenid@ " + usuarioEscogido + ".");
					bienvenidaLabel.setHorizontalAlignment(JLabel.CENTER);
					panelDatos.add(bienvenidaLabel);
					JButton botonInicio = new JButton("Iniciar Picasso");
					panelDatos.add(botonInicio);
					botonInicio.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							papel = new Papel();
							componente = new ComponentePapel(papel);
							menu = new VentanaMenu(componente, papel, usuarioEscogido);
							dispose();
							
						}
					});
					repaint();
					validate();					
					
				} else {
					JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectas");
				}
			}
		});

		crearUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				crearUsuario();
				

			}
		});


		setVisible(true);		
	}


	public boolean comprobar() {
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				for (int i = 0; i < campos.length; i++) {
					if (i % 2 == 0 && campos[i].equals(usuario.getText()) && campos[i+1].equals(contraseña.getText())) { 
						usuarioEscogido = campos[i];
						return true;
					}
				}
			}

		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "No se encontró el archivo: usuariosdata.txt");
		}
		return false;
	}


	
	public void crearUsuario() {
		if (!usuario.getText().isEmpty() && !contraseña.getText().isEmpty()) {
			try {
				File directorioPersonal = new File(usuario.getText() + "\\galeria\\");
				if (!directorioPersonal.exists()){
					FileWriter fw = new FileWriter(file, true);
					fw.append(usuario.getText() +  ";" + contraseña.getText() + ";");
					fw.flush();
					fw.close();		
					directorioPersonal.mkdirs();
					JOptionPane.showMessageDialog(null, "Usuario creado con éxito");

				} else {
					JOptionPane.showMessageDialog(null, "Este usuario ya existe");

				}
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(null, "No se pudo leer el archivo: " + file);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Para crear un usuario rellena los campos 'Usuario y 'Contraseña'.");
		}
	}
	
}
