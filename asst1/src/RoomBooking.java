/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Representation of a booking on a single day.
 *
 * This class includes methods for booking-to-booking comparisons and overlaps,
 * which is its primary functional purpose.
 */
public class RoomBooking implements Comparable<RoomBooking> {
    /**
     * Constructs a new room booking with a given owner, title, the starting
     * time and booking length in hours.
     *
     * @param user
     *            Owner of the booking.
     * @param title
     *            Title for the booking.
     * @param startTime
     *            Starting time for the booking.
     * @param duration
     *            Duration of the booking in hours.
     */
    public RoomBooking(String user, String title, Calendar startTime,
            int duration) {
        this.user = user;
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
    }

    /**
     * Checks if two bookings overlap.
     *
     * @param other
     *            Booking to check against.
     * @return Whether or not the bookings overlap.
     */
    public boolean overlaps(RoomBooking other) {
        Calendar thisStart = this.startTime;
        Calendar otherStart = other.startTime;
        Calendar thisEnd = (Calendar) thisStart.clone();
        Calendar otherEnd = (Calendar) otherStart.clone();

        thisEnd.add(Calendar.HOUR_OF_DAY, this.duration);
        otherEnd.add(Calendar.HOUR_OF_DAY, other.duration);

        // Check if start is sandwiched in the other request.
        if (otherStart.compareTo(thisStart) <= 0
                && thisStart.compareTo(otherEnd) < 0)
            return true;
        // Do the same for end. Take care with interval end points.
        if (otherStart.compareTo(thisEnd) < 0
                && thisEnd.compareTo(otherEnd) <= 0)
            return true;

        // The only other option now is that this one completely includes the
        // other booking.
        if (thisStart.compareTo(otherStart) <= 0
                && otherStart.compareTo(thisEnd) < 0)
            return true;
        // And just to be safe, do the other one, too.
        if (thisStart.compareTo(otherEnd) < 0
                && otherEnd.compareTo(thisEnd) <= 0)
            return true;

        // Should be good now.
        return false;
    }

    /**
     * Gets owner of the booking.
     *
     * @return Owner of the booking.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets booking duration.
     *
     * @return Duration of the booking, in hours.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Converts booking to a suitable format for output, compliant to assignment
     * spec.
     *
     * We override Object.toString(), since it is *the* method designed for this
     * particular purpose. Creating a .describeSelf() is semantically invalid.
     */
    @Override
    public String toString() {
        String repr = user;
        repr += " " + monthToString();
        repr += " " + startTime.get(Calendar.DATE);
        repr += " " + startTime.get(Calendar.HOUR_OF_DAY);
        repr += " " + duration;
        repr += " " + title;

        return repr;
    }

    /**
     * Compares two room bookings based solely on their starting time.
     */
    @Override
    public int compareTo(RoomBooking other) {
        return startTime.compareTo(other.startTime);
    }

    /**
     * Compares two room bookings based solely on their starting time.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RoomBooking other = (RoomBooking) obj;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        return true;
    }

    /**
     * Converts this booking's month to a 3-letter English abbreviation.
     *
     * @return String with short month name.
     */
    private String monthToString() {
        return (new SimpleDateFormat("MMM", Locale.ENGLISH)).format(startTime
                .getTime());
    }

    String user;
    String title;
    Calendar startTime;

    /**
     * Duration of the booking, in hours.
     */
    int duration;
}
