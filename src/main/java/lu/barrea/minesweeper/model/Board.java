package lu.barrea.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Board {
    private int nbMines;
    private Case[][] cases;
    private List<CaseMine> mines;
    private int nbRow;
    private int nbColumn;

    public Board(int nbMines, int row, int column){
        this.nbMines = nbMines;
        this.nbRow = row;
        this.nbColumn = column;
        this.cases = new Case[row][column];
        this.mines = new ArrayList<>();

        placeCaseMines();

        for(int ro=0; ro < nbRow; ro++)
        {
            for(int co=0; co < nbColumn; co++)
            {
                if(cases[ro][co] == null) cases[ro][co] = new Case();
                cases[ro][co].setNeighbors( addNeighbors( ro, co, c->{
                    if ( c == null ) return new Case();
                    return c;
                }));
            }
        }
    }

    private void placeCaseMines(){
        Random random = new Random();
        int seed = random.nextInt();
        int i,j = 1;
        for(int cpt=0; cpt<nbMines; cpt++)
        {
            do
            {
                random.setSeed(seed += nbMines+cpt);
                i = random.nextInt(0,nbRow);
                random.setSeed(seed += i);
                j = random.nextInt(0,nbColumn);
            }
            while (cases[i][j] instanceof CaseMine);

            cases[i][j] = new CaseMine();
            mines.add((CaseMine)cases[i][j]);
            cases[i][j].setNeighbors(addNeighbors(i,j, c->{
                        if ( c == null ) c = new CaseNumber();
                        else if ( c instanceof CaseNumber ) ((CaseNumber)c).increment();
                        return c;
                    }
            ));
        }
    }

    private List<Case> addNeighbors(int row, int column, Function<Case, Case> method){
        int ro, co;
        List<Case> neighbors = new ArrayList<>();
        ro = row == 0 ? 0 : row-1;
        for( ; ro <= row+1 && ro < this.nbRow ; ro++ )
        {
            co = column == 0 ? 0 : column-1;
            for ( ; co <= column+1 && co < this.nbColumn ; co++ )
            {
                cases[ro][co] = method.apply(cases[ro][co]);
                neighbors.add(cases[ro][co]);
            }
        }
        return neighbors;
    }

    public int getNbRow(){
        return this.nbRow;
    }

    public int getNbColumn(){
        return this.nbColumn;
    }

    public Case getCase(int i, int j){
        return cases[i][j];
    }

    /**
     * Put a flag on the case positionned at (row,column) on the board.
     * @Note the count start at 0.
     * @param row
     * @param column
     * @throws ArrayIndexOutOfBoundsException if the coordinates row, column are not within the board
     */
    public void flagCase(int row, int column) throws ArrayIndexOutOfBoundsException {
        this.cases[row][column].flag();
    }

    /**
     * Reveal the case positionned at (row,column) on the board.
     * @Note the count start at 0.
     * @param row
     * @param column
     * @throws ArrayIndexOutOfBoundsException if the coordinates row, column are not within the board
     */
    public void revealCase(int row, int column) throws ArrayIndexOutOfBoundsException {
        this.cases[row][column].reveal();
    }

    /**
     * Reveal all the mines on the board whatever their current state.
     */
    public void revealMines(){
        for(CaseMine mine : mines){
            mine.forceReveal();
        }
    }

    /**
     *
     * @return the number of cases on the board.
     */
    public int getNbCase() { return this.nbColumn * this.nbRow; }

    private String printHLine(){
        String line = "\n";
        for(int i=0; i<nbColumn; i++) line += " ---";
        line += "\n";
        return line;
    }

    @Override
    public String toString() {
        String board = "";
        for(int co=0; co < nbColumn; co ++) board += " " + (co < 10 ? " " + co : co) + " ";
        board += printHLine();
        int li = 0;
        for(Case[] line : cases){
            board += "|";
            for(Case c : line ){
                board += " " + c.toSring() + " |";
            }
            board += " " + li++ + printHLine();
        }
        return board;
    }
}
