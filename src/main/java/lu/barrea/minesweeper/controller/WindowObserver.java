package lu.barrea.minesweeper.controller;

import javafx.stage.Stage;

public final class WindowObserver {

    private static Stage stage;

    public static void setStage(Stage stage) {
        WindowObserver.stage = stage;
    }

    public static void refreshStage() {
        if (stage != null && !stage.isMaximized()) {
            stage.sizeToScene();
        }
    }
}
