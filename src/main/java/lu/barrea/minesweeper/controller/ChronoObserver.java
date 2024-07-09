package lu.barrea.minesweeper.controller;

import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChronoObserver implements PropertyChangeListener {
    private Label label;

    public ChronoObserver(Label label) {
        this.label = label;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int seconds = (int) evt.getNewValue();
        label.setText(""+(seconds/60)/10+(seconds/60)%10+":"+(seconds%60)/10+(seconds%60)%10);
    }
}
