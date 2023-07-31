package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
    private int pomodoroCount = 0;
    private boolean isFirst = false;

    public void initialize() {
    }
    public void startTimer(double time) {
        if(pomodoroCount == 0 && isFirst) {
            Timeline animation = new Timeline(
                    new KeyFrame(Duration.millis(200), new KeyValue(firstP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(150), new KeyValue(secondP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(100), new KeyValue(thirdP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(50), new KeyValue(fourthP.styleProperty(), "-fx-fill: #f4f1f0"))
            );
            animation.play();
            animation.setOnFinished((d) -> {
                        timerButton.setDisable(true);
                        timerButton.setStyle("-fx-background-color: #efe8e7");
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
                        );
                        timeline.play();
                        timeline.setOnFinished((finish) -> {
                            timerButton.setDisable(false);
                            timerLook.setLength(360);
                            timerButton.setStyle("-fx-background-color: #f4f1f0");
                            pomodoroCount++;
                            switchDone();
                        });
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
                timerButton.setDisable(false);
                timerLook.setLength(360);
                timerButton.setStyle("-fx-background-color: #f4f1f0");
                pomodoroCount++;


                if (pomodoroCount == 4) {
                    switchDone();
                    isFirst = true;
                    pomodoroCount = 0;
                } else {
                    switchDone();

                }
            });
        }

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
        startTimer(1);
    }

}
