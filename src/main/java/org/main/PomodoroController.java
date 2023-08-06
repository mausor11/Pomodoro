package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
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
    @FXML
    Button pauseButton;
    @FXML
    Group counterLook;
    @FXML
    Arc counterCircle;
    @FXML
    Circle whiteBackgroundRounds;
    @FXML
    Circle backgroundRounds;
    private int pomodoroCount = 0;
    private boolean isFirst = false;
    private boolean isBreak = false;
    private boolean isLongBreak = false;
    private boolean isTimer = true;
    private boolean isPaused = false;
    private boolean isListener = false;
    public static int pomodoroTime = 1;
    public static int pomodoroBreak = 1;
    public static int pomodoroLongBreak = 1;
    public static int pomodoroRounds = 2;
    private int doneRounds = 0;
    Label isPause = new Label();

    public void initialize() {
        initializeCircleRounds(pomodoroRounds);
        clock.setText(PomodoroController.pomodoroTime / 60 + ":00");

        SettingsPomodoro.actTime.textProperty().addListener((observable, oldValue, newValue) -> {
            PomodoroController.pomodoroTime = Integer.parseInt(newValue);
            setUpTimer(pomodoroTime);
        });

        SettingsPomodoro.actBreak.textProperty().addListener((observable, oldValue, newValue) -> PomodoroController.pomodoroBreak = Integer.parseInt(newValue));

        SettingsPomodoro.actLongBreak.textProperty().addListener((observable, oldValue, newValue) -> PomodoroController.pomodoroLongBreak = Integer.parseInt(newValue));

        SettingsPomodoro.actRoundsNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            PomodoroController.pomodoroRounds = Integer.parseInt(newValue);
            initializeCircleRounds(pomodoroRounds);

        });

    }
    private void startTimer(double time) {
        if(pomodoroCount == 0 && isFirst) {
            showButtons(false);

            timerButton.setStyle("-fx-background-color: #efe8e7");
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                    new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
            );
            timeline.play();
            pauseClock(timeline);
            timeline.setOnFinished((finish) -> {
                setUpTimer(pomodoroTime);
                resetTimer();
                timerButton.setStyle("-fx-background-color: #f4f1f0");
                pomodoroCount++;
                switchDone();
                isBreak = true;
                isTimer = false;
                timerLook.setStyle("-fx-fill: #fa5e42");
                clock.setText(PomodoroController.pomodoroBreak / 60 + ":00");

            });
        } else {
            showButtons(false);
            timerButton.setStyle("-fx-background-color: #efe8e7");
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                    new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
            );
            timeline.play();
            pauseClock(timeline);
            timeline.setOnFinished((finish) -> {
                setUpTimer(pomodoroTime);
                resetTimer();
                timerButton.setStyle("-fx-background-color: #f4f1f0");
                pomodoroCount++;
                if (pomodoroCount == 4) {
                    isLongBreak = true;
                    isTimer = false;
                    timerLook.setStyle("-fx-fill: #3165a0");
                    clock.setText(PomodoroController.pomodoroLongBreak / 60 + ":00");
                    switchDone();
                    isFirst = true;
                    pomodoroCount = 0;
                } else {
                    switchDone();
                    isBreak = true;
                    isTimer = false;
                    timerLook.setStyle("-fx-fill: #fa5e42");
                    clock.setText(PomodoroController.pomodoroBreak / 60 + ":00");
                }
            });

        }
    }
    private void startBreak(double time) {
        showButtons(false);
        timerButton.setStyle("-fx-background-color: #efe8e7");
        timerLook.setStyle("-fx-fill: #fa5e42");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
        );
        timeline.play();
        pauseClock(timeline);
        timeline.setOnFinished((finish) -> {
            isBreak = false;
            isTimer = true;
            timerLook.setStyle("-fx-fill: #946057");
            resetTimer();
        });
    }
    public void startLongBreak(double time) {
        showButtons(false);
        timerButton.setStyle("-fx-background-color: #efe8e7");
        timerLook.setStyle("-fx-fill: #3165a0");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), 360)),
                new KeyFrame(Duration.seconds(time), new KeyValue(timerLook.lengthProperty(), 0))
        );
        timeline.play();
        pauseClock(timeline);
        timeline.setOnFinished((finish) -> {
            System.out.println("done");
            isLongBreak = false;
            isTimer = true;
            timerLook.setStyle("-fx-fill: #946057");
            resetTimer();
            Timeline animation = new Timeline(
                    new KeyFrame(Duration.millis(200), new KeyValue(firstP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(150), new KeyValue(secondP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(100), new KeyValue(thirdP.styleProperty(), "-fx-fill: #f4f1f0")),
                    new KeyFrame(Duration.millis(50), new KeyValue(fourthP.styleProperty(), "-fx-fill: #f4f1f0"))
            );
            animation.play();
            doneRounds++;
            switchDoneRound(doneRounds, pomodoroRounds);
        });
        if(doneRounds == pomodoroRounds) {
            switchDoneRound(doneRounds, pomodoroRounds);
            doneRounds = 0;
        }
    }
    private void switchDone() {
        switch(pomodoroCount) {
            case 1 -> firstP.setStyle("-fx-fill: #946057");
            case 2 -> secondP.setStyle("-fx-fill: #946057");
            case 3 -> thirdP.setStyle("-fx-fill: #946057");
            case 4 -> fourthP.setStyle("-fx-fill: #946057");
        }
    }
    public void startClock() {
        System.out.println(doneRounds + "/" + pomodoroRounds);
        if(isBreak) {
            startBreak(PomodoroController.pomodoroBreak);
            startTime(PomodoroController.pomodoroBreak);
        } else if(isLongBreak) {
            startLongBreak(PomodoroController.pomodoroLongBreak);
            startTime(PomodoroController.pomodoroLongBreak);
        } else if(isTimer) {
            startTime(PomodoroController.pomodoroTime);
            startTimer(PomodoroController.pomodoroTime);
        }

    }
    private void startTime(int time) {
        AtomicInteger k = new AtomicInteger(time - 1);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            setUpTimer(k.get());
            k.getAndDecrement();
        }));

        timeline.setCycleCount(time);
        timeline.play();
        pauseTime(timeline);

    }
    private void setUpTimer(int time) {
        int min = time / 60;
        int sec = time % 60;
        if(sec < 10) {
            clock.setText(min + ":0" + sec);
        } else {
            clock.setText(min + ":" + sec);
        }
    }
    private void resetTimer() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerLook.lengthProperty(), timerLook.getLength())),
                new KeyFrame(Duration.millis(200), new KeyValue(timerLook.lengthProperty(), 360))
        );
        timeline.play();
        isPaused = false;
        isPause = new Label();
        isListener = false;
        timeline.setOnFinished((finish) -> showButtons(true));
    }
    private void showButtons(boolean isStart) {
        if(isStart) {
            timerButton.setDisable(false);
            pauseButton.setDisable(true);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.opacityProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(pauseButton.opacityProperty(), 1)),
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.prefWidthProperty(), pauseButton.getPrefWidth())),
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.layoutXProperty(), timerButton.getLayoutX())),

                    new KeyFrame(Duration.millis(100), new KeyValue(timerButton.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(pauseButton.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(50), new KeyValue(timerButton.prefWidthProperty(), pauseButton.getPrefWidth() + 48)),
                    new KeyFrame(Duration.millis(50), new KeyValue(timerButton.layoutXProperty(), timerButton.getLayoutX() - 24))
            );
            timeline.play();
        } else {
            timerButton.setDisable(true);
            pauseButton.setDisable(false);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.opacityProperty(), 1)),
                    new KeyFrame(Duration.ZERO, new KeyValue(pauseButton.opacityProperty(), 0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.prefWidthProperty(), timerButton.getPrefWidth())),
                    new KeyFrame(Duration.ZERO, new KeyValue(timerButton.layoutXProperty(), timerButton.getLayoutX())),

                    new KeyFrame(Duration.millis(100), new KeyValue(timerButton.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(pauseButton.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(timerButton.prefWidthProperty(), pauseButton.getPrefWidth())),
                    new KeyFrame(Duration.millis(100), new KeyValue(timerButton.layoutXProperty(), timerButton.getLayoutX() + 24))

            );
            timeline.play();
        }
    }
    private void pauseClock(Timeline timeline) {
        pauseButton.setOnAction(actionEvent -> {
            if(isPaused) {
                timeline.play();
                isPaused = false;
                isPause.setText("true");
                pauseButton.setStyle("-fx-background-color: #f4f1f0;");

            } else {
                timeline.pause();
                isPaused = true;
                isPause.setText("false");
                pauseButton.setStyle("-fx-background-color: rgba(148,96,87,0.2)");
            }
        });
    }
    private void pauseTime(Timeline timeline) {
        if(!isListener) {
            isPause.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.equals("true")) {
                    timeline.play();
                } else {
                    timeline.pause();
                }
            });
            isListener = true;
        }
    }
    private void initializeCircleRounds(int many) {

        counterLook.getChildren().clear();
        counterLook.getChildren().addAll(backgroundRounds, counterCircle, whiteBackgroundRounds);
        if(many != 1) {
            double arc = 360.0 / many;
            for(int i=0; i< many;i++) {
                Rectangle rectangle = new Rectangle(10, 172);
                rectangle.setLayoutX(-5);
                rectangle.setLayoutY(-172);
                rectangle.setFill(Color.WHITE);
                Rotate rotate = new Rotate(arc*i, 5, 172);
                rectangle.getTransforms().add(rotate);
                counterLook.getChildren().add(rectangle);
            }
            counterCircle.setLength(360.0 - arc*doneRounds);
        }

    }



    private void switchDoneRound(int stage, int many) {

        if(stage != many) {
            double arc = 360 / many;
            counterCircle.setLength(360.0 - arc*stage);
        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(counterCircle.lengthProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(counterCircle.lengthProperty(), 360))
            );
            timeline.play();
        }
    }


}