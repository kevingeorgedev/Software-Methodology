package oitkmg.project2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class is used to test the isValid() method of the Date class.
 * @author Kevin George, Omar Talaat
 */
public class DateTest {

    /**
     * Test valid date.
     */
    @Test
    public void test_valid_date(){
        Date d2 = new Date("1/1/2000");
        assertTrue(d2.isValid());
        boolean expectedOutput = true;
        boolean actualOutput = d2.isValid();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     *Test days for a non leap year in February
     */
    @Test
    public void test_days_in_Feb_nonLeap(){
        Date d1 = new Date("2/29/2011");
        assertFalse(d1.isValid());
        boolean expectedOutput = false;
        boolean actualOutput = d1.isValid();
        assertEquals(expectedOutput, actualOutput);

        Date d2 = new Date("2/28/2013");
        assertTrue(d2.isValid());
        expectedOutput = true;
        actualOutput = d2.isValid();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test valid range for the month.
     */
    @Test
    public void test_month_range(){
        Date d1 = new Date("-1/2/2000");
        assertFalse(d1.isValid());
        boolean expectedOutput = false;
        boolean actualOutput = d1.isValid();
        assertEquals(expectedOutput, actualOutput);

        Date d2 = new Date("13/1/2000");
        assertFalse(d2.isValid());
        actualOutput = d2.isValid();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test valid day range for the month.
     */
    @Test
    public void test_days_in_month(){
        Date d1 = new Date("1/32/2000");
        assertFalse(d1.isValid());
        boolean expectedOutput = false;
        boolean actualOutput = d1.isValid();
        assertEquals(expectedOutput, actualOutput);

        Date d2 = new Date("9/31/2000");
        assertFalse(d2.isValid());
        actualOutput = d2.isValid();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test negative month or year.
     */
    @Test
    public void test_negative_year_or_month(){
        Date d1 = new Date("1/-3/0");
        assertFalse(d1.isValid());
        boolean expectedOutput = false;
        boolean actualOutput = d1.isValid();
        assertEquals(expectedOutput, actualOutput);

        Date d2 = new Date("1/1/-1");
        assertFalse(d2.isValid());
        actualOutput = d2.isValid();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test valid leap year.
     */
    @Test
    public void test_valid_leap_year(){
        Date d1 = new Date("2/29/2020");
        assertTrue(d1.isValid());
        boolean expectedOutput = true;
        boolean actualOutput = d1.isValid();
        assertEquals(expectedOutput, actualOutput);
    }
}