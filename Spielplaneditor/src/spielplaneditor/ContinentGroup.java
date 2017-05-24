/**
 * Represents a continent with its specific values and a list of all countries.
 * @author Udo Wiethaeuper
 * @version 1.0
 */
package spielplaneditor;

import java.util.LinkedList;
import java.util.Random;

public class ContinentGroup extends Group {
    /**
     * background color
     */
    private Color fillColor;
    /**
     * line color
     */
    private Color lineColor;
    /**
     * line width
     */
    private double lineWidth;
    /**
     * name
     */
    private String name;
    /**
     * unique identifier
     */
    private int id;
    /**
     * bonus
     */
    private int bonus;
    /**
     * automatically calculate bonus
     */
    private boolean autocalculateForBonus;
    /**
     * keeps track of IDs
     */
    private static int lastID = 0;
        /**
     * list of all existing Colors which are used as continent background
     */
    private static LinkedList<Color> listOfExistingContinentColors = new LinkedList<Color>();
    
    /**
     * constructor
     *      */    
    public ContinentGroup() {
	// TODO Auto-generated constructor stub
	this.setFillColor(autoFillColor());
	
	id = ContinentGroup.nextID();
	this.name = "Continent" + id;
    }
    /**
     * @return name
     */
    public String getName() {
	return name;
    }
    /**
     * @param name
     */
    public void setName(String name) {
	this.name = name;
    }
    /**
     * @return unique ID
     */
    public int getId() {
	return id;
    }
    /**
     * @return bonus
     */
    public int getBonus() {
	return bonus;
    }
    /**
     * @param bonus
     */
    public void setBonus(int bonus) {
	this.bonus = bonus;
    }
    /**
     * @return boolean autocalculateForBonus
     */
    public boolean getAutocalculateForContinentBonus() {
    	return autocalculateForBonus;
    }
    /**
     * @param boolean autocalc
     */
    public void setAutocalculateForBonus(boolean autocalc) {
    	autocalculateForBonus = autocalc;
    }
    /**
     * @return background color
     */
    public Color getFillColor() {
	return fillColor;
    }
    /**
     * @param background color
     */
    public void setFillColor(Color color) {
	fillColor = color;
    }
    /**
     * @return line color
     */
    public Color getLineColor() {
	return lineColor;
    }
    /**
     * @param line color
     */
    public void setLineColor(Color color) {
	lineColor = color;
	
    }
    /**
     * @return line width
     */
    public double getLineWidth() {
	return lineWidth;
    }
    /**
     * @param line width
     */
    public void setLineWidth(double width) {
	lineWidth = width;
    }
    /**
     * Increments global ID counter and returns next available continent ID
     * @return unique ID
     */
    protected static int nextID(){
	lastID++;
	return lastID;
    }
    /**
     * This method fills shapes with a color
     * if no color is given by the user
     * @param
     * @return
     */
    private spielplaneditor.Color autoFillColor() {
    	spielplaneditor.Color col = null;
    	if(lastID >= StandardColor.values().length) {
    		col = createRandomContinentColor();
    	}
    	else {
    	switch(lastID) {
    		case 0:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.BLUE);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 1:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.RED);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 2:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.GREEN);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 3:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.ORANGE);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 4:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.GREY);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 5:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.PINK);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 6:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.MAGENTA);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 7:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.YELLOW);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 8:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.BROWN);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 9:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.CYAN);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 10:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.DARKGREY);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 11:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.SIENNA);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 12:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.DARKGREEN);
    			listOfExistingContinentColors.add(col);
    			break;
    		case 13:
    			col = StandardColor.getRGBOfStandardColor(StandardColor.DARKGOLD);
    			listOfExistingContinentColors.add(col);
    			break;
    	}
    	}
    	return col;
    }
    /**
     * this method creates a random color
     * for a new continent
     */
    private spielplaneditor.Color createRandomContinentColor() {
    	double red = 1.0;
    	double green = 1.0;
    	double blue = 1.0;
    	double alpha = 1.0;
    	Color col = new Color(red, green, blue, alpha);
    	boolean colorAlreadyExists = true;
    	try {
    		while(colorAlreadyExists) {
    			Random rand = new Random();
    			red = rand.nextDouble();
    			green = rand.nextDouble();
    			blue = rand.nextDouble();
    			col = new Color(red, green, blue, alpha);
    			if(listOfExistingContinentColors.size() == 0) {
    				listOfExistingContinentColors.add(col);
    				colorAlreadyExists = false;
    			}
    			for(int i = 0; i < listOfExistingContinentColors.size(); i++) {
    	    		if(!listOfExistingContinentColors.get(i).getColor().equals(col)) {
    	    			colorAlreadyExists = false;
    	    			listOfExistingContinentColors.add(col);
    	    			break;
    	    		}
    	    	}
    		}
    	} catch (Exception e) {
    		logging.Logger.schreibeFehler(e);
    	}
    	return col;
    }
    
}
