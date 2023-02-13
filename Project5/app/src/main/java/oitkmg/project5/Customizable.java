package oitkmg.project4;

/**
 * Implements two add and remove methods.
 * @author Kevin George, Omar Talaat
 * @param <E> Type of Object
 */
public interface Customizable<E> {
    /**
     * Adds an object.
     * @param obj Object to be added.
     * @return true if object is added; false otherwise.
     */
    boolean add (Object obj);

    /**
     * Removes an object.
     * @param obj Object to be removed.
     * @return true if object is removed; false otherwise.
     */
    boolean remove (Object obj);
}
