package org.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

public class SettingsPomodoro {
    @FXML
    Slider workSlider;
    @FXML
    Slider breakSlider;
    @FXML
    Slider longBreakSlider;
    @FXML
    Label workTime;
    @FXML
    Label breakTime;
    @FXML
    Label longBreakTime;
    @FXML
    Rectangle progressBarWork;
    @FXML
    Rectangle progressBarBreak;
    @FXML
    Rectangle progressBarLongBreak;
    public static Label actTime = new Label();
    public static Label actBreak = new Label();
    public static Label actLongBreak = new Label();
    public void initialize() {
        progressBarWork.setDisable(true);
        progressBarBreak.setDisable(true);
        progressBarLongBreak.setDisable(true);

        workSlider.setValue(PomodoroController.pomodoroTime / 60.0);
        breakSlider.setValue(PomodoroController.pomodoroBreak / 60.0);
        longBreakSlider.setValue(PomodoroController.pomodoroLongBreak / 60.0);

        double percentage1 = ((PomodoroController.pomodoroTime / 60.0)  - 1) / (workSlider.getMax() - 1);
        double percentage2 = ((PomodoroController.pomodoroBreak / 60.0) - 1) / (breakSlider.getMax() - 1);
        double percentage3 = ((PomodoroController.pomodoroLongBreak / 60.0) - 1) / (longBreakSlider.getMax() - 1);

        progressBarWork.setWidth(366 * percentage1);
        progressBarBreak.setWidth(366 * percentage2);
        progressBarLongBreak.setWidth(366 * percentage3);

        workTime.setText(PomodoroController.pomodoroTime / 60 + ":00");
        breakTime.setText(PomodoroController.pomodoroBreak / 60 + ":00");
        longBreakTime.setText(PomodoroController.pomodoroLongBreak / 60 + ":00");

        workSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double percentage = (newValue.doubleValue()-1)  / (workSlider.getMax() -1);
                progressBarWork.setWidth(366 * percentage);
                PomodoroController.pomodoroTime = newValue.intValue() * 60;
                setUpTimerWork(newValue.intValue());
                actTime.setText(newValue.intValue()*60 + "");
            }
        });
        breakSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double percentage = (newValue.doubleValue()-1)  / (breakSlider.getMax() -1);
                progressBarBreak.setWidth(366 * percentage);
                PomodoroController.pomodoroBreak = newValue.intValue() * 60;
                setUpTimerBreak(newValue.intValue());
                actBreak.setText(newValue.intValue()*60 + "");
            }
        });
        longBreakSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double percentage = (newValue.doubleValue()-1)  / (longBreakSlider.getMax() -1);
                progressBarLongBreak.setWidth(366 * percentage);
                PomodoroController.pomodoroLongBreak = newValue.intValue() * 60;
                setUpTimerLongBreak(newValue.intValue());
                actLongBreak.setText(newValue.intValue()*60 + "");
            }
        });

    }
    public void setUpTimerWork(int time) {
        workTime.setText(time + ":00");
    }
    public void setUpTimerBreak(int time) {
        breakTime.setText(time + ":00");
    }
    public void setUpTimerLongBreak(int time) {
        longBreakTime.setText(time + ":00");
    }

}
