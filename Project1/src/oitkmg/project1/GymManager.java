package oitkmg.project1;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * GymManager operates the usage of several commands to maintain and
 * display a Gym.
 * @author Kevin George, Omar Talaat
 */
public class GymManager {
    private static final MemberDatabase database = new MemberDatabase();
    private static final FitnessClass[] fitnessClasses =
            { new FitnessClass("Pilates", "Jennifer", "Morning"),
              new FitnessClass("Spinning", "Denise", "Afternoon"),
              new FitnessClass("Cardio", "Kim", "Afternoon")};
    private final Scanner s = new Scanner(System.in);

    /**
     * Called to start accepting inputs the GymManager. This function will
     * run continuously until the Quit command is used to stop the program.
     * Inputs are accepted at any time this function is running.
     * Inputs include: <pre>
     *
     * "A"
     *      - Description -> Add a member to the database.
     *      - Usage -> A FirstName LastName DateOfBirth ExpirationDate
     *                 Location
     * "R"
     *      - Description -> Remove a member from the database.
     *      - Usage -> R FirstName LastName DateOfBirth
     * "P"
     *      - Description -> Print a list of the members in the database in
     *          current position.
     * "PC"
     *      - Description -> Print a list of the members in the database
     *          ordered by County, and ZIP code.
     * "PN"
     *      - Description -> Print a list of the members in the database
     *          ordered by last name, and first name.
     * "PD"
     *      - Description -> Print a list of the members in the database
     *          ordered by expiration date.
     * "S"
     *      - Description -> Print the fitness class schedule and the
     *          members checked in.
     * "C"
     *      - Description -> Check in a member to a fitness class.
     *      - Usage -> C ClassName FirstName LastName DateOfBirth
     * "D"
     *      - Description -> Check out a member from a fitness class.
     *      - Usage -> D ClassName FirstName LastName DateOfBirth
     * "Q"
     *      - Description -> Exit Gym Manager.
     * </pre>
     */
    public void run(){
        System.out.println("Gym Manager running...\n");
        while(s.hasNextLine()){
            String line = s.nextLine();
            if(line.trim().length() == 0) {
                System.out.println();
                continue;
            }

            String[] inputs = line.split(" ");
            String command = inputs[0];
            switch (command) {
                case "A" -> addMember(inputs);
                case "R" -> removeMember(inputs);
                case "P" -> database.print();
                case "PC" -> database.printByCounty();
                case "PN" -> database.printByName();
                case "PD" -> database.printByExpirationDate();
                case "S" -> printFitnessClassSchedule();
                case "C" -> checkInMember(inputs);
                case "D" -> uncheckInMember(inputs);
                case "Q" -> {
                    System.out.println("Gym Manager terminated.");
                    System.exit(0);
                }
                default -> System.out.println(inputs[0] +
                        " is an invalid command!");
            }
        }
    }

    /**
     * Called to add a member to the database. If a member enters an invalid
     * calendar date for date of birth, a date of birth in the future, a
     * member who's under 18 years of age, an invalid calendar date for
     * expiration date, or an invalid gym location the method will return
     * and print the reason for terminating; Otherwise, the member will be
     * added to the datebase.
     * @param inputs command inputs used to create a member object.
     */
    private void addMember(String[] inputs){
        String fname = inputs[1], lname = inputs[2];
        Date dob = new Date(inputs[3]), expire = new Date(inputs[4]),
                now = new Date();

        if(!dob.isValid()){
            System.out.println("DOB " + dob.toString()
                    + ": invalid calendar date!");
            return;
        } else if(now.compareTo(dob) >= 0){
            System.out.println("DOB " + dob.toString()
                    + ": cannot be today or a future date!");
            return;
        } else if(!isEighteenOrOlder(dob, now)){
            System.out.println("DOB " + dob.toString()
                    + ": must be 18 or older to join!");
            return;
        } else if(!expire.isValid()){
            System.out.println("Expiration date " + expire.toString()
                    + ": invalid calendar date!");
            return;
        }

        Location location;
        try{    location = Location.valueOf(inputs[5].toUpperCase());   }
        catch (Exception e){
            System.out.println(inputs[5] + ": invalid location!");
            return;
        }

        Member member = new Member(fname, lname, dob, expire, location);
        if(database.add(member))
            System.out.println(fname + " " + lname + " added.");
        else
            System.out.println(fname + " " + lname
                    + " is already in the database.");
    }

