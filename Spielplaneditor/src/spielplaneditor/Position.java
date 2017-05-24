/**
 * Positions are two-dimensional Vectors
 * @author Florian Huber
 * @version 1.0
 */
package spielplaneditor;

public class Position {
    /**
     * Coordinates
     */
    private double xPos, yPos;
    
    /**
     * Constructor
     * @param xPos
     * @param yPos
     */
    public Position(double xPos, double yPos) {
	this.xPos = xPos;
	this.yPos = yPos;
    }
    /**
     * Returns the sum of two vectors
     * @param pos1
     * @param pos2
     * @return Sum of two vectors
     */
    public static Position add(Position pos1, Position pos2){
	double x = pos1.getX() + pos2.getX();
	double y = pos1.getY() + pos2.getY();
	return new Position (x,y);
    }
    /**
     * Returns the difference between two vectors
     * @param pos1
     * @param pos2
     * @return Difference of two vectors
     */
    public static Position substract(Position pos1, Position pos2){
	double x = pos1.getX() - pos2.getX();
	double y = pos1.getY() - pos2.getY();
	return new Position (x,y);
    }
    /**
     * Returns a scaled vector
     * @param pos
     * @param factor
     * @return Scaled vector
     */
    public static Position scale(Position pos, Double factor){
	double x = pos.getX() * factor;
	double y = pos.getY() * factor;
	return new Position(x, y);
    }
    /**
     * Returns the distance between two vectors
     * @param pos1
     * @param pos2
     * @return distance
     */
    public static double distance(Position pos1, Position pos2){
	Position dir = Position.substract(pos1, pos2);
	double result = Math.hypot(dir.getX(), dir.getY());
	return result;
    }
    /**
     * @return xPos
     */
    public double getX(){
	return xPos;
    }
    
    /**
     * @return yPos
     */
    public double getY(){
	return yPos;
    }
    /**
     * Checks if two positions are equal
     * @param pos
     * @return
     */
    public boolean equals(Position pos){
	if((pos.getX() == this.xPos) && (pos.getY() == this.yPos)){
	    return true;
	}
	return false;
    }

}
