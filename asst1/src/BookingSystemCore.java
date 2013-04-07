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
 * It is enough to identify each booking with the date and starting time, since
 * bookings cannot overlap within their assigned rooms.
 */
public class BookingSystemCore {
	// TODO
	public BookingSystemCore() {
		rooms = new ArrayList<Room>();
	}

	// TODO
	public void createRoom(String roomName, int capacity) {
		rooms.add(new Room(roomName, capacity));
	}

	// TODO
	public String addNewBooking(String user, String title,
			BookingTimePeriod period, int capacity) {
		for (Room room : rooms) {
			if (room.isAvailable(period, capacity)) {
				room.makeBooking(user, title, period, capacity);
				return room.getName();
			}
		}

		return null;
	}

	// TODO
	public String updateExistingBookings(String user, String roomName,
			String newTitle, BookingTimePeriod oldPeriod,
			BookingTimePeriod newPeriod, int newCapacity) {
		Room room = findRoom(roomName);

		// To get this to work, we need some kind of transactional support.
		// Unfortunately, because Java is retarded, there is no easy way to do
		// a deep copy of the Room's state, and so we will need to manually
		// unroll a failed transaction, breaking ALL THE ABSTRACTIONS!

		String allocatedRoom = null;

		// Begin our two-step transaction.
		Object transaction = room.beginDeletionTransaction(user, oldPeriod);

		if (transaction != null) {
			// Add a new booking, effectively replacing the old one.
			allocatedRoom = addNewBooking(user, newTitle, newPeriod,
					newCapacity);

			// If allocation failed, roll back the deletion.
			if (allocatedRoom == null) {
				room.rollbackDeletionTransaction(transaction);
			}
		}

		return allocatedRoom;
	}

	// TODO
	public boolean deleteBookings(String user, String roomName,
			BookingTimePeriod period) {
		return findRoom(roomName).deleteBookings(user, period);
	}

	/**
	 * Gets room's bookings as a string.
	 *
	 * @param roomName
	 *            Name of the room.
	 * @return String containing the name of the room and all its bookings.
	 */
	public String describeBookings(String roomName) {
		return findRoom(roomName).describeBookings();
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
			if (room.getName().equals(name))
				return room;

		throw new NoSuchElementException("Room " + name + " not found.");
	}

	private ArrayList<Room> rooms;
}
