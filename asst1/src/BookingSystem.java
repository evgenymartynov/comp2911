/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.ArrayList;
import java.util.LinkedList;
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
	public BookingSystem() {
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
				return "Room " + room.getName() + " assigned";
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

		// Grab the deletion list.
		LinkedList<RoomBooking> removalList = room.getRemovalListOrNull(user,
				oldPeriod);

		if (removalList != null) {
			// This should now succeed.
			assert (room.deleteBookings(user, oldPeriod));

			// Add a new booking, effectively replacing the old one.
			allocatedRoom = addNewBooking(user, newTitle, newPeriod,
					newCapacity);

			// If allocation failed, undelete.
			if (allocatedRoom == null) {
				room.undelete(removalList);
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
			if (room.getName().equals(name))
				return room;

		throw new NoSuchElementException("Room " + name + " not found.");
	}

	private ArrayList<Room> rooms;
}
