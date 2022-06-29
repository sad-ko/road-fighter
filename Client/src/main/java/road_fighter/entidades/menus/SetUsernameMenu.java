package road_fighter.entidades.menus;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import road_fighter.Config;

public class SetUsernameMenu extends Menu {

	private TextField input;

	public SetUsernameMenu() {
		Text setUsername = new Text("ESCRIBE TU\nUSERNAME");
		setUsername.setTextAlignment(TextAlignment.CENTER);
		setUsername
				.setFont(Font.loadFont(ClassLoader.getSystemResource("font/monogram.ttf").toString(), FONT_SIZE * 2));
		setUsername.setFill(Color.CORNFLOWERBLUE);
		setUsername.setTranslateX(X);
		setUsername.setTranslateY(Y / 2);

		setInput();
		render = new Pane(setUsername, input);
	}

	public String getUsername() {
		return input.getText().trim();
	}

	private void setInput() {
		input = new TextField();
		input.setPromptText("Username");
		input.setPrefSize(X * 2, FONT_SIZE);
		input.setTranslateX(Config.width / 2 - input.getPrefWidth() / 2);
		input.setTranslateY(Y - FONT_SIZE / 2);
		input.setFont(font);

		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case ENTER:
				case ESCAPE:
					render.requestFocus();
					break;

				default:
					break;
				}
			}
		});
	}

}
