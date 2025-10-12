package MiinaharavaFXML;


import Miinaharava.Minesweep;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * @author omistaja
 * @version 28.8.2025
 *
 */
public class MiinaharavaGUIController {

    @FXML
    private GridPane gridi;
    
    @FXML
    private MenuBar fxMenu;
    
    @FXML
    private Label mineLabel;

    private ObservableList<Node> gridisolut;

    private Solut solut;
    private Minesweep maingame;
    
    /**
     * @param settings Arraylist amounts of rows, columns and mines
     * @param stage Ikkuna tms ???
     */
    public void alustus(int[] settings, Stage stage) {

        this.gridisolut = gridi.getChildren();
        
        // peli pitäis alustaa vasta ensimmäisen klikkauksen jälkeen, ettei heti kuole
        this.maingame = new Minesweep(settings[1], settings[0]);
        
        int y = this.maingame.getY();
        int x = this.maingame.getX();
        
        this.solut = new Solut(y, x, this.maingame, settings[2]);
        this.solut.alustus(this.maingame.getTaulukko());
        
        for (int i = 0; i < y; i++) {
            gridi.getRowConstraints().add(new RowConstraints(40));
            for (int j = 0; j < x; j++) {
                gridi.getColumnConstraints().add(new ColumnConstraints(40));
                
                Solufx s = solut.getSolu(i, j);
                GridPane.setConstraints(s, j, i);
                
                this.gridisolut.add(s);
            }
        }
        
        maingame.tulostaTaul();
        
        int lev = x * 42 - 2;
        int kor = y * 42 + 96;
        
        stage.setMaxHeight(kor);
        stage.setMaxWidth(lev);
        
        stage.setMinHeight(kor);
        stage.setMinWidth(lev);
        
        fxMenu.setMaxWidth(lev);
        fxMenu.setMinWidth(lev);
        
        //stage.sizeToScene();

    }

}