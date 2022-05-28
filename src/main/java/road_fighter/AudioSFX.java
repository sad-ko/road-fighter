package road_fighter;

import javafx.scene.media.AudioClip;

public final class AudioSFX {

	//usamos AudioClip para efectos de sonidos cortos porque se cargan en memoria
	/*ejemplo para llamar desde otra clase
	 * AudioClip explosion;
		explosion= AudioSFX.getExplosionAudio();
		explosion.play();
	 */
	
	    private static AudioClip create(String name) {
	    	return new AudioClip(ClassLoader.getSystemResource(name).toString());
	    }

		public static AudioClip getDerrapeAudio() {
			return create("sfx/derrape.wav");
		}

		public static AudioClip getExplosionAudio() {
			return create("sfx/explosion.wav");
		}

		public static AudioClip getLargadaStartAudio() {
			return create("sfx/largada_start.wav");
		}

		public static AudioClip getPowerUpAudio() {
			return create("sfx/powerUp.wav");
		}
		
		public static AudioClip getSemaforoStartAudio() {
			return create("sfx/semaforo_start.wav");
		}
		
	}
