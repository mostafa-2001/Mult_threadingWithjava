package com.example.project_os;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GUI_Controller {
    private final int tileSize = 50 ;
    Image g = createImage(Color.GREEN);
    Image b = createImage(Color.BLACK);
    Image w = createImage(Color.WHITE);
    Parent root;
    Stage stage;
    Scene scene;
    Image[][] mazeChar;
    int[][] maze;

    @FXML
    Button generateButton;
    @FXML
    TextField sizeField;

    GridPane matrix = new GridPane();
    Scene matrixScene;

    private Image createImage(Color color) {
        WritableImage image = new WritableImage(1, 1);
        image.getPixelWriter().setColor(0, 0, color);
        return image ;
    }

    @FXML
    void onButtonClickGenerate(ActionEvent event) {
        int y ;
        int x = 0;
        int size = Integer.parseInt(sizeField.getText());
        maze = new int[size][size];
        TextField[][] tf = new TextField[size][size];
        for (y = 0; y < size; y++) {
            for (x = 0; x < size; x++) {
                Random rand = new Random();
                int value = rand.nextInt(2);
                // Create a new TextField in each Iteration
                tf[y][x] = new TextField();
                tf[y][x].setPrefHeight(50);
                tf[y][x].setPrefWidth(50);
                tf[y][x].setAlignment(Pos.CENTER);
                tf[y][x].setEditable(true);
                tf[y][x].setText(String.valueOf(value));
                matrix.setRowIndex(tf[y][x], y+1);
                matrix.setColumnIndex(tf[y][x], x);
                matrix.getChildren().add(tf[y][x]);
            }
        }
        Button BTN2 = new Button("Done");
        Button BTN = new Button("Back");
        matrix.setRowIndex(BTN, 0);
        matrix.setColumnIndex(BTN, 0);
        matrix.getChildren().add(BTN);
        matrix.setRowIndex(BTN2, y+1);
        matrix.setColumnIndex(BTN2, x/2);
        matrix.getChildren().add(BTN2);

        BTN.setOnAction(e ->{
            try {
                switchScene(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        BTN2.setOnAction(e ->{
            try {
                for(int  i = 0; i < size; i++)
                {
                    for(int j = 0; j < size; j++) {
                        maze[i][j] = Integer.parseInt(tf[i][j].getText());
                    }
                }
                RunThread runThread = new RunThread(maze);

                Thread t1 = new Thread(runThread , "0");
                Thread t2 = new Thread(runThread, "1");
                Thread t3 = new Thread(runThread, "2");
                t1.start();
                t2.start();
                t3.start();

                t1.join();
                t2.join();
                t3.join();


                mazeChar = new Image[size][size];
                for(int  i = 0; i < size; i++)
                {
                    for(int j = 0; j < size; j++) {
                        if(runThread.path(runThread.getId())[i][j] == 1){
                            mazeChar[i][j] = w;
                        }else if(runThread.path(runThread.getId())[i][j] == 0){
                            mazeChar[i][j] = b;
                        }else {
                            mazeChar[i][j] = g;
                        }
                    }
                }
                switchScene2(e, maze, mazeChar);

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });


        matrixScene = new Scene(matrix, 500, 500);
        Stage stage = ((Stage)((Button)(event.getSource())).getScene().getWindow());
        stage.setScene(matrixScene);
    }
    public void switchScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene2(ActionEvent event, int[][] maze, Image[][] mazeChar) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        int y ;
        int x = 0;


        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setStyle("-fx-background-color: grey;");
        for (y = 0 ; y < mazeChar.length ; y++) {
            for (x = 0 ; x < mazeChar[y].length ; x++) {
                ImageView imageView = new ImageView(mazeChar[y][x]);
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                gridPane.add(imageView, x, y);
            }
        }
        Button BTN = new Button("Back");
        gridPane.setRowIndex(BTN, y);
        gridPane.setColumnIndex(BTN, x/2);
        gridPane.getChildren().add(BTN);
        Scene scene = new Scene(gridPane);

        BTN.setOnAction(e ->{
            try {
                switchScene(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        stage.setScene(scene);
        stage.show();

    }
    public void back(ActionEvent e) throws IOException {
        switchScene(e);
    }
}