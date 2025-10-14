package MiinaharavaFXML;

import Miinaharava.BadInputException;
import Miinaharava.TooManyMinesException;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

    int[] results = { 9, 9, 15 };
    
    
    
    @FXML
    void handleOK() {
        
        try {
            int r = Integer.parseInt(rows.getText());
            int c = Integer.parseInt(columns.getText());
            int m = Integer.parseInt(mines.getText());
            
            this.results[0] = c;
            this.results[1] = r;
            this.results[2] = m;
            
            if (results[0] <= 3 || results[1] <= 3 || results[2] < 0) {
                throw new BadInputException("Input for rows and columns should be a whole number greater than three"
                        + " and greater than zero for mines");
            }
            
            if (results[2] >= results[0] * results[1]) {
                throw new TooManyMinesException("Too many mines");
            }
            
            if (results[1] > 23 ||  results[0] > 45) {
                throw new BadInputException("Maximum rows is 45 and maximum columns is 23");
            }
            
            
        } catch (NumberFormatException n) {
            alertti("Bad input", "Input for rows and columns should be a whole number greater than three"
                    +" and greater than zero for mines");
            return;
        } catch (BadInputException e) {
            alertti("Bad input", e.getMessage());
            return;
        } catch (TooManyMinesException e) {
            alertti("Too many mines", "Amount of mines cannot exceed or equal total cells");
            return;
        }
        
        ModalController.closeStage(columns);
        
        try {
            Stage stage = new Stage();
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("MiinaharavaGUIView.fxml"));
            final Pane root = ldr.load();
            MiinaharavaGUIController miinaharavaCtrl = (MiinaharavaGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("miinaharava.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Miinaharava");
            
            miinaharavaCtrl.alustus(this.results, stage);
            stage.show();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        
    }

    
    private void alertti(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    /**
     * initializes the default values for textfields
     * @param settings settings
     */
    public void init(int[] settings) {
        
        Platform.runLater(() -> rows.requestFocus());
        
        this.columns.setText(String.valueOf(settings[0]));
        this.rows.setText(String.valueOf(settings[1]));
        this.mines.setText(String.valueOf(settings[2]));
    }

}
