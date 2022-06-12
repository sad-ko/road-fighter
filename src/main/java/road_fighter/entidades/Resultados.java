package road_fighter.entidades;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class Resultados extends Objeto {

	private Etiqueta resultadosLabel;

	public Resultados() {
		super(Entidad.TEXT, new Vector2D(0, 0));
		Rectangle box = new Rectangle(Config.width * 0.78, Config.height, Color.BLACK);
		box.setOpacity(0.5);

		resultadosLabel = new Etiqueta("...", new Vector2D(Config.mapLeft, Config.height / 4));
		resultadosLabel.setColor(Color.CORAL);
		render = new Pane(box, resultadosLabel.getRender());
	}

	@Override
	public void update(double delta) {
		resultadosLabel.update(delta);
		render.toFront();
	}

	@Override
	public void remover() {}

	public void setResultados(String resultados) {
		resultadosLabel.setText(resultados);
	}

}
