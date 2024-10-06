package lu.barrea.minesweeper.model;

public class StateCaseHidden extends StateCase{

    protected static StateCaseHidden instance;

    private StateCaseHidden(){}

    /**
     * Create an instance of StateCaseHidden or return it if it exists.
     * @return a unique instance of StateCaseHidden
     */
    public static StateCase getInstance() {
        if (instance == null) instance = new StateCaseHidden();
        return instance;
    }

    @Override
    public void flag(Case c) {
        if(Game.getInstance().decrementFlag()){
            c.setState(StateCaseFlagged.getInstance());
            c.getPropertyChangeSupport().firePropertyChange("state", "hidden", "flagged");
        }
    }

    @Override
    public void reveal(Case c) {
        c.setState(StateCaseRevealed.getInstance());
        c.getPropertyChangeSupport().firePropertyChange("state", "hidden", "revealed");
        c.getPropertyChangeSupport().firePropertyChange("value", null, c.displayValue());
        c.revealAction();
    }

    @Override
    public String displayCase(Case c) {
        return "#";
    }
}
