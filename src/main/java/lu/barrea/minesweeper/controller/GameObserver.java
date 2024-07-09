package lu.barrea.minesweeper.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import lu.barrea.minesweeper.model.Chrono;
import lu.barrea.minesweeper.model.StateGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GameObserver implements PropertyChangeListener {
    private List<Button> buttons;
    private Label label;
    private Chrono chrono;

    public GameObserver(List<Button> buttons, Label label, Chrono chrono) {
        this.buttons = buttons;
        this.label = label;
        this.chrono = chrono;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("state")) {
            if (evt.getNewValue() == StateGame.READY)
            {
                this.chrono.reset();
                this.label.setTextFill(Paint.valueOf("#000000"));
                this.label.setText("Appuyez sur une case pour commencer");
            }
            else if (evt.getNewValue() == StateGame.STARTED)
            {
                this.label.setText("");
                this.chrono.start();
            }
            else if (evt.getNewValue() == StateGame.LOST || evt.getNewValue() == StateGame.WON)
            {
                this.chrono.stop();

                for (Button button : buttons) {
                    button.setDisable(true);
                }

                if (evt.getNewValue() == StateGame.WON)
                {
                    this.label.setTextFill(Paint.valueOf("#0284c7"));
                    this.label.setText("Félicitations, vous avez gagné!");
                }
                else
                {
                    this.label.setTextFill(Paint.valueOf("#9f1239"));
                    this.label.setText("Dommage, vous avez perdu!");
                }
            }
        }
    }
}
