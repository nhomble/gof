package org.hombro.gof.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    private final static String path = "board.fxml";
    final public static int width = 500;
    final public static int height = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL u = getClass().getClassLoader().getResource(path);
        if (u == null)
            throw new RuntimeException("Could not locate " + path);
        Parent root = FXMLLoader.load(u);
        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
