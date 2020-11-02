import java.awt.*;  
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class VentanaIniciarSesion extends JFrame {

	private JPanel panelUsuario;
	private JTextField usuario;
	private JLabel labelUsuario = new JLabel("        Usuario: ");

	private JPanel panelContrase�a;
	private JPasswordField contrase�a;
	private JLabel labelContrase�a = new JLabel("  Contrase�a: ");

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
		labelDecorativo = new JLabel(new ImageIcon("iconos/login.png"));
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

		panelContrase�a = new JPanel();
		contrase�a = new JPasswordField();
		contrase�a.setPreferredSize(new Dimension(100,25));
		panelContrase�a.add(labelContrase�a);
		panelContrase�a.add(contrase�a);
		panelContrase�a.setBackground(new Color(188, 245, 175));
		panelDatos.add(panelContrase�a);

		panelBotonera = new JPanel();
		aceptar = new JButton("Iniciar Sesi�n");
		crearUsuario = new JButton("Crear Nuevo Usuario");
		aceptar.setEnabled(false);
		crearUsuario.setEnabled(false);
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
					JOptionPane.showMessageDialog(null, "Usuario y/o contrase�a incorrectas");
				}
			}
		});


		crearUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crearUsuario();
			}
		});



		usuario.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				changed();
			}
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
			public void insertUpdate(DocumentEvent e) {
				changed();
			}
		});

		contrase�a.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				changed();
			}
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
			public void insertUpdate(DocumentEvent e) {
				changed();
			}
		});		

		setVisible(true);		
	}






	public void changed() {
		if (usuario.getText().equals("") || contrase�a.getText().equals("")
			|| usuario.getText().contains(" ") || contrase�a.getText().contains(" ")
			|| usuario.getText().contains(";") || contrase�a.getText().contains(";")
			|| usuario.getText().contains("(") || contrase�a.getText().contains("(")
			|| usuario.getText().contains(")") || contrase�a.getText().contains(")")) {
			aceptar.setEnabled(false);
			crearUsuario.setEnabled(false);
		}
		else {
			aceptar.setEnabled(true);
			crearUsuario.setEnabled(true);
		}
	}



	public boolean comprobar() {
		try {
			Class.forName("org.sqlite.JDBC");

			Connection conn = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from usuarios");

			while(rs.next()) {
				String usuarioBD = rs.getString("usuario");
				String contrase�aBD = rs.getString("contrase�a");
				System.out.println(usuarioBD);
				System.out.println(contrase�aBD);
				if (usuarioBD.equals(usuario.getText()) && contrase�aBD.equals(contrase�a.getText())) {
					usuarioEscogido = usuarioBD;
					return true;
				}

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}



	public void crearUsuario() {

		try {
			Class.forName("org.sqlite.JDBC");

			Connection conn = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
			Statement stmt = (Statement) conn.createStatement();

			ResultSet rs1 = stmt.executeQuery("Select * from usuarios");

			ArrayList<String> usuariosBD = new ArrayList<>();

			while(rs1.next()) {
				String usuarioBD = rs1.getString("usuario");
				String contrase�aBD = rs1.getString("contrase�a");
				usuariosBD.add(usuarioBD);
			}
			rs1.close();

			if (!usuariosBD.contains(usuario.getText())) {
				String instruccion = "INSERT INTO usuarios (usuario, contrase�a) VALUES ('" + usuario.getText()  + "','" + contrase�a.getText() + "');";
				File directorioPersonal = new File("clientes\\" + usuario.getText() + "\\galeria\\");
				directorioPersonal.mkdirs();
				JOptionPane.showMessageDialog(null, "Usuario creado con �xito");
				int rs2 = stmt.executeUpdate(instruccion);
				
			} else {
				JOptionPane.showMessageDialog(null, "Este usuario ya existe");
			}
			
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Se ha ejecutado el metodo de crear usuario a pesar del error");
		}	

	} 



}
