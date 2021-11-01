package Model;

/**
 * @author Gabriel Fernandez Patak
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor to create a product object
     * @param id the product id
     * @param name the product name
     * @param price the product price
     * @param stock the product stock
     * @param min the product min stock
     * @param max the product max stock
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * @return product id
     */
    public int getId(){return id;}

    /**
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * @return product name
     */
    public String getName(){return name;}

    /**
     * @param name the name to set
     */
    public void setName(String name){this.name = name;}

    /**
     * @return the product price
     */
    public double getPrice(){return price;}

    /**
     * @param price the price to set
     */
    public void setPrice(double price){this.price = price;}

    /**
     * @return the product stock
     */
    public int getStock(){return stock;}

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock){this.stock = stock;}

    /**
     * @return the product min stock
     */
    public int getMin(){return min;}

    /**
     * @param min the min stock to set
     */
    public void setMin(int min){this.min = min;}

    /**
     * @return the product max stock
     */
    public int getMax(){return max;}

    /**
     * @param max the max stock to set
     */
    public void setMax(int max){this.max = max;}

    /**
     * @param part the part to add to the list of associated parts
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart the part to delete from associated parts list
     * @return true if part deleted else false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        int i;
        for (i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * @return the list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){return associatedParts;}







}
