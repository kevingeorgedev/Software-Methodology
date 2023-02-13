package oitkmg.project4;

/**
 * Creates a certain type of pizza that is Chicago style.
 * @author Kevin George, Omar Talaat
 */
public class ChicagoPizza implements PizzaFactory{

    /**
     * Creates a deluxe chicago style pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.DEEP_DISH);
    }

    /**
     * Creates a meatzza pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.STUFFED);
    }

    /**
     * Creates a BBQ Chicken pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.PAN);
    }

    /**
     * Creates a "Build your own" pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.PAN);
    }
}
