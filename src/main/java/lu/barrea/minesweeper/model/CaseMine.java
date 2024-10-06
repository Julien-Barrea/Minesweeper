package lu.barrea.minesweeper.model;

/**
 * This class inherit from Case and represent a mine.
 */
public class CaseMine extends Case{
    public CaseMine(){
        super();
    }

    /**
     * Reveal the case whatever its current state.
     */
    public void forceReveal(){
        setState(StateCaseHidden.getInstance());
        state.reveal(this);
    }

    @Override
    protected void revealAction(){
        Game.getInstance().explosion();
    }

    @Override
    protected String displayValue(){
        return "*";
    }
}
