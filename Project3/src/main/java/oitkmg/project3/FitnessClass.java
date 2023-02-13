package oitkmg.project3;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * FitnessClass represents a specified fitness class. FitnessClass can
 * be either PILATES, SPINNING, or CARDIO only. The purpose of this class
 * is to hold the characteristics of a fitness class, the members and
 * guests checked into a fitness class.
 * @author Kevin George, Omar Talaat
 */
public class FitnessClass {

    /**
     * Name of the fitness class.
     */
    private final String className;
    /**
     * Instructor of the fitness class.
     */
    private final String instructor;
    /**
     * Time of the fitness class.
     */
    private final Time time;
    /**
     * Members checked into the fitness class.
     */
    private ArrayList<Member> membersCheckedIn;
    /**
     * Guests checked into the fitness class.
     */
    private ArrayList<Guest> guests;
    /**
     * Location of the fitness class.
     */
    private Location location;
    /**
     * Amount of members checked into fitness class.
     */
    private int sizeCheckedIn;

    /**
     * Instantiates a new Fitness Class object. This class can be called
     * using PILATES, SPINNING, or CARDIO only.
     * @param className the name of the fitness class.
     * @param instructor the instructor of the fitness class.
     * @param time the time of the fitness class.
     * @param location the location of the fitness class.
     */
    FitnessClass(String className, String instructor, String time, Location location){
        this.className = className.toUpperCase();
        this.instructor = instructor.toUpperCase();
        this.time = Time.valueOf(time.toUpperCase());
        this.location = location;
        this.sizeCheckedIn = 0;
        this.membersCheckedIn = new ArrayList<>();
        this.guests = new ArrayList<>();
    }

    /**
     * Gets the name of a Fitness Class.
     * @return a string representing the class name of a FitnessClass
     */
    public String getClassName(){
        return className;
    }

    /**
     * Gets the name of a Fitness Class's instructor.
     * @return a string representing the Fitness Class's instructor.
     */
    public String getInstructor(){
        return instructor;
    }

    /**
     * Gets the time of a Fitness Class. Can be either MORNING(9:30),
     * AFTERNOON(14:00), or EVENING(18:30).
     * @return a Time representing a FitnessClass's time slot.
     */
    public Time getTime(){
        return time;
    }

    /**
     * Checks in a member to a FitnessClass.
     * @param member the member to check in.
     */
    public void checkInMember(Member member){
        if(!memberExists(member)) {
            membersCheckedIn.add(member);
            sizeCheckedIn++;
        }
    }

    /**
     * Checks in a guest to this fitness class.
     * @param parent Parent of the guest to be added.
     */
    public void checkInGuest(Member parent){
        guests.add(new Guest(parent));
    }

    /**
     * Checks out a guest from this fitness class.
     * @param parent Parent of the guest to be removed.
     * @return true if member was checked out; false otherwise.
     */
    public boolean checkOutGuest(Member parent){
        Guest guest = new Guest(parent);
        for(int i = 0; i < guests.size(); ++i){
            if(guests.get(i).equals(guest)) {
                guests.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the guest list of a class.
      * @return An ArrayList of guests present in a fitness class.
     */
    public ArrayList<Guest> getGuests(){
        return guests;
    }

    /**
     * Checks out a member of a FitnessClass. Method must only be called
     * if the member exists in the list already. Otherwise, nothing will
     * happen.
     * @param member the member to be checked out.
     */
    public void unCheckInMember(Member member){
        for(int i = 0; i < sizeCheckedIn; ++i){
            if(membersCheckedIn.get(i).equals(member)){
                membersCheckedIn.remove(i);
                sizeCheckedIn--;
                return;
            }
        }
    }

    /**
     * Determines whether a fitness class is equal to this class.
     * A fitness class is equal to another if their name, instructor,
     * and location are all the same.
     * @param fitnessClass the reference fitness class with which to
     *                     compare.
     * @return true if this fitness class is equal to the fitness class
     * argument; false otherwise.
     */
    public boolean equals(FitnessClass fitnessClass){
        return (className.equals(fitnessClass.getClassName())
                && instructor.equals(fitnessClass.getInstructor())
                && time.equals(fitnessClass.getTime())
                && location.getTown().equals(fitnessClass.getLocation().getTown()));
    }

    /**
     * Check if member exists in the FitnessClass.
     * @param member the member to be determined if it exists in a
     *               FitnessClass.
     * @return true if the member exists in the FitnessClass; false otherwise.
     */
    public boolean memberExists(Member member){
        for (Member val : membersCheckedIn) {
            if(val == null) return false;
            else if (val.equals(member)) return true;
        }
        return false;
    }

    /**
     * Creates a string of a fitness class. The string contains the fitness
     * class' name, instructor, time and location. As well as all the participants
     * in the class.
     * @return a String representing the fitness class.
     */
    @Override
    public String toString(){
        DecimalFormat df1 = new DecimalFormat("00");
        String output = className + " - " +
                instructor + ", "
                + time.getHour()
                + ":" + df1.format(time.getMinute())
                + ", " + location.getTown();
        if(sizeCheckedIn != 0){
            output += ("\n- Participants -");
            for(int i = 0; i < sizeCheckedIn; ++i){
                output += ("\n\t" + (membersCheckedIn.get(i)));
            }
        }
        return output.toString();
    }

    /**
     * Gets the list of all members checked into a FitnessClass.
     * @return a Member[] representing the members checked into a
     * FitnessClass
     */
    public ArrayList<Member> getMembersCheckedIn(){
        return membersCheckedIn;
    }

    /**
     * Gets the numbers of members checked in.
     * @return an integer representing the number of members checked into a
     * FitnessClass
     */
    public int getSizeCheckedIn(){
        return sizeCheckedIn;
    }

    /**
     * Gets the location of this fitness class.
     * @return the Location of this class.
     */
    public Location getLocation(){
        return location;
    }
}

