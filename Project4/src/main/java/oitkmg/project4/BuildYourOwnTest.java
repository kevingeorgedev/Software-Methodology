package oitkmg.project4;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class used to test the BuildYourOwn pizza price method.
 * @author Kevin George, Omar Talaat
 */
public class BuildYourOwnTest {

    /**
     * Tests the price method for the BuildYourOwn pizza.
     */
    @Test
    public void price() {
        //Same amount of toppings different size (TWO TOPPINGS)

        //Small
        PizzaFactory pizzaFactory = new ChicagoPizza();
        Pizza pizza = pizzaFactory.createBuildYourOwn();
        pizza.setToppings();
        pizza.setCrust(Crust.PAN);
        pizza.add(Topping.BACON);
        pizza.add(Topping.GREEN_PEPPER);
        pizza.setSize(Size.SMALL);
        double expected = 12.17;
        assertEquals(expected, pizza.price(), 0);

        //Medium
        pizza.setSize(Size.MEDIUM);
        expected = 14.17;
        assertEquals(expected, pizza.price(), 0);

        //Large
        pizza.setSize(Size.LARGE);
        expected = 16.17;
        assertEquals(expected, pizza.price(), 0);

        //Add two toppings to pizza should increase price by 1.59 * 2
        expected = pizza.price() + 1.59 * 2;
        pizza.add(Topping.PEPPERONI);
        pizza.add(Topping.BBQ_CHICKEN);
        assertEquals(expected, pizza.price(), 0);
    }
}