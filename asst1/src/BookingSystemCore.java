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
 * It is enough to identify each booking with the room, date, and starting time,
 * since bookings cannot overlap within their assigned rooms.
 *
 * The system expects each room to have a unique identifier. Requests will be
 * rejected if a request leads to such an invalid state.
 */
public class BookingSystemCore {
    /**
     * Instantiates a new BookingSystemCore with an empty set of rooms.
     */
    public BookingSystemCore() {
        rooms = new ArrayList<Room>();
    }

    /**
     * Creates a new room with a given identifier and maximum capacity. We need
     * to ensure that no rooms have the same identifier, thus an exception is
     * thrown if a new room has the same identifier as a pre-existing room.
     *
     * @param roomName
     *            Identifier for the new room to use.
     * @param capacity
     *            Maximum capacity for the new room.
     * @throws IllegalStateException
     *             If the new room has the same ID as an existing room.
     */
    public void createRoom(String roomName, int capacity) {
        // Ensure no other room has the same name.
        for (Room room : rooms)
            if (room.getName().equals(roomName))
                throw new IllegalStateException();

        rooms.add(new Room(roomName, capacity));
    }

    /**
     * Attempts to place a new booking into the system. It is satisfied using
     * the first room (in the same order as the rooms were added to the system)
     * that is free and has the required capacity.
     *
     * @param user
     *            User under which the booking is registered. Only this user can
     *            modify or cancel this booking.
     * @param title
     *            Title of the booking.
     * @param period
     *            Requested booking period.
     * @param capacity
     *            Requested room capacity.
     * @return Identifier for the room if a booking was successful, or
     *         {@code null} if the booking request cannot be satisfied.
     */
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

    /**
     * Replaces a user's previous bookings with a new set of bookings,
     * potentially in a different room at a different time.
     *
     * @param user
     *            User requesting the change. Must own the bookings that are to
     *            be changed.
     * @param roomName
     *            Name of the room under which the old bookings were placed.
     * @param newTitle
     *            Title for the new booking.
     * @param oldPeriod
     *            Period of old bookings to replace.
     * @param newPeriod
     *            Requested period for the new bookings.
     * @param newCapacity
     *            Requested capacity for the new booking.
     * @return Identifier for the room if the change was successful, or
     *         {@code null} if the change cannot take place for any reason.
     */
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

    /**
     * Deletes previously made bookings by a certain user in the given period of
     * time.
     *
     * @param user
     *            User who is requesting the deletion. Must be the same user who
     *            created the bookings being removed.
     * @param roomName
     *            Room which contains the bookings being removed.
     * @param period
     *            Period over which to remove the bookings.
     * @return Whether the deletion has succeeded. It will not succeed if
     *         bookings weren't found, or if the specified user does not own the
     *         bookings being deleted.
     */
    public boolean deleteBookings(String user, String roomName,
            BookingTimePeriod period) {
        return findRoom(roomName).deleteBookings(user, period);
    }

    /**
     * Gets room's bookings as a string. The format matches the assignment
     * output specification.
     *
     * @param roomName
     *            Name of the room to stringify.
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

    /**
     * List of all rooms belonging to this booking system. Stored in order of
     * creation, this order is used for tie-breaking.
     */
    private ArrayList<Room> rooms;
}
