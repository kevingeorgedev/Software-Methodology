package oitkmg.project1;
import java.util.Calendar;

/**
 * Used to represent a date object.
 * A Date object represents a specific day, month, and year. The values
 * of a date object cannot be changed after initiallizing an instance
 * of Date.
 * @author Kevin George, Omar Talaat
 */
public class Date implements Comparable<Date> {
    private final int year;
    private final int month;
    private final int day;

    /**
     * Creates a Date object using today's date. The day, month,
     * and year are equal to the current date's
     * day, month, and year.
     */
    public Date() {
        Calendar calender = Calendar.getInstance();
        this.month = calender.get(Calendar.MONTH);
        this.day = calender.get(Calendar.DAY_OF_MONTH);
        this.year = calender.get(Calendar.YEAR);
    }

    /**
     * Creates a Date object using a string input.
     * @param date string representing a date in MM/DD/YYYY format.
     */
    public Date(String date) {
        String[] dateArray  = date.split("/");
        this.month = Integer.parseInt(dateArray[0]) - 1;
        this.day   = Integer.parseInt(dateArray[1]);
        this.year  = Integer.parseInt(dateArray[2]);
    }

    /**
     * Compares this date with the specified date.
     * @param date the object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * this date is less than, equal to, or greater than the
     * specified date.
     */
    @Override
    public int compareTo(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, date.getYear());
        c1.set(Calendar.MONTH, date.getMonth());
        c1.set(Calendar.DAY_OF_MONTH, date.getDay());

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, year);
        c2.set(Calendar.MONTH, month);
        c2.set(Calendar.DAY_OF_MONTH, day);
        return c1.compareTo(c2);
    }

    /**
     * Determines whether a date object has a valid date.
     * The day, month, and year of a date cannot be negative.
     * All positive years are accepted as valid. A month is valid based on
     * the amount of days there are in that month. Leap years are accounted
     * for.
     * @return true if the date is a valid date; false otherwise.
     */
    public boolean isValid() {
        if(day <= 0 || month < 0 || year <= 0 || month > 11)
            return false;

        switch(month){
            case (Calendar.JANUARY):
            case (Calendar.MARCH):
            case (Calendar.MAY):
            case (Calendar.JULY):
            case (Calendar.AUGUST):
            case (Calendar.OCTOBER):
            case (Calendar.DECEMBER):
                if(day > 31) return false;
                break;
            case (Calendar.APRIL):
            case (Calendar.JUNE):
            case (Calendar.SEPTEMBER):
            case (Calendar.NOVEMBER):
                if (day > 30) return false;
                break;
            case (Calendar.FEBRUARY):
                if(day == 29) return ((year % 4 == 0) && !(year % 100 == 0))
                                                      ||  (year % 400 == 0);
                else if(day > 29) return false;
        }

        return true;
    }

    /**
     * Gets the year of a Date object.
     * @return an integer representing the year of a Date object.
     */
    public int getYear(){
        return year;
    }

    /**
     * Gets the month of a Date object.
     * @return an integer representing the month of a Date object.
     */
    public int getMonth(){
        return month;
    }

    /**
     * Gets the day of the month of a Date object.
     * @return an integer representing the day of the month of a Date object.
     */
    public int getDay(){
        return day;
    }

    /**
     * Creates a string of a Date's day, month, and year.
     * @return a string of a Date object's date in MM/DD/YYYY format.
     */
    public String toString(){
        return String.valueOf(month + 1) + "/" + String.valueOf(day) +
                "/" + String.valueOf(year);
    }

    /**
     * Main is used to test the isValid() method.
     * @param args an array of command-line arguments for the application.
     */
    public static void main(String[] args){
        Date[] dList = new Date[9];        //TEST CASES
        dList[0] = new Date("1/1/2000");   //#1
        dList[1] = new Date("-1/2/2000");  //#2
        dList[2] = new Date("13/1/2000");  //#2
        dList[3] = new Date("2/29/2399");  //#3
        dList[4] = new Date("1/32/2000");  //#4
        dList[5] = new Date("9/31/2000");  //#4
        dList[6] = new Date("1/-3/0");     //#5
        dList[7] = new Date("1/1/-1");     //#5
        dList[8] = new Date("2/29/2020");  //#6
        String[] cases = {"1", "2", "2", "3", "4", "4", "5", "5", "6"};
        int caseNumber = 0;
        for (Date date : dList) {
            System.out.println("Case #" + cases[caseNumber] + ": " + date);
            System.out.println("\tOutput: " + (date.isValid() ? "PASSED" : "FAILED"));
            caseNumber++;
        }
    }
}