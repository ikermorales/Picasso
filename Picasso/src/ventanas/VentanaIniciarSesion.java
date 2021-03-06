package ventanas;
import java.awt.*;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.callback.TextOutputCallback;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import org.apache.commons.dbutils.DbUtils;
import org.sqlite.core.DB;

public class VentanaIniciarSesion extends JFrame {

	private JPanel panelUsuario;
	private JTextField usuario;
	private JLabel labelUsuario = new JLabel("        Usuario: ");

	private JPanel panelcontraseña;
	private JPasswordField contraseña;
	private JLabel labelcontraseña = new JLabel("  Contraseña: ");

	private JButton aceptar;
	private JButton crearUsuario;
	private JPanel panelBotonera;

	private JPanel panelDatos;

	private JPanel panelDecorativo;
	private JLabel labelDecorativo;

	private static Papel papel;
	private static String usuarioEscogido;
	private static VentanaMenu menu;


	public VentanaIniciarSesion(Logger logger) {
		setTitle("Inicio");
		setSize(310,260);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);


		panelDecorativo = new JPanel();
		panelDecorativo.setLayout(new GridLayout(1,1));
		panelDecorativo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); //TOC
		labelDecorativo = new JLabel(new ImageIcon("iconos/login.png"));
		panelDecorativo.setBackground(Color.WHITE);
		panelDecorativo.add(labelDecorativo);
		add(panelDecorativo);


		panelDatos = new JPanel();
		panelDatos.setLayout(new GridLayout(3,1));
		panelDatos.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelDatos.setSize(new Dimension(100,100));
		panelDatos.setBackground(new Color(111, 195, 179));
		panelDatos.setPreferredSize(new Dimension(280,100));
		add(panelDatos);

		panelUsuario = new JPanel();
		usuario = new JTextField();
		usuario.setPreferredSize(new Dimension(100,25));
		panelUsuario.add(labelUsuario);
		panelUsuario.add(usuario);
		panelUsuario.setBackground(new Color(111, 195, 179));
		panelDatos.add(panelUsuario);

		panelcontraseña = new JPanel();
		contraseña = new JPasswordField();
		contraseña.setPreferredSize(new Dimension(100,25));
		panelcontraseña.add(labelcontraseña);
		panelcontraseña.add(contraseña);
		panelcontraseña.setBackground(new Color(111, 195, 179));
		panelDatos.add(panelcontraseña);

		panelBotonera = new JPanel();
		aceptar = new JButton("Iniciar Sesión");
		crearUsuario = new JButton("Crear Nuevo Usuario");
		aceptar.setEnabled(false);
		crearUsuario.setEnabled(false);
		panelBotonera.add(aceptar);
		panelBotonera.add(crearUsuario);
		panelBotonera.setBackground(new Color(111, 195, 179));
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
							papel = new Papel(logger);
							menu = new VentanaMenu(papel.getCompPapel(), papel, usuarioEscogido, logger);
							dispose();

						}
					});

					JButton botonEliminar = new JButton("Eliminar Usuario");
					botonEliminar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int selectedOption = JOptionPane.showConfirmDialog(null,  "¿Seguro que quieres borrar el usuario: " + usuarioEscogido + "?", "Elige:", JOptionPane.YES_NO_OPTION);
							if(selectedOption == 0) {
								eliminarUsuario();
								dispose();
								JOptionPane.showMessageDialog(null, "Se ha eliminado el usuario " + usuarioEscogido + ".");
								logger.log(Level.INFO, "Se ha borrado el usuario " + usuarioEscogido + " de la base de datos.");
								new VentanaIniciarSesion(logger);
							} 

						}
					});
					panelDatos.add(botonEliminar);


					repaint();
					validate();					
					logger.log(Level.INFO, "Se ha iniciado sesion con " + usuarioEscogido + ".");

				} else {
					JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectas");
					logger.log(Level.INFO, "Se ha intentado iniciar sesion, pero los datos han sido erroneos.");

				}
			}
		});

		crearUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				crearUsuario();
				logger.log(Level.INFO, "Se ha creado un nuevo usuario.");

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

		contraseña.getDocument().addDocumentListener(new DocumentListener() {
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
		if (contraseña.getText().equals("") || contraseña.getText().contains(" ") || usuario.getText().equals("") || usuario.getText().contains(" ")){
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
				String contraseñaBD = rs.getString("contraseña");

				if (usuarioBD.equals(usuario.getText()) && contraseñaBD.equals(contraseña.getText())) {
					usuarioEscogido = usuarioBD;
					
					DbUtils.closeQuietly(rs);
					DbUtils.closeQuietly(stmt);
					DbUtils.closeQuietly(conn);
					
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

			ResultSet rs = stmt.executeQuery("Select * from usuarios");

			ArrayList<String> usuariosBD = new ArrayList<>();

			while(rs.next()) {
				String usuarioBD = rs.getString("usuario");
				String contraseñaBD = rs.getString("contraseña");
				usuariosBD.add(usuarioBD);
			}

			if (!usuariosBD.contains(usuario.getText())) {
				String instruccion = "INSERT INTO usuarios (usuario, contraseña) VALUES ('" + usuario.getText()  + "','" + contraseña.getText() + "');";
				File directorioPersonal = new File("clientes\\" + usuario.getText() + "\\galeria\\");
				directorioPersonal.mkdirs();
				JOptionPane.showMessageDialog(null, "Usuario creado con éxito");
				int rs2 = stmt.executeUpdate(instruccion);
			} else {
				JOptionPane.showMessageDialog(null, "Este usuario ya existe");
			}
			
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Se ha ejecutado el metodo de crear usuario a pesar del error");
		}	

	} 


	public void eliminarUsuario() {
		try {
			
			Class.forName("org.sqlite.JDBC");

			Connection conn = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
			Statement stmt = (Statement) conn.createStatement();
			

			String instruccion = "Delete from usuarios where usuario='" + usuarioEscogido + "';" ;
			int rs = stmt.executeUpdate(instruccion);

			File file = new File("clientes/" + usuarioEscogido + "/");

			deleteDirectory(file);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean deleteDirectory(File directoryToBeDeleted) {	//METODO RECURSIVO
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
	
	
	
	
	public static String getUsuarioEscogido() {
		return usuarioEscogido;
	}
	public static void setUsuarioEscogido(String usuarioEscogido) {
		VentanaIniciarSesion.usuarioEscogido = usuarioEscogido;
	}




}
