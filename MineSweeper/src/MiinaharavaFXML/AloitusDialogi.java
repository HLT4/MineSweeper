package MiinaharavaFXML;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Dialog that pops up when starting the program.
 * Lets user set amount of cells and mines 
 * @author Hannes Tornberg
 * @version 12.10.2025
 *
 */
public class AloitusDialogi {
    
    ArrayList<Integer> results = new ArrayList<Integer>();
    
    /**
     * Creates the startup dialog
     */
    public AloitusDialogi() {
        
        this.results.add(9);
        this.results.add(9);
        this.results.add(15);
        
        Dialog<ArrayList<Integer>> dialog = new Dialog<>();
        dialog.setTitle("Choose settings");
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField rows = new TextField();
        TextField columns = new TextField();
        TextField mines = new TextField();

        rows.setText("9");
        columns.setText("9");
        mines.setText("15");
        
        grid.add(new Label("Rows:"), 0, 0);
        grid.add(rows, 1, 0);
        grid.add(new Label("Columns:"), 0, 1);
        grid.add(columns, 1, 1);
        grid.add(new Label("Mines:"), 0, 2);
        grid.add(mines, 1, 2);
        
        Platform.runLater(() -> rows.requestFocus());
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    
                    ArrayList<Integer> values = new ArrayList<Integer>();
                    int r = Integer.parseInt(rows.getText());
                    int c = Integer.parseInt(columns.getText());
                    int m = Integer.parseInt(mines.getText());
                    
                    values.add(r);
                    values.add(c);
                    values.add(m);
                    
                    return values;
                    
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Bad input");
                    alert.setHeaderText(null);
                    alert.setContentText("Input should be a whole number");
                    alert.showAndWait();
                }
                
                return null;
                
            }
            return null;
        });
        
        Optional<ArrayList<Integer>> result = dialog.showAndWait();
        
        result.ifPresent(value -> {
            this.results.set(0, value.get(0));
            this.results.add(1, value.get(1));
            this.results.add(2, value.get(2));

        });
        
    }
    
    
    /**
     * Returns the chosen settings
     * @return ArrayList with num of rows, columns and mines
     */
    public ArrayList<Integer> getResult() {
        return this.results;
    }
   
    
}
