package lu.barrea.minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {
    private StateGame state;
    private int flagCounter;
    private int casesLeftToReveal;
    private Board board;

    private static final int nbLevel = 3;
    private static Game game;

    private PropertyChangeSupport stateChangeSupport;
    private PropertyChangeSupport flagChangeSupport;

    private Game(int level){
        this.stateChangeSupport = new PropertyChangeSupport(this);
        this.flagChangeSupport = new PropertyChangeSupport(this);
        int nbMines;

        switch (level) //When a new level is added, do not forget to update the static attribute nbLevel.
        {
            case 2:
                nbMines = 40;
                this.board = new Board(nbMines, 18, 14);
                casesLeftToReveal = this.board.getNbCase() - nbMines;
                this.flagCounter = nbMines;
                break;
            case 3:
                nbMines = 99;
                this.board = new Board(nbMines, 24, 20);
                casesLeftToReveal = this.board.getNbCase() - nbMines;
                this.flagCounter = nbMines;
                break;
            default: //level1
                nbMines = 10;
                this.board = new Board(nbMines, 10, 8);
                casesLeftToReveal = this.board.getNbCase() - nbMines;
                this.flagCounter = nbMines;
        }
        this.state = StateGame.READY;
        this.stateChangeSupport.firePropertyChange("state", null, this.state);
    }

    /**
     * Retrieve the singleton of Game, or create it with its default level (1).
     * @return the only instance of Game.
     */
    public static Game getInstance(){
        if(game == null) game = new Game(1);
        return game;
    }

    /**
     * Retrieve the singleton of Game, or create it with the level mentionned.
     * If an instance exists, but its level is not the one mentionned, a new instance will replace the existing one.
     * @return the only instance of Game.
     */
    public static Game getInstance(int level){
        return game = new Game(level);
    }

    public Board getBoard(){
        return board;
    }

    public int getFlagCounter(){
        return flagCounter;
    }

    /**
     * Get the current state of the game.
     * @return a StateGame enum value [ READY, STARTED, WON, LOST]
     */
    public StateGame getState() {
        return state;
    }

    public void addPropertyChangeListenerStateGame(PropertyChangeListener listener){
        this.stateChangeSupport.addPropertyChangeListener(listener);
        this.stateChangeSupport.firePropertyChange("state", null, this.state);
    }

    public void removePropertyChangeListenerStateGame(PropertyChangeListener listener){
        this.stateChangeSupport.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListenerFlag(PropertyChangeListener listener){
        this.flagChangeSupport.addPropertyChangeListener(listener);
        this.flagChangeSupport.firePropertyChange("flag", null, this.flagCounter);
    }

    public void removePropertyChangeListenerFlag(PropertyChangeListener listener){
        this.flagChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Put a flag on the case positionned at (row,column) on the board, only if
     * row and column are valid within the board, and if a flag is available.
     * @Note the count start at 0.
     * @param row
     * @param column
     */
    public void flagCase(int row, int column){
        try{
            this.board.flagCase(row, column);
        }catch(ArrayIndexOutOfBoundsException exc){
            System.out.println("Attention, les coordonnées ne correspondent pas au plateau.");
        }
    }

    /**
     * Take a flag if one is available.
     * @return true if a flag was available, false if the number of flags left is 0.
     */
    protected boolean decrementFlag(){
        if(flagCounter > 0){
            this.flagCounter--;
            this.flagChangeSupport.firePropertyChange("flag", null, this.flagCounter);
            return true;
        }
        return false;
    }

    /**
     * Free a flag which was used.
     */
    protected void incrementFlag(){
        this.flagCounter++;
        this.flagChangeSupport.firePropertyChange("flag", null, this.flagCounter);
    }

    /**
     * Reveal the case positionned at (row,column) on the board, only if
     * row and column are valid within the board. A case cannot be revealed if it is flagged.
     * @Note the count start at 0.
     * @param row
     * @param column
     */
    public void revealCase(int row, int column){
        if(state != StateGame.STARTED){
            this.state = StateGame.STARTED;
            this.stateChangeSupport.firePropertyChange("state", null, this.state);
        }
        try{
            this.board.revealCase(row, column);
        }catch(ArrayIndexOutOfBoundsException exc){
            System.out.println("Attention, les coordonnées ne correspondent pas au plateau.");
        }
    }

    /**
     * Decrement the number of cases that need to be revealed to win the game.
     */
    protected void decrementCasesLeftToReveal(){
        this.casesLeftToReveal--;
        if(this.casesLeftToReveal == 0){
            this.state = StateGame.WON;
            this.stateChangeSupport.firePropertyChange("state", null, this.state);
        }
    }

    /**
     * Reveal all of the mines on the board whatever their current state, and stop the game.
     */
    protected void explosion(){
        this.state = StateGame.LOST;
        this.stateChangeSupport.firePropertyChange("state", null, this.state);
        this.board.revealMines();
    }

    @Override
    public String toString() {
        String str = "Drapeaux : "+this.flagCounter;
        return str + this.board.toString();
    }

    public static int getNbLevel(){
        return Game.nbLevel;
    }
}
