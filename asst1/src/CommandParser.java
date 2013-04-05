/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.Scanner;

/**
 * Parses commands from an input stream and routes them accordingly to the
 * booking system's methods.
 *
 * Perhaps a better name would be CommandRouter, but it has to deal with input
 * parsing and command issuing.
 */
public class CommandParser {
	/**
	 * @param system
	 *            Booking system against which to issue the commands.
	 * @param stream
	 *            Stream of commands, as defined in the assignment spec.
	 */
	public CommandParser(BookingSystem system, Scanner stream) {
		this.system = system;
		this.stream = stream;
	}

	/**
	 * Enter the read-parse-execute loop. Exits once there are no more commands
	 * to process.
	 *
	 * This is a fairly ugly method, but so is anything that deals with user
	 * input.
	 */
	public void execute() {
		while (stream.hasNext()) {
			String command = stream.next();

			System.out.println("* " + command);

			// We can probably do this with a more compact method, but is it
			// really worth the hassle?
			// Also, did I mention how much Java 7 rocks? It has String-switch
			// statements... :(
			if (command.equals("Room")) {
				// Room <capacity> <room>

				int capacity = stream.nextInt();
				String roomName = stream.next();

				system.createRoom(roomName, capacity);
			} else if (command.equals("Book")) {
				// Book <user> <capacity> <numWeeks> <month> <date> <time>
				// <duration> <title>

				String user = stream.next();
				int capacity = stream.nextInt();
				int numWeeks = stream.nextInt();
				String month = stream.next();
				int date = stream.nextInt();
				int time = stream.nextInt();
				int duration = stream.nextInt();
				String title = stream.next();

				String response = system.addNewBooking(user, title,
						new BookingTimePeriod(month, date, time, duration,
								numWeeks), capacity);
				System.out.println(response);
			} else if (command.equals("Change")) {
				// Change <user> <room> <numWeeks> <month1> <date1> <time1>
				// <capacity> <month2> <date2> <time2> <duration2> <title>
				String user = stream.next();
				String roomName = stream.next();
				int numWeeks = stream.nextInt();
				String oldMonth = stream.next();
				int oldDate = stream.nextInt();
				int oldTime = stream.nextInt();
				int newCapacity = stream.nextInt();
				String newMonth = stream.next();
				int newDate = stream.nextInt();
				int newTime = stream.nextInt();
				int newDuration = stream.nextInt();
				String newTitle = stream.next();

				// Ignore old duration -- cancel entire booking.
				BookingTimePeriod oldPeriod = new BookingTimePeriod(oldMonth,
						oldDate, oldTime, 0, numWeeks);
				BookingTimePeriod newPeriod = new BookingTimePeriod(newMonth,
						newDate, newTime, newDuration, numWeeks);

				system.updateExistingBookings(user, roomName, newTitle,
						oldPeriod, newPeriod, newCapacity);
			} else if (command.equals("Delete")) {
				// Delete <user> <room> <numWeeks> <month> <date> <time>

				String user = stream.next();
				String roomName = stream.next();
				int numWeeks = stream.nextInt();
				String month = stream.next();
				int date = stream.nextInt();
				int time = stream.nextInt();

				boolean success = system.deleteBookings(user, roomName,
						new BookingTimePeriod(month, date, time, 0, numWeeks));
				System.out.println(success ? "Reservations deleted"
						: "Deletion rejected");
			} else if (command.equals("Print")) {
				// Print <room>
				String roomName = stream.next();

				String bookings = system.getBookings(roomName);
				System.out.println(bookings);
			} else {
				throw new IllegalArgumentException("Unexpected command '"
						+ command + "'");
			}
		}

		System.out.println("Exiting.");
	}

	private BookingSystem system;
	private Scanner stream;
}
