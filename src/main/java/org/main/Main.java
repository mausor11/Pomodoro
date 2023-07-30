package org.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {

        stage.setMinWidth(1280);
        stage.setMinHeight(800);

        stage.setHeight(800);
        stage.setWidth(1280);
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("main.fxml")).load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("theme/light_theme.css")).toExternalForm());


        stage.setScene(scene);
        stage.show();




    }
}