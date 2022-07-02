package road_fighter.entidades.cuerpos;

import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;
import road_fighter.logica.Partida;

/**
 * La clase {@code Meta} es una clase que hereda de {@code Cuerpo} y es el que
 * define cuando se llega al final del mapa.
 */
public class Meta extends Colisionables {

	private final Partida partidaActual;
	private Sprite sprite;
	private static final int SIZE = (int) (Config.height * 0.05);

	/**
	 * @param meta          :{@code float} - Posicion de la meta en el eje Y.
	 * @param ancho         :{@code float} - Ancho de la meta en el eje X.
	 * @param partidaActual :{@code Partida} - Partida actual donde Meta esta siendo
	 *                      instanciada.
	 */
	public Meta(double meta, double li, double ld, Partida partidaActual, boolean renderizable, long cuerpo_id) {
		super(Entidad.META, new Vector2D(li, -meta + Config.height), new Vector2D(ld - li, -SIZE), cuerpo_id);
		this.partidaActual = partidaActual;

		if (renderizable) {
			this.sprite = new Sprite("img/meta.png", new Vector2D(ld - li, SIZE));
			this.sprite.realocate(new Vector2D(0, -SIZE));

			this.render = sprite.getRender();
			this.render.setViewOrder(10);
		}
	}

	public Meta(double meta, double li, double ld, Partida partidaActual, long cuerpo_id) {
		this(meta, li, ld, partidaActual, true, cuerpo_id);
	}

	public Partida getPartidaActual() {
		return this.partidaActual;
	}

	@Override
	public void update(double delta) {
		if (posicion.getY() < Config.height && Config.currentVelocity > 0.0) {
			this.posicion.setY(this.posicion.getY() + (Config.currentVelocity * delta / Config.acceleration));

			if (sprite != null) {
				Sprite.setRenderPosition((ImageView) this.render, this.posicion);
			}
		}
	}

}
