/**
 * XML Reader for risk maps
 * @author Udo Wiethaeuper
 */
package spielplaneditor.io;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import spielplaneditor.ContinentGroup;
import spielplaneditor.EditorCore;
import spielplaneditor.Group;
import spielplaneditor.Shape;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;

public class ReadXML {
    /**
     * Reads XML file and creates map objects for the editor
     * @param core
     * @param filePath
     */
    protected static HashMap<Integer, ContinentGroup> readXML(EditorCore core, String filePath ,HashMap<Integer, Shape> countryMap){
	HashMap<Integer, ContinentGroup> continents = new HashMap<Integer, ContinentGroup>();
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
	    NodeList continentList = doc.getElementsByTagName("Kontinent");

	    //add all shapes to decoshapes first, move them later if theyre countries
	    Group decoShapes = core.getDecoShapes();
	    for(Shape shape : countryMap.values()){
		core.addShape(shape, decoShapes);      
	    }

	    for(int i = 0; i < continentList.getLength();i++){
		Node continentNode = continentList.item(i);
		Element continentElement = (Element)continentNode;
		ContinentGroup continentObject = new ContinentGroup();
		core.addContinent(continentObject);
		continents.put(Integer.parseInt(continentElement.getAttribute("id")), continentObject);
		NodeList continentChilds = continentNode.getChildNodes();
		for(int j = 0; j < continentChilds.getLength();j++){
		    Node continentChildNode = continentChilds.item(j);
		    //Ignore whitespaces
		    if (continentChildNode.getNodeType() == Node.ELEMENT_NODE) {
			//Bonus
			if(continentChildNode.getNodeName().equals("Bonus")){
			    NodeList bonusList = continentChildNode.getChildNodes();
			    for(int k = 0; k < bonusList.getLength();k++){
				Node bonus = bonusList.item(k);
				if (bonus.getNodeType() == Node.ELEMENT_NODE) {
				    Element bonusElement = (Element)bonus;
				    if(bonusElement.getAttribute("armeeKlasse").equals("Bodeneinheit")){
					int value = Integer.parseInt(bonusElement.getAttribute("anzahl"));
					continentObject.setBonus(value);
				    }
				}
			    }
			}
			//Move countries to continents and create borders
			if(continentChildNode.getNodeName().equals("Land")){
			    Element countryElement = (Element)continentChildNode;
			    int id = Integer.parseInt(countryElement.getAttribute("id"));
			    Shape shape = countryMap.get(id);
			    core.moveShapeToGroup(shape, continentObject);
			    NodeList borderList = countryElement.getElementsByTagName("Grenze");
			    for(int k = 0; k < borderList.getLength();k++){
				Element borderElement = (Element)borderList.item(k);
				if(borderElement.getAttribute("armeeKlasse").equals("Bodeneinheit")){
				    Shape oppositeShape = countryMap.get(Integer.parseInt(borderElement.getAttribute("id")));
				    core.addBorder(shape, oppositeShape);
				}
			    }

			    //Cards
			    //to be added
			}


		    }
		    //Joker
		    //to be added


		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return continents;
    }

}