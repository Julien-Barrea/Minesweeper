package lu.barrea.minesweeper.model;

public class StateCaseFlagged extends StateCase{

    protected static StateCaseFlagged instance;

    private StateCaseFlagged(){}

    /**
     * Create an instance of StateCaseFlagged or return it if it exists.
     * @return a unique instance of StateCaseFlagged
     */
    public static StateCase getInstance() {
        if (instance == null) instance = new StateCaseFlagged();
        return instance;
    }

    @Override
    public void flag(Case c) {
        Game.getInstance().incrementFlag();
        c.setState(StateCaseHidden.getInstance());
        c.getPropertyChangeSupport().firePropertyChange("state", "flagged", "hidden");
    }

    @Override
    public void reveal(Case c) { }

    @Override
    public String displayCase(Case c) {
        return "\033[32mF\033[0m";
    }
}
