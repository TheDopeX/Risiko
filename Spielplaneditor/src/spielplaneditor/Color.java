/**
 * Color values as RGBA
 * @author Florian Huber
 * @version 1.0
 */
package spielplaneditor;

public class Color {
    /**
     * RGBA values
     */
    private double r,g,b,a;
    /**
     * Constructor. Value range for parameters is 0-1;
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    public Color(double red, double green, double blue, double alpha){
	r = red;
	g = green;
	b = blue;
	a = alpha;
	limitValues();
    }
    /**
     * Constructor. Value range for parameters is 0-255
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    public Color(int red, int green, int blue, int alpha) {
	r = (red / 255.0);
	g = (green / 255.0);
	b = (blue / 255.0);
	a = (alpha / 255.0);
	limitValues();
    }
    /**
     * checks if the values are within the range from 0 to 1 and adjusts them if not
     */
    private void limitValues(){
	if(r < 0) r = 0;
	if(r > 1) r = 1;
	if(g < 0) g = 0;
	if(g > 1) g = 1;
	if(b < 0) b = 0;
	if(b > 1) b = 1;
	if(a < 0) a = 0;
	if(a > 1) a = 1;
    }
    /**
     * 
     * @return red value
     */
    public double getRed(){
	return r;
    }
    
    /**
     * @return green value
     */
    public double getGreen(){
	return g;
    }
    
    /**
     * @return blue value
     */
    public double getBlue(){
	return b;
    }
    
    /**
     * @return alpha value
     */
    public double getAlpha(){
	return a;
    }
    
    /**
     * @return Array with RGBA values
     */
    public double[] getColor(){
	double[] col = {r,g,b,a};
	return col;
    }
    /**
     * Returns color as hexadecimal value. ignores alpha
     * @return
     */
    public String toHex(){
	String red = Integer.toHexString((int)(r * 255));
	if(red.equals("0")){
	    red = "00";
	}
	String green = Integer.toHexString((int)(g * 255));
	if(green.equals("0")){
	    green = "00";
	}
	String blue = Integer.toHexString((int)(b * 255));
	if(blue.equals("0")){
	    blue = "00";
	}
	return "#" + red + green + blue;
    }
    public String toRGBAString(){
	String red = Integer.toString((int)(r * 255));
	String green = Integer.toString((int)(g * 255));
	String blue = Integer.toString((int)(b * 255));
	String alpha = Double.toString(a);
	return "rgba(" + red + "," + green + "," + blue + "," + alpha + ")";
    }
}
