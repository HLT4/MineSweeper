package MiinaharavaFXML;

import MiinaharavaFXML.Minesweep.Solu;
import javafx.scene.layout.Pane;

/**
 * Yksittäinen miinaharavan solu
 */
public class Solufx extends Pane {
    
    private Solu pelisolu;
    private Solut isanta;
    
    /**
     * Alustin
     * @param i Viite kontaineriluokkaan
     */
    public Solufx(Solut i) {
        this.isanta = i;
    }
    
    
    public void handleRClick() {
        
        // TODO temp
        this.setVari("blue");
        
    }
    
    /**
     * Käsittelijä klikkauksille
     */
    public void handleLClick() {
        
        if (pelisolu.getAvattu()) {
            return;
        }
        
        // TODO temp. poista:
        this.setVari(this.pelisolu.getPommi() ? "red" : "green");
        
        this.isanta.handleClick();
    }
    
    /**
     * Asettaa annetun värin solulle
     * @param vari Asetettava väri
     */
    public void setVari(String vari) {
        this.setStyle("-fx-background-color: " + vari + ";");
    }
    
    
    /**
     * Asettaa solufx:n soluksi pelin sisäisen varsinaisen solun
     * @param solu Asetettava solu
     */
    public void asetaSolu(Solu solu) {
        this.pelisolu = solu;
    }
    
}
