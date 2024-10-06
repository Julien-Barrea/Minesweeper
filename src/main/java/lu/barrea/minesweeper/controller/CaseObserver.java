package lu.barrea.minesweeper.controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import lu.barrea.minesweeper.model.StateCase;
import org.kordamp.ikonli.javafx.FontIcon;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class allows a Case object to be observed and link its state to a button
 */
public class CaseObserver implements PropertyChangeListener {

    private Button b;
    private FontIcon bombIcon;
    private FontIcon flagIcon;

    /**
     * Constructor
     * @param b an initialized Button object that will get its appeanrance linked to a Case object
     */
    public CaseObserver(Button b){
        this.b = b;
        bombIcon = new FontIcon("fa-bomb");
        bombIcon.setIconColor(Paint.valueOf("#9f1239"));
        flagIcon = new FontIcon("fa-flag");
        flagIcon.setIconColor(Paint.valueOf("#0284c7"));
    }

    /**
     * Adapt the appearance of the button on the state of a Case (hidden, flagged, revealed)
     * and its content (number, mine).
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(()->{
            if(evt.getPropertyName().equals("state")) {
                if (evt.getNewValue() == "revealed")
                {
                    b.getStyleClass().add("uncovered_case");
                    b.getStyleClass().remove("active_case");
                }
                else if (evt.getNewValue() == "flagged")
                {
                    b.setGraphic(flagIcon);
                }
                else
                {
                    b.setGraphic(new FontIcon());
                }
            }

            if(evt.getPropertyName().equals("value")){
                if (evt.getNewValue().equals("*")){
                    b.getStyleClass().add("exploded_case");
                    b.setGraphic(bombIcon);
                }else{
                    b.setText(evt.getNewValue().toString());
                    switch (evt.getNewValue().toString()){
                        case "1":
                            b.setTextFill(Paint.valueOf("#84cc16"));
                            break;
                        case "2":
                            b.setTextFill(Paint.valueOf("#eab308"));
                            break;
                        case "3":
                            b.setTextFill(Paint.valueOf("#ea580c"));
                            break;
                        case "4":
                            b.setTextFill(Paint.valueOf("#c2410c"));
                            break;
                        case "5":
                            b.setTextFill(Paint.valueOf("#7f1d1d"));
                            break;
                        default:
                            b.setTextFill(Paint.valueOf("#450a0a"));
                    }
                }
            }
        });
    }
}