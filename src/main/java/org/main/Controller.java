package org.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    @FXML
    GridPane container;
    @FXML
    Label menuLabel;
    @FXML
    Label statsLabel;
    @FXML
    Label optionsLabel;

    @FXML
    ImageView menuIcon;
    @FXML
    ImageView statsIcon;
    @FXML
    ImageView optionsIcon;
    private int prevFocus;
    private int focusOn;
    public void initialize() throws IOException {
        container.add(new FXMLLoader(getClass().getResource("pomodoro.fxml")).load(), 1, 0);
        menuLabel.getStyleClass().remove("menuLabel");
        menuLabel.getStyleClass().add("menuLabelFocused");
        menuIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/timerF.png")).toExternalForm()));
        prevFocus = 0;
    }
    public void focusTimer() throws IOException {
        focusOn = 0;
        changeFocus(prevFocus, focusOn);
        prevFocus = 0;
    }
    public void focusStats() throws IOException {
        focusOn = 1;
        changeFocus(prevFocus, focusOn);
        prevFocus = 1;
    }
    public void focusOptions() throws IOException {
        focusOn = 2;
        changeFocus(prevFocus, focusOn);
        prevFocus = 2;
    }

    public void changeFocus(int prevFocus, int focusOn) throws IOException {
        switch(prevFocus) {
            case 0 -> {
                menuLabel.getStyleClass().remove("menuLabelFocused");
                statsLabel.getStyleClass().remove("menuLabel");
                optionsLabel.getStyleClass().remove("menuLabel");
            }
            case 1 -> {
                menuLabel.getStyleClass().remove("menuLabel");
                statsLabel.getStyleClass().remove("menuLabelFocused");
                optionsLabel.getStyleClass().remove("menuLabel");
            }
            case 2 -> {
                menuLabel.getStyleClass().remove("menuLabel");
                statsLabel.getStyleClass().remove("menuLabel");
                optionsLabel.getStyleClass().remove("menuLabelFocused");
            }
        }
        switch(focusOn) {
            case 0 -> {
                menuLabel.getStyleClass().add("menuLabelFocused");
                statsLabel.getStyleClass().add("menuLabel");
                optionsLabel.getStyleClass().add("menuLabel");

                menuIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/timerF.png")).toExternalForm()));
                statsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/stats.png")).toExternalForm()));
                optionsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/settings.png")).toExternalForm()));

                container.getChildren().remove(1);
                container.add(new FXMLLoader(getClass().getResource("pomodoro.fxml")).load(), 1, 0);

            }
            case 1 -> {
                menuLabel.getStyleClass().add("menuLabel");
                statsLabel.getStyleClass().add("menuLabelFocused");
                optionsLabel.getStyleClass().add("menuLabel");

                menuIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/timer.png")).toExternalForm()));
                statsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/statsF.png")).toExternalForm()));
                optionsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/settings.png")).toExternalForm()));

                container.getChildren().remove(1);
                container.add(new FXMLLoader(getClass().getResource("stats.fxml")).load(), 1, 0);
            }
            case 2 -> {
                menuLabel.getStyleClass().add("menuLabel");
                statsLabel.getStyleClass().add("menuLabel");
                optionsLabel.getStyleClass().add("menuLabelFocused");

                menuIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/timer.png")).toExternalForm()));
                statsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/stats.png")).toExternalForm()));
                optionsIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("img/settingsF.png")).toExternalForm()));

                container.getChildren().remove(1);
                container.add(new FXMLLoader(getClass().getResource("settings.fxml")).load(), 1, 0);
            }
        }
    }



    
}
