package org.main;

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
    Slider roundsSlider;
    @FXML
    Label workTime;
    @FXML
    Label breakTime;
    @FXML
    Label longBreakTime;
    @FXML
    Label roundsNumber;
    @FXML
    Rectangle progressBarWork;
    @FXML
    Rectangle progressBarBreak;
    @FXML
    Rectangle progressBarLongBreak;
    @FXML
    Rectangle progressBarRounds;
    public static Label actTime = new Label();
    public static Label actBreak = new Label();
    public static Label actLongBreak = new Label();
    public static Label actRoundsNumber = new Label();
    public void initialize() {
        setUpTimeSlider(progressBarWork, workSlider, PomodoroController.pomodoroTime, workTime);
        setUpTimeSlider(progressBarBreak, breakSlider, PomodoroController.pomodoroBreak, breakTime);
        setUpTimeSlider(progressBarLongBreak, longBreakSlider, PomodoroController.pomodoroLongBreak, longBreakTime);
        progressBarRounds.setDisable(true);
        roundsSlider.setValue(PomodoroController.pomodoroRounds);
        double percentage4 = ((PomodoroController.pomodoroRounds) - 1) / (roundsSlider.getMax() - 1);
        progressBarRounds.setWidth(366 * percentage4);
        roundsNumber.setText(PomodoroController.pomodoroRounds + "");

        workSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            double percentage = (newValue.doubleValue()-1)  / (workSlider.getMax() -1);
            progressBarWork.setWidth(366 * percentage);
            PomodoroController.pomodoroTime = newValue.intValue() * 60;
            setUpTimerWork(newValue.intValue());
            actTime.setText(newValue.intValue()*60 + "");
        });
        breakSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            double percentage = (newValue.doubleValue()-1)  / (breakSlider.getMax() -1);
            progressBarBreak.setWidth(366 * percentage);
            PomodoroController.pomodoroBreak = newValue.intValue() * 60;
            setUpTimerBreak(newValue.intValue());
            actBreak.setText(newValue.intValue()*60 + "");
        });
        longBreakSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            double percentage = (newValue.doubleValue()-1)  / (longBreakSlider.getMax() -1);
            progressBarLongBreak.setWidth(366 * percentage);
            PomodoroController.pomodoroLongBreak = newValue.intValue() * 60;
            setUpTimerLongBreak(newValue.intValue());
            actLongBreak.setText(newValue.intValue()*60 + "");
        });
        roundsSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            double percentage = (newValue.doubleValue()-1)  / (roundsSlider.getMax() -1);
            progressBarRounds.setWidth(366 * percentage);
            PomodoroController.pomodoroRounds = newValue.intValue();
            setUpRoundsNumber(newValue.intValue());
            actRoundsNumber.setText(newValue.intValue() + "");
        });

    }
    private void setUpTimerWork(int time) {
        workTime.setText(time + ":00");
    }
    private void setUpTimerBreak(int time) {
        breakTime.setText(time + ":00");
    }
    private void setUpTimerLongBreak(int time) {
        longBreakTime.setText(time + ":00");
    }
    private void setUpRoundsNumber(int number) {
        roundsNumber.setText(number + "");
    }
    private void setUpTimeSlider(Rectangle progressBar, Slider slider, int time, Label timeLabel) {
        progressBar.setDisable(true);
        slider.setValue(time / 60.0);
        double percentage = ((time / 60.0)  - 1) / (slider.getMax() - 1);
        progressBar.setWidth(366 * percentage);
        timeLabel.setText(time / 60 + ":00");
    }

}
