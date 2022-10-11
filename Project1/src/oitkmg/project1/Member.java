package oitkmg.project1;

/**
 * Class is used to create and manipulate the characteristics of a gym member.
 * The member object is used to allow gyms to keep track of all gym members'
 * different characteristics. Members can be added and removed from the
 * member database of a gym.
 * @author Kevin George, Omar Talaat
 */
public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    /**
     * Creates a Member object with the specified name, date of birth,
     * expiration date, and gym location.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param expire the member's gym expiration date.
     * @param location the member's gym location.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location){
        this.fname    = fname;
        this.lname    = lname;
        this.dob      = dob;
        this.expire   = expire;
        this.location = location;
    }
    /**
     * Creates a string of a member containing the member's first name,
     * last name, date of birth, membership expiration date, and the
     * location of the gym the member attends to.
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
            return fname.toUpperCase().equals(obj2.getFname().toUpperCase())
                    && lname.toUpperCase().equals(obj2.getLname().toUpperCase())
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

    /**
     * Main is used to test the compareTo() method.
     * @param args an array of command-line arguments for the application.
     */
    public static void main(String[] args){
        Member[] list = new Member[10];
        //TEST CASES
        //#1
        list[0] = new Member("Kevin", "John", null, null, null);
        list[1] = new Member("kevin", "John", null, null, null);
        System.out.println("Test case #" + "1: " + list[0].getFullName() + " : " + list[1].getFullName());
        System.out.println("\tOutput: " + list[0].compareTo(list[1]) + " PASSED (expected negative)");
        //#2
        list[2] = new Member("John", "Doe", null, null, null);
        list[3] = new Member("John", "Doe", null, null, null);
        System.out.println("Test case #" + "1: " + list[2].getFullName() + " : " + list[3].getFullName());
        System.out.println("\tOutput: " + list[2].compareTo(list[3]) + " PASSED (expected zero)");
        //#3
        list[4] = new Member("John", "Smith", null, null, null);
        list[5] = new Member("Alex", "Park", null, null, null);
        System.out.println("Test case #" + "1: " + list[4].getFullName() + " : " + list[5].getFullName());
        System.out.println("\tOutput: " + list[4].compareTo(list[5]) + " PASSED (expected positive)");
        //#4
        list[6] = new Member("Alex", "Smith", null, null, null);
        list[7] = new Member("John", "Smith", null, null, null);
        System.out.println("Test case #" + "1: " + list[6].getFullName() + " : " + list[7].getFullName());
        System.out.println("\tOutput: " + list[6].compareTo(list[7]) + " PASSED (expected negative)");
        //#5
        list[8] = new Member("Alex", "Smith", null, null, null);
        list[9] = new Member("Alex", "John", null, null, null);
        System.out.println("Test case #" + "1: " + list[8].getFullName() + " : " + list[9].getFullName());
        System.out.println("\tOutput: " + list[8].compareTo(list[9]) + " PASSED (expected positive)");
    }
}


