package oitkmg.project4;

/**
 * Creates topping of a pizza.
 * @author Kevin George, Omar Talaat
 */
public enum Topping {

    /**
     * Sausage topping.
     */
    SAUSAGE("sausage"),
    /**
     * Pepperoni topping.
     */
    PEPPERONI("pepperoni"),
    /**
     * Green pepper topping.
     */
    GREEN_PEPPER("green_pepper"),
    /**
     * Onion topping.
     */
    ONION("onion"),
    /**
     * Mushroom topping.
     */
    MUSHROOM("mushroom"),
    /**
     * Provolone topping.
     */
    PROVOLONE("provolone"),
    /**
     * Cheddar topping.
     */
    CHEDDAR("cheddar"),
    /**
     * BBQ Chicken topping.
     */
    BBQ_CHICKEN("bbq_chicken"),
    /**
     * Beef topping.
     */
    BEEF("beef"),
    /**
     * Ham topping.
     */
    HAM("ham"),
    /**
     * Pineapple topping.
     */
    PINEAPPLE("pineapple"),
    /**
     * Bacon topping.
     */
    BACON("bacon"),
    /**
     * Prosciutto topping.
     */
    PROSCIUTTO("prosciutto");

    /**
     * String representing the topping.
     */
    private final String topping;

    /**
     * Initializes the topping.
     * @param topping a String representing the topping.
     */
    Topping(String topping){
        this.topping = topping;
    }

    /**
     * Gets a string representing the topping.
     * @return a String representing the topping.
     */
    public String getTopping(){
        return topping;
    }

    /**
     * Determines if two toppings are equal.
     * @param topping topping to be compared.
     * @return true if toppings are the same; false otherwise.
     */
    public boolean equals(Topping topping){
        return topping.getTopping().equals(this.topping);
    }
}
