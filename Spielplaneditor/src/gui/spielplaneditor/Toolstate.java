package gui.spielplaneditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/**
 * Created by Kevin Gerspacher on 23.05.17.
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
