package oitkmg.project4;

/**
 * Creates a crust object.
 * @author Kevin George, Omar Talaat
 */
public enum Crust {

    /**
     * Brooklyn crust.
     */
    BROOKLYN("Brooklyn"),
    /**
     * Deep dish crust.
     */
    DEEP_DISH("Deep_Dish"),
    /**
     * Hand tossed crust.
     */
    HAND_TOSSED("Hand_Tossed"),
    /**
     * Pan crust.
     */
    PAN("Pan"),
    /**
     * Stuffed crust.
     */
    STUFFED("Stuffed"),
    /**
     * Thin crust.
     */
    THIN("Thin");

    /**
     * Crust of the pizza.
     */
    private final String crust;

    /**
     * Initializes Crust.
     * @param crust String representing the crust.
     */
    Crust(String crust){
        this.crust = crust;
    }

    /**
     * Gets the crust.
     * @return a String representing the crust.
     */
    public String getCrust(){
        return crust;
    }

    /**
     * Determines if a crust is equal to another.
     * @param crust Crust to be compared to
     * @return True if crusts are equal; false otherwise.
     */
    public boolean equals(Crust crust){
        return crust.getCrust().equals(this.crust);
    }
}
