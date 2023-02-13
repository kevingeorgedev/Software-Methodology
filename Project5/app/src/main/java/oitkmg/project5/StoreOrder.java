package oitkmg.project4;

import java.util.ArrayList;

/**
 * Store Order holds the orders currently in the store.
 * @author Kevin George, Omar Talaat
 */
public class StoreOrder implements Customizable{
    /**
     * Orders in the store order.
     */
    private ArrayList<Order> orders;
    /**
     * Number of orders.
     */
    private int size;

    /**
     * Initializes the store order.
     */
    public StoreOrder(){
        this.orders = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Gets the orders in the store.
     * @return an ArrayList representing the orders in the store.
     */
    public ArrayList<Order> getOrders(){
        return orders;
    }

    /**
     * Adds an order to the store order.
     * @param obj Order to be added.
     * @return true if the order was added; false otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if(obj instanceof Order order){
            if(orderExists(order) == -1) {
                orders.add((order));
                size++;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an order from the store order.
     * @param obj Order to be removed.
     * @return true if order was removed; false otherwise.
     */
    @Override
    public boolean remove(Object obj) {
        if(obj instanceof Order order){
            int index = orderExists(order);
            if(index != -1){
                orders.remove(index);
            }
        }
        return false;
    }

    /**
     * Gets index of an order in store order.
     * @param order Order to be searched for.
     * @return an integer representing the index of the order.
     */
    public int orderExists(Order order){
        for(int i = 0; i < orders.size(); ++i){
            if(orders.get(i).getId() == order.getId()) return i;
        }
        return -1;
    }

    /**
     * Gets the nubmer of orders.
     * @return an integer representing the number of orders.
     */
    public int getSize() {
        return size;
    }
}
