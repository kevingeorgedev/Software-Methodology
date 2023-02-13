package oitkmg.project4;

/**
 * Creates a certain type of pizza that is New York style.
 * @author Kevin George, Omar Talaat
 */
public class NYPizza implements PizzaFactory{

    /**
     * Creates a deluxe pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN);
    }

    /**
     * Creates a meatzza pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HAND_TOSSED);
    }

    /**
     * Creates a BBQ Chicken pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN);
    }

    /**
     * Creates a "Build your own" pizza.
     * @return a new pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}
