package Controller;
/**
 * @author Gabriel Fernandez Patak
 */
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class to handle the Main form
 */
public class MainMenu implements Initializable {
    public TableView<Part> partTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partStock;
    public TableColumn partPrice;
    public TableView<Product> productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productStock;
    public TableColumn productPrice;
    public TextField searchPart;
    public TextField searchProduct;
    Inventory inventory;
    private final ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private final ObservableList<Product> productsInventory = FXCollections.observableArrayList();

    /**
     * The constructor initializes the inventory field
     * @param inventory the main inventory
     */
    public MainMenu(Inventory inventory){
        this.inventory = inventory;
    }

    /**
     * Closes the application
     * @param event
     */
    @FXML
    private void exitButton(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Filters objects from part table
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
                    alert.setTitle("Error");
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
     * Filters objects from product table
     * @param event
     */
    @FXML
    private void searchForProduct(ActionEvent event) {
        String searchText = searchProduct.getText().trim();
        if (!searchText.isEmpty()) {
            ObservableList<Product> foundProducts = inventory.lookupProduct(searchText);
            if(foundProducts.size() == 0){
                try {
                    int productId = Integer.parseInt(searchText);
                    Product foundProductId = inventory.lookupProduct(productId);
                    if (foundProductId != null) {
                        foundProducts.add(foundProductId);
                    }
                }catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not found");
                    alert.showAndWait();
                }
            }
            productTable.setItems(foundProducts);
            productTable.refresh();
        }else{
            productTable.setItems(productsInventory);
        }
    }

    /**
     * Calls Add Part controller to open form
     * @param event
     */
    @FXML
    private void addPartButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Add Part.fxml"));
            AddPart controller = new AddPart(inventory);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Calls Modify part controller to open form
     * @param event
     */
    @FXML
    private void modifyPartButton(ActionEvent event) {

        Part selectedPart = partTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null || partsInventory.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Modify Part.fxml"));
                ModifyPart controller = new ModifyPart(inventory, selectedPart);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }catch(Exception e){
                System.out.println(e);
            }
        }

    }

    /**
     * @param event Calls Add Product controller to open form
     */
    @FXML
    private void addProductButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Add Product.fxml"));
            AddProduct controller = new AddProduct(inventory);


            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Calls Modify Part controller to open form
     * @param event
     */
    @FXML
    private void modifyProductButton(ActionEvent event) {

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null || productsInventory.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product selected");
            alert.showAndWait();
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Modify Product.fxml"));
                ModifyProduct controller = new ModifyProduct(inventory, selectedProduct);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Removes part from part table
     * @param event
     */
    @FXML
    private void deletePartButton(ActionEvent event){
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null || partsInventory.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel");
            alert.setHeaderText("Delete part?");
            alert.setContentText("Ok to continue");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                inventory.deletePart(selectedPart);
                partsInventory.remove(selectedPart);
                partTable.refresh();
            }
        }
    }

    /**
     * Removes product from product table if it does not have an associated part to it
     * @param event
     */
    @FXML
    private void deleteProductButton(ActionEvent event){
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null || productsInventory.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product selected");
            alert.showAndWait();
        }else {
            if(selectedProduct.getAllAssociatedParts().size()==0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancel");
                alert.setHeaderText("Delete product?");
                alert.setContentText("Ok to continue");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    inventory.deleteProduct(selectedProduct);
                    productsInventory.remove(selectedProduct);
                    productTable.refresh();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The product has a part associated with it.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Populates information for the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsInventory.setAll(inventory.getAllParts());
        partTable.setItems(partsInventory);
        partTable.refresh();

        productsInventory.setAll(inventory.getAllProducts());
        productTable.setItems(productsInventory);
        productTable.refresh();




        partId.setCellValueFactory(new PropertyValueFactory<>("id")); //getId() from Part class
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));




    }
}
