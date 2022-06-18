package road_fighter;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import road_fighter.logica.Invocador;

public abstract class SceneHandler {
	protected final long NANOS_IN_SECOND = 1_000_000_000;
	protected final double NANOS_IN_SECOND_D = 1_000_000_000.0;

	protected int frames = 0;
	protected int last_fps_frame = 0;
	protected AtomicInteger fps = new AtomicInteger(0);

	protected AnimationTimer gameTimer;
	protected long previousNanoFrame;
	protected long previousNanoSecond;
	protected Main main;

	protected Scene scene;

	protected EventHandler<KeyEvent> keyEventHandler;
	protected EventHandler<KeyEvent> keyReleasedEventHandler;
	protected EventHandler<MouseEvent> mouseEventHandler;

	protected SceneHandler(Main main) {
		this.main = main;
		prepareScene();
		defineEventHandlers();
	}

	public void oneSecondUpdate(double delta) {
		fps.set(frames - last_fps_frame);
		last_fps_frame = frames;
	}

	public Scene getScene() {
		return scene;
	}

	public void update(double delta) {
		frames++;
		Invocador.getInstancia().update(delta);
	}

	public void afterUpdate() {
		Invocador.getInstancia().removePendings();
	}

	protected void addTimeEventsAnimationTimer() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long currentNano) {
				// Update tick
				update((currentNano - previousNanoFrame) / NANOS_IN_SECOND_D);
				previousNanoFrame = currentNano;

				// Update second
				if (currentNano - previousNanoSecond > NANOS_IN_SECOND) {
					oneSecondUpdate((currentNano - previousNanoSecond) / NANOS_IN_SECOND_D);
					previousNanoSecond = currentNano;
				}

				afterUpdate();
			}
		};

		previousNanoSecond = System.nanoTime();
		previousNanoFrame = System.nanoTime();
		gameTimer.start();
	}

	protected void addInputEvents() {
		if (keyEventHandler != null) {
			scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
		}

		if (keyReleasedEventHandler != null) {
			scene.addEventHandler(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
		}

		if (mouseEventHandler != null) {
			scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
		}
	}

	protected void removeInputEvents() {
		if (keyEventHandler != null) {
			scene.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
		}

		if (keyReleasedEventHandler != null) {
			scene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
		}

		if (mouseEventHandler != null) {
			scene.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
		}
	}

	protected abstract void prepareScene();

	protected abstract void defineEventHandlers();

	protected abstract void load(boolean start);

	protected void unload() {
		Invocador.getInstancia().clear();
		gameTimer.stop();
		removeInputEvents();
	}
}
