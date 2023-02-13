package oitkmg.project3;

/**
 * Class is used to create and manipulate the characteristics of a gym member
 * with Family membership traits. Family member schedule fees are $59.99, and
 * they get 1 guest pass at a time.
 * @author Kevin George, Omar Talaat
 */
public class Family extends Member{

    /**
     * Amount of guest passes remaining.
     */
    private int guestPasses = 1;

    /**
     * Creates a Family object with the specified name, date of birth,
     * expiration date, and gym location.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param expire the member's expiration date.
     * @param location the member's gym location.
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location){
        super(fname, lname, dob, expire, location);
    }

    /**
     * Creates a string of the member. The string contains the member's
     * first name, last name, date of birth, membership expiration date,
     * location of the gym, and the amount of guest passes remaining.
     * @return string of member's information.
     */
    @Override
    public String toString(){
        return this.getFname() + " " + this.getLname() + ", " + "DOB: " +
                this.getDob().toString() + ", " +
                (this.getExpire().compareTo(new Date()) < 0 ?
                        "Membership expires " : "Membership expired ") +
                this.getExpire().toString() + ", Location: " +
                this.getLocation().toString() + ", " +
                "(Family) Guest-pass remaining: " + guestPasses;
    }

    /**
     * Gets the amount of remaining guest passes.
     * @return an integer representing the amount
     * of guest passes a member has remaining.
     */
    @Override
    public int getGuestPasses(){
        return guestPasses;
    }

    /**
     * Decrements the amount of remaining guest passes
     * by one. Called when a guest is allowed into a
     * fitness class under a gym member.
     */
    public void useGuestPass(){
        guestPasses--;
    }

    /**
     * Increments the amount of remaining guest passes
     * by one. Called when a guest checks out of a
     * fitness class.
     */
    public void addGuestPass(){
        guestPasses++;
    }

    /**
     * Gets the calculated membership fee the
     * member has due.
     * @return a double representing the calculated
     * membership fee for a member.
     */
    @Override
    public double membershipFee(){
        double INITIAL_FEE = 29.99;
        int MONTHS = 3;
        double SCHEDULE_FEE = 59.99;
        return INITIAL_FEE + (MONTHS * SCHEDULE_FEE);
    }
}
