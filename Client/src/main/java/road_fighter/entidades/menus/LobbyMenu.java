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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import road_fighter.Client;
import road_fighter.Config;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;
import road_fighter.networking.Sala;

public class LobbyMenu extends Menu {

	private static final double PADDING = Config.height * 0.05;
	private static final double HEIGHT = Y - FONT_SIZE;

	private Client client;
	private double vmax = 0;
	private Text[] texts;
	private int focus = 0;
	private Text deleteText;

	protected VBox salasBox;
	protected ScrollBar scroll;

	public LobbyMenu(Client client) {
		this.client = client;
		List<String> salas = client.getSalasNames();
		texts = new Text[salas.size()];

		salasBox = new VBox(PADDING);
		salasBox.setAlignment(Pos.TOP_CENTER);
		salasBox.setPrefWidth(2 * X);
		salasBox.setTranslateX(X);
		salasBox.setTranslateY(Y);
		salasBox.setFillWidth(true);

		for (int i = 0; i < texts.length; i++) {
			showLobbies(salas.get(i), i);
		}

		texts[focus].setFill(Color.CORAL);

		scroll = new ScrollBar();
		scroll.setLayoutX(Config.width - X);
		scroll.setLayoutY(Y);
		scroll.setOrientation(Orientation.VERTICAL);
		scroll.setPrefHeight(HEIGHT);
		scroll.setMax(vmax);

		scroll.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double value = newValue.doubleValue();
				if (value > scroll.getMax()) {
					value = scroll.getMax();
				} else if (value < scroll.getMin()) {
					value = scroll.getMin();
				}
				salasBox.setLayoutY(-value);
			}
		});

		deleteText = info("d - Eliminar Sala", 1);
		deleteText.setFill(Color.RED);
		deleteText.setVisible(false);

		Rectangle bottomBorder = new Rectangle(0, Config.height - FONT_SIZE, Config.width, FONT_SIZE);

		render = new Pane(salasBox, scroll, bottomBorder, deleteText, info("c - Crear Sala\nz - Unirse en Sala", 2));
	}

	private void showLobbies(String sala, int index) {
		texts[index] = new Text(sala.toUpperCase());
		texts[index].setTextAlignment(TextAlignment.CENTER);
		texts[index].setFont(font);
		texts[index].setFill(Color.WHITE);
		texts[index].setWrappingWidth(2 * X);

		vmax += texts[index].getLayoutBounds().getHeight() + PADDING / 2;
		salasBox.getChildren().add(texts[index]);
	}

	private void showToDeleteSala() {
		if (!deleteText.isVisible() && isOwner()) {
			deleteText.setVisible(true);
		} else {
			deleteText.setVisible(false);
		}
	}

	public void moveUp() {
		if (texts.length > 0) {
			texts[focus].setFill(Color.WHITE);
			focus = (focus == 0) ? texts.length - 1 : focus - 1;
			scroll.setValue(texts[focus].getLayoutY() - 50);
			texts[focus].setFill(Color.CORAL);
			showToDeleteSala();
		}
	}

	public void moveDown() {
		if (texts.length > 0) {
			texts[focus].setFill(Color.WHITE);
			focus = (focus == texts.length - 1) ? 0 : focus + 1;
			scroll.setValue(texts[focus].getLayoutY() - 50);
			texts[focus].setFill(Color.CORAL);
			showToDeleteSala();
		}
	}

	public boolean isOwner() {
		return client.isOwnerOf(texts[focus].getText());
	}

	public Mensaje eliminarSala() {
		Mensaje msg = new Mensaje(Comando.ELIMINAR_SALA);
		msg.agregar(texts[focus].getText());
		return msg;
	}

	public Mensaje unirseSala() {
		Mensaje msg = new Mensaje(Comando.UNIRSE_SALA);
		Sala sala = client.getSala(focus);
		if (sala != null) {
			msg.agregar(sala.getNombre());
			msg.agregar(sala.getOwner());
		}
		return msg;
	}

	public int getFocus() {
		return focus;
	}

}
