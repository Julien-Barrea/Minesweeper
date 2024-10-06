package lu.barrea.minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * This class represents a basic blank case of the game Minesweeper.
 */
public class Case {

    protected StateCase state;
    protected PropertyChangeSupport changeSupport;
    private List<Case> neighbors;

    public Case(){
        this.state = StateCaseHidden.getInstance();
        this.neighbors = null;
        this.changeSupport = new PropertyChangeSupport(this);
    }

    protected void setState(StateCase state){
        this.state = state;
    }

    protected PropertyChangeSupport getPropertyChangeSupport(){
        return changeSupport;
    }

    public void setNeighbors(List<Case> neighbors){
        this.neighbors = neighbors;
    }

    /**
     * Add a new observer to this object.
     * @param listener a valid observer preferably of type CaseObserver for best performance.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Change the state of the case to flagged if it's hidden,
     * or the hidden if its flagged. This method doesn't do anything if the case is revealed.
     */
    public void flag(){ state.flag(this); }

    /**
     * Reveal this case if it is both hidden and not flagged. And also increment by one
     * the number of revealed case in the current game.
     * If the case is neither a mine, nor a number, it also call the reveal method on all
     * its neighbors.
     */
    public void reveal(){ state.reveal(this); }

    /**
     * Execute the action required when a case is revealed.
     */
    protected void revealAction(){
        for(Case c : neighbors) c.reveal();
        Game.getInstance().decrementCasesLeftToReveal();
    }

    /**
     * Display a case accurately to its current state.
     * @return a string representing its current state
     */
    public String toSring(){  return state.displayCase(this);  }

    /**
     * Display the character which should be used when a case is revealed.
     * @return a string of 1 character representing this specific kind of case
     */
    protected String displayValue(){ return " "; }
}
