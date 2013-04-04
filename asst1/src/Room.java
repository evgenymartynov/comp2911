/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

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
		return "";
	}

	private String name;
	private int capacity;
	private TreeSet<RoomBooking> bookings;
}
