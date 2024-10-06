package lu.barrea.minesweeper.model;

/**
 * This class inherit from Case and represent a case neighbor of one or more mines.
 * It contains the number of mines around it.
 */
public class CaseNumber extends Case{
    private int number;

    public CaseNumber(){
        super();
        this.number = 1;
    }

    /**
     * Increment the number of this case by one.
     */
    protected void increment() {
        this.number++;
    }

    @Override
    protected void revealAction(){ Game.getInstance().decrementCasesLeftToReveal(); }

    @Override
    protected String displayValue(){ return ""+number ; }
}
