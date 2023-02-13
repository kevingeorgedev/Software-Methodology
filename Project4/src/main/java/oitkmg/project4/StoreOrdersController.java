package oitkmg.project4;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * JavaFX Controller for the Store Orders view.
 * @author Kevin George, Omar Talaat
 */
public class StoreOrdersController {
    /**
     * List view of orders.
     */
    @FXML
    private ListView<String> storeOrdersListView;

    /**
     * Total price of orders.
     */
    @FXML
    private TextField storeOrdersTotal;

    /**
     * Combo box to select order ID.
     */
    @FXML
    private ComboBox<Integer> storeOrdersID;

    /**
     * Initializes the Store Orders view.
     */
    public void initialize(){
        makeComboBox();
    }

    /**
     * Makes the combo box with order IDs
     */
    private void makeComboBox(){
        storeOrdersID.getItems().clear();
        int size = MainController.getOrders().getOrders().size();
        if(size == 0) return;
        for(int i = 1; i <= size; ++i){
            Order order = MainController.getOrders().getOrders().get(i-1);
            storeOrdersID.getItems().add(order.getId());
        }
    }

    /**
     * Store orders combo box is clicked.
     */
    @FXML
    private void storeOrdersIDClick(){
        NumberFormat nf = new DecimalFormat("#,###.00");
        storeOrdersListView.getItems().clear();
        int index = storeOrdersID.getSelectionModel().getSelectedIndex();
        if(index == -1) return;
        Order order = MainController.getOrders().getOrders().get(index);
        double subTotal = 0;
        for(Pizza pizza : order.getPizzas()){
            storeOrdersListView.getItems().add(MainController
                    .pizzaToString(pizza));
            subTotal += pizza.price();
        }
        double tax = subTotal * 0.06625;
        storeOrdersTotal.setText(nf.format(subTotal + tax));
    }

    /**
     * Handles store order cancelled button.
     */
    @FXML
    private void storeOrdersCancel(){
        int index = storeOrdersID.getSelectionModel().getSelectedIndex();
        if(index == -1){
            makeAlert(Alert.AlertType.ERROR, "Cancel Error", "Order not selected").show();
            return;
        }
        Order selected = MainController.getOrders().getOrders().get(index);
        MainController.getOrders().remove(selected);
        storeOrdersTotal.setText("");
        storeOrdersListView.getItems().clear();
        storeOrdersID.getItems().remove(index);
        storeOrdersID.setValue(null);
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
     * Exports the store orders to a text file.
     */
    @FXML
    private void export(){
        NumberFormat nf = new DecimalFormat("#,###.00");
        try (FileWriter outputStream = new
                FileWriter("src/StoreOrderExport.txt")) {
            StoreOrder orders = MainController.getOrders();
            if(orders.getOrders().size() == 0){
                makeAlert(Alert.AlertType.ERROR, "Export Error",
                        "There are currently no orders to export.").show();
                return;
            }
            for (Order order : orders.getOrders()) {
                double subTotal = 0;
                String p = "";
                for (Pizza pizza : order.getPizzas()) {
                    subTotal += pizza.price();
                    p += "\t" + MainController.pizzaToString(pizza) + "\n";
                }
                double tax = Double.parseDouble(nf
                        .format(subTotal * 0.006625));
                outputStream.write(String.format("Order ID and Total: %s\n%s",
                        order.getId(), p));
            }
            makeAlert(Alert.AlertType.CONFIRMATION, "Export Confirmation",
                    "Store orders export successful").show();
        } catch (Exception e) {
        }
    }
}
