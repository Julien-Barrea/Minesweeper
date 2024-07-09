module lu.barrea.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome;

    opens lu.barrea.minesweeper to javafx.fxml;
    exports lu.barrea.minesweeper;
    exports lu.barrea.minesweeper.controller;
    opens lu.barrea.minesweeper.controller to javafx.fxml;
    exports lu.barrea.minesweeper.view;
    opens lu.barrea.minesweeper.view to javafx.fxml;
}