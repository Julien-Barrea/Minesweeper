package lu.barrea.minesweeper.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lu.barrea.minesweeper.controller.WindowObserver;

import java.io.IOException;

public class Minesweeper extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("minesweeper.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResource("img/bomb.png").toExternalForm()));

        WindowObserver.setStage(stage);

        stage.setScene(scene);
        stage.setTitle("Démineur");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}