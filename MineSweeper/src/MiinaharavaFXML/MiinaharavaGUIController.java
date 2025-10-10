package MiinaharavaFXML;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    private ObservableList<Node> gridisolut;
    private Stage ikkuna; 

    private Solut solut;
    private Minesweep maingame;
    
    /**
     * @param y rivien määrä
     * @param x sarakkeiden määrä
     * @param ms Peli
     * @param stage Ikkuna tms ???
     */
    public void alustus(int y, int x, Minesweep ms, Stage stage) {

        this.gridisolut = gridi.getChildren();
        this.ikkuna = stage;
        
        // peli pitäis alustaa vasta ensimmäisen klikkauksen jälkeen, ettei heti kuole
        this.maingame = ms; // todo erikokoiset pelit
        
        this.solut = new Solut(y, x);
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
        
        stage.sizeToScene();

    }

}