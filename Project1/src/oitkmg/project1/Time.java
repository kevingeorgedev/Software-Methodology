package oitkmg.project1;

/**
 * Time class represents a specified time of day.
 * The two possible times of day are Morning and Afternoon.
 * FitnessClass uses a Time object to represent the time of
 * day the fitness class starts.
 * @author Kevin George, Omar Talaat
 */
public enum Time {
    /**
     * Morning Fitness Class
     */
    Morning(9, 30),
    /**
     * Afternoon Fitness Class
     */
    Afternoon(14,0);

    private final int hour;
    private final int minute;

    /**
     * Instantiates a new Time object. This class can be called using
     * Morning or Afternoon only.
     * @param hour the hour of the day.
     * @param minute the minute of the hour.
     */
    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of time.
     * @return an integer representing the hour of the day from 0 to 23.
     */
    public int getHour(){
        return hour;
    }

    /**
     * Gets the minute of the hour.
     * @return an integer representing the minute of the hour from 0 to 59.
     */
    public int getMinute(){
        return minute;
    }
}
