package MiinaharavaFXML;

import java.util.ArrayList;

import MiinaharavaFXML.Minesweep.Solu;
import javafx.scene.input.MouseButton;

/**
 * Container-luokka solufx:ille
 */
public class Solut {

    private int y;
    private int x;
    
    private Solufx[][] taulukko;
    private ArrayList<Solufx> nollalista = new ArrayList<Solufx>();
    
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
        klikattu.avaa();
        
        if (klikattu.onPommi()) havio();
        
        if (klikattu.getMonta() == 0) {
            this.etsiNollat(klikattu, true);
            for (Solufx nolla : this.nollalista) this.avaaNaapurit(nolla);
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
    
}
