package oitkmg.project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * GymManager operates the usage of several commands to maintain and
 * display a Gym.
 * @author Kevin George, Omar Talaat
 */
public class GymManager {
    /**
     * Database that holds all the members.
     */
    private static MemberDatabase database;
    /**
     * Class schedule which holds all fitness classes.
     */
    private ClassSchedule classSchedule;

    /**
     * Instantiate a GymManager object.
     */
    public GymManager(){
        database = new MemberDatabase();
    }

    /**
     * Called to start accepting inputs the GymManager. This function will
     * run continuously until the Quit command is used to stop the program.
     * Inputs are accepted at any time this function is running.
     * Inputs include: <pre>
     *
     * "A"
     *      - Description -> Add a member to the database with expiration
     *          date in 3 months. This is a standard member.
     *      - Usage -> A FirstName LastName DateOfBirth Location
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
     * "LS"
     *      - Description -> Load the fitness class schedule from the file
     *          "classSchedule.txt" to the class schedule software system.
     * "LM"
     *      - Description -> Load the list of members from the file
     *          "memberList.txt" to the member database.
     * "AF"
     *      - Description -> Add a member with the family membership to
     *          the membership database. Membership expires 3 months later.
     *      - Usage -> FirstName LastName DateOfBirth Location
     * "AP"
     *      - Description -> Add a member with the premium membership to
     *          the membership database. Membership expires 1 year later.
     *      - Usage -> FirstName LastName DateOfBirth Location
     * "PF"
     *      - Description -> Prints a list of all members including their
     *          calculated membership fees.
     * "CG"
     *      - Description -> Family guest check-in for a fitness class.
     *          Uses one guest pass. Can't be used by standard member.
     *      - Usage -> ClassName Instructor Location FirstName LastName
     *          DateOfBirth
     * "DG"
     *      - Description -> Family guest check-out for a fitness class.
     *          Adds one guest pass.
     *      - Usage -> ClassName Instructor Location FirstName LastName
     *          DateOfBirth
     * "Q"
     *      - Description -> Exit Gym Manager.
     * </pre>
     */
    public void run(){
        System.out.println("Gym Manager running...\n");
        Scanner s = new Scanner(System.in);
        while(s.hasNextLine()){
            String line = s.nextLine();
            if(line.trim().length() == 0) {
                System.out.println();
                continue;
            }
            String[] inputs = line.split(" ");
            String command = inputs[0];
            switch (command) {
                case "A" -> addMember(inputs[1], inputs[2], new Date(inputs[3]), inputs[4]);
                case "R" -> removeMember(inputs);
                case "P" -> database.print();
                case "PC" -> database.printByCounty();
                case "PN" -> database.printByName();
                case "PD" -> database.printByExpirationDate();
                case "S" -> printFitnessClassSchedule();
                case "C" -> checkInMember(inputs);
                case "D" -> checkOutMember(inputs);
                case "LS" -> loadFitnessSchedule();
                case "LM" -> loadMembers();
                case "AF" -> addFamilyMember(inputs[1], inputs[2], new Date(inputs[3]), inputs[4]);
                case "AP" -> addPremiumMember(inputs[1], inputs[2], new Date(inputs[3]), inputs[4]);
                case "PF" -> database.printWithFees();
                case "CG" -> checkInGuest(inputs);
                case "DG" -> checkOutGuest(inputs);
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
     * Add member with the premium membership.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param loc the member's gym location.
     */
    private void addPremiumMember(String fname, String lname, Date dob, String loc){
        Date now = new Date();

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
        }
        //if(now.getMonth() + 3)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date expire = new Date(cal);
        Location location;
        try{    location = Location.valueOf(loc.toUpperCase());   }
        catch (Exception e){
            System.out.println(loc + ": invalid location!");
            return;
        }

        Premium member = new Premium(fname, lname, dob, expire, location);
        if(database.add(member))
            System.out.println(fname + " " + lname + " added.");
        else
            System.out.println(fname + " " + lname
                    + " is already in the database.");
    }

    /**
     * Add member with the family membership.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param loc the member's gym location.
     */
    private void addFamilyMember(String fname, String lname, Date dob, String loc){
        Date now = new Date();

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
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date expire = new Date(cal);
        Location location;
        try{    location = Location.valueOf(loc.toUpperCase());   }
        catch (Exception e){
            System.out.println(loc + ": invalid location!");
            return;
        }

        Family member = new Family(fname, lname, dob, expire, location);
        if(database.add(member))
            System.out.println(fname + " " + lname + " added.");
        else
            System.out.println(fname + " " + lname
                    + " is already in the database.");
    }

    /**
     * Loads the legacy members from "memberList.txt"
     */
    private void loadMembers(){
        Scanner s = resetScanner("memberList.txt");
        while(s.hasNextLine()){
            String[] inputs = s.nextLine().replaceAll("\\s+", " ").split(" ");
            database.add(new Member(inputs[0], inputs[1], new Date(inputs[2]), new Date(inputs[3]), Location.valueOf(inputs[4].toUpperCase())));
        }
        System.out.println("-list of members loaded-");
        Member[] mlist = database.getMlist();
        for(int i = 0; i < database.getSize(); ++i){
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");
    }

    /**
     * Loads the fitness class schedule.
     */
    private void loadFitnessSchedule() {
        Scanner s = resetScanner("classSchedule.txt");
        System.out.println("-Fitness classes loaded-");
        int count = 0;
        while(s.hasNextLine()){
            count++;
            s.nextLine();
        }
        s = resetScanner("classSchedule.txt");
        classSchedule = new ClassSchedule(count);
        while(s.hasNextLine()){
            String[] inputs = s.nextLine().split(" ");
            classSchedule.addClass(new FitnessClass(inputs[0], inputs[1],
                    inputs[2], Location.valueOf(inputs[3].toUpperCase())));
        }
        FitnessClass[] classes = classSchedule.getClasses();
        for(FitnessClass fitnessClass : classes){
            System.out.println(fitnessClass.toString());
        }
        System.out.println("-end of class list-");
    }

    /**
     * Resets the scanner with specified file name.
     * @param fileName file name to reset the scanner with.
     * @return a Scanner object that uses the fileName.
     */
    private Scanner resetScanner(String fileName){
        try {
            return new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called to add a member to the database. If a member enters an invalid
     * calendar date for date of birth, a date of birth in the future, a
     * member who's under 18 years of age, an invalid calendar date for
     * expiration date, or an invalid gym location the method will return
     * and print the reason for terminating; Otherwise, the member will be
     * added to the database.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param loc the member's gym location.
     */
    private void addMember(String fname, String lname, Date dob, String loc){

        Date now = new Date();

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
        }
        //if(now.getMonth() + 3)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date expire = new Date(cal);
        Location location;
        try{    location = Location.valueOf(loc.toUpperCase());   }
        catch (Exception e){
            System.out.println(loc + ": invalid location!");
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
            for(FitnessClass fitnessClass : classSchedule.getClasses()){
                if(fitnessClass.memberExists(memberToRemove)){
                    fitnessClass.unCheckInMember(memberToRemove);
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
     * members/guests that are checked in. For each fitness class, its class
     * name, instructor, and class time will be printed.
     */
    private void printFitnessClassSchedule(){
        if(classSchedule == null || classSchedule.getNumClasses() == 0){
            System.out.println("Fitness class schedule is empty.");
            return;
        }
        System.out.println("-Fitness classes-");
        for (FitnessClass fitnessClass : classSchedule.getClasses()) {
            DecimalFormat df1 = new DecimalFormat("00");
            System.out.print(fitnessClass.getClassName() + " - " +
                    fitnessClass.getInstructor() + ", "
                    + fitnessClass.getTime().getHour()
                    + ":" + df1.format(fitnessClass.getTime().getMinute())
                    + ", " + fitnessClass.getLocation().getTown());
            if(fitnessClass.getMembersCheckedIn().size() +
                    fitnessClass.getGuests().size() > 0) {
                System.out.println(printClass(fitnessClass));
            } else{
                System.out.println();
            }
        }
        System.out.println("-end of class list-");
    }

    /**
     * Prints the fitness classes participants. This includes the members
     * and the guests.
     * @param fitnessClass The fitness class to be represented by a string.
     * @return a string representing the fitness class' members and guests.
     */
    private String printClass(FitnessClass fitnessClass){
        String output = "";
        if(fitnessClass.getSizeCheckedIn() > 0) {
            output += "\n- Participants -";
            for (Member member : fitnessClass.getMembersCheckedIn()) {
                output += ("\n\t" + member.toString());
            }
        }
        if(fitnessClass.getGuests().size() > 0){
            output += "\n- Guests -";
            for(Guest guest : fitnessClass.getGuests()){
                output += ("\n\t" + guest.toString());
            }
        }
        return output;
    }

    /**
     * Checks in a member to a fitness class. If the date of birth input is
     * invalid, the member does not exist, the member's expiration date is
     * expired, or class name inputted does not exist the method will return
     * and the user will be notified why.
     * @param inputs command inputs used to create a member object.
     */
    private void checkInMember(String[] inputs){
        Member member = new Member(inputs[4], inputs[5],
                new Date(inputs[6]), null, null);
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
        String className = inputs[1],
                instructor = inputs[2],
                town = inputs[3];
        if(errorJoinOrLeave(className, instructor, town)){
            return;
        } else if(member.getExpire().compareTo(new Date()) > 0){
            System.out.println(member.getFname() + " " + member.getLname()
                    + " " + member.getDob() + " membership expired.");
            return;
        }
        System.out.println(getMemberJoinClassResponse(className, instructor, town, member));
    }

    /**
     * Called to add a member to a fitness class given they meet all
     * the qualifications to. This class will return a string based on
     * whether the member was able to join the class.
     * @param member member that is requested to be checked in.
     * @param className class name the member wishes to join.
     * @param town town of the gym location.
     * @param instructor instructor of the class.
     * @return a string representing a confirmation or error joining
     * message
     */
    private String getMemberJoinClassResponse(String className, String instructor,
                                              String town, Member member){
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(className,
                instructor, "morning", Location.valueOf(town.toUpperCase())));
        if(classToJoin == null)
            return className + " by " + instructor + " does not exist at "
                    + town;
        else if(classToJoin.memberExists(member))
            return member.getFullName() + " already checked in.";
        else if (!classToJoin.getLocation().getTown()
                .equalsIgnoreCase(member.getLocation().getTown())
                && !(member instanceof Family)){
            return member.getFullName() + " checking in "
                    + Location.valueOf(town.toUpperCase()).toString() + " - standard membership " +
                    "location restriction.";
        }
        Time timeOfClass = classToJoin.getTime();
        if(isTimeConflict(member, classToJoin))
            return String.format(("Time conflict - %s - %s, %s, %s"),
                    className.toUpperCase(), instructor.toUpperCase(),
                    timeOfClass.toString(), classToJoin.getLocation().toString());
        classToJoin.checkInMember(member);
        return String.format("%s checked in %s - %s, %s, %s%s", member.getFullName(),
                classToJoin.getClassName(), classToJoin.getInstructor(),
                classToJoin.getTime().toString(), classToJoin.getLocation().getTown(),
                printClass(classToJoin));
    }

    /**
     * Checks in a guest to a fitness class. Prints success or failure
     * to join class reason.
     * @param inputs The parent member of the guest that wishes to join the
     *               class.
     */
    private void checkInGuest(String[] inputs){
        String fname = inputs[4], lname = inputs[5];
        Date dob = new Date(inputs[6]);
        Member member = new Member(fname, lname, dob, null);
        Member[] mlist =database.getMlist();
        for(int i = 0; i < database.getSize(); ++i){
            if(mlist[i].equals(member)){
                member = mlist[i];
                break;
            }
        }
        if(!(member instanceof  Family)){
            System.out.println("Standard membership - guest check-in is not allowed.");
            return;
        }
        else if(member.getGuestPasses() == 0){
            System.out.println(member.getFullName() + " ran out of guest passes.");
            return;
        }
        Location joinLocation = Location.valueOf(inputs[3].toUpperCase());
        if(!member.getLocation().equals(joinLocation)){
            System.out.printf("%s Guest checking in %s - guest location restriction.\n",
                    member.getFullName(), joinLocation.toString());
            return;
        }
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(
                inputs[1], inputs[2], "morning", joinLocation));
        Family fam = (Family) member;
        classToJoin.checkInGuest(fam);
        System.out.printf("%s (guest) checked in %s - %s, %s, %s",
                fam.getFullName(), classToJoin.getClassName(),
                classToJoin.getInstructor(), classToJoin.getTime().toString(),
                classToJoin.getLocation().getTown());
        fam.useGuestPass();
        System.out.println(printClass(classToJoin));
    }

    /**
     * Checks out a guest from a fitness class. Called with the assumption
     * the guest is present in the fitness class.
     * @param inputs command inputs used to create a member object.
     */
    private void checkOutGuest(String[] inputs){
        String fname = inputs[4], lname = inputs[5];
        Date dob = new Date(inputs[6]);
        Member member = new Member(fname, lname, dob, null);
        Location joinLocation = Location.valueOf(inputs[3].toUpperCase());
        FitnessClass classToLeave = classSchedule.exists(new FitnessClass(
                inputs[1], inputs[2], "morning", joinLocation));
        Member[] mlist =database.getMlist();
        for(int i = 0; i < database.getSize(); ++i){
            if(mlist[i].equals(member)){
                member = mlist[i];
                break;
            }
        }
        classToLeave.checkOutGuest(member);
        Family fam = (Family) member;

        fam.addGuestPass();
        System.out.printf("%s Guest done with the class.%n", member.getFullName());
    }

    /**
     * Determines if there is a time conflict for a member joining a class.
     * There is a time conflict if the member is checked into another
     * fitness class that happens during the same time slot.
     * @param member Member to be evaluated.
     * @param newClass fitness class the member wishes to join.
     * @return true if there is a time conflict; false otherwise.
     */
    private boolean isTimeConflict(Member member, FitnessClass newClass){
        for(FitnessClass fitnessClass : classSchedule.getClasses())
            if(fitnessClass.memberExists(member)
                    && fitnessClass.getTime().equals(newClass.getTime()))
                return true;
        return false;
    }

    /**
     *  Checks out a member from a fitness class. If the date of birth
     *  input is invalid, member is not checked in, or the class name
     *  inputted does not exist the method will return and the user
     *  will be notified why.
     * @param inputs command inputs used to create a member object.
     */
    private void checkOutMember(String[] inputs){
        Member member = new Member(inputs[4], inputs[5],
                new Date(inputs[6]), null, null);
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
        String className = inputs[1],
                instructor = inputs[2],
                town = inputs[3];
        if(errorJoinOrLeave(className, instructor, town)){
            return;
        }
        System.out.println(memberLeaveClassResponse(className, instructor, town, member));
    }

    /**
     * Gets the response for when a member tries to leave a class.
     * @param className class name of the fitness class.
     * @param instructor instructor of the fitness class.
     * @param town town of the fitness class.
     * @param member member of the fitness class.
     * @return a String representing the success or failure of
     * the member attempting to leave the class.
     */
    private String memberLeaveClassResponse(String className, String instructor,
                                 String town, Member member){
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(className,
                instructor, "morning", Location.valueOf(town.toUpperCase())));
        if(!classToJoin.memberExists(member))
            return member.getFullName() + " did not check in.";
        classToJoin.unCheckInMember(member);
        return String.format("%s done with class.", member.getFullName());
    }

    /**
     * Gets the error message for a member joining or leaving a fitness
     * class.
     * @param className class name of the fitness class.
     * @param instructor instructor of the fitness class.
     * @param town town of the fitness class.
     * @return true if there was an error joining the class; false
     * otherwise.
     */
    private boolean errorJoinOrLeave(String className, String instructor,
                                 String town){
        try{    Location.valueOf(town.toUpperCase());}
        catch (IllegalArgumentException e) {
            System.out.println(town + " - invalid location.");
            return true;
        }
        if(!classSchedule.isValidClass(new FitnessClass(className, "",
                "morning",null))){
            System.out.println(className + " - class does not exist.");
            return true;
        } else if(!classSchedule.isValidInstructor(new FitnessClass("",
                instructor, "morning", null))){
            System.out.println(instructor + " - instructor does not exist.");
            return true;
        }
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(className,
                instructor, "morning", Location.valueOf(town.toUpperCase())));
        if(classToJoin == null) {
            System.out.println(className + " by " + instructor + " does not exist at "
                    + town);
            return true;
        }
        return false;
    }
}
