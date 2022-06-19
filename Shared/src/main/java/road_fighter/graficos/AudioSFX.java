package road_fighter.graficos;

import java.util.HashMap;
import javafx.scene.media.AudioClip;

public final class AudioSFX extends Audio {

	private static AudioSFX instancia = null;
	private HashMap<String, AudioClip> audios;

	private AudioSFX() {
		super();
		this.audios = new HashMap<>();
		audios.put("derrape", new AudioClip(ClassLoader.getSystemResource("sfx/derrape.wav").toString()));
		audios.put("explosion", new AudioClip(ClassLoader.getSystemResource("sfx/explosion.wav").toString()));
		audios.put("largada_start", new AudioClip(ClassLoader.getSystemResource("sfx/largada_start.wav").toString()));
		audios.put("powerUp", new AudioClip(ClassLoader.getSystemResource("sfx/powerUp.wav").toString()));
		audios.put("semaforo_start", new AudioClip(ClassLoader.getSystemResource("sfx/semaforo_start.wav").toString()));
		audios.put("running", new AudioClip(ClassLoader.getSystemResource("sfx/running.wav").toString()));
	}

	public static AudioSFX getInstancia() {
		if (instancia == null) {
			instancia = new AudioSFX();
		}

		return instancia;
	}

	public void play(String name, boolean forcePlay) {
		AudioClip audio = audios.get(name);

		if (audio == null || volumen == 0.0) {
			return;
		}

		if (!audio.isPlaying() || forcePlay) {
			audio.play(this.volumen);
		}
	}

	public void play(String name) {
		play(name, false);
	}

	public void stop(String name) {
		AudioClip audio = audios.get(name);
		if (audio != null && audio.isPlaying()) {
			audio.stop();
		}
	}
}
