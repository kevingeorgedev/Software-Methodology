package oitkmg.project4;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * JavaFX Controller for the Chicago Pizza View.
 * @author Kevin George, Omar Talaat
 */
public class ChicagoStyleController {

    /**
     * Chicago Flavor combo box.
     */
    @FXML
    private ComboBox<String> chicagoFlavor;

    /**
     * Chicago Pizza image.
     */
    @FXML
    private ImageView chicagoImg;

    /**
     * List view for toppings.
     */
    @FXML
    private ListView<String> toppingsChicago, selectedToppingsChicago;

    /**
     * Chicago pizza buttons.
     */
    @FXML
    private Button addToppingChicago, removeToppingChicago, addToOrderChicago;

    /**
     * Chicago pizza text fields.
     */
    @FXML
    private TextField chicagoCrust, totalText;

    /**
     * Chicago pizza Size button toggle group.
     */
    @FXML
    private ToggleGroup sizeButtonGroup;

    /**
     * Initializes the Chicago Style Controller.
     */
    public void initialize(){
        ArrayList<String> flavors = new ArrayList<>();
        flavors.add("Build_your_own");
        flavors.add("BBQ_Chicken");
        flavors.add("Deluxe");
        flavors.add("Meatzza");
        toppingsChicago.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham");
        try {
            InputStream nyImg = new FileInputStream
                    ("src/images/chicagopizza.jpg");
            chicagoImg.setImage(new Image(nyImg));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        chicagoFlavor.setItems(FXCollections.observableList(flavors));
        chicagoFlavor.setValue("Build_your_own");
        addToOrderChicago.setOnAction(event -> {
            addToOrderChicagoClick();
            Alert addToOrderAlert = new Alert(Alert.AlertType.INFORMATION);
            addToOrderAlert.setTitle("Pizza Confirmation");
            addToOrderAlert.setHeaderText("Chicago style added to order!");
            addToOrderAlert.show();
        });
        chicagoFlavorSelection();
        changeTotal();
    }

    /**
     * Handles chicago flavor combo box action.
     */
    @FXML
    private void chicagoFlavorSelection(){
        String selection = chicagoFlavor.getSelectionModel()
                .getSelectedItem();
        switch(selection){
            case("Deluxe"):
                String[] toppings = {"Sausage", "Pepperoni",
                        "Green_Pepper", "Onion", "Mushroom"};
                chicagoCrust.setText("Deep_Dish");
                removeToppings(toppings);
                break;
            case("BBQ_Chicken"):
                String[] toppings2 = {"BBQ_Chicken", "Green_Pepper",
                        "Provolone", "Cheddar"};
                chicagoCrust.setText("Pan");
                removeToppings(toppings2);
                break;
            case("Meatzza"):
                String[] toppings3 = {"Sausage", "Pepperoni", "Beef",
                        "Ham"};
                chicagoCrust.setText("Stuffed");
                removeToppings(toppings3);
                break;
            case("Build_your_own"):
                chicagoCrust.setText("Pan");
                resetToppings();
                break;
            default:
                return;
        }
        changeTotal();
    }

    /**
     * Handles the chicago size button press.
     */
    @FXML
    private void chicagoSizeButtonPressed(){
        changeTotal();
    }

    /**
     * Changes the total price for the pizza.
     */
    private void changeTotal(){
        String size = ((RadioButton) sizeButtonGroup
                .getSelectedToggle()).getText().toUpperCase();
        if(chicagoFlavor.getSelectionModel().getSelectedItem()
                .equals("Build_your_own")){
            double tops = selectedToppingsChicago.getItems().size() * 1.59;
            getTot(8.99, size, tops);
        } else{
            getTot(12.99, size, 0);
        }
    }

    /**
     * Updates the total for the pizza.
     * @param total total price of pizza.
     * @param size size of pizza.
     * @param tops price of toppings.
     */
    private void getTot(double total, String size, double tops){
        NumberFormat nf = new DecimalFormat("#,###.00");
        if(size.equals("SMALL")){
            String tot = nf.format(total + chicagoFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText.setText(String.valueOf(tot));
        } else if(size.equals("MEDIUM")){
            String tot = nf.format(total + 2 + chicagoFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText.setText(String.valueOf(tot));
        } else{
            String tot = nf.format(total + 4 + chicagoFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText.setText(String.valueOf(tot));
        }
    }

    /**
     * Selects toppings from argument toppings and disables corresponding
     * list views and buttons.
     * @param toppings toppings to be selected.
     */
    private void removeToppings(String[] toppings){
        toppingsChicago.getItems().clear();
        toppingsChicago.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham", "Bacon", "Pineapple", "Prosciutto");
        toppingsChicago.setDisable(true);
        selectedToppingsChicago.getItems().clear();
        for(String topping : toppings){
            selectedToppingsChicago.getItems().add(topping);
        }
        addToppingChicago.setDisable(true);
        removeToppingChicago.setDisable(true);
    }

    /**
     * Resets the toppings.
     */
    private void resetToppings(){
        toppingsChicago.getItems().clear();
        toppingsChicago.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham", "Pineapple", "Bacon", "Prosciutto");
        selectedToppingsChicago.getItems().clear();
        addToppingChicago.setDisable(false);
        removeToppingChicago.setDisable(false);
        toppingsChicago.setDisable(false);
    }

    /**
     * Handles add topping click. Add selected topping to selected
     * toppings. Won't work if toppings count is seven.
     */
    @FXML
    private void addToppingChicagoClick(){
        int index = toppingsChicago.getSelectionModel().getSelectedIndex();
        if(index != -1){
            if(selectedToppingsChicago.getItems().size() > 6){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add topping error.");
                alert.setHeaderText("You can't add more than 7 toppings.");
                alert.show();
            }
            else{
                selectedToppingsChicago.getItems().add(toppingsChicago
                        .getItems().remove(index));
                double total = Double.parseDouble(totalText.getText());
                NumberFormat nf = new DecimalFormat("#,###.00");
                total += 1.59;
                totalText.setText(nf.format(total));
            }
        }
    }

    /**
     * Handles remove toppings click.
     */
    @FXML
    private void removeToppingChicagoClick(){
        int index = selectedToppingsChicago.getSelectionModel()
                .getSelectedIndex();
        if(index != -1){
            toppingsChicago.getItems().add(selectedToppingsChicago
                    .getItems().remove(index));
            double total = Double.parseDouble(totalText.getText());
            NumberFormat nf = new DecimalFormat("#,###.00");
            total -= 1.59;
            totalText.setText(nf.format(total));
        }
    }

    /**
     * Adds pizza to order.
     */
    private void addToOrderChicagoClick(){
        PizzaFactory pizzaFactory = new ChicagoPizza();
        String flavor;
        try{    flavor = chicagoFlavor.getSelectionModel().getSelectedItem();   }
        catch (Exception e){  return; }
        switch (flavor) {
            case ("Deluxe") -> {
                Pizza pizza = pizzaFactory.createDeluxe();
                pizza.setCrust(Crust.valueOf(chicagoCrust.getText().toUpperCase()));
                setSize(pizza);
                pizza.setToppings();
                addToppings(pizza);
                MainController.addToCurrentOrder(pizza);
            }
            case ("BBQ_Chicken") -> {
                Pizza pizza2 = pizzaFactory.createBBQChicken();
                pizza2.setCrust(Crust.valueOf(chicagoCrust.getText().toUpperCase()));
                setSize(pizza2);
                pizza2.setToppings();
                addToppings(pizza2);
                MainController.addToCurrentOrder(pizza2);
            }
            case ("Meatzza") -> {
                Pizza pizza3 = pizzaFactory.createMeatzza();
                pizza3.setCrust(Crust.valueOf(chicagoCrust.getText().toUpperCase()));
                setSize(pizza3);
                pizza3.setToppings();
                addToppings(pizza3);
                MainController.addToCurrentOrder(pizza3);
            }
            case ("Build_your_own") -> {
                Pizza pizza4 = pizzaFactory.createBuildYourOwn();
                pizza4.setCrust(Crust.valueOf(chicagoCrust.getText().toUpperCase()));
                setSize(pizza4);
                pizza4.setToppings();
                addToppings(pizza4);
                MainController.addToCurrentOrder(pizza4);
            }
        }
    }

    /**
     * Sets the size of the pizza.
     * @param pizza Pizza whose size will be set.
     */
    private void setSize(Pizza pizza){
        pizza.setSize(Size.valueOf(((RadioButton) sizeButtonGroup
                .getSelectedToggle()).getText().toUpperCase()));
    }

    /**
     * Adds toppings to pizza.
     * @param pizza Pizza whose toppings will be added to.
     */
    private void addToppings(Pizza pizza){
        for (String topping : selectedToppingsChicago.getItems())
            pizza.add(Topping.valueOf(topping.toUpperCase()));
    }
}
