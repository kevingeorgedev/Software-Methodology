package oitkmg.project1;

/**
 * FitnessClass represents a specified fitness class. FitnessClass can
 * be either PILATES, SPINNING, or CARDIO only. The purpose of this class
 * is to hold the characteristics of a fitness class and the members
 * checked into a fitness class.
 * @author Kevin George, Omar Talaat
 */
public class FitnessClass {

    private final String className;
    private final String instructor;
    private final Time time;
    private Member[] membersCheckedIn;
    private int sizeCheckedIn;

    /**
     * Instantiates a new Fitness Class object. This class can be called
     * using PILATES, SPINNING, or CARDIO only.
     * @param className the name of the fitness class.
     * @param instructor the instructor of the fitness class.
     * @param time the time of the class.
     */
    FitnessClass(String className, String instructor, String time){
        this.className = className;
        this.instructor = instructor;
        this.time = Time.valueOf(time);
        this.sizeCheckedIn = 0;
        this.membersCheckedIn = new Member[4];
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
     * Gets the time of a Fitness Class. Can be either MORNING(9:30) or
     * AFTERNOON(14:00).
     * @return a Time representing a FitnessClass's time slot.
     */
    public Time getTime(){
        return time;
    }

    /**
     * Increase the size of the list holding the members checked into a
     * FitnessClass. Method is called when the list of members checked in
     * reaches its capacity.
     */
    private void grow(){
        Member[] newMembersCheckedIn = new Member[sizeCheckedIn + 4];
        for(int i = 0; i < sizeCheckedIn; ++i){
            newMembersCheckedIn[i] = membersCheckedIn[i];
        }
        membersCheckedIn = newMembersCheckedIn;
    }

    /**
     * Checks in a member to a FitnessClass. If the list of members checked
     * in is full then this method will call on grow() to increase the size.
     * @param member the member to check in.
     */
    public void checkInMember(Member member){
        if(sizeCheckedIn == 0){
            membersCheckedIn[0] = member;
            sizeCheckedIn++;
            return;
        }

        for(int i = 0; i < sizeCheckedIn; ++i){
            if(membersCheckedIn[i] == null){
                membersCheckedIn[i] = member;
                sizeCheckedIn++;
                return;
            }
            else if(i == sizeCheckedIn - 1){
                grow();
                membersCheckedIn[sizeCheckedIn] = member;
                sizeCheckedIn++;
                return;
            }
        }
    }

    /**
     * Checks out a member of a FitnessClass. Method must only be called
     * if the member exists in the list already. Otherwise, nothing will
     * happen.
     * @param member the member to be checked out.
     */
    public void unCheckInMember(Member member){
        Member[] newMembersCheckedIn = new Member[membersCheckedIn.length];
        boolean passed = false;

        for(int i = 0; i < sizeCheckedIn; ++i){
            if(!passed && membersCheckedIn[i].equals(member)){
                passed = true;
                continue;
            }
            else if(passed){
                newMembersCheckedIn[i-1] = membersCheckedIn[i];
                continue;
            }
            newMembersCheckedIn[i] = membersCheckedIn[i];
        }

        membersCheckedIn = newMembersCheckedIn;
        sizeCheckedIn--;
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
     * Prints the names of all members checked into a FitnessClass.
     */
    public void printNames(){
        for(Member member : membersCheckedIn){
            if(member == null) return;
            System.out.println(member.toString());
        }
    }

    /**
     * Gets the list of all members checked into a FitnessClass.
     * @return a Member[] representing the members checked into a
     * FitnessClass
     */
    public Member[] getMembersCheckedIn(){
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
}

