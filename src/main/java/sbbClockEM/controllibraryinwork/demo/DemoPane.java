/*
 *
 * Copyright (c) 2015 by FHNW
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package sbbClockEM.controllibraryinwork.demo;

import ch.fhnw.ws4c.sbbClockEM.controllibraryinwork.ClockControl;
import ch.fhnw.ws4c.sbbClockEM.controllibraryinwork.NumberRangeControl;
import ch.fhnw.ws4c.sbbClockEM.controllibraryinwork.SkinType;

import eu.hansolo.medusa.*;
import eu.hansolo.medusa.Clock.ClockSkinType;
import eu.hansolo.medusa.Alarm.Repetition;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * @author Dieter Holz
 */
public class DemoPane extends BorderPane {
	private NumberRangeControl customControl;

	// Medusa Clock
	private static int noOfNodes = 0;
	private ClockControl clockControl;
	private Clock clock5;
	private Clock clock6;

	private TextField titleField;
	private TextField valueField;
	private Label     valueLabel;
	private TextField unitField;
	private TextField minValueField;
	private TextField maxValueField;
	private CheckBox  isAnimated;

	public DemoPane() {
		initializeControls();
		layoutControls();
		addBindings();
	}

	private void initializeControls() {
		setPadding(new Insets(10));

		customControl = new NumberRangeControl(SkinType.TITLE);

		titleField = new TextField();
		valueField = new TextField();
		valueLabel = new Label();
		unitField = new TextField();
		minValueField = new TextField();
		maxValueField = new TextField();
		isAnimated = new CheckBox();

		TimeSection nextTrainGreen = TimeSectionBuilder.create()
				.start(LocalTime.now().plusMinutes(10))
				.stop(LocalTime.now().plusHours(3))
				.color(Color.rgb(0, 205, 0, 0.1))
				.highlightColor(Color.rgb(222, 111, 0, 0.75))
				.onTimeSectionEntered(event -> System.out.println("Train departed"))
				.onTimeSectionLeft(event -> System.out.println("Train arrived"))
				.build();

		TimeSection nextTrainOrange = TimeSectionBuilder.create()
				.start(LocalTime.now().plusMinutes(5))
				.stop(LocalTime.now().plusHours(2))
				.color(Color.rgb(255, 140, 0, 0.1))
				.highlightColor(Color.rgb(222, 0, 0, 0.75))
				.onTimeSectionEntered(event -> System.out.println("Train departed"))
				.onTimeSectionLeft(event -> System.out.println("Train arrived"))
				.build();

		TimeSection nextTrainRed = TimeSectionBuilder.create()
				.start(LocalTime.now().plusSeconds(2))
				.stop(LocalTime.now().plusHours(1))
				.color(Color.rgb(200, 0, 0, 0.1))
				.highlightColor(Color.rgb(222, 0, 0, 0.75))
				.onTimeSectionEntered(event -> System.out.println("Train departed"))
				.onTimeSectionLeft(event -> System.out.println("Train arrived"))
				.build();

		LightOn  lightOn  = new LightOn();
		LightOff lightOff = new LightOff();

		Alarm alarmLightOn =
				AlarmBuilder.create()
						.time(ZonedDateTime.now().plusMinutes(1))
						.repetition(Repetition.ONCE)
						.text("Light On")
						.command(lightOn)
						.color(Color.BLUE)
						.onAlarmMarkerPressed(e -> System.out.println("Light On marker pressed"))
						.build();

		Alarm alarmLightOff =
				AlarmBuilder.create()
						.time(ZonedDateTime.now().plusMinutes(10))
						.repetition(Repetition.ONCE)
						.text("Light off")
						.command(lightOff)
						.color(Color.RED)
						.onAlarmMarkerPressed(e -> System.out.println("Light Off marker pressed"))
						.build();

		clock5 = ClockBuilder.create()
				.skinType(ClockSkinType.DB)
				.backgroundPaint(Color.WHITE)
				.titleVisible(true)
				.title("Next Train")
				.sectionsVisible(true)
				.highlightSections(true)
				.sections(nextTrainGreen)
				.checkSectionsForValue(true)
				.areasVisible(true)
				.highlightAreas(true)
				.areas(nextTrainOrange, nextTrainRed)
				.secondsVisible(true)
				.running(true)
				.build();

		clock6 = ClockBuilder.create()
				.skinType(ClockSkinType.DB)
				.backgroundPaint(Color.DARKBLUE)
				.knobColor(Color.rgb(255,255,255))
				.hourColor(Color.rgb(255,255,255))
				.minuteColor(Color.rgb(255,255,255))
				.hourTickMarkColor(Color.rgb(255, 255, 255))
				.minuteTickMarkColor(Color.rgb(255, 255, 255))
				.tickLabelColor(Color.rgb(255, 255, 255))
				.areasVisible(true)
				.areas(new TimeSection(LocalTime.of(9, 0, 0), LocalTime.of(12, 0, 0), Color.rgb(255, 255, 255, 0.3)))
				.running(true)
				.build();

	}

	private void layoutControls() {
		VBox box = new VBox(titleField, valueField, valueLabel, unitField, new Label("departure, arrival:"), minValueField, maxValueField, new Label("animated:"), isAnimated);
		box.setSpacing(10);
		box.setPadding(new Insets(20));

		setTop(customControl);
		setCenter(clock5);
		setLeft(clock6);
		setRight(new ScrollPane(box));

		// Calculate number of nodes
		calcNoOfNodes(box);
		System.out.println(noOfNodes + " Nodes in SceneGraph");
	}

	private void addBindings() {
		titleField.textProperty().bindBidirectional(customControl.titleProperty());
		unitField.textProperty().bindBidirectional(customControl.unitProperty());
		Bindings.bindBidirectional(valueField.textProperty(), customControl.valueProperty(), new NumberStringConverter());
		valueLabel.textProperty().bind(customControl.valueProperty().asString("%.2f"));

		Bindings.bindBidirectional(minValueField.textProperty(), customControl.minValueProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(maxValueField.textProperty(), customControl.maxValueProperty(), new NumberStringConverter());

		isAnimated.selectedProperty().bindBidirectional(customControl.animatedProperty());
	}

	// ******************** Misc **********************************************
	private static void calcNoOfNodes(Node node) {
		if (node instanceof Parent) {
			if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
				ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
				noOfNodes += tempChildren.size();
				tempChildren.forEach(n -> calcNoOfNodes(n));
			}
		}
	}

	// ******************** Inner Classes *************************************
	class LightOn implements Command {
		@Override public void execute() {
			System.out.println("Here we will switch the light on");
		}
	}
	class LightOff implements Command {
		@Override public void execute() {
			System.out.println("Here we will switch the light off");
		}
	}

}
