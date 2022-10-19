package oitkmg.project2;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

/**
 * Used to test checking in and out a member/guest.
 * @author Kevin George, Omar Talaat
 */
public class FitnessClassTest {
    /**
     * Test checking in and checking out a member from a test fitness class.
     */
    @Test
    public void test_check_in_out_member(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1);
        String fname = "John", lname = "Doe";
        Date dob = new Date("01/01/2000"), expire = new Date(cal);
        Location loc = Location.BRIDGEWATER;
        Member m = new Member(fname, lname, dob, expire, loc);
        boolean expectedOutput = true;
        FitnessClass fitnessClass = new FitnessClass("Pilates",
                "Jennifer", "morning", loc);
        fitnessClass.checkInMember(m);
        boolean actualOutput = fitnessClass.memberExists(m);
        assertEquals(expectedOutput, actualOutput);

        expectedOutput = false;
        fitnessClass.unCheckInMember(m);
        actualOutput = fitnessClass.memberExists(m);
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test guest check in and check out.
     */
    @Test
    public void test_check_in_out_guest(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1);
        String fname = "John", lname = "Doe";
        Date dob = new Date("01/01/2000"), expire = new Date(cal);
        Location loc = Location.BRIDGEWATER;
        Premium m = new Premium(fname, lname, dob, expire, loc);
        Guest g = new Guest(m);
        boolean expectedOutput = true;
        FitnessClass fitnessClass = new FitnessClass("Pilates",
                "Jennifer", "morning", loc);
        fitnessClass.checkInGuest(m);
        Guest[] guests = fitnessClass.getGuests().toArray(new Guest[0]);
        boolean actualOutput = false;
        for (Guest guest : guests) {
            if (g.equals(guest)) {
                actualOutput = true;
                break;
            }
        }
        assertEquals(expectedOutput, actualOutput);

        expectedOutput = false;
        fitnessClass.checkOutGuest(m);
        guests = fitnessClass.getGuests().toArray(new Guest[0]);
        actualOutput = false;
        for(int i = 0; i < guests.length; ++i){
            if(g.equals(guests[i])){
                actualOutput = true;
                break;
            }
        }
        assertEquals(expectedOutput, actualOutput);
    }
}