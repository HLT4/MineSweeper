package MiinaharavaFXML;

import Miinaharava.Minesweep.Solu;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Yksittäinen miinaharavan solu
 */
public class Solufx extends Pane {
    
    private Solu pelisolu;
    private Solut isanta;
    
    private int y;
    private int x;
    
    private boolean flagged = false;
    private boolean loydetty = true;
    
    
    
    /**
     * Käytetään, kun nollasolu löytyy
     */
    public void loytyi() {
        this.loydetty = true;
    }
    
    
    /**
     * Vain nollasolujen etsimistä varten
     * @return true, jos on jo löydetty 
     */
    public boolean onLoydetty() {
        return this.loydetty;
    }
    
    
    /**
     * @return x-koordinaatti
     */
    public int getX() {
        return this.x;
    }
    
    
    /**
     * @return Y-koordinaatti
     */
    public int getY() {
        return this.y;
    }
    
    
    /**
     * @return True, jos solu on pommi
     */
    public boolean onPommi() {
        return this.pelisolu.getPommi();
    }
    
    
    /**
     * @return Kuinka monta miinaa ympärillä
     */
    public int getMonta() {
        return this.pelisolu.getMonta();
    }
    
    
    /**
     * @return true, jos solu on liputettu
     */
    public boolean onLiputettu() {
        return this.flagged;
    }
    
    
    /**
     * @return true, jos ruutu on jo avattu
     */
    public boolean onAvattu() {
        return this.pelisolu.getAvattu();
    }
    
    /**
     * Alustin
     * @param i Viite kontaineriluokkaan
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public Solufx(Solut i, int y, int x) {
        this.isanta = i;
        this.y = y;
        this.x = x;
    }
    
    
    /**
     * Käsittelijä hiiren oikean näppäimen painallukselle
     */
    public void handleRClick() {
        
        if (this.onAvattu()) return;
        
        if (this.flagged) {
            this.flagged = false;
            this.getChildren().clear();
            this.isanta.addMinesLeft();
        }
        else {
            this.flagged = true;
            Label lippu = new Label("🚩");
            lippu.setStyle("-fx-text-fill: red;");
            lippu.setAlignment(Pos.BASELINE_CENTER);
            lippu.setPrefSize(40, 40);
            this.getChildren().add(lippu);
            this.isanta.subMinesLeft();
            this.isanta.checkVictory();
        }
        
        
    }
    
    /**
     * Käsittelijä klikkauksille
     */
    public void handleLClick() {
        
        if (this.onAvattu() || this.flagged) {
            return;
        }
        
        this.isanta.handleClick(this);
    }
    
    
    /**
     * Avaa solun
     */
    public void avaa() {
        this.setVari(this.pelisolu.getPommi() ? "red" : "green");
        if (this.onAvattu()) return;
        
        this.pelisolu.avaa();
        
        Label teksti = new Label(this.getMonta() == 0 ? " " : String.valueOf(this.pelisolu.getMonta()));
        teksti.setAlignment(Pos.BASELINE_CENTER);
        teksti.setPrefSize(40, 40);
        
        if (this.onPommi()) teksti.setText("💣");
        
        this.getChildren().add(teksti);
        this.setVari(this.pelisolu.getPommi() ? "red" : "white");
    }
    
    /**
     * Asettaa annetun värin solulle
     * @param vari Asetettava väri
     */
    public void setVari(String vari) {
        this.setStyle("-fx-background-color: " + vari + ";");
    }
    
    
    /**
     * Asettaa solufx:n soluksi pelin sisäisen varsinaisen solun ja tekstin, joka kertoo
     * kuinka monta miinaa ympärillä on
     * @param solu Asetettava solu
     */
    public void asetaSolu(Solu solu) {
        this.pelisolu = solu;
        
        this.loydetty = !(solu.getMonta() == 0);
    }
    
    
    /**
     * @return a string rep. of cell for debug printing 
     */
    public String debugPrintable() {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append(this.pelisolu.getMonta());
        sb.append(this.flagged ? "F" : "!");
        sb.append(this.loydetty ? "L" : "!");
        sb.append("|");
        return sb.toString();
    }
    
}
