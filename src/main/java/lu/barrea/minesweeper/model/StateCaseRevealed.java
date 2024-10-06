package lu.barrea.minesweeper.model;

public class StateCaseRevealed extends StateCase{

    protected static StateCaseRevealed instance;

    private StateCaseRevealed(){}

    /**
     * Create an instance of StateCaseRevealed or return it if it exists.
     * @return a unique instance of StateCaseRevealed
     */
    public static StateCase getInstance() {
        if (instance == null) instance = new StateCaseRevealed();
        return instance;
    }

    @Override
    public void flag(Case c) {}

    @Override
    public void reveal(Case c) {}

    @Override
    public String displayCase(Case c) {
        return c.displayValue();
    }
}
