package MiinaharavaFXML;

import javax.naming.directory.InvalidAttributesException;

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
                throw new InvalidAttributesException(); // Should make a new exception class but lazy
            }
            
            if (results[2] >= results[0] * results[1]) {
                throw new IndexOutOfBoundsException(); // Should make a new exception class but lazy
            }
            
        } catch (InvalidAttributesException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bad input");
            alert.setHeaderText(null);
            alert.setContentText("Input for rows and columns should be a whole number greater than three"
                    + " and greater than zero for mines");
            alert.showAndWait();
            return;
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bad input");
            alert.setHeaderText(null);
            alert.setContentText("Amount of mines cannot exceed or equal total cells");
            alert.showAndWait();
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
