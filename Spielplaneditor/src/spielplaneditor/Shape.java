/**
 * Shapes are drawn objects and can either be countries or decorative elements.
 * Properties that affect countries only will be kept for decorative elements but will not affect them.
 * @author Udo Wiethaeuper
 * @version 1.0
 */
package spielplaneditor;

import java.util.LinkedList;
import java.util.List;

import kern.ArmeeTyp;

public class Shape {
    /**
     * keeps track of IDs
     */
    private static int lastID;
    /**
     * list of elements for copy command
     */
    private static LinkedList<Shape> copyList = new LinkedList<Shape>();
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
     * Overwrite continent color settings?
     */
    private boolean overwriteColor;
    /**
     * unique id
     */
    private int id;
    /**
     * name
     */
    private String name;
    /**
     * position
     */
    private Position position;
    /**
     * lines that represent this shape
     */
    private List<Line> lines = new LinkedList<Line>();
    /**
     * Armyclass for cards
     */
    private ArmeeTyp armyType;
    
    /**
     * image
     */
    private Image image;
    /**
     * upper left image position
     */
    private Position imageLeftTop;
    /**
     * lower right image position
     */
    private Position imageRightBottom;
    
    /**
     * constructor
     */
    public Shape() {	
	this.id = Shape.nextID();
    }
    
    /**
     * constructor
     * @param position
     */
    public Shape(Position position) {
	this.position = position;
	this.fillColor = new Color(0.5,0.5,0.5,1.0);
	this.lineColor = new Color(1.0,1.0,1.0,1.0);
	this.lineWidth = 1;
	this.overwriteColor = false;
	this.name = "Shape";
	this.armyType = ArmeeTyp.Bodeneinheit;
	this.id = Shape.nextID();
    }
    /**
     * 
     * @param position
     * @param name
     * @param armyType
     */
    public Shape(Position position, String name, ArmeeTyp armyType){
	this.position = position;
	this.name = name;
	this.armyType = armyType;
	this.fillColor = new Color(0.5,0.5,0.5,1.0);
	this.lineColor = new Color(1.0,1.0,1.0,1.0);
	this.lineWidth = 1;
	this.overwriteColor = false;
	this.id = Shape.nextID();
    }
    /**
     * 
     * @param position
     * @param name
     * @param armyType
     * @param overwriteColor
     * @param fillColor
     * @param lineColor
     * @param lineWidth
     */
    public Shape(Position position, String name, ArmeeTyp armyType, boolean overwriteColor, Color fillColor, Color lineColor, double lineWidth){
	this.position = position;
	this.name = name;
	this.armyType = armyType;
	this.fillColor = fillColor;
	this.lineColor = lineColor;
	this.lineWidth = lineWidth;
	this.overwriteColor = overwriteColor;
    }
    
