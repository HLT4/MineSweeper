package MiinaharavaFXML;

import java.io.PrintStream;
import java.util.Random;

/**
 * Perus miinaharavasetti
 */
public class Minesweep {

    static class Solu {
        private int monta;
        private boolean onPommi;
        
        private boolean onAvattu = false;
        
        public Solu() {
            this.monta = 0;
            this.onPommi = false;
        }
        
        public void setPommi() {
            this.onPommi = true;
        }
        
        public void nostaLkm() {
            this.monta++;
        }
        
        public int getMonta() {
            return this.monta;
        }
        
        public boolean getPommi() {
            return this.onPommi;
        }
        
        public void avaa() {
            this.onAvattu = true;
        }
        
        public boolean getAvattu() {
            return this.onAvattu;
        }
        
        public String getTulostettava() {
            if (this.onPommi) return "P";
            return String.valueOf(this.monta);
        }
        
    }
    
    private int pommimaara;    
    private int Yaks;
    private int Xaks;
    private Solu[][] taulukko;

    /**
     * @param Yaks Y-pituus
     * @param Xaks X-pituus
     */
    public Minesweep(int Yaks, int Xaks) {
        this.Yaks = Yaks;
        this.Xaks = Xaks;
        this.taulukko = new Solu[Yaks][Xaks];
        
        // TODO vaikeustasot sun muut
        this.pommimaara = (Xaks * Yaks) / 6;  
        
        this.alustaPeli();
    }


    /**
     * defaultti constructori
     */
    public Minesweep() {
        this(9, 9);
    }

    
    /**
     * Palauttaa pystysuorien rivien määrän
     * @return pystysuorien rivien määrä
     */
    public int getY() {
        return this.Yaks;
    }
    
    
    /**
     * Palauttaa vaakasuorien rivien määrän
     * @return vaakasuorien rivien määrä
     */
    public int getX() {
        return this.Xaks;
    }
    
    
    /**
     * Palauttaa taulukosta annettujen indeksien kohdalta solun
     * @param y Solun indeksipaikka pystysuunnassa
     * @param x Solun indeksipaikka vaakasuunnassa
     * @return Solu annetuissa indeksipaikoissa
     */
    public Solu getTaulukosta(int y, int x) {
        return this.taulukko[y][x];
    }
    
    
    /**
     * Palauttaa solujen taulukon
     * @return Taulukko soluista
     */
    public Solu[][] getTaulukko() {
        return this.taulukko;
    }
    
    
    /**
     * Alustaa pelin solut ja sijoittaa pommit paikoilleen
     */
    private void alustaPeli() {
        Random rand = new Random();
        
        for (int a = 0; a < this.Yaks; a++) {
            for (int b = 0; b < this.Xaks; b++) {
                this.taulukko[a][b] = new Solu();
            }
        }
        
        for (int i = 0; i < this.pommimaara; i++) {
            int y = rand.nextInt(9);
            int x = rand.nextInt(9);
            
            if (this.taulukko[y][x].getPommi()) {
                i--;
                continue;
            }
            
            this.taulukko[y][x].setPommi();
            
            for (int j = y - 1; j <= y + 1; j++) {
                
                if (j < 0 || j >= this.Yaks) continue;
                
                for (int k = x - 1; k <= x + 1; k++) {
                    
                    if (k < 0 || k >= this.Xaks) continue;
                    
                    if (!this.taulukko[j][k].getPommi()) this.taulukko[j][k].nostaLkm(); 
                    
                }
                
            }
            
        }
        
    }
    
    
    /**
     * Tulostaa pelitaulukon annettuun ulostukseen
     * @param os Outputti streami
     */
    public void tulostaTaul(PrintStream os) {
        for (int i = 0; i < this.Yaks; i++) {
            for (int j = 0; j < this.Xaks; j++) {
                os.printf(" %s",this.taulukko[i][j].getTulostettava());
            }
            os.println();
        }
    }


    /**
     * Tulostaa pelitaulukon
     */
    public void tulostaTaul() {
        tulostaTaul(System.out);
    }


    /**
     * Pääohjelma
     */
    public static void main() {
        Minesweep peli = new Minesweep();
        peli.tulostaTaul();
    }

}