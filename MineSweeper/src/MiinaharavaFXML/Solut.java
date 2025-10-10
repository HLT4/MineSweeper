package MiinaharavaFXML;

import MiinaharavaFXML.Minesweep.Solu;
import javafx.scene.input.MouseButton;

/**
 * Container-luokka solufx:ille
 */
public class Solut {

    private int y;
    private int x;
    
    private Solufx[][] taulukko;
    
    
    /**
     * Konstruktori
     * @param y Rivien määrä
     * @param x Sarakkeiden määrä
     */
    public Solut(int y, int x) {
        this.y = y;
        this.x = x;
        this.taulukko = new Solufx[y][x];
    }
    
    
    /**
     * Alustaa kaikki solut
     * @param t Taulukko, jonka mukaan solujen arvot tms alustetaan
     */
    public void alustus(Solu[][] t) {
        for (int i = 0; i < this.y; i++) {
            
            for (int j = 0; j < this.x; j++) {
                Solufx s = new Solufx(this);
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
                    
                 // TODO näyttää ymp. pommien määrän
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
     */
    public void handleClick() {
        // TODO jos 0 => avaa naapureita
        // TODO jos pommi => häviä
        // TODO jos ei pommi => avaa
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
    
}
