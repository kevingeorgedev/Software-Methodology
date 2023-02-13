package oitkmg.project4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * JavaFX Controller for the current order view.
 * @author Kevin George, Omar Talaat
 */
public class CurrentOrderController {

    /**
     * List view of the pizzas in the order.
     */
    @FXML
    private ListView<String> currentOrderListView;
    /**
     * Text field for current order view.
     */
    @FXML
    private TextField currentOrderID, currentOrderSubtotal,
            currentOrderTotal, currentOrderSalesTax;

    /**
     * Initializes the Current Order Controller.
     */
    public void initialize(){
        NumberFormat nf = new DecimalFormat("#,###.00");
        Order currentOrder = MainController.getCurrOrder();
        currentOrderID.setText(String.valueOf(currentOrder.getId()));
        double subTotal = 0;
        for(Pizza pizza : currentOrder.getPizzas()){
            currentOrderListView.getItems().add(MainController
                    .pizzaToString(pizza));
            subTotal += pizza.price();
        }
        String tax = nf.format(subTotal * 0.06625);
        currentOrderSubtotal.setText(nf.format(subTotal));
        currentOrderSalesTax.setText(tax);
        currentOrderTotal.setText(nf.format(subTotal +
                Double.parseDouble(tax)));
    }

    /**
     * Handles clear order button pressed.
     */
    @FXML
    private void onClearOrderButton(){
        if(currentOrderListView.getItems().size() == 0){
            makeAlert(Alert.AlertType.ERROR, "Clear Pizza Error",
                    "No pizzas in order.").show();
        } else{
            currentOrderListView.getItems().clear();
            MainController.resetOrder();
            currentOrderSubtotal.setText(".00");
            currentOrderTotal.setText(".00");
            currentOrderSalesTax.setText(".00");
            Order currentOrder = MainController.getCurrOrder();
            currentOrderID.setText(String.valueOf(currentOrder.getId()));
        }

    }

    /**
     * Handles place order button.
     */
    @FXML
    private void onCurrentPizzaPlaceOrder(){
        Order currentOrder = MainController.getCurrOrder();
        if(currentOrder.getPizzas().size() == 0){
            makeAlert(Alert.AlertType.ERROR, "Place order error",
                    "No pizzas added to order yet.").show();
        }
        else{
            MainController.getOrders().add(currentOrder);
            onClearOrderButton();
        }
    }

    /**
     * Handles remove pizza button.
     */
    @FXML
    private void onCurrentOrderRemovePizza(){
        Order currentOrder = MainController.getCurrOrder();
        if(currentOrder.getPizzas().size() == 0){
            makeAlert(Alert.AlertType.ERROR, "Remove pizza error.",
                    "You have no pizzas added.").show();
        } else if(currentOrderListView.getSelectionModel()
                .getSelectedItem() == null){
            makeAlert(Alert.AlertType.ERROR, "Remove pizza error.",
                    "Select a pizza to remove.").show();
        } else{
            boolean removed = currentOrder.remove(currentOrderListView
                    .getSelectionModel().getSelectedItem());
            if(removed){
                currentOrderListView.getItems().remove(currentOrderListView
                        .getSelectionModel().getSelectedIndex());
                makeAlert(Alert.AlertType.CONFIRMATION, "Confirmation",
                        "Pizza Removed.").show();
                resetPrices();
            }
        }
    }

    /**
     * Creates an alert.
     * @param type Alert Type
     * @param title title of the alert
     * @param header header of the alert
     * @return the new Alert
     */
    private Alert makeAlert(Alert.AlertType type, String title, String header){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert;
    }

    /**
     * Resets the prices displayed.
     */
    private void resetPrices(){
        NumberFormat nf = new DecimalFormat("#,###.00");
        Order currentOrder = MainController.getCurrOrder();
        double subTotal = 0;
        for(Pizza pizza : currentOrder.getPizzas()){
            subTotal += pizza.price();
        }
        String tax = nf.format(subTotal * 0.06625);
        currentOrderSubtotal.setText(nf.format(subTotal));
        currentOrderSalesTax.setText(tax);
        currentOrderTotal.setText(nf.format(subTotal +
                Double.parseDouble(tax)));
    }
}
