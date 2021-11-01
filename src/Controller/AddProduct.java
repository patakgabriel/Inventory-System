package Controller;
/**
 * @author Gabriel Fernandez Patak
 */
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class to handle the Add Product form
 */
public class AddProduct implements Initializable {


    public TableView<Part> partTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partStock;
    public TableColumn partPrice;
    public TableView<Part> associatedTable;
    public TableColumn aPartId;
    public TableColumn aPartName;
    public TableColumn aPartStock;
    public TableColumn aPartPrice;
    public TextField searchPart;
    Inventory inventory;
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
    private final ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private final ObservableList<Part> associatedInventory = FXCollections.observableArrayList();

    /**
     * Constructor to set inventory
     * @param inventory for products
     */
    public AddProduct(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This method uses the input fields to build the product object
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

            Product savedProduct = new Product(Integer.parseInt(id.getText()),nameTemp, priceTemp, stockTemp, minTemp, maxTemp);
            inventory.addProduct(savedProduct);
            for(int i = 0; i < associatedInventory.size(); i++){
                savedProduct.addAssociatedPart(associatedInventory.get(i));
            }
            mainMenu(event);
        }catch(Exception e){
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
    }

    /**
     * Handles automate ID generation to make sure they are unique
     */
    private void generateID() {

        Random randomNum = new Random();
        Integer generatedId = randomNum.nextInt(1000);

        if (inventory.getAllProducts().size() == 0) {
            id.setText(generatedId.toString());

        }
        if (inventory.getAllProducts().size() == 1000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add product");
            alert.setContentText("Invalid format!");
            alert.showAndWait();
        } else {
            Product foundId = inventory.lookupProduct(generatedId);
            if(foundId !=null){
                generateID();
            }else{
                id.setText(generatedId.toString());
            }

        }
    }

    /**
     * Handles the textfield search to use input and refresh table
     * @param event
     */
    @FXML
    private void searchForPart(ActionEvent event) {
        String searchText = searchPart.getText().trim();
        if (!searchText.isEmpty()) {
            ObservableList<Part> foundParts = inventory.lookupPart(searchText);

            if(foundParts.size() == 0){
                try {
                    int partId = Integer.parseInt(searchText);
                    Part foundPartId = inventory.lookupPart(partId);
                    if (foundPartId != null) {
                        foundParts.add(foundPartId);

                    }
                }catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("Part not found");
                    alert.showAndWait();
                }

            }
            partTable.setItems(foundParts);
            partTable.refresh();

        }else{
            partTable.setItems(partsInventory);
        }
    }

    /**
     * Copies part from top table to bottom one and adds it to product.associatedParts
     * @param event
     */
    @FXML
    private void addAssociatedPart(ActionEvent event){
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        associatedInventory.add(selectedPart);
        associatedTable.setItems(associatedInventory);
        associatedTable.refresh();
    }

    /**
     * Removes part from bottom table one and from product.associatedParts
     * @param event
     */
    @FXML
    private void removeAssociatedPart(ActionEvent event){
        Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();
        System.out.println(associatedInventory.isEmpty());
        System.out.println(selectedPart == null);
        if(selectedPart == null || associatedInventory.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel");
            alert.setHeaderText("Are you sure you want to delete the product?");
            alert.setContentText("Click ok to proceed");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedInventory.remove(selectedPart);
                associatedTable.setItems(associatedInventory);
                associatedTable.refresh();
            }
        }
    }

    /**
     * Calls method to go back to main menu
     * @param event
     */
    @FXML
    private void cancelAddProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            mainMenu(event);
        }

    }

    /**Loads JavaFX main menu form
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
     * Populates tables and calls clear form and generate id methods
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearFields();
        generateID();
        partId.setCellValueFactory(new PropertyValueFactory<>("id")); //getId() from Part class
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        aPartId.setCellValueFactory(new PropertyValueFactory<>("id")); //getId() from Part class
        aPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        partsInventory.setAll(inventory.getAllParts());
        partTable.setItems(partsInventory);
        partTable.refresh();

    }
}
