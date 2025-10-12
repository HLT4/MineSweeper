package MiinaharavaFXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller for the startup dialog
 * @author hanne
 * @version 12.10.2025
 *
 */
public class StartDialogController {

    @FXML
    private TextField columns;

    @FXML
    private TextField mines;

    @FXML
    private TextField rows;

    int[] results = { 9, 9, 15};

    private MiinaharavaMain isanta;
    
    @FXML
    void handleOK(ActionEvent event) {
        try {
            int r = Integer.parseInt(rows.getText());
            int c = Integer.parseInt(columns.getText());
            int m = Integer.parseInt(mines.getText());
            
            this.results[0] = r;
            this.results[1] = c;
            this.results[2] = m;
            
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bad input");
            alert.setHeaderText(null);
            alert.setContentText("Input should be a whole number");
            alert.showAndWait();
            return;
        }
    }

    
    /**
     * Sets the parent of this controller to the object that called it
     * @param isanta Object that called this method
     */
    public void setIsanta(MiinaharavaMain isanta) {
        this.isanta = isanta;
    }

}