    /**
     * constructor for copies
     * @param shape to copy, position of new shape, name of shape
     */
    public Shape(Shape shape, Position position, String name) {
    	this.fillColor = new Color(shape.getFillColor().getRed(), shape.getFillColor().getGreen(),
    			shape.getFillColor().getBlue(), shape.getFillColor().getAlpha());
    	this.lineColor = new Color(shape.getLineColor().getRed(), shape.getLineColor().getGreen(),
    			shape.getLineColor().getBlue(), shape.getLineColor().getAlpha());
    	this.lineWidth = shape.getLineWidth();
    	// Following line has to be checked again...
    	this.overwriteColor = this.getOverwriteColor();
    	this.name = name;
    	this.position = position;
    	this.armyType = shape.getArmyType();
    	this.image = shape.getImage();
    	for(Line l : shape.getLines()){
    		this.lines.add(new Line(l.getPoints(), this));
    	}
    	this.image = shape.getImage();
    	// Following line has to be checked again...
    	this.imageRightBottom = new Position(shape.getPosition().getX(), shape.getPosition().getY());
    	// Following line has to be checked again...
    	this.imageLeftTop = new Position(shape.getPosition().getX(), shape.getPosition().getY());
    }
    /**
     * returns the unique Shape ID
     * @return id
     */
    public int getID(){
	return id;
    }
    /**
     * @return background color
     */
    public Color getFillColor(){
	return fillColor;
    }
    /**
     * @param background color
     */
    public void setFillColor(Color color){
	fillColor = color;
    }
    /**
     * @return line color
     */
    public Color getLineColor(){
	return lineColor;
    }
    /**
     * @param line color
     */
    public void setLineColor(Color color){
	lineColor = color;
    }
    /**
     * @return line width
     */
    public double getLineWidth(){
	return lineWidth;
    }
    /**
     * @param line width
     */
    public void setLineWidth(double width){
	lineWidth = width;
    }
    /**
     * @return overwrite continent color?
     */
    public boolean getOverwriteColor(){
	return overwriteColor;
    }
    /**
     * @param overwrite continent color?
     */
    public void setOverwriteColor(boolean value){
	overwriteColor = value;
    }
    /**
     * @return position
     */
    public Position getPosition(){
	return position;
    }
    /**
     * @param position
     */
    public void setPosition(Position position){
	this.position = position;
    }
    /**
     * @return list of lines that represent the shape
     */
    public List<Line> getLines(){
	return lines;
    }
    /**
     * 
     * @return array of lines
     */
    public Line[] getLineArray(){
	Line[] l = new Line[lines.size()];
	l = lines.toArray(l);
	return l;
    }
    /**
     * @Param line to add
     */
    public void addLine(Line line){
	lines.add(line);
    }
    /**
     * @param line to remove
     */
    public void removeLine(Line line){
	lines.remove(line);
    }
    /**
     * @return army type
     */
    public ArmeeTyp getArmyType(){
	return armyType;
    }
    /**
     * @param army type
     */
    public void setArmyType(ArmeeTyp armytype){
	this.armyType = armytype;
    }
    /**
     * @return name
     */
    public String getName(){
	return name;
    }
    /**
     * @param name
     */
    public void setName(String name){
	this.name = name;
    }
    /**
     * @return image
     */
    public Image getImage(){
	return image;
    }
    /**
     * 
     * @param image
     */
    public void setImage(Image image){
	this.image = image;
    }
    
    /**
     * Check if two shapes have matching borders
     * @param a
     * @param b
     * @return
     */
    public static boolean hasBorderMatch(Shape a, Shape b){
	for(Line lineA : a.getLines()){
	    for(Line lineB : b.getLines()){
		if(Line.checkLineMatch(lineA, lineB)){
		    return true;
		}
	    }
	}
	return false;
    }
    
    
    /**
     * This method copies a list of shapes.
     * @param shape
     * @param pos
     */
    protected void copy(LinkedList<Shape> shape) {
    	if(!shape.isEmpty()) {
    	    copyList = shape;
    	}
    	else {
    	    logging.Logger.schreibeFehler("[ERROR] package spielplaneditor class shape: empty list can not be copied");
    	}
    }
    
   /**
    * This method can paste copied shapes.
    * For every copied shape this method will
    * create a new object from type shape with the same attributes.
    * The position of new shapes is the position from the copied shape
    * plus the position which is given as parameter.
    * @param pos
    * @return newShapes
    */
    protected LinkedList<Shape> paste(Position pos) {
    	LinkedList<Shape> newShapes = new LinkedList<Shape>();
		int index = 0;	
		try {
			while(!copyList.isEmpty()) {
				index++;
				double posX = pos.getX();
				double posY = pos.getY();
				pos = new Position((copyList.getFirst().getPosition().getX() + pos.getX()),
						(copyList.getFirst().getPosition().getY() + pos.getY()));
				Shape sh = new Shape(copyList.getFirst(), pos, 
					(copyList.getFirst().getName() + "Copy" + index));			
				newShapes.add(sh);
				pos = new Position(posX, posY);
				copyList.removeFirst();
			}
		} catch (Exception e) {
			logging.Logger.schreibeFehler(e);
		}
		return newShapes;
	}    
    protected static int nextID(){
	lastID++;
	return lastID;
    }
}
