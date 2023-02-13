package oitkmg.project4;

/**
 * Methods that all classes implementing Pizza Factory must implement.
 * @author Kevin George, Omar Talaat
 */
public interface PizzaFactory {
    /**
     * Creates a deluxe pizza.
     * @return a new pizza.
     */
    Pizza createDeluxe();
    /**
     * Creates a meatzza pizza.
     * @return a new pizza.
     */
    Pizza createMeatzza();
    /**
     * Creates a BBQ Chicken pizza.
     * @return a new pizza.
     */
    Pizza createBBQChicken();
    /**
     * Creates a "Build your own" pizza.
     * @return a new pizza.
     */
    Pizza createBuildYourOwn();
}
