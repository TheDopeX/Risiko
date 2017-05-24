/**
 * SVG Writer for Risk maps
 * @author Udo Wiethaeuper
 */
package spielplaneditor.io;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import spielplaneditor.EditorCore;
import spielplaneditor.Group;
import spielplaneditor.Line;
import spielplaneditor.Position;
import spielplaneditor.Shape;

public class WriteSVG {
    /**
     * Writes map data to SVG
     * @param core
     * @param filePath
     */
    protected static void writeSVG(EditorCore core, String filePath){
	//combine all groups into one
	Group[] continents = core.getContinentsArray();
	Group[] groups = new Group[continents.length +1];
	for(int i = 0; i < continents.length;i++){
	    groups[i] = continents[i];
	}
	groups[groups.length -1] = core.getDecoShapes();
	
	try{
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        	
        	//create doc and root element
        	Document doc = docBuilder.newDocument();
        	Element rootElement = doc.createElement("svg");
        	rootElement.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        	rootElement.setAttribute("width", "6in"); //to be changed
        	rootElement.setAttribute("height", "5in"); //to be changed
        	rootElement.setAttribute("viewBox", "0 0 300 250"); //to be changed
        	doc.appendChild(rootElement);
        	
        	for(Group group : groups){
        	    for(Shape shape : group.getShapeArray()){
                	    Element shapeElement = doc.createElement("path");
                	    shapeElement.setAttribute("id", Integer.toString(shape.getID()));
                	    shapeElement.setAttribute("fill", shape.getFillColor().toRGBAString());
                	    //shapeElement.setAttribute("fill-opacity", Double.toString(shape.getFillColor().getAlpha()));
                	    shapeElement.setAttribute("stroke", shape.getLineColor().toRGBAString());
                	    shapeElement.setAttribute("stroke-width", Double.toString(shape.getLineWidth()));
                	    Line[] lines = shape.getLineArray();
                	    String path = "";
                	    //multiple lines are combined into one path
                	    for(Line line:lines){
                		Position[] points = line.getGlobalPoints();
                		path += "M " + points[0].getX() + "," + points[0].getY() + " L ";
                		for(Position p:points){
                		    path += p.getX() + "," + p.getY() +  " ";
                		}
                		path += "Z ";
                	    }
                	    
                	    shapeElement.setAttribute("d", path);
                	    
                	    rootElement.appendChild(shapeElement);
        	    }
        	    
        	}  
        	
        	//write to file 
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20010904//EN");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));
		
		transformer.transform(source, result);
	
	} catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	
    }
}