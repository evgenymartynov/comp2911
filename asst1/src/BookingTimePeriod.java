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
 * TODO
 */
public class BookingTimePeriod {
    public BookingTimePeriod(String month, int day, int time, int hours,
            int numWeeks) {
        // Combine the input data for use with Calendar.
        Date date;

        // Parse out the month name.
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
        } catch (ParseException e) {
            date = new Date();
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

    public int getNumWeeks() {
        return numWeeks;
    }

    public int getHours() {
        return hours;
    }

    public Calendar getTime() {
        return startDate;
    }

    private final int DEFAULT_YEAR = 2013;
    private Calendar startDate;
    private int numWeeks;
    private int hours;
}
