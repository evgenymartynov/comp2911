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
 * This class provides operations for placing and modifying bookings of the
 * room.
 */
public class Room {
	/**
	 * Constructs a new room with a given name and capacity.
	 *
	 * @param name
	 *            Name of the room.
	 * @param capacity
	 *            Capacity of the room.
	 */
	public Room(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.bookings = new TreeSet<RoomBooking>();
	}

	/**
	 * Gets the room's name.
	 *
	 * @return Room's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns description of the bookings made for this room.
	 *
	 * @return Description of bookings.
	 */
	public String describeBookings() {
		String summary = name;

		for (RoomBooking booking : bookings)
			summary += "\n" + booking.toString();

		return summary;
	}

	/**
	 * Check availability of this room for a given booking request.
	 *
	 * The fact that this runs in O(weeks * bookings) makes me very sad.
	 * Standard Java classes seem to be very limiting, I'd happily do away with
	 * Calendar.
	 *
	 * @param period
	 *            Booking period being tested.
	 * @param capacity
	 *            Required minimum capacity for the booking.
	 * @return Whether or not the booking request can be satisfied.
	 */
	public boolean isAvailable(BookingTimePeriod period, int capacity) {
		if (this.capacity < capacity)
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

		// System.out.println("[+] Added booking to room " + name);
	}

	/**
	 * Deletes a set of bookings as described by a given time period. The owner
	 * must match the given user.
	 *
	 * This method does not guarantee deletion. All weekly bookings must exist
	 * in the given period and must be owned by the specified user.
	 *
	 * @param user
	 *            User that must own the booking.
	 * @param period
	 *            Time period over which to delete existing bookings.
	 * @return Whether or not the deletion succeeded.
	 */
	public boolean deleteBookings(String user, BookingTimePeriod period) {
		LinkedList<RoomBooking> removalList = getRemovalListOrNull(user, period);

		if (removalList == null)
			return false;

		// System.out.println("[+] removing bookings: " + removalList);
		bookings.removeAll(removalList);
		return true;
	}

	/**
	 * Starts a deletion transaction that can be rolled back. The room's state
	 * will be changed as if the deletions have gone through. Transaction itself
	 * should be treated externally as an opaque object.
	 *
	 * There are no guarantees that the rollback will result in a valid state if
	 * the room has been modified in-between the start and end of this
	 * transaction.
	 *
	 * A transaction does not need to be explicitly committed.
	 *
	 * @param user
	 *            User that owns deleted bookings.
	 * @param period
	 *            Time period over which to delete existing bookings.
	 * @return Object representing the transaction state, which is null if and
	 *         only if the deletion cannot go through.
	 */
	public Object beginDeletionTransaction(String user, BookingTimePeriod period) {
		LinkedList<RoomBooking> removalList = getRemovalListOrNull(user, period);

		deleteBookings(user, period);

		return removalList;
	}

	/**
	 * Rolls back a previously started deletion transaction. See comments on
	 * beginDeletionTransaction().
	 *
	 * @param transaction
	 *            Transaction to be rolled back.
	 */
	public void rollbackDeletionTransaction(Object transaction) {
		LinkedList<RoomBooking> savedBookings;

		try {
			assert transaction instanceof LinkedList<?>;
			savedBookings = (LinkedList<RoomBooking>) transaction;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Given transaction is of wrong type");
		}

		bookings.addAll(savedBookings);
	}

	/**
	 * Generates a list of bookings that must be removed to fulfill the deletion
	 * request.
	 *
	 * @param user
	 *            User that must own the booking.
	 * @param period
	 *            Time period over which to delete existing bookings.
	 * @return List of bookings that need to be removed, or null if removals are
	 *         not allowed.
	 */
	private LinkedList<RoomBooking> getRemovalListOrNull(String user,
			BookingTimePeriod period) {
		LinkedList<RoomBooking> removalList = new LinkedList<RoomBooking>();

		for (int week = 0; week < period.getNumWeeks(); week++) {
			Calendar thisTime = (Calendar) period.getTime().clone();
			thisTime.add(Calendar.WEEK_OF_YEAR, week);

			RoomBooking looksLike = new RoomBooking("", "", thisTime, 0);

			RoomBooking existing = bookings.floor(looksLike);
			if (existing == null) {
				// System.out.println("[!] can't find " + looksLike);
				return null;
			}

			// Permissions check.
			if (!existing.getUser().equals(user)) {
				// System.out.println("[!] permissions failed: " + existing);
				return null;
			}

			removalList.add(existing);
		}

		return removalList;
	}

	private String name;
	private int capacity;
	private TreeSet<RoomBooking> bookings;
}
