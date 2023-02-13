package oitkmg.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class is used to create a database that holds the members of the gym.
 * Gives user the ability to add and remove members from the database
 * creates an array that holds the amount of members Sorts members by
 * location, zip code, member expiration date and first and last name
 * @author Kevin George, Omar Talaat
 */
public class MemberDatabase {

    /**
     * List of members in the database.
     */
    private Member [] mlist;

    /**
     * Size of the member database.
     */
    private int size;

    /**
     * Default constructor for Member Database
     */
    public MemberDatabase(){

    }

    /**
     * Load legacy members from member list text file.
     */
    public void loadMembers(){
        Scanner s;
        try {
            s = new Scanner(new File("src/main/memberList.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(s.hasNextLine()){
            String[] inputs = s.nextLine().replaceAll("\\s+", " ").split(" ");
            add(new Member(inputs[0], inputs[1], new Date(inputs[2]), new Date(inputs[3]), Location.valueOf(inputs[4].toUpperCase())));
        }
    }

    /**
     * Finds the index of a member in the Member List.
     * @param member Member object to be searched for.
     * @return an integer representation the members
     * index in the Member list, returns -1 if member
     * is not found.
     */
    private int find(Member member) {
        final int NOT_FOUND = -1;
        for(int i = 0; i < size; ++i){
            if(mlist[i].equals(member)) return i;
        }
        return NOT_FOUND;
    }

    /**
     * Creates an array to copy the content of the original to an
     * array with a size of 4 greater than the original.
     */
    private void grow() {
        Member[] copy = new Member[size + 4];
        for(int i = 0; i < size; ++i){
            copy[i] = mlist[i];
        }
        mlist = copy;
    }

    /**
     * Add new member to the Member database.
     * The new member is added to the end of the Member database.
     * If the Member database is full, its capacity increases by 4
     * before appending the new member.
     * @param member member to join the Member database.
     * @return true if member was successfully added; false if the
     * member exists in the Member database already.
     */
    public boolean add(Member member) {
        final int NOT_FOUND = -1;
        if(size == 0){
            mlist = new Member[4];
            mlist[0] = member;
            size++;
            return true;
        } //Member list is empty, must be initialized
        else if(find(member) == NOT_FOUND){
            if(size % 4 == 0){
                grow();
            } //Member list reached capacity, increment size by 4
            mlist[size] = member;
            size++;
            return true;
        }
        return false;
    }

    /**
     * Remove member from the database.
     * @param member parameter to track the value of the members
     *              in the database
     * @return true if member was successfully removed from the
     * Member database; false if the member does not exist in the
     * member database.
     */
    public boolean remove(Member member) {
        final int NOT_FOUND = -1;
        if(find(member) == NOT_FOUND) return false;

        Member[] copy = new Member[mlist.length];
        boolean passed = false;
        for(int i = 0; i < size; ++i){
            if(!passed && mlist[i].equals(member)){
                passed = true;
                continue;
            }
            else if(passed){
                copy[i-1] = mlist[i];
                continue;
            }
            copy[i] = mlist[i];
        }
        mlist = copy;
        size--;
        return true;
    }

    /**
     * Print all members of the Member database in the order
     * as is.
     * @return a String representing the members in the database
     * as is.
     */
    public String print () {
        String msg = "";
        if(size == 0){
            return "Member database is empty!";
        }
        msg += "-list of members-\n";
        for(int i = 0; i < size; ++i){
            msg += (mlist[i].toString() + "\n");
        }
        msg += "-end of list-";
        return msg;
    }

    /**
     * Print all members of the Member database in order by
     * county, then zipcode.
     * @return a String representing the members in the datebase
     * by county.
     */
    public String printByCounty() {
        String msg = "";
        if(size == 0){
            return ("Member database is empty!");
        }
        for(int i = 1; i < size; ++i){
            Member val = mlist[i];
            int j = i - 1;
            while(j >= 0 && mlist[j].getLocation().compareTo(val.getLocation()) > 0){
                mlist[j + 1] = mlist[j];
                j = j - 1;
            }
            mlist[j + 1] = val;
        }

        msg += ("-list of members sorted by county and zipcode-\n");
        for(int i = 0; i < size; ++i){
            msg += (mlist[i].toString() + "\n");
        }
        msg += ("-end of list-");
        return msg;
    }

    /**
     * Print all members of the Member database in order by
     * their expiration dates.
     * @return a String representing the members in the database
     * by expiration date.
     */
    public String printByExpirationDate() {
        String msg = "";
        if(size == 0){
            return("Member database is empty!");
        }

        for(int i = 1; i < size; ++i){
            Member val = mlist[i];
            int j = i - 1;
            while(j >= 0 && mlist[j].getExpire().compareTo(val.getExpire()) < 0){
                mlist[j + 1] = mlist[j];
                j = j - 1;
            }
            mlist[j + 1] = val;
        }

        msg += ("-list of members sorted by membership " +
                "expiration date-\n");
        for(int i = 0; i < size; ++i){
            msg += (mlist[i].toString() + "\n");
        }
        msg += ("-end of list-");
        return msg;
    }

    /**
     * Print all members of the Member database in order by
     * their last names, then first names.
     * @return a String representing the members in the database
     * by last name, first name.
     */
    public String printByName() {
        String msg = "";
        if(size == 0){
            return("Member database is empty!");
        }

        for(int i = 1; i < size; ++i){
            Member val = mlist[i];
            int j = i - 1;
            while(j >= 0 && mlist[j].compareTo(val) > 0){
                mlist[j + 1] = mlist[j];
                j = j - 1;
            }
            mlist[j + 1] = val;
        }

        msg += ("-list of members sorted by last name, and first name-\n");
        for(int i = 0; i < size; ++i){
            msg += (mlist[i].toString() + "\n");
        }
        msg += ("-end of list-");
        return msg;
    }

    /**
     * Print the members of the database with their membership fees.
     * @return a String representing the members in database with
     * Membership Fees.
     */
    public String printWithFees(){
        String msg = "";
        if(size == 0){
            return("Member database is empty!");
        }
        msg += ("-list of members with membership fees-\n");
        for(int i = 0; i < size; ++i){
            msg += (mlist[i].toString() + ", Membership fee: $" + mlist[i].membershipFee() + "\n");
        }
        msg += ("-end of list-");
        return msg;
    }

    /**
     * Gets the list containing all members in the Member database.
     * @return a Member array representing all Members of the database in
     * no specific order.
     */
    public Member[] getMlist(){
        return mlist;
    }

    /**
     * Gets the size of the Member database.
     * @return an integer representing the amount of members
     * in the Member database.
     */
    public int getSize(){
        return size;
    }
}