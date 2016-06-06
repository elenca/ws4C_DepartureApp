package CustomControl.myFahrplanControll.demo;

import CustomControl.myFahrplanControll.MyFahrplanHeadControl;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by bettina on 07.06.16.
 */
public class DemoPaneHead extends BorderPane {

    private MyFahrplanHeadControl customControl;

    private TextField departureField;
    private TextField destinationField;
    private TextField trackValueField;
    private TextField trainNrField;

    public DemoPaneHead() {
        initializeControls();
        layoutControls();
        addBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        customControl = new MyFahrplanHeadControl();

        departureField = new TextField();
        destinationField = new TextField();
        trainNrField = new TextField();
        trackValueField = new TextField();
    }

    private void layoutControls() {
        VBox box = new VBox(departureField, destinationField, trainNrField, trackValueField);
        box.setSpacing(10);
        box.setPadding(new Insets(20));

        setCenter(customControl);
        setRight(box);
    }

    private void addBindings() {
        departureField.textProperty().bindBidirectional(customControl.departureProperty());
        destinationField.textProperty().bindBidirectional(customControl.destinationProperty());
        trainNrField.textProperty().bindBidirectional(customControl.trainNrProperty());
        trackValueField.textProperty().bindBidirectional(customControl.trackValueProperty());
        // trackValueField.textProperty().bind(customControl.trackValueP().asString("%.2f"));
        // Bindings.bindBidirectional(destinationField.textProperty(), customControl.valueProperty(), new NumberStringConverter());
    }
}
