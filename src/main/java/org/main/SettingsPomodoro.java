package org.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SettingsPomodoro {
    @FXML
    Slider timeSlider;
    @FXML
    Label time;
    public static Label actTime = new Label();
    public void initialize() {
        timeSlider.setValue(PomodoroController.pomodoroTime / 60);
        time.setText(PomodoroController.pomodoroTime / 60 + ":00");
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                PomodoroController.pomodoroTime = newValue.intValue();
                setUpTimer(newValue.intValue());
                actTime.setText(newValue.intValue() + "");
            }
        });
    }
    public void setUpTimer(int time) {
        this.time.setText(time + ":00");
    }

}
