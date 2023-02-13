package oitkmg.project4;

/**
 * Creates a "Build your own" pizza.
 * @author Kevin George, Omar Talaat
 */
public class BuildYourOwn extends Pizza{

    /**
     * Instantiates a "Build your own" pizza.
     * @param crust Crust of the pizza.
     */
    public BuildYourOwn(Crust crust){
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
     * Gets the price of a BBQ Chicken Pizza.
     * @return a double representing the price.
     */
    @Override
    public double price() {
        String size = this.getSize().size().toUpperCase();
        double toppingsTotal = this.getToppings().size() * 1.59;
        return switch (size) {
            case ("SMALL") -> 8.99 + toppingsTotal;
            case ("MEDIUM") -> 10.99 + toppingsTotal;
            default -> 12.99 + toppingsTotal;
        };
    }
}