    /**
     * Used to determine if the date of birth of a member is 18
     * years or older.
     * @param dob a Date object that represents the date of birth of a
     *            member.
     * @param now a Date object that represents the current date.
     * @return true if the date of birth is 18 years or older; false
     * otherwise.
     */
    private boolean isEighteenOrOlder(Date dob, Date now){
        if(dob.getYear() + 18 > now.getYear()){
            return false;
        } else if(dob.getYear() + 18 == now.getYear()){
            if(dob.getMonth() > now.getMonth()){
                return false;
            } else if(dob.getMonth() == now.getMonth()){
                return dob.getDay() <= now.getDay();
            }
        }
        return true;
    }

    /**
     * Called to remove a member from the database. If the member is not in
     * the database then the user will be notified. If the member is in the
     * database then the member will be checked out of all fitness classes
     * too.
     * @param inputs command inputs used to create a member object.
     */
    private void removeMember(String[] inputs){
        Member memberToRemove = new Member(inputs[1], inputs[2],
                new Date(inputs[3]),
                null, null);
        if(database.remove(memberToRemove)){
            for(FitnessClass fitnessClass : fitnessClasses){
                Member[] membersCheckedIn = fitnessClass.getMembersCheckedIn();
                for(int i = 0; i < fitnessClass.getSizeCheckedIn(); ++i){
                    if(membersCheckedIn[i].equals(memberToRemove)){
                        fitnessClass.unCheckInMember(membersCheckedIn[i]);
                        break;
                    }
                }
            }
            System.out.println(memberToRemove.getFname() + " "
                    + memberToRemove.getLname() + " removed.");
        }
        else{
            System.out.println(memberToRemove.getFname() + " "
                    + memberToRemove.getLname()
                    + " is not in the database.");
        }
    }

    /**
     * Prints the fitness class schedule and its corresponding
     * members that are checked in. For each fitness class, its class
     * name, instructor, and class time will be printed.
     */
    private void printFitnessClassSchedule(){
        DecimalFormat df1 = new DecimalFormat("00");
        System.out.println("-Fitness classes-");
        for (FitnessClass fitnessClass : fitnessClasses) {
            System.out.println(fitnessClass.getClassName() + " - " +
                    fitnessClass.getInstructor() + " "
                    + fitnessClass.getTime().getHour()
                    + ":" + df1.format(fitnessClass.getTime().getMinute()));
            if(fitnessClass.getSizeCheckedIn() != 0){
                System.out.println("\t** participants **");
                for(int i = 0; i < fitnessClass.getSizeCheckedIn(); ++i){
                    System.out.println("\t\t"
                            + fitnessClass.getMembersCheckedIn()[i].toString());
                }
            }
        }
    }

    /**
     * Checks in a member to a fitness class. If the date of birth input is
     * invalid, the member does not exist, the member's expiration date is
     * expired, or class name inputed does not exist the method will return
     * and the user will be notified why.
     * @param inputs command inputs used to create a member object.
     */
    private void checkInMember(String[] inputs){
        Member member = new Member(inputs[2], inputs[3],
                new Date(inputs[4]), null, null);
        if(!member.getDob().isValid()) {
            System.out.println("DOB " + member.getDob().toString()
                    + ": invalid calendar date!");
            return;
        } //Determine if member date of birth is valid
        Member[] mlist = database.getMlist();
        for(int i = 0; i < mlist.length; ++i){
            if(mlist[i].equals(member)){
                member = mlist[i];
                break;
            } else if(i == database.getSize() - 1){
                System.out.println(member.getFname() + " " + member.getLname()
                        + " " + member.getDob().toString()
                        + " is not in the database.");
                return;
            }
        } //Find member in member list, if member is not found return
        if(member.getExpire().compareTo(new Date()) > 0){
            System.out.println(member.getFname() + " " + member.getLname()
                    + " " + member.getDob() + " membership expired.");
            return;
        } //Determine if membership has expired
        String classNameInput = inputs[1].toUpperCase();
        if(!(classNameInput.equals("PILATES")
                || classNameInput.equals("SPINNING")
                || classNameInput.equals("CARDIO"))){
            System.out.println(inputs[1] + " class does not exist.");
            return;
        }
        //Try to use class name input, catch executed if class name is invalid
        System.out.println(getMemberJoinClassResponse(member, classNameInput));
    }

