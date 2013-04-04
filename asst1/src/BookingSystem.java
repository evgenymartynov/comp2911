/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Main class for the booking system. In particular, all commands from user will
 * be executed via this class.
 *
 * Bookings are stored inside their rooms as maps to allow fast lookups. It is
 * enough to identify each booking with the date and starting time, since
 * bookings cannot overlap.
 */
public class BookingSystem {
	// TODO
	public void createRoom(String name, int capacity) {

	}

	// TODO
	public boolean addNewBooking(String user, String title,
			BookingTimePeriod period, int capacity) {
		return false;
	}

	// TODO
	public boolean updateExistingBookings(String user, String roomName,
			String newTitle, BookingTimePeriod oldPeriod,
			BookingTimePeriod newPeriod, int newCapacity) {
		return false;
	}

	// TODO
	public boolean deleteBookings(String user, String roomName,
			BookingTimePeriod period) {
		return false;
	}

	/**
	 * Gets room's bookings as a string.
	 *
	 * @param roomName
	 *            Name of the room.
	 * @return String containing the name of the room and all its bookings.
	 */
	public String getBookings(String roomName) {
		return findRoom(roomName).getBookings();
	}

	/**
	 * Finds an existing room by its name.
	 *
	 * @param name
	 *            Name of the room to look for.
	 * @return Corresponding room, if found.
	 * @throws NoSuchElementException
	 *             If no room with such name has been registered.
	 */
	private Room findRoom(String name) {
		for (Room room : rooms)
			if (room.getName() == name)
				return room;

		throw new NoSuchElementException("Room " + name + " not found.");
	}

	private ArrayList<Room> rooms;
}
