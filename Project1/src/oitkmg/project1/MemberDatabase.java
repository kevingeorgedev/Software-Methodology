package oitkmg.project1;

/**
 * Class is used to create a database that holds the members of the gym.
 * Gives user the ability to add and remove members from the database
 * creates an array that holds the ammount of members Sorts members by
 * location, zip code, member expieriation date and first and last name
 * @author Kevin George, Omar Talaat
 */
public class MemberDatabase {
    private Member [] mlist;
    private int size;

    /**
     * Finds the index of a member in the Member List.
     * @param member Member object to be searched for.
     * @return an integer representating the members
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
        } //Member list is empty, must be initiallized
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
     */
    public void print () {
        if(size == 0){
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println("-list of members-");
        for(int i = 0; i < size; ++i){
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");
    }

    /**
     * Print all members of the Member database in order by
     * county, then zipcode.
     */
    public void printByCounty() {
        if(size == 0){
            System.out.println("Member database is empty!");
            return;
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

        System.out.println("-list of members sorted by county and zipcode-");
        for(int i = 0; i < size; ++i){
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");

    }

    /**
     * Print all members of the Member database in order by
     * their expiration dates.
     */
    public void printByExpirationDate() {
        if(size == 0){
            System.out.println("Member database is empty!");
            return;
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

        System.out.println("-list of members sorted by membership " +
                "expiration date-");
        for(int i = 0; i < size; ++i){
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");
    }

    /**
     * Print all members of the Member database in order by
     * their last names, then first names.
     */
    public void printByName() {
        if(size == 0){
            System.out.println("Member database is empty!");
            return;
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

        System.out.println("-list of members sorted by last name, and first name-");
        for(int i = 0; i < size; ++i){
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");
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