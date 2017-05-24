package spielplaneditor;

public enum StandardColor {
	BLUE, RED, GREEN, ORANGE, GREY, PINK, MAGENTA,YELLOW,
	BROWN, CYAN, DARKGREY, SIENNA, DARKGREEN, DARKGOLD;
	
	/**
	 * this method returns RGB values of
	 * enumeration types in this class
	 * @param StandardColor col
	 * @return spielplaneditor.Color color
	 */
	public static spielplaneditor.Color getRGBOfStandardColor(StandardColor col) {
		spielplaneditor.Color color = null;
		switch(col) {
		case BLUE:
			color = new spielplaneditor.Color(0.0, 0.0, 1.0, 1.0);
			break;
		case RED:
			color = new spielplaneditor.Color(1.0, 0.0, 0.0, 1.0);
			break;
		case GREEN:
			color = new spielplaneditor.Color(0.0, 1.0, 0.0, 1.0);
			break;
		case ORANGE:
			color = new spielplaneditor.Color(1.0, 0.65, 0.0, 1.0);
			break;
		case GREY:
			color = new spielplaneditor.Color(0.75, 0.75, 0.75, 1.0);
			break;
		case PINK:
			color = new spielplaneditor.Color(1.0, 0.75, 0.8, 1.0);
			break;
		case MAGENTA:
			color = new spielplaneditor.Color(1.0, 0, 1.0, 1.0);
			break;
		case YELLOW:
			color = new spielplaneditor.Color(1.0, 1.0, 0, 1.0);
			break;
		case BROWN:
			color = new spielplaneditor.Color(0.65, 0.16, 0.16, 1.0);
			break;
		case CYAN:
			color = new spielplaneditor.Color(0.0, 1.0, 1.0, 1.0);
			break;
		case DARKGREY:
			color = new spielplaneditor.Color(0.32, 0.32, 0.32, 1.0);
			break;
		case SIENNA:
			color = new spielplaneditor.Color(1.0, 0.51, 0.40, 1.0);
			break;
		case DARKGREEN:
			color = new spielplaneditor.Color(0.0, 0.39, 0.0, 1.0);
			break;
		case DARKGOLD:
			color = new spielplaneditor.Color(0.93, 0.68, 0.06, 1.0);
			break;
		}
		return color;
	}
}
