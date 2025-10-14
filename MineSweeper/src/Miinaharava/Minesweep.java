package Miinaharava;

import java.io.PrintStream;
import java.util.Random;

/**
 * Perus miinaharavasetti
 */
public class Minesweep {

    /**
     * Yksittäinen miinaharavan solu
     * @author Hannes Tornberg
     * @version 12.10.2025
     *
     */
    public static class Solu {
        private int monta;
        private boolean onPommi;
        
        private boolean onAvattu = false;
        
        /**
         * Initalizes a new cell
         */
        public Solu() {
            this.monta = 0;
            this.onPommi = false;
        }
        
        /**
         * Sets this cell as a mine
         */
        public void setPommi() {
            this.onPommi = true;
        }
        
        /**
         * Increments the amount of mines around this cell
         */
        public void nostaLkm() {
            this.monta++;
        }
        
        /**
         * @return Amount of mines around this cell
         */
        public int getMonta() {
            return this.monta;
        }
        
        /**
         * @return ture if this cell is a mine
         */
        public boolean getPommi() {
            return this.onPommi;
        }
        
        /**
         * Opens the cell
         */
        public void avaa() {
            this.onAvattu = true;
        }
        
        /**
         * @return ture if cell has been opened
         */
        public boolean getAvattu() {
            return this.onAvattu;
        }
        
        /**
         * For debug
         * @return M if the cell is a mine, otherwise amount of mines next to this cell
         */
        public String getTulostettava() {
            if (this.onPommi) return "M";
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
     * @param pommimaara Pommien määrä, jos -1, helppo versio
     */
    public Minesweep(int Yaks, int Xaks, int pommimaara) {
        this.Yaks = Yaks;
        this.Xaks = Xaks;
        this.taulukko = new Solu[Yaks][Xaks];
        
        // TODO vaikeustasot sun muut
        this.pommimaara = pommimaara;
        if (pommimaara == -1) this.pommimaara = (Xaks * Yaks) / 6;
        
        this.alustaPeli();
    }


    /**
     * defaultti constructori
     */
    public Minesweep() {
        this(9, 9, -1);
    }

    
    /**
     * Constructs a mineless game
     * @param Yaks Num of rows
     * @param Xaks Num of columns
     */
    public Minesweep(int Yaks, int Xaks) {
        this.Yaks = Yaks;
        this.Xaks = Xaks;
        this.taulukko = new Solu[Yaks][Xaks];
        this.alustaTyhja();
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
     * Initializes a game with all cells set to 0
     */
    public void alustaTyhja() {
        for (int a = 0; a < this.Yaks; a++) {
            for (int b = 0; b < this.Xaks; b++) {
                this.taulukko[a][b] = new Solu();
            }
        }
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
            int y = rand.nextInt(this.Yaks);
            int x = rand.nextInt(this.Xaks);
            
            if (this.taulukko[y][x].getPommi()) {
                i--;
                continue;
            }
            
            this.taulukko[y][x].setPommi();
            this.taulukko[y][x].monta = -1;
            
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
     * Initializes a game where the cell at [y][x] is empty
     * @param yy Y-coordinate of the cell
     * @param xx X-coordinate of the cell
     * @param mines Amount of mines in the game
     */
    public void alusta(int yy, int xx, int mines) {
        Random rand = new Random();
        
        this.pommimaara = mines;
        
        for (int i = 0; i < this.pommimaara; i++) {
            int y = rand.nextInt(this.Yaks);
            int x = rand.nextInt(this.Xaks);
            
            if (this.taulukko[y][x].getPommi()) {
                i--;
                continue;
            }
            
            if (y == yy || y == yy - 1 || y == yy + 1) {
                if (x == xx || x == xx - 1 || x == xx + 1) {
                    i--;
                    continue;
                }
            }
            
            
            this.taulukko[y][x].setPommi();
            this.taulukko[y][x].monta = -1;
            
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


    /**
     * @return Amount of mine in game
     */
    public int getPommit() {
        return this.pommimaara;
    }

}