package road_fighter;

public class Config {

	public static final double width = 1024;
	public static final double height = 1024;
	public static final int cellSize = (int) (Config.width / 16);

	public static final double mapLeft = Config.width * 0.215;
	public static final double mapRight = Config.width * 0.602;
	public static final double mapaLength = Config.height * 24;
	
	public static final int minPlayers = 2;
	public static final int maxPlayers = 8;

	public static double acceleration = 0.1;
	public static double currentVelocity = 0.0;

	private Config() {}
}
