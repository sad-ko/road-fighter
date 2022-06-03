package road_fighter.graficos;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import road_fighter.fisica.Vector2D;

public class Sprite {

	private static final String RESOURCES_DIR = "file:src/main/resources/";

	private ImageView render;
	private Vector2D scale;
	private Image sprite;

	public Sprite(String imageDir, Vector2D imageSize, Vector2D scale) {
		this.sprite = new Image(RESOURCES_DIR + imageDir, imageSize.getX() * scale.getX(),
				imageSize.getY() * scale.getY(), false, true);
		this.render = new ImageView(sprite);
		this.scale = scale;
	}

	public Sprite(String imageDir, Vector2D imageSize, float scale) {
		this(imageDir, imageSize, new Vector2D(scale, scale));
	}

	public Sprite(String imageDir, Vector2D imageSize) {
		this(imageDir, imageSize, new Vector2D(1, 1));
	}

	public static void setRenderPosition(ImageView render, Vector2D position) {
		render.setX(position.getX());
		render.setY(position.getY());
	}

	public void realocate(Vector2D position) {
		this.render.relocate(position.getX() * this.scale.getX(), position.getY() * this.scale.getY());
	}

	public void setViewport(Vector2D position, Vector2D size) {
		double x = position.getX() * this.scale.getX();
		double y = position.getY() * this.scale.getY();

		double ancho = size.getX() * this.scale.getX();
		double alto = size.getY() * this.scale.getY();

		Rectangle2D value = new Rectangle2D(x, y, ancho, alto);
		this.render.setViewport(value);
	}

	public ImageView getRender() {
		return render;
	}

	public Image getSprite() {
		return sprite;
	}
}
