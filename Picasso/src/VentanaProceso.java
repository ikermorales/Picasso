
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class VentanaProceso extends JFrame{
	private JPanel panelProceso;
	private JLabel imagenProceso = new JLabel();
	private JPanel panelSeleccion;
	private JButton avanzar;
	private JButton retroceder;
	private JButton separador;
	private int pagina = 0;
	private HashMap<Integer, String> hashPaginas = new HashMap<>();
	
	
	
	public VentanaProceso(ComponentePapel cp) {
		setTitle("Proceso");
		setSize(440, 700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		
		panelProceso = new JPanel();
		panelProceso.setPreferredSize(new Dimension(400,600));
		add(panelProceso);
		
		panelSeleccion = new JPanel();
		panelSeleccion.setPreferredSize(new Dimension(400, 37));
		panelSeleccion.setBorder(new LineBorder(Color.lightGray));
		add(panelSeleccion);
		
		for (int i = 0; i <= 4; i++) { // cp.getContadorImagen()
			hashPaginas.put(i, "proceso/" + i + ".jpg");
		}
		
		avanzar = new JButton(">");
		avanzar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pagina < cp.getContadorImagen() - 1) {
					pagina++;
					ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
					Image imagen = ic.getImage(); 
					Image nuevaimagenreeslada = imagen.getScaledInstance(400, 600,  java.awt.Image.SCALE_SMOOTH);
					ic = new ImageIcon(nuevaimagenreeslada);  
					imagenProceso.setIcon(ic);
					separador.setLabel(pagina + " / " + (cp.getContadorImagen() - 1) );
					repaint();
					validate();
				
				}
				
			}
		});
		retroceder = new JButton("<");
		retroceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pagina >= 1) {	//Este esta bien
					pagina--;
					ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
					Image imagen = ic.getImage(); 
					Image nuevaimagenreeslada = imagen.getScaledInstance(400, 600,  java.awt.Image.SCALE_SMOOTH);   
					ic = new ImageIcon(nuevaimagenreeslada);  
					imagenProceso.setIcon(ic);
					separador.setLabel(pagina + " / " + (cp.getContadorImagen() - 1) );
					repaint();
					validate();
					
				}
				
			}
		});
		
		separador = new JButton(pagina + " /" + (cp.getContadorImagen() - 1) ); 
		panelSeleccion.add(retroceder);
		panelSeleccion.add(separador);
		panelSeleccion.add(avanzar);
		panelProceso.add(imagenProceso);
		
		//INIT
		ImageIcon ic = new ImageIcon(hashPaginas.get(pagina));
		Image imagen = ic.getImage(); 
		Image nuevaimagenreeslada = imagen.getScaledInstance(400, 600,  java.awt.Image.SCALE_SMOOTH);
		ic = new ImageIcon(nuevaimagenreeslada);  
		imagenProceso.setIcon(ic);
		
		
		setVisible(true);
	}
	
	
}