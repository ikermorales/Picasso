package clasesBase;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class Musica {
	private AudioInputStream ais; //Este es como el getter de los archivos, el que gettea el archivo de audio.
	private Clip clip; //Este sirve para, con el audio getteado, playearlo.
	private boolean activado;
	
		
	public Clip getClip() {
		return clip;
	}

	public AudioInputStream getAis() {
		return ais;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public void setAis(AudioInputStream ais) {
		this.ais = ais;
	}
	public boolean isActivado() {
		return activado;
	}
	public void setActivado(boolean activado) {
		this.activado = activado;
	}


	public void playear(String fichero) { // 
		if(activado == true) {
			if(ais == null && clip == null) {
				try {
					setClip(AudioSystem.getClip());
				} catch (LineUnavailableException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				try {
					setAis(AudioSystem.getAudioInputStream(new File(fichero) )); 
				} catch (UnsupportedAudioFileException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				try {
					clip.open(getAis());

				} catch (LineUnavailableException | IOException | NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				clip.loop(Clip.LOOP_CONTINUOUSLY);	
			} else {
				clip.close();
				try {
					setClip(AudioSystem.getClip());
				} catch (LineUnavailableException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				try {
					setAis(AudioSystem.getAudioInputStream(new File(fichero) )); 
				} catch (UnsupportedAudioFileException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				try {
					clip.open(getAis());

				} catch (LineUnavailableException | IOException | NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Error");
				}
				clip.loop(Clip.LOOP_CONTINUOUSLY);	
			}
		} else {
			try {
			clip.close();
			clip = null;
			ais = null;
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "Selecciona una canción primero.");
			}
		}
	}


}




