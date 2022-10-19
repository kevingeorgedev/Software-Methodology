package oitkmg.project2;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Used to test the calculation of the membership fee for a
 * Premium member.
 * @author Kevin George, Omar Talaat
 */
public class PremiumTest {
    /**
     * Test membership fee for premium member.
     */
    @Test
    public void test_membershipFee() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1);
        Premium p = new Premium("John", "Doe", new Date("01/01/2000"),
                new Date(cal), Location.BRIDGEWATER);
        double expectedOutput = 659.89;
        double actualOutput = p.membershipFee();
        assertEquals(expectedOutput, actualOutput, 0);
    }
}