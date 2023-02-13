package oitkmg.project3;

/**
 * Class is used to create and manipulate the characteristics of a gym member.
 * The member object is used to allow gyms to keep track of all gym members'
 * different characteristics. Members can be added and removed from the
 * member database of a gym.
 * @author Kevin George, Omar Talaat
 */
public class Member implements Comparable<Member> {

    /**
     * First name of the member.
     */
    private String fname;
    /**
     * Last name of the member.
     */
    private String lname;
    /**
     * Date of birth of the member.
     */
    private Date dob;
    /**
     * Expiration date of the member.
     */
    private Date expire;
    /**
     * Location the member has a membership at.
     */
    private Location location;

    /**
     * Creates a Member object with the specified name, date of birth,
     * expiration date, and gym location.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param expire the member's membership expiration date.
     * @param location the member's gym location.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location){
        this.fname         = fname;
        this.lname         = lname;
        this.dob           = dob;
        this.expire        = expire;
        this.location      = location;
    }

    /**
     * Creates a Member object with the specified name, date of birth, and
     * gym location.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param location the member's gym location.
     */
    public Member(String fname, String lname, Date dob, Location location){
        this.fname         = fname;
        this.lname         = lname;
        this.dob           = dob;
        this.location      = location;
    }

    /**
     * Creates a string of a member. The string contains the member's
     * first name, last name, date of birth, membership expiration date,
     * and the location of the gym the member attends to.
     * @return string of member's information.
     */
    @Override
    public String toString() {
        return fname + " " + lname + ", " + "DOB: " + dob.toString() + ", " +
                (expire.compareTo(new Date()) < 0 ?
                        "Membership expires " : "Membership expired ") +
                expire.toString() + ", Location: " + location.toString();
    }

    /**
     * Determines whether some object is an instance of Member and the
     * first name, last name, and date of birth are equal.
     * @param obj the reference object with which to compare.
     * @return true if this member is equal to the obj argument;
     * false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member obj2){
            return fname.equalsIgnoreCase(obj2.getFname())
                    && lname.equalsIgnoreCase(obj2.getLname())
                    && dob.compareTo(obj2.getDob()) == 0;
        }
        return false;
    }

    /**
     * Compares this member's name with the specified member.
     * If the members' last names are equal then the first names
     * will be compared.
     * @param member the member object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * this member is less than, equal to, or greater than the
     * specified member.
     */
    @Override
    public int compareTo(Member member) {
        int lnameCompare = lname.compareTo(member.getLname());
        if(lnameCompare == 0){
            return fname.compareTo(member.getFname());
        }
        return lnameCompare;
    }

    /**
     * Calculates the membership fee.
     * @return a double representing the membership fee.
     */
    public double membershipFee(){
        double INITIAL_FEE = 29.99;
        double FEE_SCHEDULE = 39.99;
        int MONTH = 3;
        return INITIAL_FEE + (MONTH * FEE_SCHEDULE);
    }

    /**
     * Gets the remaining guest passes of a member. Will
     * always be zero for a standard member.
     * @return an integer representing the remaining guest
     * passes
     */
    public int getGuestPasses(){
        return 0;
    }

    /**
     * Gets the member's last name.
     * @return a string representing the member's last name.
     */
    public String getLname(){
        return lname;
    }
    /**
     * Gets the member's first name.
     * @return a string representing the member's first name.
     */
    public String getFname(){
        return fname;
    }
    /**
     * Gets the member's date of birth.
     * @return a date representing the member's date of birth.
     */
    public Date getDob(){
        return dob;
    }

    /**
     * Get the member's membership expiration date.
     * @return a date representing the member's membership expiration date.
     */
    public Date getExpire(){
        return expire;
    }

    /**
     * Get the location of the gym that the member has a membership at.
     * @return a location representing the gym that the member has a
     * membership at.
     */
    public Location getLocation(){
        return location;
    }

    /**
     * Gets the member's first and last name.
     * @return a string representing the member's full name.
     */
    public String getFullName(){
        return fname + " " + lname;
    }
}


