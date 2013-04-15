/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class is a wrapper around booking periods. It serves a double purpose of
 * storing and passing input requests as one object, and for loading the input
 * parameters into an instance of an appropriate datetime-managing class
 * (Calendar, in this case).
 */
public class BookingTimePeriod {
    /**
     * Instantiates a new BookingTimePeriod with the given date, time, and week
     * and hour durations for the booking.
     *
     * @param month
     *            Three-letter English abbreviation of the month of the first
     *            day of booking.
     * @param day
     *            Day of the month for the first day of booking.
     * @param time
     *            Starting time (hour of day) of every booking in this period.
     * @param hours
     *            Duration, in hours, of ever booking in this period.
     * @param numWeeks
     *            Number of weeks that this booking repeats for.
     */
    @SuppressWarnings("deprecation")
    public BookingTimePeriod(String month, int day, int time, int hours,
            int numWeeks) {
        // Combine the input data for use with Calendar.
        Date date;

        // Parse out the month name.
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Cannot parse month: " + month);
        }

        // Set the year just to be safe.
        date.setYear(DEFAULT_YEAR);
        date.setDate(day);
        date.setHours(time);

        // And we're done.
        this.startDate = Calendar.getInstance();
        this.startDate.setTime(date);

        this.numWeeks = numWeeks;
        this.hours = hours;
    }

    /**
     * Get the number of weeks that this booking repeats for.
     *
     * @return Number of weeks this booking repeats.
     */
    public int getNumWeeks() {
        return numWeeks;
    }

    /**
     * Gets a single booking's duration, in hours.
     *
     * @return Duration of a single booking, in hours.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Gets a copy of the object describing starting date and time for the first
     * booking in this period.
     *
     * @return Copy of very first datetime that this booking runs on.
     */
    public Calendar getTime() {
        return (Calendar) startDate.clone();
    }

    /**
     * We use 2013 as the default year. This is easily changed once we can
     * specify an actual year in our booking requests.
     */
    private final int DEFAULT_YEAR = 2013;

    private Calendar startDate;
    private int numWeeks;
    private int hours;
}
