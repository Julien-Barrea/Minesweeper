package lu.barrea.minesweeper.model;

/**
 * This class inherit from Case and represent a mine.
 */
public class CaseMine extends Case{
    public CaseMine(){
        super();
    }

    /**
     * Reveal this case as well as all the other cases containing a mine on the board, and ends the game.
     * @note This method does not behave recursively as its super class does.
     */
    @Override
    public void reveal() {
        if(state == StateCase.HIDDEN){
            Game.getInstance().explosion();
        }
    }

    /**
     * Reveal the case whatever its current state.
     */
    public void forceReveal(){
        state = StateCase.REVEALED;
        this.changeSupport.firePropertyChange("state", StateCase.HIDDEN, StateCase.REVEALED);
        this.changeSupport.firePropertyChange("value", null, "mine");
    }

    @Override
    public String toSring(){
        return state == StateCase.REVEALED ? "*" : super.toSring();
    }
}
