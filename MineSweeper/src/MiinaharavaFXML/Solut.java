package MiinaharavaFXML;

import java.util.ArrayList;
import java.util.Optional;

import Miinaharava.Minesweep;
import Miinaharava.Minesweep.Solu;
import fi.jyu.mit.fxgui.ModalController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * Should've been just a container-class but turned into an everything-class
 */
public class Solut {

    private Label mineLabel;
    private Minesweep game;
    private boolean firstClick = true;
    private boolean godMode = false;
    private int pommimaara;
    private int minesLeft;
    private int y;
    private int x;
    private Stage mainStage;
    private Solufx[][] taulukko;
    private ArrayList<Solufx> nollalista = new ArrayList<Solufx>();
    private Label godModeLabel;
    
    /**
     * Konstruktori
     * @param y Rivien määrä
     * @param x Sarakkeiden määrä
     * @param game Game object
     * @param pommimaara Amount of mines
     * @param mineLabel Label that shows amount of mines left
     * @param mainStage primary stage
     * @param godModeLabel Label that shows if godmode is on
     */
    public Solut(int y, int x, Minesweep game, int pommimaara, Label mineLabel, Stage mainStage, Label godModeLabel) {
        this.y = y;
        this.x = x;
        this.taulukko = new Solufx[y][x];
        this.game = game;
        this.pommimaara = pommimaara;
        this.minesLeft = pommimaara;
        this.mineLabel = mineLabel;
        this.mineLabel.setText("💣: " + pommimaara);
        this.mineLabel.getStyleClass().add("peliLabel");
        this.mainStage = mainStage;
        this.godModeLabel = godModeLabel;
        this.godModeLabel.getStyleClass().add("peliLabel");
    }
    
    /**
     * Toggles on invulnerability to mines 
     */
    public void toggleGodMode() {
        if (this.godMode) {
            this.godMode = false;
            this.godModeLabel.setText("");
        }
        else {
            this.godMode = true;
            this.godModeLabel.setText("G");
        }
    }
    
