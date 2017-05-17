package gui.spielplaneditor;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Gerspacher und David Bartberger on 17.05.17.
 */
public class CommandController{

    private List<Pane> group = new ArrayList<>();


    public Pane undo(Pane group)
    {
        if(group.getChildren().size() >= 2)
        {
            add(group);
            group.getChildren().remove(group.getChildren().size()-1);
        }
        return group;
    }

    public Pane redo(Pane group)
    {
        Pane holder;
        if(!this.group.isEmpty())
        {
            holder = this.group.get(this.group.size()-1);
            this.group.remove(this.group.size()-1);
        }
        else
        {
            holder = group;
        }
        return holder;
    }

    private void add(Pane group)
    {

        this.group.add(group);
    }
}
