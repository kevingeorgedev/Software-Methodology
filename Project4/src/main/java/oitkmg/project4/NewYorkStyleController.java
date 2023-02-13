package oitkmg.project4;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * JavaFX Controller for the New York Pizza View.
 * @author Kevin George, Omar Talaat
 */
public class NewYorkStyleController {

    /**
     * New York Flavor combo box.
     */
    @FXML
    private ComboBox<String> nyFlavor;
    /**
     * New York Pizza image.
     */
    @FXML
    private ImageView nyPizzaImg;
    /**
     * New York pizza buttons.
     */
    @FXML
    private Button addToppingNY, removeToppingNY, addToOrderNY;
    /**
     * New York pizza Size button toggle group.
     */
    @FXML
    private ToggleGroup sizeButtonGroup2;
    /**
     * New York pizza text fields.
     */
    @FXML
    private TextField nyCrust, totalText2;
    /**
     * List view for toppings.
     */
    @FXML
    private ListView<String> toppingsNY, selectedToppingsNY;

    /**
     * Initializes the New York style Controller.
     */
    public void initialize(){
        ArrayList<String> flavors = new ArrayList<>();
        flavors.add("Build_your_own");
        flavors.add("BBQ_Chicken");
        flavors.add("Deluxe");
        flavors.add("Meatzza");
        toppingsNY.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham", "Pineapple", "Bacon", "Prosciutto");
        try {
            InputStream nyImg = new FileInputStream("src/images/NYPizza.jpg");
            nyPizzaImg.setImage(new Image(nyImg));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        nyFlavor.setItems(FXCollections.observableList(flavors));
        nyFlavor.setValue("Build_your_own");
        addToOrderNY.setOnAction(event -> {
            addToOrderNYClick();
            Alert addToOrderAlert = new Alert(Alert.AlertType.INFORMATION);
            addToOrderAlert.setTitle("Pizza Confirmation");
            addToOrderAlert.setHeaderText("New York style added to order!");
            addToOrderAlert.show();
        });
        nyFlavorSelection();
        changeTotal();
    }

    /**
     * Handles the new york flavor combo box action.
     */
    @FXML
    private void nyFlavorSelection(){
        String selection = nyFlavor.getSelectionModel().getSelectedItem();
        switch(selection){
            case("Deluxe"):
                String[] toppings = {"Sausage", "Pepperoni",
                        "Green_Pepper", "Onion", "Mushroom"};
                nyCrust.setText("Brooklyn");
                removeToppings(toppings);
                break;
            case("BBQ_Chicken"):
                String[] toppings2 = {"BBQ_Chicken", "Green_Pepper",
                        "Provolone", "Cheddar"};
                nyCrust.setText("Thin");
                removeToppings(toppings2);
                break;
            case("Meatzza"):
                String[] toppings3 = {"Sausage", "Pepperoni", "Beef",
                        "Ham"};
                nyCrust.setText("Hand_tossed");
                removeToppings(toppings3);
                break;
            case("Build_your_own"):
                nyCrust.setText("Hand_tossed");
                resetToppings();
                break;
            default:
                return;
        }
        changeTotal();
    }

    /**
     * Changes the total price for the pizza.
     */
    private void changeTotal(){
        String size = ((RadioButton) sizeButtonGroup2
                .getSelectedToggle()).getText().toUpperCase();
        if(nyFlavor.getSelectionModel().getSelectedItem()
                .equals("Build_your_own")){
            double tops = selectedToppingsNY.getItems().size() * 1.59;
            getTot(8.99, size, tops);
        } else{
            getTot(12.99, size, 0);
        }
    }

    /**
     * Updates the total for the pizza.
     * @param total total price of the pizza.
     * @param size size of pizza.
     * @param tops price of toppings.
     */
    private void getTot(double total, String size, double tops){
        NumberFormat nf = new DecimalFormat("#,###.00");
        if(size.equals("SMALL")){
            String tot = nf.format(total + nyFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText2.setText(String.valueOf(tot));
        } else if(size.equals("MEDIUM")){
            String tot = nf.format(total + 2 + nyFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText2.setText(String.valueOf(tot));
        } else{
            String tot = nf.format(total + 4 + nyFlavor
                    .getSelectionModel().getSelectedIndex() + tops);
            totalText2.setText(String.valueOf(tot));
        }
    }

    /**
     * Resets the toppings.
     */
    private void resetToppings(){
        toppingsNY.getItems().clear();
        toppingsNY.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham", "Pineapple", "Bacon", "Prosciutto");
        selectedToppingsNY.getItems().clear();
        addToppingNY.setDisable(false);
        removeToppingNY.setDisable(false);
        toppingsNY.setDisable(false);
    }

    /**
     * Selects toppings from argument toppings and disables corresponding
     * list views and buttons.
     * @param toppings Toppings to be removed.
     */
    private void removeToppings(String[] toppings){
        //toppingsChicago.getItems().clear();
        toppingsNY.getItems().clear();
        toppingsNY.getItems().addAll("Sausage", "Pepperoni",
                "Green_Pepper", "Onion", "Mushroom", "Provolone", "Cheddar",
                "BBQ_Chicken", "Beef", "Ham", "Pineapple", "Bacon", "Prosciutto");
        toppingsNY.setDisable(true);
        selectedToppingsNY.getItems().clear();
        for(String topping : toppings){
            selectedToppingsNY.getItems().add(topping);
        }
        addToppingNY.setDisable(true);
        removeToppingNY.setDisable(true);
    }

    /**
     * Handles the new york size button press.
     */
    @FXML
    private void nySizeButtonPressed(){
        changeTotal();
    }

    /**
     * Adds pizza to order.
     */
    private void addToOrderNYClick(){
        PizzaFactory pizzaFactory = new ChicagoPizza();
        String flavor;
        try{    flavor = nyFlavor.getSelectionModel().getSelectedItem();   }
        catch (Exception e){  return; }
        switch (flavor) {
            case ("Deluxe") -> {
                Pizza pizza = pizzaFactory.createDeluxe();
                pizza.setCrust(Crust.valueOf(nyCrust.getText().toUpperCase()));
                setSize(pizza);
                pizza.setToppings();
                addToppings(pizza);
                MainController.addToCurrentOrder(pizza);
            }
            case ("BBQ_Chicken") -> {
                Pizza pizza2 = pizzaFactory.createBBQChicken();
                pizza2.setCrust(Crust.valueOf(nyCrust.getText().toUpperCase()));
                setSize(pizza2);
                pizza2.setToppings();
                addToppings(pizza2);
                MainController.addToCurrentOrder(pizza2);
            }
            case ("Meatzza") -> {
                Pizza pizza3 = pizzaFactory.createMeatzza();
                pizza3.setCrust(Crust.valueOf(nyCrust.getText().toUpperCase()));
                setSize(pizza3);
                pizza3.setToppings();
                addToppings(pizza3);
                MainController.addToCurrentOrder(pizza3);
            }
            case ("Build_your_own") -> {
                Pizza pizza4 = pizzaFactory.createBuildYourOwn();
                pizza4.setCrust(Crust.valueOf(nyCrust.getText().toUpperCase()));
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
        pizza.setSize(Size.valueOf(((RadioButton) sizeButtonGroup2
                .getSelectedToggle()).getText().toUpperCase()));
    }

    /**
     * Adds toppings to pizza.
     * @param pizza Pizza whose toppings will be added to.
     */
    private void addToppings(Pizza pizza){
        for (String topping : selectedToppingsNY.getItems())
            pizza.add(Topping.valueOf(topping.toUpperCase()));
    }

    /**
     * Handles add topping click. Add selected topping to selected
     * toppings. Won't work if toppings count is seven.
     */
    @FXML
    private void addToppingNYClick(){
        int index = toppingsNY.getSelectionModel().getSelectedIndex();
        if(index != -1){
            if(selectedToppingsNY.getItems().size() > 6){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add topping error.");
                alert.setHeaderText("You can't add more than 7 toppings.");
                alert.show();
            } else {
                selectedToppingsNY.getItems().add(toppingsNY.getItems().remove(index));
                double total = Double.parseDouble(totalText2.getText());
                NumberFormat nf = new DecimalFormat("#,###.00");
                total += 1.59;
                totalText2.setText(nf.format(total));
            }
        }
    }

    /**
     * Handles remove toppings click.
     */
    @FXML
    private void removeToppingNYClick(){
        int index = selectedToppingsNY.getSelectionModel().getSelectedIndex();
        if(index != -1){
            toppingsNY.getItems().add(selectedToppingsNY.getItems().remove(index));
            double total = Double.parseDouble(totalText2.getText());
            NumberFormat nf = new DecimalFormat("#,###.00");
            total -= 1.59;
            totalText2.setText(nf.format(total));
        }
    }

}
