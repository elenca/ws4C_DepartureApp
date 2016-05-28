package ch.fhnw.ws4c.colormemoryinwork;


import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by bettina on 03.05.16.
 */
public class ColorListCell extends ListCell<Color> {
    @Override
    protected void updateItem(Color item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if(item != null && !empty){
            setText("RGB: "+item.getRed()+", "+ item.getGreen()+", "+ item.getBlue());
            setGraphic(new Circle(30,item));
        }

    }
}
