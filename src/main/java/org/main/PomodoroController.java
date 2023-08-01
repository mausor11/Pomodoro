package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class PomodoroController {
    @FXML
    Arc timerLook;
    @FXML
    Button timerButton;
    @FXML
    Circle firstP;
    @FXML
    Circle secondP;
    @FXML
    Circle thirdP;
    @FXML
    Circle fourthP;
    @FXML
    Label clock;
    private int pomodoroCount = 0;
    private boolean isFirst = false;
    private boolean isBreak = false;
    private boolean isLongBreak = false;
    public static int pomodoroTime = 1500;
    public static int pomodoroBreak = 300;
    public static int pomodoroLongBreak = 600;

    public void initialize() {
        clock.setText(PomodoroController.pomodoroTime / 60 + ":00");

        SettingsPomodoro.actTime.textProperty().addListener((observable, oldValue, newValue) -> {
            PomodoroController.pomodoroTime = Integer.parseInt(newValue);
            setUpTimer(pomodoroTime);
        });

        SettingsPomodoro.actBreak.textProperty().addListener((observable, oldValue, newValue) -> {
            PomodoroController.pomodoroBreak = Integer.parseInt(newValue);
        });

        SettingsPomodoro.actLongBreak.textProperty().addListener((observable, oldValue, newValue) -> {
            PomodoroController.pomodoroLongBreak = Integer.parseInt(newValue);
        });
    }
    public void startTimer(double time) {
        if(pomodoroCount == 0 && isFirst) {
                        timerButton.setDisable(true);
                        timerButton.setStyle("-fx-background-color: #efe8e7");
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
                        );
                        timeline.play();
                        timeline.setOnFinished((finish) -> {
                            setUpTimer(pomodoroTime);
                            resetTimer();
                            timerButton.setStyle("-fx-background-color: #f4f1f0");
                            pomodoroCount++;
                            switchDone();
                            isBreak = true;
                            timerLook.setStyle("-fx-fill: #fa5e42");
                            clock.setText(PomodoroController.pomodoroBreak / 60 + ":00");

                        });
        } else {
            timerButton.setDisable(true);
            timerButton.setStyle("-fx-background-color: #efe8e7");
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                    new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
            );
            timeline.play();
            timeline.setOnFinished((finish) -> {
                setUpTimer(pomodoroTime);
                resetTimer();
                timerButton.setStyle("-fx-background-color: #f4f1f0");
                pomodoroCount++;

                if (pomodoroCount == 4) {
                    isLongBreak = true;
                    timerLook.setStyle("-fx-fill: #3165a0");
                    clock.setText(PomodoroController.pomodoroLongBreak / 60 + ":00");
                    switchDone();
                    isFirst = true;
                    pomodoroCount = 0;
                } else {
                    switchDone();
                    isBreak = true;
                    timerLook.setStyle("-fx-fill: #fa5e42");
                    clock.setText(PomodoroController.pomodoroBreak / 60 + ":00");

                }
            });
        }
    }
    public void startBreak(double time) {
        timerButton.setDisable(true);
        timerButton.setStyle("-fx-background-color: #efe8e7");
        timerLook.setStyle("-fx-fill: #fa5e42");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
        );
        timeline.play();
        timeline.setOnFinished((finish) -> {
            isBreak = false;
            timerLook.setStyle("-fx-fill: #946057");
            resetTimer();
        });
    }
    public void startLongBreak(double time) {
        timerButton.setDisable(true);
        timerButton.setStyle("-fx-background-color: #efe8e7");
        timerLook.setStyle("-fx-fill: #3165a0");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
        );
        timeline.play();
        timeline.setOnFinished((finish) -> {
            isLongBreak = false;
            timerLook.setStyle("-fx-fill: #946057");
            resetTimer();
            Timeline animation = new Timeline(
                    new KeyFrame(Duration.millis(200), new KeyValue(firstP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(150), new KeyValue(secondP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(100), new KeyValue(thirdP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(50), new KeyValue(fourthP.styleProperty(), "-fx-fill: #f4f1f0"))
            );
            animation.play();
        });
    }
    public void switchDone() {
        switch(pomodoroCount) {
            case 1 -> {
                firstP.setStyle("-fx-fill: #946057");
            }
            case 2 -> {
                secondP.setStyle("-fx-fill: #946057");
            }
            case 3 -> {
                thirdP.setStyle("-fx-fill: #946057");
            }
            case 4 -> {
                fourthP.setStyle("-fx-fill: #946057");
            }
        }
    }
    public void startClock() {
        if(isBreak) {
            startBreak(PomodoroController.pomodoroBreak);
            startTime(PomodoroController.pomodoroBreak);
        } else if(isLongBreak) {
            startLongBreak(PomodoroController.pomodoroLongBreak);
            startTime(PomodoroController.pomodoroLongBreak);
        } else {
            startTime(PomodoroController.pomodoroTime);
            startTimer(PomodoroController.pomodoroTime);
        }

    }
    public void startTime(int time) {
        AtomicInteger k = new AtomicInteger(time - 1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            setUpTimer(k.get());
            k.getAndDecrement();
        }));

        timeline.setCycleCount(time);
        timeline.play();
    }
    public void setUpTimer(int time) {
        int min = time / 60;
        int sec = time % 60;
        if(sec < 10) {
            clock.setText(min + ":0" + sec);
        } else {
            clock.setText(min + ":" + sec);
        }
    }
    public void resetTimer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(timerLook.lengthProperty(), 360))
        );
        timeline.play();
        timeline.setOnFinished((finish) -> {
            timerButton.setDisable(false);
        });
    }

}
