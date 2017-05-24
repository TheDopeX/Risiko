/**
 * Base class to store shapes
 * @author Florian Huber
 * @version 1.0
 */
package spielplaneditor;

import java.util.ArrayList;
import java.util.List;

public abstract class Group {

    /**
     * shapes
     */
    private List<Shape> shapes = new ArrayList<Shape>();

    /**
     * 
     * @return shapes
     */
    protected List<Shape> getShapes() {
	return shapes;
    }

    /**
     * Adds a new shape
     * @param shape
     */
    protected void addShape(Shape shape) {
	shapes.add(shape);
    }

    /**
     * Removes a shape
     * @param shape
     */
    protected void removeShape(Shape shape) {
	shapes.remove(shape);
    }
    /**
     * returns array of shapes
     * @return shapes
     */
    public Shape[] getShapeArray(){
	Shape[] s = new Shape[shapes.size()];
	s = shapes.toArray(s);
	return s;
    }
    /**
     * Checks if a shape belongs to this group
     * @param shape
     * @return boolean
     */
    public boolean containsShape(Shape shape){
	if(shapes.contains(shape)){
	    return true;
	}else{
	    return false;
	}
    }
}
