package MiinaharavaFXML;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;



/**
 * @author Hannes Tornberg
 * @version 28.8.2025
 *
 */
public class MiinaharavaMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        try {
            
            FXMLLoader sldr = new FXMLLoader(getClass().getResource("AloitusDialogi.FXML"));
            final Pane sroot = sldr.load();
            StartDialogController startCtrl = (StartDialogController)sldr.getController();
            
            
            primaryStage.setMaxHeight(200);
            primaryStage.setMaxWidth(200);
            
            primaryStage.setMinHeight(200);
            primaryStage.setMinWidth(200);
            
            startCtrl.init();
            
            Scene sscene = new Scene(sroot);
            sscene.getStylesheets().add(getClass().getResource("aloitus.css").toExternalForm());
            primaryStage.setScene(sscene);
            primaryStage.setTitle("Startup");
            primaryStage.show();
            
            
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

}