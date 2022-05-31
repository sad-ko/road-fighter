package road_fighter.graficos;

import java.io.File;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioSound extends Audio {

	private static AudioSound instancia = null;
	private MediaPlayer mediaPlayer;
	private HashMap<String, Media> mapa;

	private AudioSound() {
		super();
		this.mapa = new HashMap<>();
		mapa.put("menu", new Media(new File("src/main/resources/sound/0-menu.mp3").toURI().toString()));
		mapa.put("game_start", new Media(new File("src/main/resources/sound/1-game_start.mp3").toURI().toString()));
		mapa.put("game_music", new Media(new File("src/main/resources/sound/2-game_music.mp3").toURI().toString()));
		mapa.put("ganador", new Media(new File("src/main/resources/sound/3-ganador.mp3").toURI().toString()));
		mapa.put("game_over", new Media(new File("src/main/resources/sound/4-game_over.mp3").toURI().toString()));
	}

	public static AudioSound getInstancia() {
		if (instancia == null) {
			instancia = new AudioSound();
		}
		return instancia;
	}

	private void play(Media media, boolean loop) {
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setVolume(this.volumen);
		if (loop) {
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		}
		mediaPlayer.play();
	}

	public void playMenuSound() {
		Media media = mapa.get("menu");
		play(media, true);
	}

	public void playGameStartSound() {
		Media media = mapa.get("game_start");
		play(media, false);
	}

	public void playGameSound() {
		Media media = mapa.get("game_music");
		play(media, true);
	}

	public void playGanadorSound() {
		Media media = mapa.get("ganador");
		play(media, false);
	}

	public void playGameOverSound() {
		Media media = mapa.get("game_over");
		play(media, false);
	}

	public void stopSound() {
		mediaPlayer.stop();
	}

	@Override
	public void subirVolumenSound() {
		super.subirVolumenSound();
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volumen);
		}
	}

	@Override
	public void bajarVolumenSound() {
		super.bajarVolumenSound();
		if (mediaPlayer != null) {
			mediaPlayer.setVolume(volumen);
		}
	}
}
