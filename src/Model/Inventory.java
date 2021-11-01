package Model;
/**
 * @author Gabriel Fernandez Patak
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory{

    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method to add part object to Parts table
     * @param newPart the part to be added
     */
    public void addPart(Part newPart){
        if (newPart != null){
            this.allParts.add(newPart);
        }
    }

    /**
     * Method to add product object to Products table
     * @param newProduct the product to be added
     */
    public void addProduct(Product newProduct){
        if (newProduct != null){
            this.allProducts.add(newProduct);
        }
    }

    /**
     * Method that returns a part object searched by ID
     * @param partId the part id
     * @return Part object
     */
    public Part lookupPart(int partId){
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }

        }
        return null;
    }

    /**
     * Method that return a product object searched by ID
     * @param productId the product id
     * @return Product object
     */
    public Product lookupProduct(int productId){
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productId) {
                    return allProducts.get(i);
                }
            }

        }
        return null;
    }

    /**
     * Method that returns a part list search by name
     * @param partName the part name
     * @return Part List
     */
    public ObservableList<Part> lookupPart(String partName){
        if (!allParts.isEmpty()) {
            ObservableList partsList = FXCollections.observableArrayList();
            for (Part p : allParts) {
                if (p.getName().contains(partName)) {
                    partsList.add(p);
                }
            }

            return partsList;
        }
        return null;
    }

    /**
     * Method that returns a product list search by name
     * @param productName the product name
     * @return Product List
     */
    public ObservableList<Product> lookupProduct(String productName){
        if (!allProducts.isEmpty()) {
            ObservableList productsList = FXCollections.observableArrayList();
            for (Product p : allProducts) {
                if (p.getName().contains(productName)) {
                    productsList.add(p);
                }
            }
            return productsList;
        }
        return null;
    }

    /**
     * Method that updates part object based on index and product
     * @param index the index of the part
     * @param selectedPart the part object
     */
    public void updatePart(int index , Part selectedPart){
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == index) {
                allParts.set(i, selectedPart);
                break;
            }
        }
        return;


    }

    /**
     * Method that updates product object based on index and product
     * @param index the product id
     * @param selectedProduct the product to be updated
     */
    public void updateProduct(int index, Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == index) {
                allProducts.set(i, selectedProduct);
                break;
            }
        }
        return;
    }

    /**
     * Method to delete part object from part list
     * @param selectedPart the part object
     * @return True if object was deleted else false
     */
    public boolean deletePart(Part selectedPart){
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param selectedProduct the product object
     * @return True if object was deleted else false
     */
    public boolean deleteProduct(Product selectedProduct){
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * @return parts list
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return products list
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}