    /**
     * Called to add a member to a fitness class given they meet all
     * the qualifications to. This class will return a string based on
     * whether the member was able to join the class.
     * @param member member that is requested to be checked in.
     * @param className class name the member wishes to join.
     * @return a string representing a confirmation or error joining
     * message
     */
    private String getMemberJoinClassResponse(Member member, String className){
        int index = -1;

        switch (className) {
            case "PILATES":
                if (fitnessClasses[0].memberExists(member))
                    return member.getFullName() + " has already checked in "
                            + fitnessClasses[0].getClassName() + ".";
                else {
                    fitnessClasses[0].checkInMember(member);
                    return member.getFullName() + " checked in "
                            + fitnessClasses[0].getClassName() + ".";
                }
            case "SPINNING":
                if (fitnessClasses[1].memberExists(member))
                    return member.getFullName() + " has already checked in "
                            + fitnessClasses[1].getClassName() + ".";
                else if (fitnessClasses[2].memberExists(member))
                    return "Spinning time conflict -- " + member.getFullName()
                            + " has already checked in Cardio.";
                index = 1;
                break;
            case "CARDIO":
                if (fitnessClasses[2].memberExists(member))
                    return member.getFullName() + " has already checked in "
                            + fitnessClasses[2].getClassName() + ".";
                else if (fitnessClasses[1].memberExists(member))
                    return "Cardio time conflict -- " + member.getFullName()
                            + " has already checked in Spinning.";
                index = 2;
                break;
        }

        fitnessClasses[index].checkInMember(member);
        return member.getFname() + " " + member.getLname() + " checked in "
                + fitnessClasses[index].getClassName() + ".";
    }

    /**
     *  Checks out a member from a fitness class. If the date of birth
     *  input is invalid, member is not checked in, or the class name
     *  inputed does not exist the method will return and the user
     *  will be notified why.
     * @param inputs command inputs used to create a member object.
     */
    private void uncheckInMember(String[] inputs){
        if((!new Date(inputs[4]).isValid())){
            System.out.println("DOB " + inputs[4]
                    + ": invalid calendar date!");
            return;
        }

        String className = inputs[1].toUpperCase();
        FitnessClass fitClass;
        for(int i = 0; i < fitnessClasses.length; ++i){
            if(fitnessClasses[i].getClassName().toUpperCase().equals(className)){
                fitClass = fitnessClasses[i];
                break;
            }
            else if(i == fitnessClasses.length - 1){
                System.out.println(className + " class does not exist.");
                return;
            }
        }

        Member memberToRemove = new Member(inputs[2], inputs[3],
                new Date(inputs[4]), null, null);

        switch (className) {
            case "PILATES" -> checkedOutPrint(0, memberToRemove);
            case "SPINNING" -> checkedOutPrint(1, memberToRemove);
            case "CARDIO" -> checkedOutPrint(2, memberToRemove);
        }
    }

    /**
     * Prints out a statement based on whether the member was
     * able to be removed from a Fitness Class.
     * @param index represents which fitness class. 0 for Pilates,
     *              1 for Spinning, and 2 for Cardio.
     * @param member represents the member that wishes to be
     *               removed.
     */
    private void checkedOutPrint(int index, Member member){
        FitnessClass fitnessClass = fitnessClasses[index];
        String className = fitnessClass.getClassName();

        if (fitnessClass.memberExists(member)) {
            fitnessClass.unCheckInMember(member);
            System.out.println(member.getFullName()
                    + " dropped " + className + ".");
        } else
            System.out.println(member.getFullName()
                + " is not a participant in " + className + ".");
    }
}
