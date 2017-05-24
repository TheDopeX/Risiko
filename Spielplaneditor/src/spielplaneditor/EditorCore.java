/**
 * Editor core
 * @author Udo Wiethaeuper
 * @version 1.0
 */

package spielplaneditor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polyline;

public class EditorCore {
    /**
     * observers
     */
    private LinkedList<SpielplanEditorObserver> observers = new LinkedList<SpielplanEditorObserver>();  
    /**
     * Name of the map
     */
    private String mapName = "Spielplan";
    /**
     * Maximum number of players
     */
    private int maxPlayers = 2;
    /**
     * Major version
     */
    private int versionMajor = 1;
    /**
     * Minor version
     */
    private int versionMinor = 0;
    /**
     * List of all continent groups
     */
    private List<ContinentGroup> continents = new ArrayList<ContinentGroup>();
    /**
     * List of all shapes that do not represent countries
     */
    private ShapeGroup decoShapes = new ShapeGroup();
    /**
     * List of all borders
     */
    private List<Border> borders = new ArrayList<Border>();
    /**
     * Constructor
     * 
     */
    public EditorCore() {
	this.mapName = "New Map";
    }
    /**
     * Add observer
     * @param observer
     */
    public void addObserver(SpielplanEditorObserver observer){
	observers.add(observer);
    }
    /**
     * Remove observer
     * @param observer
     */
    public void removeObserver(SpielplanEditorObserver observer){
	observers.remove(observer);
    }
    /**
     * 
     * @param version
     */
    public void setVersionMajor(int version){
	this.versionMajor = version;
    }
    /**
     * 
     * @return int
     */
    public int getVersionMajor(){
	return versionMajor;
    }
    /**
     * 
     * @param version
     */
    public void setVersionMinor(int version){
	this.versionMinor = version;
    }
    /**
     * 
     * @return int
     */
    public int getVersionMinor(){
	return versionMinor;
    }
    /**
     * 
     * @param name
     */
    public void setMapName(String name){
	this.mapName = name;
    }
    /**
     * 
     * @return string
     */
    public String getMapName(){
	return mapName;
    }
    /**
     * 
     * @param count
     */
    public void setMaxPlayers(int count){
	this.maxPlayers = count;
    }
    /**
     * 
     * @return int
     */
    public int getMaxPlayers(){
	return maxPlayers;
    }
    /**
     * Update observers
     */
    private void updateObservers(){
	for(SpielplanEditorObserver observer : observers){
	    observer.UpdateElements();
	}
    }
    /**
     * @return List of all continents
     */
    protected List<ContinentGroup> getContinents() {
	return continents;
    }
    /**
     * @return Array of all continents
     */
    public ContinentGroup[] getContinentsArray(){
	ContinentGroup[] c = new ContinentGroup[continents.size()];
	c = continents.toArray(c);
	return c;
    }
    /**
     * @return List of all non-country shapes
     */
    public ShapeGroup getDecoShapes() {
	return decoShapes;
    }
    /**
     * @return List of all borders
     */
    protected List<Border> getBorders() {
	return borders;
    }
    /**
     * 
     * @return Array of all borders
     */
    public Border[] getBorderArray(){
	Border[] b = new Border[borders.size()];
	b = borders.toArray(b);
	return b;
    }
    /**
     * Add a new continent
     */
    public void addContinent(ContinentGroup group){
	continents.add(group);
    }
    /**
     * Remove a continent
     * @param group
     */
    public void removeContinent(ContinentGroup group){
	continents.remove(group);
    }
    /**
     * Add Shape to Group
     * @param shape
     * @param group
     */
    public void addShape(Shape shape, Group group){
	group.addShape(shape);
	updateObservers();
    }
    /**
     * Remove shape and its borders
     * @param shape
     */
    public void removeShape(Shape shape){
	if(decoShapes.containsShape(shape)){
	    decoShapes.removeShape(shape);
	}else{
	    for(Group g : continents){
		if(g.containsShape(shape)){
		    g.removeShape(shape);
		}
	    }
	}
	for(Border b : borders){
	    if(b.hasShape(shape)){
		borders.remove(b);
	    }
	}
	updateObservers();
    }
    /**
     * Move a shape to another group
     * @param shape
     * @param targetGroup
     */
    public void moveShapeToGroup(Shape shape, Group targetGroup){
	if(decoShapes.containsShape(shape)){
	    decoShapes.removeShape(shape);
	}else{
	    for(Group g : continents){
		if(g.containsShape(shape)){
		    g.removeShape(shape);
		}
	    }
	}
	targetGroup.addShape(shape);
	updateObservers();
    }
    /**
     * Add new border to border list if it does not exist yet
     * @param shapeA
     * @param shapeB
     */
    public void addBorder(Shape shapeA, Shape shapeB){
	
	if(!(decoShapes.containsShape(shapeA) && decoShapes.containsShape(shapeB))){ //stop if shape is not a country
	    for(Border border : borders){
		if((border.getShapeA() == shapeA && border.getShapeB() == shapeB) || (border.getShapeA() == shapeB && border.getShapeB() == shapeA)){
        		break; //stop if border already exists
		}
	    }
	    borders.add(new Border(shapeA, shapeB));
	    updateObservers();
	}
    }
    /**
     * Checks if countries share a border and adds it if true
     */
    public void generateBorders(){
	List<Shape> shapes = new ArrayList<Shape>();
	for(ContinentGroup group : continents){
	    for(Shape shape : group.getShapes()){
		shapes.add(shape);
	    }
	}	    if(shapes.size() > 1){
        	    for(int i = 0; i < shapes.size() -1; i++){
        		for(int j = i+1;j < shapes.size();j++){
        		    if(Shape.hasBorderMatch(shapes.get(i), shapes.get(j))){
        			addBorder(shapes.get(i), shapes.get(j));
        		    }
        		}
        	    }
	    
	}
	updateObservers();
    }
    /**
     * Crops overlapping shapes
     */
    public void cropOverlaps(){
	List<Shape> shapes = new ArrayList<Shape>();
	for(ContinentGroup group : continents){
	    for(Shape shape : group.getShapes()){
		shapes.add(shape);
	    }
	}
	//�berlappungen entfernen
    }
    /**
     * this method copies a whole continent
     * @param ContinentGroup group
     */
    public void copyContinent(ContinentGroup group) {
    	Shape [] shapeArray = group.getShapeArray();
    	Shape shape = shapeArray[0];
    	LinkedList<Shape> listOfShapesToCopy = new LinkedList<Shape>();
    		for(int i = 0; i < shapeArray.length; i++) {
    			listOfShapesToCopy.add(shapeArray[i]);		
    		}
    	shape.copy(listOfShapesToCopy);
    }
    /**
     * this method copies one ore more shapes
     * @param LikedList<Shape> listOfShapes
     */
    public void copyShapes(LinkedList<Shape> listOfShapes) {
    	Shape shape = new Shape();
    	shape.copy(listOfShapes);
    }
    /**
     * this method pastes one or more shapes
     */
    public LinkedList<Shape> pasteShapes(Position pos) {
    	LinkedList<Shape> listOfShapes = new LinkedList<Shape>();    	
    	Shape shape = new Shape();
    	listOfShapes = shape.paste(pos);
    	return listOfShapes;
    }
    /**
     * this method calculates the bonus for a continent 
     * automatically
     */
    public void calculateContinentBonus(ContinentGroup group) {
    	double continentBonus = 0.0;
    	int numberOfExistingCountrys = 0;
    	double totalBonusOverContinents = 0;
    	double totalBonusOverBorders = 10;
    	for(int i = 0; i < getContinentsArray().length; i++) {
    		for(int j = 0; j < getContinentsArray()[i].getShapeArray().length; j++) {
    			numberOfExistingCountrys++;
    		}
    	}
    	totalBonusOverBorders = (double)(getBorderArray().length)
    							* ((double)bordersToOtherContinents(group) 
    									/ (double)getBorderArray().length);
    	totalBonusOverContinents = numberOfExistingCountrys / 2.0;
    	if(totalBonusOverContinents == 0) {
    		totalBonusOverContinents = 1;
    	}
    	continentBonus = ((double)totalBonusOverContinents 
    			* ((double)group.getShapeArray().length/ (double)numberOfExistingCountrys))
    			+  totalBonusOverBorders;
    	if(continentBonus < 1.0) {
    		continentBonus = 1.0;
    	}
    	group.setBonus((int)continentBonus);
    }
    /**
     * this method returns all borders from 
     * a continent to all others
     * @param ContinentGroup group
     * @return int numberOfBorders
     */
    private int bordersToOtherContinents(ContinentGroup group) {
    	int numberOfBorders = 0;
    	for(int i = 0; i < borders.size(); i++) {
    		for(int j = 0; j < getContinentsArray().length; j++) {
    			for(int f = 0; f < getContinentsArray()[j].getShapeArray().length; f++) {
    				if(getContinentsArray()[j] != group 
    						&& group.containsShape(
    								borders.get(i).getOpposite(getContinentsArray()[j].												
    									getShapeArray()[f]))) {
    					numberOfBorders++;
    				}
    			}
    		}
    	}
    	return numberOfBorders;
    }
    /**
     * this method cuts overlapping 
     * shapes. Also the equivalent core shape
     * will be replaced
     * @param spielplaneditor.Shape shape, spielplaneditor.Shape shape,
     * @param ContinentGroup contGroup2
     */
    public void cutOverlappingShapes(spielplaneditor.Shape shape,
    				spielplaneditor.Shape shape2, ContinentGroup contGroup2) {
    		//new Shape
    		spielplaneditor.Shape coreShape = null;
    		
    		//create Polyline of first shape
    		Polyline polyline1 = new Polyline();
    		//fill color is needed
    		polyline1.setFill(Color.RED);
    		//get points from shape 
    		for(int i = 0; i < shape.getLines().get(0).getPoints().length; i++) {
    				polyline1.getPoints().add(shape.getPosition().getX() +
    						shape.getLines().get(0).getPoints()[i].getX());
    				polyline1.getPoints().add(shape.getPosition().getY() +
    						shape.getLines().get(0).getPoints()[i].getY());
    		}
    		//create Polyline of second shape
    		Polyline polyline2 = new Polyline();
    		//fill color is needed
    		polyline2.setFill(Color.RED);
    		//get points from shape2
    		for(int i = 0; i < shape2.getLines().get(0).getPoints().length; i++) {
    				polyline2.getPoints().add(shape2.getPosition().getX() +
    						shape2.getLines().get(0).getPoints()[i].getX());
    				polyline2.getPoints().add(shape2.getPosition().getY() +
    						shape2.getLines().get(0).getPoints()[i].getY());
    		}
    		//check if shapes overlap each other
    		if(polyline1.intersects(polyline2.getBoundsInLocal())) {
	    		//untion both shapes and substract
    			//what remains is a shape without the
    			//overlapping area
    			Path path = (Path) Path.subtract(Path.union(polyline1, polyline2), polyline1);
    			//new shape
    			coreShape = new spielplaneditor.Shape();
    			coreShape.setArmyType(shape2.getArmyType());
    			coreShape.setFillColor(shape2.getFillColor());
    			coreShape.setImage(shape2.getImage());
    			coreShape.setLineColor(shape2.getLineColor());
    			coreShape.setLineWidth(shape2.getLineWidth());
    			coreShape.setName(shape2.getName());
    			coreShape.setOverwriteColor(shape2.getOverwriteColor());
    			coreShape.setPosition(shape2.getPosition());
    			//get points from new shape
    			double [] points = new double [(path.getElements().size()-1)*2];
    			int index = 0;
    			// going through all the path elements in the path,
    			// and get coordinates
    			for(PathElement el : path.getElements()){
    			    if(el instanceof MoveTo){
    			        MoveTo mt = (MoveTo) el;
    			        points[index] = mt.getX();
    			        points[index+1] = mt.getY();
    			    }
    			    if(el instanceof LineTo){
    			        LineTo lt = (LineTo) el;
    			        points[index] = lt.getX();
    			        points[index+1] = lt.getY();
    			    }
    			    index += 2;
    			}
    			//points of new shape
    			Position [] pos = new Position[points.length/2];
    			int z = 0;
    			for(int i = 0; i < points.length/2; i++) {
    				pos[i] = new Position(points[z++] - coreShape.getPosition().getX()
    						, points[z++] - coreShape.getPosition().getY());
    			}
    			//add new points
                coreShape.addLine(new Line(pos, coreShape));
                //first point needs to be added again
                //to close shape
                coreShape.getLines().get(0).addPoint(
                		new Position(points[0] - coreShape.getPosition().getX(),
                				points[1] - coreShape.getPosition().getY()));
                
                //remove old shape
                contGroup2.removeShape(shape2);
                //add new one
                contGroup2.addShape(coreShape);
    				
    		}	
    }
}
