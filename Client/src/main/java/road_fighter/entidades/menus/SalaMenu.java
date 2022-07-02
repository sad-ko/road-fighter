package road_fighter.entidades.menus;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import road_fighter.Client;
import road_fighter.Config;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;
import road_fighter.networking.Sala;

public class SalaMenu extends Menu {

	private static final double PADDING = Config.height * 0.025;
	private static final double HEIGHT = Y - FONT_SIZE;

	private double vmax = 0;
	private Text[] texts;
	private Text salaInfo;
	private Text play;
	private int focus = 0;
	private Color color = Color.CORNFLOWERBLUE;
	private Client client;

	protected VBox playersBox;
	protected ScrollBar scroll;

	public SalaMenu(Client client) {
		this.client = client;

		List<String> players = client.getPlayersInSala();
		texts = new Text[players.size() + 1];

		playersBox = new VBox(PADDING);
		playersBox.setAlignment(Pos.TOP_CENTER);
		playersBox.setPrefWidth(2 * X);
		playersBox.setTranslateX(X / 4);
		playersBox.setTranslateY(Y / 4);
		playersBox.setFillWidth(true);

		for (int i = 0; i < players.size(); i++) {
			showPlayers(players.get(i), i);
		}

		play = new Text("A JUGAR");
		play.setFont(font);
		play.setFill(color);
		play.setLayoutX(Config.width / 2.0 - play.getLayoutBounds().getWidth() / 2.0);
		play.setLayoutY(Config.height - FONT_SIZE);
		texts[players.size()] = play;
		focus = players.size();
		texts[focus].setFill(color);

		scroll = new ScrollBar();
		scroll.setLayoutX(X / 4 + playersBox.getPrefWidth());
		scroll.setLayoutY(Y / 4);
		scroll.setOrientation(Orientation.VERTICAL);
		scroll.setPrefHeight(HEIGHT);
		scroll.setMax(vmax / 2.0);

		scroll.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double value = newValue.doubleValue();
				if (value > scroll.getMax()) {
					value = scroll.getMax();
				} else if (value < scroll.getMin()) {
					value = scroll.getMin();
				}
				playersBox.setLayoutY(-value);
			}
		});

		Rectangle topBorder = new Rectangle(0, 0, Config.width, FONT_SIZE * 2);

		Text salaName = new Text(client.getCurrentSala().getNombre());
		salaName.setFont(font);
		salaName.setFill(color);
		salaName.setLayoutX(Config.width / 2.0 - salaName.getLayoutBounds().getWidth() / 2.0);
		salaName.setLayoutY(FONT_SIZE);

		salaInfo();
		render = new Pane(playersBox, scroll, topBorder, salaName, salaInfo, play);
	}

	private void showPlayers(String sala, int index) {
		texts[index] = new Text(sala.toUpperCase());
		texts[index].setTextAlignment(TextAlignment.CENTER);
		texts[index].setFont(font);
		texts[index].setFill(Color.WHITE);
		texts[index].setWrappingWidth(2 * X);

		vmax += texts[index].getLayoutBounds().getHeight() + PADDING / 2;
		playersBox.getChildren().add(texts[index]);
	}

	private Text salaInfo() {
		Sala sala = client.getCurrentSala();

		StringBuilder info = new StringBuilder();
		info.append("Jugadores: " + sala.getCantidadActual() + " / " + sala.getCantidadMaxima());
		info.append("\nDificultad: " + sala.getDificultadString());
		info.append("\nPropietario: " + sala.getOwner());

		salaInfo = new Text(info.toString());
		Font auxFont = Font.loadFont(ClassLoader.getSystemResource("font/monogram.ttf").toString(), FONT_SIZE / 2);

		salaInfo.setFont(auxFont);
		salaInfo.setFill(color);
		salaInfo.setLayoutX(playersBox.getPrefWidth() + X - salaInfo.getLayoutBounds().getWidth() / 2.0);
		salaInfo.setLayoutY(playersBox.getTranslateY());
		return salaInfo;
	}

	public void moveUp() {
		if (texts.length > 0) {
			texts[focus].setFill(Color.WHITE);
			focus = (focus == 0) ? texts.length - 1 : focus - 1;
			scroll.setValue(texts[focus].getLayoutY() - 50);
			texts[focus].setFill(color);
		}
	}

	public void moveDown() {
		if (texts.length > 0) {
			texts[focus].setFill(Color.WHITE);
			focus = (focus == texts.length - 1) ? 0 : focus + 1;
			if (focus < texts.length - 1 && playersBox.getChildren().get(focus).getLayoutY() > scroll.getTranslateY()
					+ scroll.getPrefHeight()) {
				scroll.setValue(texts[focus].getLayoutY() - 50);
			}
			texts[focus].setFill(color);
		}
	}

	public void startGame() {
		if (focus == texts.length - 1) {
			Mensaje msg = new Mensaje(Comando.COMENZAR_PARTIDA);
			client.enviar(msg);
		}
	}

}
