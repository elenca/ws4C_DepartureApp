package CustomControl.myFahrplanControll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by bettina on 06.06.16.
 */
public class MyFahrplanHeadControl extends Region {
    private static final String FONTS_CSS = "fonts.css";
    private static final String STYLE_CSS = "style.css";

    private static final double PREFERRED_WIDTH  = 470;
    private static final double PREFERRED_HEIGHT = 200;

    private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

    private static final double MINIMUM_WIDTH  = 25;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH  = 800;

    // all parts
	/* private Rectangle 	background; */

    private Region 		        headBG;
    private Text      	        departureLabel;
    private Text                destinationLabel;
    private Text                trainNrLabel;
    private MyFahrplanControl   trackValueLabel;

    private Pane drawingPane;

    // all properties
    private final StringProperty departure = new SimpleStringProperty("00:33");
    private final StringProperty destination = new SimpleStringProperty("Zürich");
    private final StringProperty trainNr = new SimpleStringProperty("ICN 1549");
    private final StringProperty trackValue = new SimpleStringProperty("-");


    public MyFahrplanHeadControl() {
        init();
        initializeParts();
        layoutParts();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
    }

    private void init() {
        addStyleSheets(this);
        getStyleClass().add(getStyleClassName());
    }

    private void initializeParts() {
		/*
		background = new Rectangle(0.0, 0.0, PREFERRED_WIDTH, PREFERRED_HEIGHT);
		background.getStyleClass().add("background");
		*/

        headBG = new Region();
        headBG.getStyleClass().add("headBG");
        headBG.setLayoutX(0);
        headBG.setLayoutY(0);

        trackValueLabel = new MyFahrplanControl();
        trackValueLabel.textProperty().set(getTrackValue());
        trackValueLabel.setLayoutX(17);
        trackValueLabel.setLayoutY(20);
        trackValueLabel.setPrefSize(157, 157);

        departureLabel = new Text(getDeparture());
        departureLabel.getStyleClass().add("departure");
        departureLabel.setTextOrigin(VPos.CENTER);
        departureLabel.setTextAlignment(TextAlignment.LEFT);
        departureLabel.setX(190);
        departureLabel.setY(55);


        destinationLabel = new Text(getDestination());
        destinationLabel.getStyleClass().add("destination");
        destinationLabel.setTextOrigin(VPos.CENTER);
        destinationLabel.setTextAlignment(TextAlignment.LEFT);
        destinationLabel.setX(190);
        destinationLabel.setY(127);


        trainNrLabel = new Text(getTrainNr());
        trainNrLabel.getStyleClass().add("trainNr");
        trainNrLabel.setTextOrigin(VPos.CENTER);
        trainNrLabel.setTextAlignment(TextAlignment.LEFT);
        trainNrLabel.setX(190);
        trainNrLabel.setY(153);


        applyCss(headBG);
        applyCss(departureLabel);
        applyCss(destinationLabel);
        applyCss(trainNrLabel);




        relocateDisplay();

        // always needed
        drawingPane = new Pane();
        drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(headBG, trackValueLabel, departureLabel, destinationLabel, trainNrLabel);
        getChildren().add(drawingPane);
    }

    private void addEventHandlers() {

    }

    private void addValueChangedListeners() {
        departureProperty().addListener((observable, oldValue, newValue) -> {
            departureLabel.setText(newValue);
            relocateDisplay();
        });

        destinationProperty().addListener((observable, oldValue, newValue) -> {
            destinationLabel.setText(newValue);
            relocateDisplay();
        });

        trainNrProperty().addListener((observable, oldValue, newValue) -> {
            trainNrLabel.setText(newValue);
            relocateDisplay();
        });


        trackValueProperty().addListener((observable, oldValue, newValue) -> {
            trackValueLabel.setText(newValue);
            relocateDisplay();
        });


        // always needed
        widthProperty().addListener((observable, oldValue, newValue) -> resize());
        heightProperty().addListener((observable, oldValue, newValue) -> resize());
    }

    private void addBindings() {
    }

    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / PREFERRED_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - PREFERRED_WIDTH) * 0.5, (getHeight() - PREFERRED_HEIGHT) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDisplay() {
        // departureLabel.setX((PREFERRED_WIDTH - departureLabel.getLayoutBounds().getWidth()) * 0.5);
    }

    // some useful helper-methods

    private void applyCss(Node node) {
        Group group = new Group(node);
        group.getStyleClass().add(getStyleClassName());
        addStyleSheets(group);
        new Scene(group);
        node.applyCss();
    }

    private void addStyleSheets(Parent parent) {
        String fonts = getClass().getResource(FONTS_CSS).toExternalForm();
        parent.getStylesheets().add(fonts);

        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        parent.getStylesheets().add(stylesheet);
    }

    private String getStyleClassName() {
        String className = this.getClass().getSimpleName();

        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    // compute sizes

    @Override
    protected double computeMinWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding   = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return PREFERRED_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding   = padding.getTop() + padding.getBottom();

        return PREFERRED_HEIGHT + verticalPadding;
    }


    // getter and setter for all properties

    public String getText() {
        return departure.get();
    }

    public StringProperty departureProperty() {
        return departure;
    }

    public void setText(String text) {
        this.departure.set(text);
    }

    public String getDeparture() {
        return departure.get();
    }

    public void setDeparture(String departure) {
        this.departure.set(departure);
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getTrainNr() {
        return trainNr.get();
    }

    public StringProperty trainNrProperty() {
        return trainNr;
    }

    public void setTrainNr(String trainNr) {
        this.trainNr.set(trainNr);
    }

    public String getTrackValue() {
        return trackValue.get();
    }

    public StringProperty trackValueProperty() {
        return trackValue;
    }

    public void setTrackValue(String trackValue) {
        this.trackValue.set(trackValue);
    }
}
