package lu.barrea.minesweeper.model;

/**
 * Intended to represent the different possible state of a case, which are "hidden", "flagged", "revealed".
 */
public abstract class StateCase{

    /**
     * Flag or unflag the case c if its current state allows it.
     * @param c a valid and not null case
     */
    public abstract void flag(Case c);

    /**
     * Reveal the case c if its current state allows it.
     * @param c a valid and not null case
     */
    public abstract void reveal(Case c);

    /**
     * Display a case according to its current state.
     * @param c The case to display
     * @return a string representing the case c
     */
    public abstract String displayCase(Case c);
}
