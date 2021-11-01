package Main;
/**
 * @author Gabriel Fernandez Patak
 */
import Controller.MainMenu;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to run application and default data
 */
public class Main extends Application {

    /**
     * Javadoc folder is located in "..\C482-GFP\javadoc".
     * @param args Java's main method requires this string.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initiates JavaFX and loads a primary stage
     * @param primaryStage primary stage object
     * @throws Exception if method can't start throws exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory inventory = new Inventory();
        addData(inventory);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Main.fxml"));
        MainMenu controller = new MainMenu(inventory);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("IMS");
        primaryStage.show();
    }

    /**
     * Adds test data to the program
     * @param inventory main inventory
     */
    public void addData(Inventory inventory){
        inventory.addPart(new InHouse(1, "Brakes",49.99, 10, 1, 20 ,1){});
        inventory.addPart(new InHouse(2, "Wheel",40.99, 16, 2, 30,2 ){});
        inventory.addPart(new InHouse(3, "Window",87.75, 11, 7, 20,5 ){});
        inventory.addPart(new InHouse(4, "Rear Mirror",50, 13, 3, 25,6 ){});
        inventory.addPart(new Outsourced(10, "Seat",249.99, 10, 1, 20 ,"Tesla"){});
        inventory.addPart(new Outsourced(14, "Engine",1999.99, 10, 1, 100 ,"Tesla"){});
        inventory.addPart(new Outsourced(15, "Transmission",300, 10, 1, 20 ,"Tesla"){});

        inventory.addProduct(new Product(1, "Giant Bike", 299.99, 5, 1, 20));
        inventory.addProduct(new Product(2, "Tricycle", 99.99, 3, 1, 20));
        inventory.addProduct(new Product(3, "Tesla", 30000, 5, 1, 20));
        inventory.addProduct(new Product(4, "Honda", 2948.59, 3, 1, 20));
        inventory.addProduct(new Product(5, "Toyota", 9999.99, 5, 1, 20));
        inventory.addProduct(new Product(6, "Ford", 5000.99, 3, 1, 20));
    }
}
