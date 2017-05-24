package gui.spielplaneditor;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/**
 * @author Kevin Gerspacher
 * @version 0.2
 */
public interface Toolstate
{
    public void setPane(Pane pane);
    public void onLeftClick(MouseEvent event);
    public void onRightClick(MouseEvent event);
    public void onLeftDown(MouseEvent event);
    public void onRightDown(MouseEvent event);
    public void onLeftUp(MouseEvent event);
    public void onRightUp(MouseEvent event);
}
