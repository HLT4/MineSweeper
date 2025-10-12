package MiinaharavaFXML;

import java.util.ArrayList;

import javafx.application.Application;
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
    @Override
    public void start(Stage primaryStage) {
        try {
            
            AloitusDialogi a = new AloitusDialogi();
            ArrayList<Integer> settings = a.getResult();
            
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("MiinaharavaGUIView.fxml"));
            final Pane root = ldr.load();
            final MiinaharavaGUIController miinaharavaCtrl = (MiinaharavaGUIController) ldr.getController();
            
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

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}