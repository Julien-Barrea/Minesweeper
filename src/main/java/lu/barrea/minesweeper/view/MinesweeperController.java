package lu.barrea.minesweeper.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import lu.barrea.minesweeper.controller.*;
import lu.barrea.minesweeper.model.Chrono;
import lu.barrea.minesweeper.model.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperController {
    private Game g;
    private List<Button> buttonsLst;
    private CaseObserver[][] caseObserverArray;
    private GameObserver gameObserver;
    private ChronoObserver chronoObserver;

    @FXML
    private HBox infoBox;

    @FXML
    private MenuButton levelSelector;
    private int level;

    @FXML
    private Pane infoBox_space;

    @FXML
    private Label flagCounter;
    private FlagObserver flagObserver;

    @FXML
    private Label chronoLabel;
    private Chrono chrono;

    @FXML
    private GridPane grid;

    @FXML
    private Label gameStateLabel;

    /**
     * Initialize all the controls of the window, such as the labels and the buttons.
     */
    public void initialize() {
        for(int i=1; i<=Game.getNbLevel(); i++){
            final int lvl = i;
            MenuItem mi = new MenuItem("Niveau "+lvl);
            mi.addEventHandler( ActionEvent.ACTION, (e)->{
                this.level = lvl;
                createGrid(lvl);
                WindowObserver.refreshStage();
            } );
            levelSelector.getItems().add(mi);
        }

        this.level = 1;
        this.infoBox.setHgrow(infoBox_space, Priority.ALWAYS);

        this.chrono = new Chrono();
        this.chronoObserver = new ChronoObserver(this.chronoLabel);
        this.chrono.addPropertyChangeListener(this.chronoObserver);

        createGrid(this.level);
    }

    /**
     * Create the grid, which is the main part of the game, based on a chosen level
     * @param level the level chosen for the game.
     */
    private void createGrid(int level){
        this.grid.getChildren().clear();
        this.g = Game.getInstance(level);
        this.buttonsLst = new ArrayList<>();
        this.chrono.reset();

        this.flagObserver = new FlagObserver(this.flagCounter);
        this.g.addPropertyChangeListenerFlag(flagObserver);

        this.gameObserver = new GameObserver(this.buttonsLst, this.gameStateLabel, this.chrono);
        this.g.addPropertyChangeListenerStateGame(this.gameObserver);

        int cols = g.getBoard().getNbColumn();
        int rows = g.getBoard().getNbRow();

        this.caseObserverArray = new CaseObserver[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Button bt = new Button();
                bt.getStyleClass().setAll("case", "active_case", "covered_case");

                this.caseObserverArray[row][col] = new CaseObserver(bt);
                this.g.getBoard().getCase(row,col)
                        .addPropertyChangeListener(caseObserverArray[row][col]);
                bt.addEventHandler(MouseEvent.MOUSE_CLICKED, new ActionCase(row, col));

                this.grid.add(bt,row, col);
                this.buttonsLst.add(bt);
            }
        }
    }

    public void restart(ActionEvent actionEvent) {
        createGrid(this.level);
    }

    private class ActionCase implements EventHandler<MouseEvent> {
        private int row;
        private int column;

        public ActionCase(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                g.revealCase(row,column);
            }else{
                g.flagCase(row,column);
            }
        }
    }
}

