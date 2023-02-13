package oitkmg.project3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Class used to handle the member database, class schedule, and the events
 * of the GUI. Interactions with the GUI are handled in this class.
 * @author Kevin George, Omar Talaat
 */
public class GymManagerController{

    /**
     * Text box that will display command usage logs.
     */
    @FXML private TextArea resultMessage;

    /**
     * Radiobutton groups.
     */
    @FXML private ToggleGroup locations1, membershipType1, checkGroup, className, instructorName, locations2;

    /**
     * Fitness Class location radio buttons.
     */
    @FXML private RadioButton bridgewater, edison, franklin, piscataway, somerville;

    /**
     * Text fields to enter corresponding information.
     */
    @FXML private TextField firstNameTextField, lastNameTextField,
            firstNameTextField2, lastNameTextField2;

    /**
     * DatePicker's used to accurately choose a valid date.
     */
    @FXML private DatePicker date, datePicker2;

    /**
     * Menu items for the Information tab.
     */
    @FXML private MenuItem printMenuItem, printByNameMenuItem,
            printByCountyMenuItem, printByExpirationMenuItem, loadMembersMenuItem,
            displayClassesMenuItem, loadClassesMenuItem, printMemFeesMenuItem;

    /**
     * Database that holds all the members.
     */
    private MemberDatabase database;
    /**
     * Class schedule which holds all fitness classes.
     */
    private ClassSchedule classSchedule;

    /**
     * Default constructor for GymManagerController.
     */
    public GymManagerController(){

    }

