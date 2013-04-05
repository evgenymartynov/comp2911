/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.Calendar;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * A single room with its associated bookings.
 *
 * TODO
 */
public class Room {
	public Room(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.bookings = new TreeSet<RoomBooking>();
	}

	public String getName() {
		return name;
	}

	// TODO
	public String getBookings() {
		String summary = name;

		for (RoomBooking booking : bookings)
			summary += "\n" + booking.toString();

		return summary;
	}

	/**
	 * Check availability of this room for a given booking request.
	 *
	 * The fact that this runs in O(weeks * bookings * log...) makes me very
	 * sad. Standard Java classes seem to be very limiting, I'd happily do away
	 * with Calendar.
	 *
	 * @param period
	 *            Booking period being tested.
	 * @param capacity
	 *            Required minimum capacity for the booking.
	 * @return Whether or not the booking request can be satisfied.
	 */
	public boolean isAvailable(BookingTimePeriod period, int capacity) {
		if (this.capacity <= capacity)
			return false;

		for (int week = 0; week < period.getNumWeeks(); week++) {
			Calendar thisTime = (Calendar) period.getTime().clone();
			thisTime.add(Calendar.WEEK_OF_YEAR, week);

			RoomBooking request = new RoomBooking("", "", thisTime,
					period.getHours());

			for (RoomBooking booking : bookings)
				if (booking.overlaps(request))
					return false;
		}

		return true;
	}

	/**
	 * Puts in the booking request into the system.
	 *
	 * Assumes that the system is in a sane state, and that the requested spots
	 * are available.
	 *
	 * @param user
	 *            Originator of the request.
	 * @param title
	 *            Title for the booking.
	 * @param period
	 *            Booking period to add.
	 * @param capacity
	 *            Required minimum capacity for booking.
	 */
	public void makeBooking(String user, String title,
			BookingTimePeriod period, int capacity) {
		for (int week = 0; week < period.getNumWeeks(); week++) {
			Calendar thisTime = (Calendar) period.getTime().clone();
			thisTime.add(Calendar.WEEK_OF_YEAR, week);

			RoomBooking request = new RoomBooking(user, title, thisTime,
					period.getHours());
			bookings.add(request);
		}

		System.out.println("[+] Added booking to room " + name);
	}

	public boolean deleteBookings(String user, BookingTimePeriod period) {
		LinkedList<RoomBooking> removalList = new LinkedList<RoomBooking>();

		for (int week = 0; week < period.getNumWeeks(); week++) {
			Calendar thisTime = (Calendar) period.getTime().clone();
			thisTime.add(Calendar.WEEK_OF_YEAR, week);

			RoomBooking looksLike = new RoomBooking("", "", thisTime, 0);

			RoomBooking existing = bookings.floor(looksLike);
			if (existing == null) {
				System.out.println("[!] can't find " + looksLike);
				return false;
			}

			// Permissions check.
			if (!existing.getUser().equals(user)) {
				System.out.println("[!] permissions failed: " + existing);
				return false;
			}

			removalList.add(existing);
		}

		System.out.println("[+] removing bookings: " + removalList);
		bookings.removeAll(removalList);
		return true;
	}

	private String name;
	private int capacity;
	private TreeSet<RoomBooking> bookings;
}
