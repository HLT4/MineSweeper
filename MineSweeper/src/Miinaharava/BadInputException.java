package Miinaharava;

/**
 * Exception for bad inputs given in startup dialog
 */
public class BadInputException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    /**
     * @param msg message
     */
    public BadInputException(String msg) {
        super(msg);
    }
    
    
}
