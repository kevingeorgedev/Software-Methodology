package oitkmg.project4;

import java.util.ArrayList;

/**
 * Order for the pizza store. Holds pizzas in the order.
 * @author Kevin George, Omar Talaat
 */
public class Order implements Customizable{

    /**
     * List of pizzas in this order.
     */
    private final ArrayList<Pizza> pizzas;
    /**
     * Number of pizzas in order.
     */
    private final int id;

    /**
     * Initializes an order.
     */
    public Order(){
        this.pizzas = new ArrayList<>();
        this.id = MainController.getOrders().getSize() + 1;
    }

    /**
     * Gets the pizzas in order.
     * @return an ArrayList representing the pizzas in this order.
     */
    public ArrayList<Pizza> getPizzas(){
        return pizzas;
    }

    /**
     * Adds a pizza to order.
     * @param obj Pizza to be added.
     * @return true if pizza is added; false otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if(obj instanceof Pizza pizza){
            pizzas.add(pizza);
            return true;
        }
        return false;
    }

    /**
     * Removes a pizza from the order.
     * @param obj Pizza to be removed.
     * @return true if pizza was removed; false otherwise.
     */
    @Override
    public boolean remove(Object obj) {
        if(obj instanceof String pizza){
            for(int i = 0; i < pizzas.size(); ++i){
                if(MainController.pizzaToString(pizzas.get(i))
                        .equals(pizza)) {
                    pizzas.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the ID of the order.
     * @return an integer representing the ID of this order.
     */
    public int getId() {
        return id;
    }
}
