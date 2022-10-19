package oitkmg.project2;

/**
 * Creates a premium member. Premium members are allowed 3 guest passes.
 * @author Kevin George, Omar Talaat
 */
public class Premium extends Family{

    /**
     * Amount of guest passes remaining.
     */
    private int guestPasses = 3;

    /**
     * Creates a premium member object with the specified name, date of birth,
     * expiration date, and gym location.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param expire the member's membership expiration date.
     * @param location the member's gym location.
     */
    public Premium (String fname, String lname, Date dob, Date expire, Location location){
        super(fname, lname, dob, expire, location);
    }

    /**
     * Creates a string of a premium member. The string contains the member's
     * first name, last name, date of birth, membership expiration date,
     * and the location of the gym the member attends to.
     * @return a String representing the member's information.
     */
    @Override
    public String toString(){
        return this.getFname() + " " + this.getLname() + ", " + "DOB: " +
                this.getDob().toString() + ", " +
                (this.getExpire().compareTo(new Date()) < 0 ?
                        "Membership expires " : "Membership expired ") +
                this.getExpire().toString() + ", Location: " +
                this.getLocation().toString() + ", " +
                "(Premium) Guest-pass remaining: " + guestPasses;
    }

    /**
     * Gets the remaining guest passes of a premium member.
     * @return an integer representing the remaining guest passes of
     * a premium member.
     */
    @Override
    public int getGuestPasses(){
        return guestPasses;
    }

    /**
     * Uses one guest pass of a premium member. Decrements guest passes
     * remaining by one.
     */
    @Override
    public void useGuestPass(){
        guestPasses--;
    }

    /**
     * Adds one guest pass to a premium member. Increments guest passes
     * remaining by one.
     */
    @Override
    public void addGuestPass(){
        guestPasses++;
    }

    /**
     * Calculates the membership fee.
     * @return a double representing the membership fee.
     */
    @Override
    public double membershipFee(){
        int MONTHS = 11;
        double FEE_SCHEDULE = 59.99;
        return MONTHS * FEE_SCHEDULE;
    }
}