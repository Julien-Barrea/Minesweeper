package lu.barrea.minesweeper.model;

/**
 * This class inherit from Case and represent a case neighbor of one or more mines.
 * It contains the number of mines around it.
 */
public class CaseNumber extends Case{
    int number;

    public CaseNumber(){
        super();
        this.number = 1;
    }

    /**
     * Reveal this case and increment the number of cases revealed in the game.
     * @note This method does not behave recursively as its super class does.
     */
    @Override
    public void reveal() {
        if(state == StateCase.HIDDEN){
            state = StateCase.REVEALED;
            Game.getInstance().decrementCasesLeftToReveal();
            this.changeSupport.firePropertyChange("state", StateCase.HIDDEN, StateCase.REVEALED);
            this.changeSupport.firePropertyChange("value", null, this.toSring());
        }
    }

    /**
     * Increment the number of this case by one.
     */
    public void increment() {
        this.number++;
    }

    public String toSring(){
        return state == StateCase.REVEALED ? ""+number : super.toSring();
    }
}
