package oitkmg.project4;

/**
 * Creates a Deluxe pizza.
 * @author Kevin George, Omar Talaat
 */
public class Deluxe extends Pizza{
    /**
     * Initializes a Deluxe pizza.
     * @param crust Crust of the pizza.
     */
    public Deluxe(Crust crust){
        this.setCrust(crust);
    }

    /**
     * Adds a topping to the pizza.
     * @param obj Topping to be added.
     * @return true if the topping was added; false otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if(obj instanceof Topping topping){
            super.add(topping);
            return true;
        }
        return false;
    }

    /**
     * Removes a topping from the pizza.
     * @param obj Topping to be removed.
     * @return true if topping was removed; false otherwise.
     */
    @Override
    public boolean remove(Object obj) {
        if(obj instanceof Topping topping){
            return super.remove(topping);
        }
        return false;
    }

    /**
     * Gets the price of a Deluxe Pizza.
     * @return a double representing the price.
     */
    @Override
    public double price() {
        String size = this.getSize().size().toUpperCase();
        return switch (size) {
            case ("SMALL") -> 14.99;
            case ("MEDIUM") -> 16.99;
            default -> 18.99;
        };
    }


}
