package oitkmg.project4;

import java.util.ArrayList;

/**
 * Abstract class Pizza that all pizza types must extend.
 * @author Kevin George, Omar Talaat
 */
public abstract class Pizza implements Customizable {
    /**
     * Toppings of a pizza.
     */
    private ArrayList<Topping> toppings;
    /**
     * Crust of a pizza.
     */
    private Crust crust;
    /**
     * Size of a pizza.
     */
    private Size size;

    /**
     * Makes the price of a pizza.
     * @return a double representing the price of a pizza.
     */
    public abstract double price();

    /**
     * Sets the crust of a pizza.
     * @param crust Crust to be set.
     */
    public void setCrust(Crust crust){
        this.crust = crust;
    }

    /**
     * Sets the size of a pizza.
     * @param size Size to be set.
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * Gets the crust of a pizza.
     * @return Crust of a pizza.
     */
    public Crust getCrust(){
        return crust;
    }

    /**
     * Gets the size of a pizza.
     * @return Size of a pizza.
     */
    public Size getSize(){
        return size;
    }

    /**
     * Adds topping to a pizza.
     * @param obj Topping to be added.
     * @return true if topping was added; false otherwise.
     */
    @Override
    public boolean add(Object obj){
        if(obj instanceof Topping topping){
            toppings.add(topping);
            return true;
        }
        return false;
    }

    /**
     * Removes a topping from a pizza.
     * @param obj Topping to be removed.
     * @return true if topping was removed; false otherwise.
     */
    @Override
    public boolean remove(Object obj){
        if(obj instanceof Topping topping){
            for(Topping t : toppings){
                if(topping.equals(t)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Initializes the toppings.
     */
    public void setToppings(){
        toppings = new ArrayList<>();
    }

    /**
     * Gets the toppings.
     * @return an ArrayList representing the toppings.
     */
    public ArrayList<Topping> getToppings(){
        return toppings;
    }
}
