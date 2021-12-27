package com.example.project_os;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        GridPane root = FXMLLoader.load(GUI_Controller.class.getResource("scene1.fxml"));
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Matrix");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}