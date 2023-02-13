package oitkmg.project4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * JavaFX Controller for the Main View
 * @author Kevin George, Omar Talaat
 */
public class MainController {

    /**
     * Images for the button options.
     */
    @FXML
    private ImageView chicagoImage, nyImage, storeOrderImg, currOrderImg;
    /**
     * Order object representing the current order.
     */
    private static Order currOrder;
    /**
     * StoreOrder object which holds all orders.
     */
    private static StoreOrder orders;

    /**
     * Initializes the Main Controller.
     */
    public void initialize(){
        orders = new StoreOrder();
        currOrder = new Order();
        try {
            InputStream chicagoImg = new
                    FileInputStream("src/images/chicagopizza.jpg");
            InputStream nyImg = new
                    FileInputStream("src/images/NYPizza.jpg");
            InputStream storeOrdersImg = new
                    FileInputStream("src/images/PizzaStore.png");
            InputStream currentOrderImg = new
                    FileInputStream("src/images/ShoppinCart.jpg");
            chicagoImage.setImage(new Image(chicagoImg));
            nyImage.setImage(new Image(nyImg));
            storeOrderImg.setImage(new Image(storeOrdersImg));
            currOrderImg.setImage(new Image(currentOrderImg));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the StoreOrder.
     * @return a StoreOrder which represents the current store order.
     */
    public static StoreOrder getOrders(){
        return orders;
    }

    /**
     * Resets the current order to a new one.
     */
    public static void resetOrder(){
        currOrder = new Order();
    }

    /**
     * Gets the current order.
     * @return an Order representing the current order.
     */
    public static Order getCurrOrder(){
        return currOrder;
    }

    /**
     * Adds a pizza to the current order.
     * @param pizza Pizza to be added.
     */
    public static void addToCurrentOrder(Pizza pizza){
        currOrder.add(pizza);
    }

    /**
     * Starts the chicago style view.
     * @throws IOException if view is not found.
     */
    @FXML
    private void chicagoStyleClick() throws IOException {
        start("ChicagoStyleView", "Chicago Pizza");
    }

    /**
     * Starts the new york style view.
     * @throws IOException if view is not found.
     */
    @FXML
    private void newYorkStyleClick() throws IOException{
        start("NewYorkStyleView", "New York Pizza");
    }

    /**
     * Starts the store order view.
     * @throws IOException if view is not found.
     */
    @FXML
    private void storeOrdersClick() throws IOException{
        start("StoreOrdersView", "Store Orders");
    }

    /**
     * Starts the current order view.
     * @throws IOException if view is not found.
     */
    @FXML
    private void currentOrderClick() throws IOException{
        start("CurrentOrderView", "Current Order");
    }

    /**
     * Creates a string of a pizza.
     * @param pizza Pizza to be represented as a string.
     * @return a String representing a pizza object.
     */
    public static String pizzaToString(Pizza pizza){
        NumberFormat nf = new DecimalFormat("#,###.00");
        String flavor = "";
        if(pizza instanceof Deluxe) flavor = "Deluxe";
        else if(pizza instanceof Meatzza) flavor = "Meatzza";
        else if(pizza instanceof BBQChicken) flavor = "BBQ_Chicken";
        else if(pizza instanceof BuildYourOwn) flavor = "Build_Your_Own";
        //else return null;
        String type;
        if(pizza.getCrust().equals(Crust.DEEP_DISH) ||
                pizza.getCrust().equals(Crust.PAN) ||
                pizza.getCrust().equals(Crust.STUFFED)){
                type = "Chicago Style";
        } else{
            type = "New York Style";
        }
        String output = String.format("%s (%s - %s), ", flavor, type,
                pizza.getCrust().getCrust());
        for(Topping topping : pizza.getToppings()){
            output += topping.getTopping() + ", ";
        }
        output += pizza.getSize().size() + ", $" + nf.format(pizza.price());
        return output;
    }

    /**
     * Starts a view.
     * @param filename a string representing the filename
     * @param title a string representing the title.
     * @throws IOException if view is not found.
     */
    private void start(String filename, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RunPizza.class.getResource(filename + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}