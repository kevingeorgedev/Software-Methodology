package oitkmg.project4;

/**
 * Size of a pizza.
 * @author Kevin George, Omar Talaat
 */
public enum Size {

    /**
     * Small size.
     */
    SMALL("Small"),
    /**
     * Medium size.
     */
    MEDIUM("Medium"),
    /**
     * Large size.
     */
    LARGE("Large");

    /**
     * String representing size.
     */
    private final String size;

    /**
     * Initializes a size.
     * @param size Size to be made.
     */
    Size(String size){
        this.size = size;
    }

    /**
     * Gets the size.
     * @return a String representing the size.
     */
    public String size(){
        return size;
    }
}
