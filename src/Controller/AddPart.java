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
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class to handle the Add Part form
 */
public class AddPart implements Initializable {

    Inventory inventory;
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
     * The constructor initializes the inventory field
     * @param inventory for the parts
     */
    public AddPart(Inventory inventory) {
        this.inventory = inventory;
    }


    /**
     * This method uses the input fields to build the part object
     ** <br>"RUNTIME ERROR" <br>
     * I've got a NumberFormatException() runtime error when words were typed on any integer/double field.<br>
     * The text in numerical fields had to be transformed to Integer or Double.<br>
     * The solution was to use a try/catch method to handle this exception and validate the fields,<br>
     * then an error message would be displayed to the user explaining what input was incorrect.<br>
     ** <br> "FUTURE ENHANCEMENT" <br>
     * A future enhancement would be to add buttons to enter the stock of a product.<br>
     * These buttons could have a "+" and "-" symbol.<br>
     * They would add or subtract from the stock variable.<br>
     * We could then disable the stock field so it can only be edited this way.<br>
     * @param event
     */
    @FXML
    private void saveButton(ActionEvent event) {
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
            if (typeBox.getText().trim() != null && !typeBox.getText().isEmpty()) {
                System.out.println(typeBox.getText());
                if (inHouseB.isSelected()) {
                    Integer machineIdTemp = Integer.parseInt(typeBox.getText().trim());
                    inventory.addPart(new InHouse(Integer.parseInt(id.getText()), nameTemp, priceTemp, stockTemp, minTemp, maxTemp, machineIdTemp));
                } else {
                    inventory.addPart(new Outsourced(Integer.parseInt(id.getText()), nameTemp, priceTemp, stockTemp, minTemp, maxTemp, typeBox.getText().trim()));
                }
                mainMenu(event);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                if (outsourcedB.isSelected()) {
                    alert.setHeaderText("Company is empty");
                } else {
                    alert.setHeaderText("Machine ID is empty");
                }
                alert.showAndWait();
                return;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return;
        }

    }

    /**
     * Cleans form and sets radio button to In House by default
     */
    private void clearFields() {
        name.setText("");
        stock.setText("");
        price.setText("");
        min.setText("");
        max.setText("");
        typeBox.setText("");
        typeLabel.setText("Machine ID");
        inHouseB.setSelected(true);
    }

    /**
     * Handles ID generation to make sure they are unique.
     */
    private void generateID() {

        Random randomNum = new Random();
        Integer generatedId = randomNum.nextInt(1000);

        if (inventory.getAllParts().size() == 0) {
            id.setText(generatedId.toString());

        }
        if (inventory.getAllParts().size() == 1000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add part");
            alert.setContentText("Invalid format!");
            alert.showAndWait();
        } else {
            Part foundId = inventory.lookupPart(generatedId);
            if(foundId !=null){
                generateID();
            }else{
                id.setText(generatedId.toString());
            }

        }
    }

    /**
     * Changes type label to Machine ID when "in house" radio button is chosen.
     * @param event
     */
    @FXML
    private void inHouseButton(ActionEvent event) {
        typeLabel.setText("Machine ID");
    }

    /**
     * Changes type label to company name when "outsourced" radio button is chosen.
     * @param event
     */
    @FXML
    private void outsourcedButton(ActionEvent event) {
        typeLabel.setText("Company Name");
    }

    /**
     * Returns to main menu
     * @param event
     */
    @FXML
    private void cancelAddPart(ActionEvent event) {
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
     * It calls two methods to clear fields and generate IDs
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearFields();
        generateID();
    }
}
