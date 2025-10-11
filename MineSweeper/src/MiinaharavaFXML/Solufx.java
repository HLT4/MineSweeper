package MiinaharavaFXML;

import java.awt.Font;

import MiinaharavaFXML.Minesweep.Solu;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Yksittäinen miinaharavan solu
 */
public class Solufx extends Pane {
    
    private Solu pelisolu;
    private Solut isanta;
    
    
    
    /**
     * @return True, jos solu on pommi
     */
    public boolean onPommi() {
        return this.pelisolu.getPommi();
    }
    
    /**
     * Alustin
     * @param i Viite kontaineriluokkaan
     */
    public Solufx(Solut i) {
        this.isanta = i;
    }
    
    
    /**
     * Käsittelijä hiiren oikean näppäimen painallukselle
     */
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
        
        Label teksti = new Label(String.valueOf(this.pelisolu.getMonta()));
        teksti.setAlignment(Pos.BASELINE_CENTER);
        teksti.setPrefSize(40, 40);
        teksti.setStyle("-fx");
        
        this.getChildren().add(teksti);
        
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
