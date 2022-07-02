package road_fighter.entidades.menus;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import road_fighter.Config;
import road_fighter.logica.Dificultad;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;

public class CreateSalaMenu extends Menu {

	private static final double PADDING = Config.height * 0.1;

	private TextField input;
	private Text players;
	private Text difficulty;
	private Text confirm;
	private int maxPlayers = Config.minPlayers;
	private int dificultad = 0;
	private Node[] options;
	private int focus = 0;

	public CreateSalaMenu() {
		Text crearSala = new Text("CREAR SALA");
		crearSala.setTextAlignment(TextAlignment.CENTER);
		crearSala.setFont(Font.loadFont(ClassLoader.getSystemResource("font/monogram.ttf").toString(), FONT_SIZE * 2));
		crearSala.setFill(Color.CORNFLOWERBLUE);
		crearSala.setTranslateX(X);
		crearSala.setTranslateY(Y / 2);

		setInput();
		setPlayers();
		setDifficulty();
		setConfirm();
		render = new Pane(crearSala, input, players, difficulty, confirm);
		options = new Node[] { input, players, difficulty, confirm };
	}

	public boolean inConfirmar() {
		return focus == options.length - 1;
	}

	public void inputFocus() {
		input.requestFocus();
	}

	public void moveUp() {
		if (options[focus] instanceof Text) {
			((Text) options[focus]).setFill(Color.WHITE);
		}
		focus = (focus == 0) ? options.length - 1 : focus - 1;
		options[focus].requestFocus();
		if (options[focus] instanceof Text) {
			((Text) options[focus]).setFill(Color.CORNFLOWERBLUE);
		}
	}

	public void moveDown() {
		if (options[focus] instanceof Text) {
			((Text) options[focus]).setFill(Color.WHITE);
		}
		focus = (focus == options.length - 1) ? 0 : focus + 1;
		options[focus].requestFocus();
		if (options[focus] instanceof Text) {
			((Text) options[focus]).setFill(Color.CORNFLOWERBLUE);
		}
	}

	public void moveRight() {
		switch (focus) {
		case 1:
			maxPlayers = (maxPlayers == Config.maxPlayers) ? Config.minPlayers : maxPlayers + 1;
			players.setText("Cantidad max. de Jugadores: < " + maxPlayers + " >");
			break;

		case 2:
			dificultad = (dificultad == 2) ? 0 : dificultad + 1;
			difficulty.setText("Dificultad: < " + Dificultad.values()[dificultad] + " >");
			break;
		default:
			break;
		}
	}

	public void moveLeft() {
		switch (focus) {
		case 1:
			maxPlayers = (maxPlayers == Config.minPlayers) ? Config.maxPlayers : maxPlayers - 1;
			players.setText("Cantidad max. de Jugadores: < " + maxPlayers + " >");
			break;

		case 2:
			dificultad = (dificultad == 0) ? 2 : dificultad - 1;
			difficulty.setText("Dificultad: < " + Dificultad.values()[dificultad] + " >");
			break;
		default:
			break;
		}
	}

	public Mensaje crearSala() {
		Mensaje msg = new Mensaje(Comando.CREAR_SALA);
		msg.agregar(input.getText().trim());
		msg.agregar(maxPlayers);
		msg.agregar(dificultad);
		return msg;
	}

	private void setInput() {
		input = new TextField();
		input.setPromptText("Sala Name");
		input.setPrefSize(X * 2, FONT_SIZE);
		input.setTranslateX(Config.width / 2 - input.getPrefWidth() / 2);
		input.setTranslateY(Y - FONT_SIZE / 2);
		input.setFont(font);
		input.setFocusTraversable(false);

		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case ENTER:
					moveDown();
					break;

				case ESCAPE:
					render.requestFocus();
					break;

				case UP:
					moveUp();
					break;

				case DOWN:
					moveDown();
					break;

				default:
					break;
				}
			}
		});
	}

	private void setPlayers() {
		players = new Text("Cantidad max. de Jugadores: < " + maxPlayers + " >");
		players.setTextAlignment(TextAlignment.CENTER);
		players.setFont(font);
		players.setFill(Color.WHITE);
		players.setTranslateX(X / 2);
		players.setTranslateY(input.getTranslateY() + input.getPrefHeight() + PADDING);
	}

	private void setDifficulty() {
		difficulty = new Text("Dificultad: < " + Dificultad.values()[dificultad] + " >");
		difficulty.setTextAlignment(TextAlignment.CENTER);
		difficulty.setFont(font);
		difficulty.setFill(Color.WHITE);
		difficulty.setTranslateX(players.getTranslateX());
		difficulty.setTranslateY(players.getTranslateY() + PADDING);
	}

	private void setConfirm() {
		confirm = new Text("CONFIRMAR");
		confirm.setTextAlignment(TextAlignment.CENTER);
		confirm.setFont(font);
		confirm.setFill(Color.WHITE);
		confirm.setTranslateX(Config.width / 2 - confirm.getLayoutBounds().getWidth() / 2);
		confirm.setTranslateY(difficulty.getTranslateY() + PADDING);
	}

}
