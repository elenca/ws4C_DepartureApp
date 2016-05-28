package ch.fhnw.ws4c.circles;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Dieter Holz
 */
public class SimpleControl extends Region {
    private static final String FONTS_CSS = "fonts.css";
    private static final String STYLE_CSS = "style.css";

    private static final double PREFERRED_WIDTH = 300;
    private static final double PREFERRED_HEIGHT = 300;

    private static final double ASPECT_RATIO = PREFERRED_WIDTH / PREFERRED_HEIGHT;

    private static final double MINIMUM_WIDTH = 80;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    // all parts
    //private Text      display;
    //private Rectangle frame;
    private Circle circle;
    private Arc arc1;
    private Arc arc2;
    private Arc arc3;
    private Text display;

    private Pane drawingPane;

    // all properties
    private final StringProperty text = new SimpleStringProperty("12:30");

    public SimpleControl() {
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
        display = new Text(getText());
        display.getStyleClass().add("display");
        applyCss(display);
        display.setTextOrigin(VPos.CENTER);
        display.setTextAlignment(TextAlignment.CENTER);
        display.setY(PREFERRED_HEIGHT * 0.5);

        arc1 = new Arc(PREFERRED_HEIGHT * 0.3, PREFERRED_HEIGHT * 0.3, PREFERRED_HEIGHT * 0.3, PREFERRED_HEIGHT * 0.3, 90, -90);
        circle = new Circle(PREFERRED_HEIGHT * 0.4);
        arc2 = new Arc(PREFERRED_HEIGHT * 0.35, PREFERRED_HEIGHT * 0.35, PREFERRED_HEIGHT * 0.35, PREFERRED_HEIGHT * 0.35, 90, -230);
        arc3 = new Arc(PREFERRED_HEIGHT * 0.25, PREFERRED_HEIGHT * 0.25, PREFERRED_HEIGHT * 0.25, PREFERRED_HEIGHT * 0.25, 360, 130);

        circle.setCenterX(PREFERRED_HEIGHT * 0.5);
        arc1.setCenterX(PREFERRED_HEIGHT * 0.5);
        arc1.setCenterY(PREFERRED_HEIGHT * 0.5);
        circle.setCenterY(PREFERRED_HEIGHT * 0.5);
        arc2.setCenterX(PREFERRED_HEIGHT * 0.5);
        arc2.setCenterY(PREFERRED_HEIGHT * 0.5);
        arc3.setCenterX(PREFERRED_HEIGHT * 0.5);
        arc3.setCenterY(PREFERRED_HEIGHT * 0.5);


        arc1.getStyleClass().add("arc1");
        arc2.getStyleClass().add("arc2");
        arc3.getStyleClass().add("arc3");
        circle.getStyleClass().add("circle");
        relocateDisplay();

        //frame = new Rectangle(0.0, 0.0, PREFERRED_WIDTH, PREFERRED_HEIGHT);
        //frame.getStyleClass().add("frame");

        // always needed
        drawingPane = new Pane();
        drawingPane.setMaxSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        drawingPane.setMinSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        drawingPane.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(circle, arc1, arc2, display);
        getChildren().add(drawingPane);
    }

    private void addEventHandlers() {
        arc1.setOnMouseDragged(event -> {
            arc1.setLength(Math.min(360.0, -event.getY()));

        });
        arc2.setOnMouseDragged(event -> {
            arc2.setLength(Math.min(360.0, -event.getY()));

        });
        arc3.setOnMouseDragged(event -> {
            arc3.setLength(Math.min(360.0, event.getY()));

        });
    }

    private void addValueChangedListeners() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            display.setText(newValue);
            String[] values = display.getText().split(":");
            //arc1.setLength(-1*Integer.parseInt(values[1])*6 + 1);
            //arc2.setLength(-1*Integer.parseInt(values[0])*15  + 1);
            //display.autosize();
            relocateDisplay();
        });

        arc1.lengthProperty().addListener((observable, oldValue, newValue) -> setText(String.format("%.0f", -1 * (arc2.lengthProperty().getValue() / 15 - 1) % 24) + ":" + String.format("%.0f", -1 * ((newValue.doubleValue() / 6) - 2) % 60)));
        arc2.lengthProperty().addListener((observable, oldValue, newValue) -> setText(String.format("%.0f", -1 * (newValue.doubleValue() / 15 - 1) % 24) + ":" + String.format("%.0f", -1 * (arc1.lengthProperty().getValue() / 6 - 1) % 60)));

        // always needed
        //widthProperty().addListener((observable, oldValue, newValue) -> resize());
        //heightProperty().addListener((observable, oldValue, newValue) -> resize());
    }

    private void addBindings() {


    }

    private void resize() {
        Insets padding = getPadding();
        double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
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
        display.setX((PREFERRED_WIDTH - display.getLayoutBounds().getWidth()) * 0.5);
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
        double verticalPadding = padding.getTop() + padding.getBottom();

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
        double verticalPadding = padding.getTop() + padding.getBottom();

        return PREFERRED_HEIGHT + verticalPadding;
    }


    // getter and setter for all properties

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

}
