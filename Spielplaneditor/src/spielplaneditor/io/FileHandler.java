package spielplaneditor.io;

import java.util.HashMap;

import spielplaneditor.ContinentGroup;
import spielplaneditor.EditorCore;
import spielplaneditor.Shape;

public class FileHandler {
    private EditorCore core;
    public FileHandler(EditorCore core) {
	this.core = core;
    }
    public void loadFile(String path){
	HashMap<Integer, Shape> shapes = ReadSVG.readSVG(path + ".svg");
	HashMap<Integer, ContinentGroup> continents = ReadXML.readXML(core, path + ".xml", shapes);
	ReadProperties.readProperties(shapes, continents, path + ".properties");
    }
    public void saveFile(String path){
	WriteSVG.writeSVG(core, path + ".svg");
	WriteXML.writeXML(core, path + ".xml");
	WriteProperties.writeProperties(core, path + ".properties");
    }

}
