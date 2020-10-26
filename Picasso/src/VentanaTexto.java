import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

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

	public VentanaTexto(ComponentePapel cp, Papel p) {
		setTitle("Insertar Texto");
		setSize(300,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);


		texto = new JTextField();
		texto.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
		texto.setPreferredSize(new Dimension(275,25));
		add(texto);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insertarTexto(cp, p);
				hiloPrevisualizador.stop();
				dispose();
			}
		});
		add(botonAceptar);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
		add(botonCancelar);

		panelPrevisualizacion = new JPanel();
		panelPrevisualizacion.setPreferredSize(new Dimension(275, 100));
		panelPrevisualizacion.setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		panelPrevisualizacion.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelPrevisualizacion.setLayout(new GridBagLayout());
		panelPrevisualizacion.add(labelTexto);
		add(panelPrevisualizacion);


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
					labelTexto.setText(texto.getText());
					labelTexto.setForeground((Color) cp.getGraficos().getPaint());
					panelPrevisualizacion.repaint();
					panelPrevisualizacion.validate();
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
		cp.getGraficos().drawString(getTexto().getText(), 
				(int) (this.getLocation().getX() - p.getLocation().getX() + 127),
				(int) (this.getLocation().getY() - p.getLocation().getY() + 90)); //para cuadrarlo con la previsualizacion
		
		cp.repaint();
		p.repaint();
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