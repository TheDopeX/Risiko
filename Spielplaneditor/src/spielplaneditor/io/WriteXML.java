/**
 * XML Writer for Risk maps
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

import spielplaneditor.Border;
import spielplaneditor.ContinentGroup;
import spielplaneditor.EditorCore;
import spielplaneditor.Shape;

public class WriteXML {
    /**
 	* Writes map data to XML
 	* @param core
 	* @param filePath
 	*/
    protected static void writeXML(EditorCore core, String filePath){
	String version = core.getVersionMajor() + "." + core.getVersionMinor();
	String maxPlayers = Integer.toString(core.getMaxPlayers());
	ContinentGroup[] continents = core.getContinentsArray();
	Border[] borders = core.getBorderArray();
	
	try{
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        	
        	//create root element
        	Document doc = docBuilder.newDocument();
        	Element rootElement = doc.createElement("Spielplan");
        	rootElement.setAttribute("version", version);
        	rootElement.setAttribute("maxSpieler", maxPlayers);
        	doc.appendChild(rootElement);
        	
        	for(ContinentGroup continent : continents){
        	    Element continentElement = doc.createElement("Kontinent");
        	    continentElement.setAttribute("id", Integer.toString(continent.getId()));
        	    
        	    //Set bonus values
        	    Element bonusElement = doc.createElement("Bonus");
        	    
        	    Element groundBonusElement = doc.createElement("Armee");
        	    groundBonusElement.setAttribute("armeeKlasse", "Bodeneinheit");
        	    groundBonusElement.setAttribute("anzahl", Integer.toString(continent.getBonus()));
        	    bonusElement.appendChild(groundBonusElement);
        	    
//        	    Element seaBonusElement = doc.createElement("Armee");
//        	    seaBonusElement.setAttribute("armeeKlasse", "Marineeinheit");
//        	    seaBonusElement.setAttribute("anzahl", Integer.toString(continent.getBonus()));
//        	    bonusElement.appendChild(seaBonusElement);
//        	    
//        	    Element airBonusElement = doc.createElement("Armee");
//        	    airBonusElement.setAttribute("armeeKlasse", "Lufteinheit");
//        	    airBonusElement.setAttribute("anzahl", Integer.toString(continent.getBonus()));
//        	    bonusElement.appendChild(airBonusElement);
        	    
        	    continentElement.appendChild(bonusElement);
        	    
        	    //Set Country values
        	    for(Shape shape : continent.getShapeArray()){
        		Element countryElement = doc.createElement("Land");
        		countryElement.setAttribute("id", Integer.toString(shape.getID()));
        		
        		//borders
        		Element borderListElement = doc.createElement("Grenzen");
        		
        		for(Border border : borders){
        		    if(border.hasShape(shape)){
        			Element borderElement = doc.createElement("Grenze");
        			borderElement.setAttribute("armeeKlasse", "Bodeneinheit");
        			borderElement.setAttribute("id", Integer.toString(border.getOpposite(shape).getID()));
        			borderListElement.appendChild(borderElement);
        		    }
        		}
        		countryElement.appendChild(borderListElement);
        		
        		//cards
        		//hardcoded, needs to be changed!!
        		Element cardListElement = doc.createElement("Kartensymbole");
        		
        		Element cardElement = doc.createElement("Symbol");
        		cardElement.setAttribute("art", "klassisch");
        		cardElement.setAttribute("symbol", "Bodeneinheit");
        		cardElement.setAttribute("anzahl", "1");        		
        		cardListElement.appendChild(cardElement);
        		
        		countryElement.appendChild(cardListElement);
        		
        		continentElement.appendChild(countryElement);
        	    }
        	    
        	    //joker
        	    //to be added
        	    
        	   rootElement.appendChild(continentElement);
        	}
        	
        	//write to file 
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
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