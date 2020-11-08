import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import org.omg.Messaging.SyncScopeHelper;

public class VentanaTexto extends JFrame {


	private JTextField texto;
	private JButton botonAceptar;
	private JButton botonCancelar;
	private JPanel panelPrevisualizacion;
	private Thread hiloPrevisualizador;
	private JLabel labelTexto = new JLabel();
	
	private int xx=0;
	private int yy=0;
	private int x=0;
	private int y=0;
	
	private Robot robot;
	
	private int xPapel;
	private int yPapel;
	           
	private int xVentanaTexto;
	private int yVentaTexto;
	           
	private int xFinal;
	private int yFinal;
	

	public VentanaTexto(ComponentePapel cp, Papel p) {
		setTitle("Insertar Texto");
		setSize(300,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setFocusable(false);


		texto = new JTextField();
		texto.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
		texto.setPreferredSize(new Dimension(275,25));
		add(texto);

		
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				cp.getTopLevelAncestor().requestFocus();

			}
		});
		add(botonCancelar);
		
		
		panelPrevisualizacion = new JPanel();
		panelPrevisualizacion.setPreferredSize(new Dimension(275, 100));
		panelPrevisualizacion.setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		panelPrevisualizacion.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelPrevisualizacion.setLayout(new GridLayout(1,1));
		botonAceptar = new JButton("Aceptar");
		botonAceptar.setOpaque(false);
		botonAceptar.setContentAreaFilled(false);
		botonAceptar.setBorderPainted(false);
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insertarTexto(cp, p);
				hiloPrevisualizador.stop();
				dispose();
				p.toFront();
				p.repaint();
				cp.forRepaint();
				cp.generarEstado(p);
			}
		});
		add(panelPrevisualizacion);
		panelPrevisualizacion.add(botonAceptar);


		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				x=e.getXOnScreen();
				y=e.getYOnScreen();
				setLocation(x-xx, y-yy);

			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx=e.getX();
				yy=e.getY();  

			}
		});

		hiloPrevisualizador = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					
					botonAceptar.setForeground((Color) cp.getGraficos().getPaint());
					botonAceptar.setLabel(texto.getText());
					
					repaint();
					validate();

				}

			}
		});
		hiloPrevisualizador.start();


		setVisible(true);
	}


	public void insertarTexto(ComponentePapel cp, Papel p) {
		this.getLocation();
		boolean rainbowEstaba = false;
		if(cp.isRainbowActivado()) {
			cp.setRainbowActivado(false);
			rainbowEstaba = true;
		}
		int xPapel = (int) p.getLocationOnScreen().getX();
		int yPapel= (int) p.getLocationOnScreen().getY();
		
		int xVentanaTexto = (int) this.getLocationOnScreen().getX();
		int yVentanaTexto = (int) this.getLocationOnScreen().getY();
		
		int xFinal = xVentanaTexto - xPapel + 128; //
		int yFinal = yVentanaTexto - yPapel + 90; //Lo cuadramos con la previsualizacion

		
		Texto textoINS = new Texto(xFinal, yFinal, 0, 0, cp.getGraficos().getColor(), cp.getTamanyo(), null, 0,  texto.getText().length(), texto.getText());
		cp.getDibujos().add(textoINS);
		textoINS.pintarString(cp);
		cp.forRepaint();
		cp.repaint();
		if(rainbowEstaba) {
			cp.setRainbowActivado(true);
		}
	}




	public JTextField getTexto() {
		return texto;
	}

	public void setTexto(JTextField texto) {
		this.texto = texto;
	}
	
	public JPanel getPanelPrevisualizacion() {
		return panelPrevisualizacion;
	}

	public void setPanelPrevisualizacion(JPanel panelPrevisualizacion) {
		this.panelPrevisualizacion = panelPrevisualizacion;
	}
	
	public JLabel getLabelTexto() {
		return labelTexto;
	}

	public void setLabelTexto(JLabel labelTexto) {
		this.labelTexto = labelTexto;
	}

}
