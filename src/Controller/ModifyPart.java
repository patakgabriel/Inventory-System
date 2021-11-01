package Controller;
/**
 * @author Gabriel Fernandez Patak
 */
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class to handle the Modify Part form
 */
public class ModifyPart implements Initializable {

    Inventory inventory;
    Part modPart;
    @FXML
    private RadioButton inHouseB;
    @FXML
    private RadioButton outsourcedB;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField stock;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField typeBox;
    @FXML
    private Label typeLabel;

    /**
     * Initializes inventory and part to be modified
     * @param inventory the main inventory
     * @param modPart the part being modified
     */
    public ModifyPart(Inventory inventory, Part modPart) {
        this.inventory = inventory;
        this.modPart = modPart;
    }

    /**
     * This method uses the input fields to update the part object
     * @param event
     */
    @FXML
    private void saveButton(ActionEvent event){
        try {
            Integer minTemp = Integer.parseInt(min.getText().trim());
            Integer maxTemp = Integer.parseInt(max.getText().trim());
            Integer stockTemp = Integer.parseInt(stock.getText().trim());
            Double priceTemp = Double.parseDouble(price.getText().trim());
            String nameTemp = name.getText().trim();

            if (minTemp > maxTemp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Min is higher than max");
                alert.showAndWait();
                return;
            }
            if (stockTemp > maxTemp || stockTemp < minTemp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Stock must be between min and max");
                alert.showAndWait();
                return;
            }
            if (nameTemp == null || nameTemp.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name is empty");
                alert.showAndWait();
                return;
            }
            if (nameTemp == null || nameTemp.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name is empty");
                alert.showAndWait();
                return;
            }
            if(typeBox.getText().trim() != null && !typeBox.getText().isEmpty()){
                System.out.println(typeBox.getText());
                if(inHouseB.isSelected()) {
                    Integer machineIdTemp = Integer.parseInt(typeBox.getText().trim());
                    inventory.updatePart(Integer.parseInt(id.getText()), new InHouse(Integer.parseInt(id.getText()),nameTemp, priceTemp, stockTemp, minTemp, maxTemp, machineIdTemp));
                }else{
                    inventory.updatePart(Integer.parseInt(id.getText()), new Outsourced(Integer.parseInt(id.getText()),nameTemp, priceTemp, stockTemp, minTemp, maxTemp, typeBox.getText().trim()));
                }
                mainMenu(event);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                if(outsourcedB.isSelected()) {
                    alert.setHeaderText("Company is empty");
                }else{alert.setHeaderText("Machine ID is empty");}
                alert.showAndWait();
                return;
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return;
        }
    }

    /**
     * Changes type field text to Machine ID if In House button is chosen
     * @param event
     */
    @FXML
    private void inHouseButton(ActionEvent event) {
        typeLabel.setText("Machine ID");
    }

    /**
     * Changes type field text to Company Name if Outsourced button is chosen
     * @param event
     */
    @FXML
    private void outsourcedButton(ActionEvent event) {
        typeLabel.setText("Company Name");
    }

    /**
     * Calls mainMenu method if users accepts warning message
     * @param event
     */
    @FXML
    private void cancelModifyPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            mainMenu(event);
        }

    }

    /**
     * Loads JavaFX main menu form
     * @param event
     */
    private void mainMenu(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Main.fxml"));
            MainMenu controller = new MainMenu(inventory);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("IMS");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Populates form based on type of object that was passed
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (modPart instanceof InHouse) {
            InHouse partHouse = (InHouse) modPart;
            inHouseB.setSelected(true);
            typeLabel.setText("Machine ID");
            this.name.setText(partHouse.getName());
            this.id.setText((Integer.toString(partHouse.getId())));
            this.stock.setText((Integer.toString(partHouse.getStock())));
            this.price.setText((Double.toString(partHouse.getPrice())));
            this.min.setText((Integer.toString(partHouse.getMin())));
            this.max.setText((Integer.toString(partHouse.getMax())));
            this.typeBox.setText((Integer.toString(partHouse.getMachineId())));
        }
        if (modPart instanceof Outsourced) {
            Outsourced partOutsourced = (Outsourced) modPart;
            outsourcedB.setSelected(true);
            typeLabel.setText("Company Name");
            this.name.setText(partOutsourced.getName());
            this.id.setText((Integer.toString(partOutsourced.getId())));
            this.stock.setText((Integer.toString(partOutsourced.getStock())));
            this.price.setText((Double.toString(partOutsourced.getPrice())));
            this.min.setText((Integer.toString(partOutsourced.getMin())));
            this.max.setText((Integer.toString(partOutsourced.getMax())));
            this.typeBox.setText(partOutsourced.getCompanyName());
        }
    }
}
