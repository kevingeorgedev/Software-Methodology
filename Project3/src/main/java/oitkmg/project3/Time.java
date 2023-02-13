package oitkmg.project3;

import java.text.DecimalFormat;

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
    MORNING(9, 30),
    /**
     * Afternoon Fitness Class
     */
    AFTERNOON(14,0),
    /**
     * Evening Fitness Class
     */
    EVENING(18, 30);

    /**
     * Hour of the day.
     */
    private final int hour;

    /**
     * Minute of the hour.
     */
    private final int minute;

    /**
     * Instantiates a new Time object. This class can be called using
     * Morning, Afternoon, and Evening only.
     * @param hour the hour of the day.
     * @param minute the minute of the hour.
     */
    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Creates a string of this time. The string contains the hour and
     * minute of this time.
     * @return a string representing the time.
     */
    @Override
    public String toString() {
        DecimalFormat df1 = new DecimalFormat("00");
        return hour + ":" + df1.format(minute);
    }

    /**
     * Determines whether some Time is equal to this Time.
     * @param time the reference time with which to compare.
     * @return true if this time is equal to the time argument; false
     * otherwise.
     */
    public boolean equals(Time time){
        return hour == time.getHour() && minute == time.getMinute();
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