    /**
     * Initialize the choice boxes and menu item events.
     */
    public void initialize() {
        locations2 = new ToggleGroup();
        bridgewater.setToggleGroup(locations2);
        edison.setToggleGroup(locations2);
        franklin.setToggleGroup(locations2);
        piscataway.setToggleGroup(locations2);
        somerville.setToggleGroup(locations2);
        resultMessage.setScrollTop(Double.MAX_VALUE);
        database = new MemberDatabase();
        initializeMemberDatabaseMenu();
        loadMembersMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.loadMembers();
                addResult(false, "Legacy members loaded.");
            }
        });
        classSchedule = new ClassSchedule();
        initializeClassScheduleMenu();
        loadClassesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                classSchedule.loadClassSchedule();
                addResult(false, "Fitness Class Schedule Loaded. " +
                        "Go to Information Hub > Class Schedule > Show all classes" +
                        " to view schedule.");
            }
        });
    }

    /**
     * Prints the fitness class schedule and its corresponding
     * members/guests that are checked in. For each fitness class, its class
     * name, instructor, and class time will be printed.
     * @return a String representing the fitness class schedule.
     */
    private String printFitnessClassSchedule(){
        String msg = "";
        if(classSchedule == null || classSchedule.getNumClasses() == 0){
            return("Fitness class schedule is empty.");
        }
        msg += ("-Fitness classes-\n");
        for (FitnessClass fitnessClass : classSchedule.getClasses()) {
            DecimalFormat df1 = new DecimalFormat("00");
            msg += (fitnessClass.getClassName() + " - " +
                    fitnessClass.getInstructor() + ", "
                    + fitnessClass.getTime().getHour()
                    + ":" + df1.format(fitnessClass.getTime().getMinute())
                    + ", " + fitnessClass.getLocation().getTown());
            if(fitnessClass.getMembersCheckedIn().size() +
                    fitnessClass.getGuests().size() > 0) {
                msg += (printClass(fitnessClass) + "\n");
            } else{
                msg += "\n";
            }
        }
        msg += ("-end of class list-");
        return msg;
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
     * Initialize the class schedule from Information tab.
     */
    private void initializeClassScheduleMenu(){
        displayClassesMenuItem.setOnAction(event -> addResult(false, printFitnessClassSchedule()));
    }

    /**
     * Initialize the Member Database menu items.
     */
    private void initializeMemberDatabaseMenu(){
        printMenuItem.setOnAction(event -> addResult(false, database.print()));
        printByNameMenuItem.setOnAction(event -> addResult(false, database.printByName()));
        printByCountyMenuItem.setOnAction(event -> addResult(false, database.printByCounty()));
        printByExpirationMenuItem.setOnAction(event -> addResult(false, database.printByExpirationDate()));
        printMemFeesMenuItem.setOnAction(event -> addResult(false, database.printWithFees()));
    }

    /**
     * Handles the add button on the Membership tab.
     */
    @FXML
    private void onAddButtonClick() {
        if(checkMissingFields()) return;
        Date dob = new Date(date.getValue());
        String membership = ((RadioButton) membershipType1
                .getSelectedToggle()).getText();
        if(!checkDate(dob))
            return;
        else if(!dob.isValid()){
            addResult(true, "Invalid calendar date!");
            return;
        }
        else if(membership.equalsIgnoreCase("standard")){
            addMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    ((RadioButton) locations1.getSelectedToggle()).getText());
        } else if(membership.equalsIgnoreCase("family")){
            addFamilyMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    ((RadioButton) locations1.getSelectedToggle()).getText());
        } else if(membership.equalsIgnoreCase("premium")){
            addPremiumMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    ((RadioButton) locations1.getSelectedToggle()).getText());
        }
    }

    /**
     * Handles the remove button on the Membership tab.
     */
    @FXML
    private void onRemoveButtonClick(){
        if(checkMissingFields()) return;
        Date dob = new Date(date.getValue());
        String membership = ((RadioButton) membershipType1
                .getSelectedToggle()).getText();
        if(!checkDate(dob)) return;
        else if(!dob.isValid())
            addResult(true, "Invalid calendar date!");
        else if(membership.equalsIgnoreCase("standard")){
            removeMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    Location.valueOf(((RadioButton) locations1
                            .getSelectedToggle()).getText().toUpperCase()));
        } else if(membership.equalsIgnoreCase("family")){
            removeMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    Location.valueOf(((RadioButton) locations1
                            .getSelectedToggle()).getText().toUpperCase()));
        } else if(membership.equalsIgnoreCase("premium")){
            removeMember(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    dob,
                    Location.valueOf(((RadioButton) locations1
                            .getSelectedToggle()).getText().toUpperCase()));
        }
    }

    /**
     * Called to remove a member from the database. If the member is not in
     * the database then the user will be notified. If the member is in the
     * database then the member will be checked out of all fitness classes
     * too.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param location the member's gym location.
     */
    private void removeMember(String fname, String lname, Date dob, Location location){
        Member memberToRemove = new Member(fname, lname, dob, null, location);
        if(database.remove(memberToRemove)){
            if(classSchedule.getNumClasses() != 0){
                for(FitnessClass fitnessClass : classSchedule.getClasses()){
                    if(fitnessClass.memberExists(memberToRemove)){
                        fitnessClass.unCheckInMember(memberToRemove);
                    }
                }
            }
            addResult(false, memberToRemove.getFullName()
                    + " removed.");
        }
        else{
            addResult(true, memberToRemove.getFullName()
                    + " is not in the database.");
        }
    }

    /**
     * Tests the date for today/future date and if the date is 18 years or
     * older.
     * @param dob Date of birth to be tested.
     * @return true if the date passed the tests; false otherwise.
     */
    private boolean checkDate(Date dob){
        if((new Date()).compareTo(dob) >= 0) {
            addResult(true, "Date of Birth: cannot be today " +
                    "or a future date.");
            return false;
        } else if(!isEighteenOrOlder(dob, new Date())) {
            addResult(true, "Date of Birth: must be 18 years " +
                    "or older.");
            return false;
        }
        return true;
    }

    /**
     * Checks for missing fields before adding member to a fitness class.
     * @return true if there are missing fields; false otherwise.
     */
    private boolean checkMissingFieldsFitnessClass(){
        String msg = "ERROR - please enter missing field(s):";
        if(className.getSelectedToggle() == null)
            msg += (" class name,");
        if(instructorName.getSelectedToggle() == null)
            msg += (" instructor,");
        if(locations2.getSelectedToggle() == null)
            msg += (" location,");
        if(firstNameTextField2.getLength() == 0)
            msg += (" first name,");
        if(lastNameTextField2.getLength() == 0)
            msg += (" last name,");
        if(datePicker2.getValue() == null)
            msg += (" date of birth,");
        if(!msg.equalsIgnoreCase("ERROR - please enter missing field(s):")){
            msg = msg.substring(0, msg.length() - 1);
            addResult(true, msg);
            return true;
        } else{
            return false;
        }

    }

    /**
     * Checks for missing fields before adding member to database.
     * @return true if there are missing fields; false otherwise.
     */
    private boolean checkMissingFields(){
        String msg = "ERROR - please enter missing field(s):";
        if(firstNameTextField.getLength() == 0)
            msg += (" first name,");
        if(lastNameTextField.getLength() == 0)
            msg += (" last name,");
        if(date.getValue() == null)
            msg += (" date of birth,");
        if(locations1.getSelectedToggle() == null)
            msg += (" location,");
        if(membershipType1.getSelectedToggle() == null)
            msg += (" membership type,");
        if(!msg.equalsIgnoreCase("ERROR - please enter missing field(s):")){
            msg = msg.substring(0, msg.length() - 1);
            addResult(true, msg);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Handles the check-in button on the fitness class tab.
     */
    @FXML
    private void onCheckInButtonClick(){
        if(checkMissingFieldsFitnessClass()) return;
        else if(((RadioButton) checkGroup.getSelectedToggle()).getText()
                .equalsIgnoreCase("member")){
            checkInMember();
        } else{
            checkInGuest();
        }
    }

    /**
     * Handles the check-out button on the fitness class tab.
     */
    @FXML
    private void onCheckOutButtonClick(){
        if(checkMissingFieldsFitnessClass()) return;
        else if(((RadioButton) checkGroup.getSelectedToggle()).getText()
                .equalsIgnoreCase("member")){
            checkOutMember();
        } else{
            checkOutGuest();
        }
    }

    /**
     * Checks out a guest from a fitness class. If member is not in
     * the class or the database nothing will happen.
     */
    private void checkOutGuest(){
        Date dob = new Date(datePicker2.getValue());
        if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. How to " +
                    "initialize schedule: Information Hub > Class Schedule > " +
                    "Load class schedule");
            return;
        } else if(!checkDate(dob)){
            return;
        } else if(!dob.isValid()){
            addResult(true, "Invalid calendar date!");
            return;
        }
        Location joinLocation = Location.valueOf(((RadioButton) locations2
                .getSelectedToggle()).getText().toUpperCase());
        FitnessClass classToLeave = classSchedule.exists(new FitnessClass(
                ((RadioButton) className.getSelectedToggle()).getText(),
                ((RadioButton) instructorName.getSelectedToggle()).getText(),
                "morning", joinLocation));
        Member[] mlist = database.getMlist();
        Member member = memberExists(new Member(firstNameTextField2.getText(),
                lastNameTextField2.getText(), dob, null));
        if(member == null) return;
        else if(classToLeave.checkOutGuest(member)){
            addResult(false, String.format("%s Guest done with the class.",
                    member.getFullName()));
            Family fam = (Family) member;
            fam.addGuestPass();
        } else{
            addResult(true, String.format("%s Guest has not checked into class.",
                    member.getFullName()));
        }
    }

    /**
     *  Checks out a member from a fitness class. If the date of birth
     *  input is invalid, member is not checked in, or the class name
     *  inputted does not exist the method will return and the user
     *  will be notified why.
     */
    private void checkOutMember(){
        if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. How to " +
                    "initialize schedule: Information Hub > Class Schedule > " +
                    "Load class schedule");
            return;
        }
        Date dob = new Date(datePicker2.getValue());
        if(!checkDate(dob)){
            return;
        } else if(!dob.isValid()){
            addResult(true, "Invalid calendar date!");
            return;
        }
        Member[] mlist = database.getMlist();
        Member member = memberExists(new Member(firstNameTextField2.getText(),
                lastNameTextField2.getText(), dob, null));
        if(member == null) return;
        String classNamex = ((RadioButton) className.getSelectedToggle()).getText(),
                instructor = ((RadioButton) instructorName.getSelectedToggle()).getText(),
                town = ((RadioButton) locations2.getSelectedToggle()).getText();
        memberLeaveClassResponse(classNamex, instructor, town, member);
    }

    /**
     * Gets the response for when a member tries to leave a class.
     * @param className class name of the fitness class.
     * @param instructor instructor of the fitness class.
     * @param town town of the fitness class.
     * @param member member of the fitness class.
     */
    private void memberLeaveClassResponse(String className, String instructor,
                                            String town, Member member){
        if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. " +
                    "How to initialize schedule: Information Hub > " +
                    "Class Schedule > Load class schedule");
            return;
        }
        FitnessClass classToJoin = classSchedule
                .exists(new FitnessClass(className,
                instructor, "morning", Location.valueOf(town.toUpperCase())));
        if(!classToJoin.memberExists(member)) {
            addResult(true, member.getFullName() + " did not check in.");
            return;
        }
        classToJoin.unCheckInMember(member);
        addResult(false, String.format("%s done with class.", member.getFullName()));
    }

    /**
     * Checks in a guest to a fitness class. Prints success or failure
     * to join class reason.
     */
    private void checkInGuest(){
        Date dob = new Date(datePicker2.getValue());
        if(!checkDate(dob)){
            return;
        } else if(!dob.isValid()){
            addResult(true, "Invalid calendar date!");
            return;
        } else if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. How to " +
                    "initialize schedule: Information Hub > Class Schedule > " +
                    "Load class schedule");
            return;
        }
        Member[] mlist = database.getMlist();
        Member member = memberExists(new Member(firstNameTextField2.getText(),
                lastNameTextField2.getText(), dob, null));
        if(guestCheckOutError(member)) return;
        Location joinLocation = Location.valueOf(((RadioButton) locations2
                .getSelectedToggle()).getText().toUpperCase());
        if(!member.getLocation().equals(joinLocation)){
            addResult(true, String.format("%s Guest checking in %s - guest" +
                            " location restriction.\n",
                    member.getFullName(), joinLocation.toString()));
            return;
        }
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(
                ((RadioButton) className.getSelectedToggle()).getText(),
                ((RadioButton) instructorName.getSelectedToggle()).getText(),
                "morning", joinLocation));
        Family fam = (Family) member;
        classToJoin.checkInGuest(fam);
        addResult(false,String.format("%s (guest) checked in %s - %s, %s, %s",
                fam.getFullName(), classToJoin.getClassName(),
                classToJoin.getInstructor(), classToJoin.getTime().toString(),
                classToJoin.getLocation().getTown()));
        fam.useGuestPass();
    }

    /**
     * Checks for a problem checking out a guest from a fitness class.
     * @param member Parent member for the guest.
     * @return true if there was an error; false otherwise.
     */
    private boolean guestCheckOutError(Member member){
        if(member == null) return true;
        else if(!(member instanceof Family)){
            addResult(true, "Standard membership - guest check-in is " +
                    "not allowed.");
            return true;
        }
        else if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. How to " +
                    "initialize schedule: Information Hub > Class Schedule > " +
                    "Load class schedule");
            return true;
        }
        else if(member.getGuestPasses() == 0){
            addResult(true, member.getFullName() + " ran out of guest " +
                    "passes.");
            return true;
        }
        return false;
    }

    /**
     * Checks if member exists in the database.
     * @param member Member to check if exists.
     * @return the Member that exists; null if member is not present.
     */
    private Member memberExists(Member member){
        Member[] mlist = database.getMlist();
        if(database.getSize() == 0){
            addResult(true, member.getFname() + " " + member.getLname()
                    + " " + member.getDob().toString()
                    + " is not in the database.");
        }
        else{
            for(int i = 0; i < database.getSize(); ++i){
                if(mlist[i].equals(member)){
                    return mlist[i];
                } else if(i == database.getSize() - 1){
                    addResult(true, member.getFname() + " " + member.getLname()
                            + " " + member.getDob().toString()
                            + " is not in the database.");
                }
            }
        }
        return null;
    }

    /**
     * Checks in a member to a fitness class. If the date of birth input is
     * invalid, the member does not exist, the member's expiration date is
     * expired, or class name inputted does not exist the method will return
     * and the user will be notified why.
     */
    private void checkInMember(){
        Date dob = new Date(datePicker2.getValue());
        if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. How to " +
                    "initialize schedule: Information Hub > Class Schedule > " +
                    "Load class schedule");
            return;
        } else if(!checkDate(dob))
            return;
        else if(!dob.isValid()){
            addResult(true, "Invalid calendar date!");
            return;
        }
        Member[] mlist = database.getMlist();
        Member member = memberExists(new Member(firstNameTextField2
                .getText(),
                lastNameTextField2.getText(),
                dob, null, null));
        String classNamex = ((RadioButton) className
                .getSelectedToggle()).getText(),
                instructor = ((RadioButton) instructorName
                        .getSelectedToggle()).getText(),
                town = ((RadioButton) locations2
                        .getSelectedToggle()).getText();
        if(member == null) return;
        else if(member.getExpire().compareTo(new Date()) > 0){
            addResult(true, member.getFname() + " " +
                    member.getLname()
                    + " " + member.getDob() + " membership expired.");
            return;
        }
        getMemberJoinClassResponse(classNamex, instructor, town, member);
    }

    /**
     * Called to add a member to a fitness class given they meet all
     * the qualifications to. This class will display a string based on
     * whether the member was able to join the class.
     * @param member member that is requested to be checked in.
     * @param className class name the member wishes to join.
     * @param town town of the gym location.
     * @param instructor instructor of the class.
     * message
     */
    private void getMemberJoinClassResponse(String className, String instructor,
                                              String town, Member member){
        if(classSchedule.getNumClasses() == 0){
            addResult(true, "Fitness class schedule is empty. " +
                    "How to initialize schedule: Information Hub > Class " +
                    "Schedule > Load class schedule");
            return;
        }
        FitnessClass classToJoin = classSchedule.exists(new FitnessClass(className,
                instructor, "morning", Location.valueOf(town.toUpperCase())));
        if(classToJoin == null) {
            addResult(true, className + " by " + instructor + " does not exist at "
                    + town);
            return;
        }
        if(classToJoin.memberExists(member)) {
            addResult(true, member.getFullName() + " already checked in.");
            return;
        }
        else if (!classToJoin.getLocation().getTown()
                .equalsIgnoreCase(member.getLocation().getTown())
                && !(member instanceof Family)){
            addResult(true, member.getFullName() + " checking in "
                    + Location.valueOf(town.toUpperCase()).toString() +
                    " - standard membership " + "location restriction.");
            return;
        }
        Time timeOfClass = classToJoin.getTime();
        if(isTimeConflict(member, classToJoin)) {
            addResult(true, String.format(("Time conflict - %s - %s, %s, %s"),
                    className.toUpperCase(), instructor.toUpperCase(),
                    timeOfClass.toString(), classToJoin.getLocation().toString()));
            return;
        }
        classToJoin.checkInMember(member);
        addResult(false, String.format("%s checked in %s - %s, %s, %s",
                member.getFullName(),
                classToJoin.getClassName(), classToJoin.getInstructor(),
                classToJoin.getTime().toString(), classToJoin.getLocation()
                        .getTown()));
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
     * Called to add a member to the database. The method will return
     * and print the reason for terminating; Otherwise, the member will be
     * added to the database.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param loc the member's gym location.
     */
    private void addMember(String fname, String lname, Date dob, String loc){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date expire = new Date(cal);
        Location location = Location.valueOf(loc.toUpperCase());
        Member member = new Member(fname, lname, dob, expire, location);
        if(database.add(member)) {
            addResult(false,member.getFullName() + " added (Standard " +
                    "Membership)." );
        }
        else {
            addResult(true, member.getFullName() + " is already in the " +
                    "database.");
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
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date expire = new Date(cal);
        Location location = Location.valueOf(loc.toUpperCase());
        Premium member = new Premium(fname, lname, dob, expire, location);
        if(database.add(member)) {
            addResult(false,member.getFullName() + " added (Premium " +
                    "Membership)." );
        }
        else {
            addResult(true, member.getFullName() + " is already in the " +
                    "database.");
        }
    }

    /**
     * Add member with the family membership.
     * @param fname the member's first name.
     * @param lname the member's last name.
     * @param dob the member's date of birth.
     * @param loc the member's gym location.
     */
    private void addFamilyMember(String fname, String lname, Date dob, String loc){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date expire = new Date(cal);
        Location location = Location.valueOf(loc.toUpperCase());
        Family member = new Family(fname, lname, dob, expire, location);
        if(database.add(member)) {
            addResult(false, member.getFullName() + " added (Family " +
                    "Membership).");
        }
        else {
            addResult(true, member.getFullName() + " is already in the " +
                    "database.");
        }
    }

    /**
     * Appends to the text box that displays command logs.
     * @param isRed true if the message box should be red; green otherwise.
     * @param msg String to be appended.
     */
    private void addResult(boolean isRed, String msg){
        if(isRed){
            resultMessage.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else{
            resultMessage.setBorder(new Border(new BorderStroke(Color.GREEN,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        if(resultMessage.getText().length() == 0){
            resultMessage.setText(msg);
        } else{
            resultMessage.appendText("\n" + msg);
        }
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
}