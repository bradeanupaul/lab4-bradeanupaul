package com.example.lab4;

import UI.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sqlite.JDBC;
import Repository.SQLTortRepository;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        HelloController hc = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 500, 460);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
        //UI ui = new UI();
        //ui.menu();

    }
}
