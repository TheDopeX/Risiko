/**
 * SVG Reader for risk maps
 * @author Udo Wiethaeuper
 */
package spielplaneditor.io;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import spielplaneditor.Color;
import spielplaneditor.Line;
import spielplaneditor.Position;
import spielplaneditor.Shape;

import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;

public class ReadSVG {
    /**
     * Reads XML file and creates map objects for the editor
     * @param core
     * @param filePath
     */
    protected static HashMap<Integer, Shape> readSVG(String filePath){
	HashMap<Integer, Shape> shapes = new HashMap<Integer, Shape>();
	try{
	    File file = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    dbFactory.setNamespaceAware(false);
	    dbFactory.setValidating(false);
	    dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
	    dbFactory.setFeature("http://xml.org/sax/features/validation", false);
	    dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	    dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(file);
	    doc.getDocumentElement().normalize();
	    NodeList shapeList = doc.getElementsByTagName("path"); 
	    for(int i = 0; i < shapeList.getLength();i++){
		Element shapeElement = (Element)shapeList.item(i);
		Shape shape = new Shape();
		String idstring = shapeElement.getAttribute("id");

		String colorstring = shapeElement.getAttribute("fill");
		Color fillColor = parseColor(colorstring);
		shape.setFillColor(fillColor);

		colorstring = shapeElement.getAttribute("stroke");
		Color strokeColor = parseColor(colorstring);
		shape.setLineColor(strokeColor);

		String linewidthString = shapeElement.getAttribute("stroke-width");
		shape.setLineWidth(Double.parseDouble(linewidthString));

		String linestring = shapeElement.getAttribute("d");

		Line currentLine = null;
		Position shapePos = null;
		String[] lineStringElements = linestring.split("\\s");
		for(String element : lineStringElements){
		    if(element.equals("Z")){
			break;
		    }else if(element.equals("C") || element.equals("L") || element.equals("")){
			continue;
		    }else if(element.equals("M")){
			currentLine = new Line(shape);
			shape.addLine(currentLine);
		    }else if(currentLine != null){
			String[] positions = element.split(",");
			double xPos = Double.parseDouble(positions[0]);
			double yPos = Double.parseDouble(positions[1]);
			Position pos = new Position(xPos, yPos);
			if(shapePos == null){
			    shapePos = pos;
			    shape.setPosition(shapePos);
			}
			currentLine.addPoint(Position.substract(pos, shapePos));
		    }
		}
		shapes.put(Integer.parseInt(idstring), shape);
	    }


	} catch (Exception e) {
	    e.printStackTrace();
	}
	return shapes;
    }
    /**
     * Determines used color format and parses it to Color object
     * @return color
     */
    private static Color parseColor(String value){
	//is hex notation
	if(value.startsWith("#")){
	    if(value.length() == 4){
		int red = Integer.parseInt(value.charAt(1) + "" + value.charAt(1), 16);
		int green = Integer.parseInt(value.charAt(2) + "" + value.charAt(2), 16);
		int blue = Integer.parseInt(value.charAt(3) + "" + value.charAt(3), 16);
		return new Color(red, green, blue, 255);
	    }else{
		int red = Integer.parseInt(value.charAt(1) + "" + value.charAt(2), 16);
		int green = Integer.parseInt(value.charAt(3) + "" + value.charAt(4), 16);
		int blue = Integer.parseInt(value.charAt(5) + "" + value.charAt(6), 16);
		return new Color(red, green, blue, 255);
	    }
	}
	//is rgb notation
	else if(value.startsWith("rgb(")){
	    String colorstring = value.substring(4, value.length()-1);
	    String[] colors = colorstring.split(",");
	    return new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2]), 255);
	}
	// is rgba notation
	else if(value.startsWith("rgba(")){
	    String colorstring = value.substring(5, value.length()-1);
	    String[] colors = colorstring.split(",");
	    double alpha = Double.parseDouble(colors[3]);
	    return new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2]), (int)(alpha * 255));
	}
	// is predefined name notation. only base colors and "none" are supported, everything else defaults to black
	else{
	    switch(value){
	    case "black":
		return new Color(0,0,0,255);
	    case "white":
		return new Color(255,255,255,255);
	    case "red":
		return new Color (255,0,0,255);
	    case "green":
		return new Color(0,255,0,255);
	    case "blue":
		return new Color(0,0,255,255);
	    case "yellow":
		return new Color(255,255,0,255);
	    case "none":
		return new Color(0,0,0,0);
	    default:
		return new Color(0,0,0,255);
	    }
	}
    }
}