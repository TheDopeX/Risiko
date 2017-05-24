/**
 * Line. One or more lines form a shape. A line consists of 1-* Points.
 * @author Udo Wiethäuper
 * @version 1.0
 */
package spielplaneditor;

import java.util.LinkedList;
import java.util.List;

public class Line {
    
    /**
     * points that form the line
     */
    private List<Position> points = new LinkedList<Position>();
    
    /**
     * parent shape
     */
    private Shape parent;
    /**
     * 
     * @param parent
     */
    public Line(Shape parent){
	this.parent = parent;
    }
    /**
     * Constructor
     * @param startPos
     * @param parent
     */
    public Line(Position startPos, Shape parent) {
	points.add(startPos);
	this.parent = parent;
    }
    public Line(Position[] points, Shape parent){
	for(Position pos : points){
	    this.points.add(pos);
	}
	this.parent = parent;
    }
    /**
     * Return array of points
     * @return points
     */
    public Position[] getPoints(){
	Position[] arr = new Position[points.size()];
	arr = points.toArray(arr);
	return arr;
    }
    /**
     * add a point to end of line
     * @param pos
     */
    public void addPoint(Position pos){
	points.add(pos);
    }
    /**
     * insert a point after index
     * @param pos
     * @param afterIndex
     */
    public void insertPoint(Position pos, int afterIndex){
	points.add(afterIndex, pos);
    }
    /**
     * remove a specified point
     * @param pos
     */
    public void removePoint(Position pos){
	points.remove(pos);
    }
    /**
     * remove point with index
     * @param index
     */
    public void removePoint(int index){
	points.remove(index);
    }
    
    /**
     * returns array with points translated to global positions
     * @return array with global points
     */
    public Position[] getGlobalPoints(){
	Position[] arr = new Position[points.size()];
	arr = points.toArray(arr);
	for(int i = 0; i < arr.length;i++){
	    arr[i] = Position.add(parent.getPosition(), arr[i]);
	}
	return arr;
    }
    /**
     * Check if a point of a shape is on the line of another shape, i.e. they have a border.
     * This is done by comparing the distance of adjacent points of a line with the sum of the
     * distances of both points to a point of another shape. If they're equal (or almost equal
     * due to floating point errors) the point is on the line, otherwise it is not.
     * @param a
     * @param b
     * @return
     */
    public static boolean checkLineMatch(Line a, Line b){
	Position[] aPoints = a.getGlobalPoints();
	Position[] bPoints = b.getGlobalPoints();
	if(aPoints.length > 1 && bPoints.length > 1){
	    for(int i = 1; i < aPoints.length; i++){
		for(int j = 1; j < bPoints.length; j++){
		    double variance = 1.0;
		    double ac = Position.distance(aPoints[i], bPoints[j]);
		    double bc = Position.distance(aPoints[i-1], bPoints[j]);
		    double ab = Position.distance(aPoints[i], aPoints[i-1]);
		    double diff = ac + bc -ab;
		    if(diff < variance && diff > -variance){
			return true;
		    }
		    ac = Position.distance(aPoints[i], bPoints[j-1]);
		    bc = Position.distance(aPoints[i-1], bPoints[j-1]);
		    ab = Position.distance(aPoints[i], aPoints[i-1]);
		    diff = ac + bc -ab;
		    if(diff < variance && diff > -variance){
			return true;
		    }
		    ac = Position.distance(bPoints[j], aPoints[i]);
		    bc = Position.distance(bPoints[j-1], aPoints[i]);
		    ab = Position.distance(bPoints[j], bPoints[j-1]);
		    diff = ac + bc -ab;
		    if(diff < variance && diff > -variance){
			return true;
		    }
		    ac = Position.distance(bPoints[j], aPoints[i-1]);
		    bc = Position.distance(bPoints[j-1], aPoints[i-1]);
		    ab = Position.distance(bPoints[j], bPoints[j-1]);
		    diff = ac + bc -ab;
		    if(diff < variance && diff > -variance){
			return true;
		    }
		    

		}
	    }
	}
	return false;
    }
}
