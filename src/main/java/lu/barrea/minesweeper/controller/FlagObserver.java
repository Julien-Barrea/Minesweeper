package lu.barrea.minesweeper.controller;

import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FlagObserver implements PropertyChangeListener {
    private Label label;

    public FlagObserver(Label label) {
        this.label = label;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        label.setText(""+evt.getNewValue());
    }
}