    /**
     * Alustaa kaikki solut
     * @param t Taulukko, jonka mukaan solujen arvot tms alustetaan
     */
    public void alustus(Solu[][] t) {
        for (int i = 0; i < this.y; i++) {
            
            for (int j = 0; j < this.x; j++) {
                Solufx s = new Solufx(this, i, j);
                s.asetaSolu(t[i][j]);
                s.setPrefSize(40, 40); s.setMaxSize(40, 40);
                s.setMinSize(40, 40);
                s.setOnMouseClicked( (event) -> {
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        s.handleRClick();
                    }
                    else {
                        s.handleLClick();
                    }
                    
                });
                
                this.taulukko[i][j] = s;
                
            }
            
        }
    }
    
    /**
     * Asettaa annetun taulukon tämän olion taulukoksi
     * @param solut Lisättävä taulukko
     */
    public void asetaTaul(Solufx[][] solut) {
        this.taulukko = solut;
    }
    
    
    /**
     * Kutsu tulee ns. alempaa, eli yksittäiseltä solulta. Tehty näin, jotta voi klikata
     * yksittäistä solua ja sitte hypätään heti ulos ja tää hoitaa logiikan  
     * @param klikattu Klikattu solu
     */
    public void handleClick(Solufx klikattu) {
        if (this.firstClick) {
            this.game.alusta(klikattu.getY(), klikattu.getX(), this.pommimaara);
            this.firstClick = false;
            this.updateCells();
            this.mineLabel.setText("💣: " + this.game.getPommit());
            this.handleClick(klikattu);
            return;
        }
        klikattu.avaa();
        this.checkVictory();
        
        if (klikattu.onPommi() && !this.godMode) havio();
        
        if (klikattu.getMonta() == 0) {
            this.etsiNollat(klikattu, true);
            for (Solufx nolla : this.nollalista) this.avaaNaapurit(nolla);
            this.nollalista = new ArrayList<Solufx>();
        }
    }
    
    
    /**
     * Checks if game has been won
     */
    public void checkVictory() {
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                Solufx cell = this.taulukko[i][j];
                //if (cell.onPommi() && !cell.onLiputettu()) return;
                if (!cell.onPommi() && !cell.onAvattu()) return;
                
            }
        }
        //System.out.println("Voitit!");
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("You won!");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations!\nDo you want  to play again?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.get() == buttonTypeYes ) {
            ModalController.closeStage(this.taulukko[0][0]);
            MiinaharavaMain miinaharavaMain = new MiinaharavaMain();
            miinaharavaMain.newGame(this.mainStage, new int[] { this.game.getX(), this.game.getY(), this.game.getPommit()} );
        }
        else {
            ModalController.closeStage(this.taulukko[0][0]);
        }
        
    }


    /**
     * Updates the visual cells to have values of initialized game cells
     */
    public void updateCells() {
        Solu[][] pelisolut = this.game.getTaulukko();
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                taulukko[i][j].asetaSolu(pelisolut[i][j]);
                //System.out.print(taulukko[i][j].debugPrintable());
            }
            //System.out.println();
        }
        
        
        
    }
    
    
    /**
     * Häviö
     */
    public void havio() {
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                Solufx pommi = taulukko[i][j];
                if (pommi.onPommi()) pommi.avaa(); 
            }
        }
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("You lost...");
        alert.setHeaderText(null);
        alert.setContentText("Too bad\nDo you want to play again?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.get() == buttonTypeYes ) {
            ModalController.closeStage(this.taulukko[0][0]);
            MiinaharavaMain miinaharavaMain = new MiinaharavaMain();
            miinaharavaMain.newGame(this.mainStage, new int[] { this.game.getX(), this.game.getY(), this.game.getPommit()} );
        }
        else {
            ModalController.closeStage(this.taulukko[0][0]);
        }
        
    }
    
    
    /**
     * Jos avatun solun ympärillä ei ole pommeja, avaa naapurisolut
     * @param nolla Avattu nollasolu
     * @param ekakierros Aseta aina true ensimmäisellä kierroksella, muuten false
     */
    public void etsiNollat(Solufx nolla, boolean ekakierros) {
        int yy = nolla.getY();
        int xx = nolla.getX();
        
        
        for (int i = yy - 1; i <= yy + 1; i++) {
            if (i < 0 || i >= this.y) continue;
            for (int j = xx - 1; j <= xx + 1; j++) {
                if (j < 0 || j >= this.x) continue;
                
                Solufx naapuri = this.getSolu(i, j);
                
                if (naapuri.onLoydetty() || naapuri.onLiputettu()) {
                    continue;
                }
                
                this.nollalista.add(naapuri);
                naapuri.loytyi();
                
            }
        }
        
        if (ekakierros) {
            for (int i = 0; i < this.nollalista.size(); i++) {
                this.etsiNollat(this.nollalista.get(i), false);
            }
        }
        
        
    }
    
    
    /**
     * Avaa nollasolujen naapurit
     * @param nolla Solu, jonka naapurit avataan
     */
    public void avaaNaapurit(Solufx nolla) {
        int yy = nolla.getY();
        int xx = nolla.getX();
        
        for (int i = yy - 1; i <= yy + 1; i++) {
            if (i < 0 || i >= this.y) continue;
            for (int j = xx - 1; j <= xx + 1; j++) {
                if (j < 0 || j >= this.x) continue;
                
                Solufx naapuri = this.getSolu(i, j);
                
                if (naapuri.onAvattu() || naapuri.onLiputettu()) {
                    continue;
                }
                
                naapuri.avaa();
                
            }
        }
    }
    
    
    /**
     * Palauttaa solun annetussa indeksipaikassa
     * @param iy Solun y-indeksi
     * @param ix Solun x-indeksi
     * @return Solu annetussa indeksipaikassa
     */
    public Solufx getSolu(int iy, int ix) {
        return this.taulukko[iy][ix];
    }


    /**
     * Decreases amount of mines left (flagging a cell)
     */
    public void subMinesLeft() {
        this.minesLeft--;
        this.mineLabel.setText("💣: " + this.minesLeft);
    }
    
    
    /**
     * Increases amount of mines left (unflagging a flagged cell)
     */
    public void addMinesLeft() {
        this.minesLeft++;
        this.mineLabel.setText("💣: " + this.minesLeft);
    }
    
}
