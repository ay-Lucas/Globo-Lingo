package com.language;

import java.io.IOException;

import com.model.SystemFACADE;
import com.narration.Narriator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static SystemFACADE systemFACADE = new SystemFACADE();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 1440, 1024);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void playNarrator() throws IOException {
        Narriator.playSound("Hola mundo");
    }

    public static void main(String[] args) {
        launch();
    }

    public static SystemFACADE getSystemFacade() {
        return systemFACADE;
    }

}
