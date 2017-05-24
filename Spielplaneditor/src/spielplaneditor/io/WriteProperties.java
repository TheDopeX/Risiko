package spielplaneditor.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import spielplaneditor.ContinentGroup;
import spielplaneditor.EditorCore;
import spielplaneditor.Shape;

public class WriteProperties {

    public static void writeProperties(EditorCore core, String filePath){
	FileWriter fWriter = null;
	BufferedWriter bWriter = null;
	File outputFile = null;
	try{
	    outputFile = new File(filePath);
	    fWriter = new FileWriter(outputFile);
	    bWriter = new BufferedWriter(fWriter);
	    
	    String continentNames = "#Kontinenbezeichnungen nach ID" + System.getProperty("line.separator");
	    String countryNames = "#Länderbezeichnungen nach ID" + System.getProperty("line.separator");
	    ContinentGroup[] continents = core.getContinentsArray();
	    for(ContinentGroup continent : continents){
		continentNames += "Kontinent_" + continent.getId() + " = " + continent.getName() + System.getProperty("line.separator");
		Shape[] countries = continent.getShapeArray();
		for(Shape country : countries){
		    countryNames += "Land_" + country.getID() + " = " + country.getName() + System.getProperty("line.separator");
		}
	    }
	    
	    bWriter.write(countryNames);
	    bWriter.write(continentNames);
	    
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    try{
		bWriter.close();
		fWriter.close();
	    }catch(Exception e){
		e.printStackTrace();
	    }
	}
    }

}
