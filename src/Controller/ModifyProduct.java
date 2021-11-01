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
import java.util.ResourceBundle;

/**
 * Class to handle the Modify Product form
 */
public class ModifyProduct implements Initializable {

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
    Product modProduct;
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
     * Initializes inventory and product to be modified
     * @param inventory the main inventory
     * @param modProduct the product being modified
     */
    public ModifyProduct(Inventory inventory, Product modProduct) {
        this.inventory = inventory;
        this.modProduct = modProduct;
    }

    /**
     * This method uses the input fields to update the product object
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

            for(int i = 0; i < associatedInventory.size(); i++){
                savedProduct.addAssociatedPart(associatedInventory.get(i));
            }
            inventory.updateProduct(Integer.parseInt(id.getText()), savedProduct );
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
     * Filters product table based on input
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
     * Copies part from top table to bottom table and adds it to associated parts from product
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
     * Removes selected part from bottom table and from associated list from product
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
     * Calls mainMenu method if user confirms alert
     * @param event
     */
    @FXML
    private void cancelModifyProduct(ActionEvent event) {
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
     * Populates tables and fields. It also sets table properties.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.name.setText(modProduct.getName());
        this.id.setText((Integer.toString(modProduct.getId())));
        this.stock.setText((Integer.toString(modProduct.getStock())));
        this.price.setText((Double.toString(modProduct.getPrice())));
        this.min.setText((Integer.toString(modProduct.getMin())));
        this.max.setText((Integer.toString(modProduct.getMax())));


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

        associatedInventory.setAll(modProduct.getAllAssociatedParts());
        associatedTable.setItems(associatedInventory);
        associatedTable.refresh();

    }
}
