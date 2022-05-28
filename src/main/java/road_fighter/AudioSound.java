package road_fighter;

import java.io.File;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioSound {

	private MediaPlayer mediaPlayer;
	HashMap<String, Media> mapa;
	private double volumen;
	
	public AudioSound() {
		this.volumen = 0.3;
		this.mapa = new HashMap<String, Media>();
		mapa.put("menu", new Media(new File("src/main/resources/sound/0-menu.mp3").toURI().toString()));
		mapa.put("game_start", new Media(new File("src/main/resources/sound/1-game_start.mp3").toURI().toString()));
		mapa.put("game_music", new Media(new File("src/main/resources/sound/2-game_music.mp3").toURI().toString()));
		mapa.put("ganador", new Media(new File("src/main/resources/sound/3-ganador.mp3").toURI().toString()));
		mapa.put("game_over", new Media(new File("src/main/resources/sound/4-game_over.mp3").toURI().toString()));		
	}

	public void playMenuSound() {
		//Media loop = new Media(new File("src/main/resources/sound/0-menu.mp3").toURI().toString());
		Media loop = mapa.get("menu");
		mediaPlayer = new MediaPlayer(loop);
		mediaPlayer.setVolume(this.volumen);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	public void playGameStartSound() {
		
		//Media loop = new Media(new File("src/main/resources/sound/1-game_start.mp3").toURI().toString());
		Media loop = mapa.get("game_start");
		mediaPlayer = new MediaPlayer(loop);
		mediaPlayer.setVolume(this.volumen);
		mediaPlayer.play();
	}
	
	public void playGameSound() {
		
		//Media loop = new Media(new File("src/main/resources/sound/2-game_music.mp3").toURI().toString());
		Media loop = mapa.get("game_music");
		mediaPlayer = new MediaPlayer(loop);
		mediaPlayer.setVolume(this.volumen);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	public void playGanadorSound() {
		
		//Media loop = new Media(new File("src/main/resources/sound/3-ganador.mp3").toURI().toString());
		Media loop = mapa.get("ganador");
		mediaPlayer = new MediaPlayer(loop);
		mediaPlayer.setVolume(this.volumen);
		mediaPlayer.play();
	}
	
	public void playGameOverSound() {
		
		//Media loop = new Media(new File("src/main/resources/sound/4-game_over.mp3").toURI().toString());
		Media loop = mapa.get("game_over");
		mediaPlayer = new MediaPlayer(loop);
		mediaPlayer.setVolume(this.volumen);
		mediaPlayer.play();
	}
	
	public void stopSound() {
		mediaPlayer.stop();
	}
	
	public void subirVolumenSound() {
		
		double incremento = this.volumen+0.1;
		
		if (incremento < 1) {			
			this.volumen=incremento;
			mediaPlayer.setVolume(incremento);
		}
		else {
			this.volumen=1;
			mediaPlayer.setVolume(1);
		}
	}
	
	public void bajarVolumenSound() {
		
		double decremento = this.volumen-0.1;
		
		if (decremento > 0) {	
			this.volumen=decremento;
			mediaPlayer.setVolume(decremento);
		}
		else {
			this.volumen=0;
			mediaPlayer.setVolume(0);
		}
	}
}
	/*testing para probarlo con las teclas
	 * 
	 * 	AudioSound sd = new AudioSound();
		sd.playMenuSound();
		
	 * private void addInputEvents() {
		currentScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case RIGHT:
					sd.subirVolumenSound();
					break;
				case LEFT:
					sd.bajarVolumenSound();
					break;
				case A:
					sd.stopSound();
					sd.playMenuSound();
					break;
				case B:
					sd.stopSound();
					sd.playGameStartSound();
					break;
				case C:
					sd.stopSound();
					sd.playGameSound();
					break;
				case D:
					sd.stopSound();
					sd.playGanadorSound();
					break;
				case E:
					sd.stopSound();
					sd.playGameOverSound();
					break;
				default:
					break;
				}
			}
		});
	}*/
	 
	
	

