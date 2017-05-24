/**
 * Contains a border relationship between two shapes
 * @author Florian Huber
 * @version 1.0
 */
package spielplaneditor;

public class Border {
    
    /**
     * shapes
     */
    private Shape shapeA, shapeB;
    
    
    /**
     * Constructor
     * @param shapeA
     * @param shapeB
     */
    public Border(Shape shapeA, Shape shapeB) {
	
	this.shapeA = shapeA;
	this.shapeB = shapeB;
    }
    /**
     * 
     * @return shapeA
     */
    public Shape getShapeA(){
	return shapeA;
    }
    /**
     * 
     * @return shapeA
     */
    public Shape getShapeB(){
	return shapeB;
    }
    /**
     * Checks if border belongs to a shape
     * @param shape to check
     * @return boolean
     */
    public boolean hasShape(Shape shape){
	if((shapeA == shape) || (shapeB == shape)){
	    return  true;
	}
	return false;
    }
    public Shape getOpposite(Shape shape){
	if(shapeA == shape){
	    return shapeB;
	}
	if(shapeB == shape){
	    return shapeA;
	}
	return null;
    }
}
