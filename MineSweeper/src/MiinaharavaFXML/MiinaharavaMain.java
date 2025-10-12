package MiinaharavaFXML;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author omistaja
 * @version 28.8.2025
 *
 */
public class MiinaharavaMain extends Application {
    
    private int[] settings;
    @Override
    public void start(Stage primaryStage) {
        
        try {

            startupDialog();
            
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("MiinaharavaGUIView.fxml"));
            final Pane root = ldr.load();
            MiinaharavaGUIController miinaharavaCtrl = (MiinaharavaGUIController) ldr.getController();
            
            miinaharavaCtrl.alustus(settings, primaryStage);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("miinaharava.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Miinaharava");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    private void startupDialog() {
        try {
            Stage startup = new Stage();
            startup.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AloitusDialogi.fxml"));
            final Pane root = ldr.load();
            final StartDialogController startCtrl = (StartDialogController)ldr.getController();
            
            startCtrl.init();
            startCtrl.setIsanta(this);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("aloitus.css").toExternalForm());
            startup.setScene(scene);
            startup.setTitle("Startup");
            startup.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    /**
     * Sets the starting settings given in the startup dialog
     * @param values Given starting values
     */
    public void setValues(int[] values) {
        this.settings = values;
    }
}