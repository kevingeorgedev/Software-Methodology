package oitkmg.project2;

/**
 * Creates a Guest object. A guest object is created when a guest
 * wishes to join a fitness class.
 * @author Kevin George, Omar Talaat
 */
public class Guest{
    /**
     * Member that the guest is a guest under.
     */
    private Member parent;

    /**
     * Creates a Guest object with the specified Parent member.
     * The parent member is the Member that the guest is registering
     * under.
     * @param parent the member who the guest will join under.
     */
    public Guest(Member parent){
        this.parent = parent;
    }

    /**
     * Gets the string of the parent member.
     * @return a string representing the information of the parent
     * member.
     */
    @Override
    public String toString(){
        return parent.toString();
    }

    /**
     * Determines whether some guest is equal to this guest.
     * A guest is equal to another if they have the same parent member.
     * @param guest the reference guest with which to compare.
     * @return true if this guest is equal to the guest argument;
     * false otherwise.
     */
    public boolean equals(Guest guest) {
        return (parent.equals(guest.parent));
    }

}
