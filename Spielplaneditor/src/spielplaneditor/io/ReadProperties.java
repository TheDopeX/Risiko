package spielplaneditor.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import spielplaneditor.ContinentGroup;
import spielplaneditor.Shape;

public class ReadProperties {

    protected static void readProperties(HashMap<Integer, Shape> shapes, HashMap<Integer, ContinentGroup> continents, String filePath){
	FileReader fReader = null;
	BufferedReader bReader = null;
	File inputFile = null;
	try{
	    inputFile = new File(filePath);
	    fReader = new FileReader(inputFile);
	    bReader = new BufferedReader(fReader);
	    
	    String line = bReader.readLine();
	    
	    while(line != null){
		if(!line.startsWith("#")){
		    String[] parts = line.split(" = ");
		    String[] key = parts[0].split("_");
		    if(key[0].equals("Land")){
			Shape country = shapes.get(Integer.parseInt(key[1]));
			country.setName(parts[1]);
		    }else if(key[0].equals("Kontinent")){
			ContinentGroup continent = continents.get(Integer.parseInt(key[1]));
			continent.setName(parts[1]);
		    }
		}
		line = bReader.readLine();
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    try{
		bReader.close();
		fReader.close();
	    }catch(Exception e){
		e.printStackTrace();
	    }
	}
    }

}
