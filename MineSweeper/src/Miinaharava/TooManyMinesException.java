package Miinaharava;


/**
 * Exception for when player tries to create too many mines in the startup dialog
 */
public class TooManyMinesException extends Exception {

    /**
     *  ???
     */
    private static final long serialVersionUID = 5172436584841443732L;
    
    
    /**
     * @param msg message
     */
    public TooManyMinesException(String msg) {
        super(msg);
    }
    
}   
