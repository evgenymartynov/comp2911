/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.InputMismatchException;
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
    public CommandParser(BookingSystemCore system, Scanner stream) {
        this.system = system;
        this.commandStream = stream;
    }

    /**
     * Enter the read-parse-execute loop. Exits once there are no more commands
     * to process.
     *
     * This is a fairly ugly method, but so is anything that deals with user
     * input. This is *the* place to use fscanf()... silly Java doesn't have it.
     */
    public void execute() {
        while (commandStream.hasNext()) {
            String command = commandStream.next();

            try {
                // We can probably do this with a more compact method, but is it
                // really worth the hassle?
                if (command.equals("Room")) {
                    // Room <capacity> <room>

                    int capacity = commandStream.nextInt();
                    String roomName = commandStream.next();

                    system.createRoom(roomName, capacity);
                } else if (command.equals("Book")) {
                    // Book <user> <capacity> <numWeeks> <month> <date> <time>
                    // <duration> <title>

                    String user = commandStream.next();
                    int capacity = commandStream.nextInt();
                    int numWeeks = commandStream.nextInt();
                    String month = commandStream.next();
                    int date = commandStream.nextInt();
                    int time = commandStream.nextInt();
                    int duration = commandStream.nextInt();
                    String title = commandStream.next();

                    String response = system.addNewBooking(user, title,
                            new BookingTimePeriod(month, date, time, duration,
                                    numWeeks), capacity);
                    if (response == null)
                        response = "Booking rejected";
                    else
                        response = "Room " + response + " assigned";
                    System.out.println(response);
                } else if (command.equals("Change")) {
                    // Change <user> <room> <numWeeks> <month1> <date1> <time1>
                    // <capacity> <month2> <date2> <time2> <duration2> <title>
                    String user = commandStream.next();
                    String roomName = commandStream.next();
                    int numWeeks = commandStream.nextInt();
                    String oldMonth = commandStream.next();
                    int oldDate = commandStream.nextInt();
                    int oldTime = commandStream.nextInt();
                    int newCapacity = commandStream.nextInt();
                    String newMonth = commandStream.next();
                    int newDate = commandStream.nextInt();
                    int newTime = commandStream.nextInt();
                    int newDuration = commandStream.nextInt();
                    String newTitle = commandStream.next();

                    // Ignore old duration -- cancel entire booking.
                    BookingTimePeriod oldPeriod = new BookingTimePeriod(
                            oldMonth, oldDate, oldTime, 0, numWeeks);
                    BookingTimePeriod newPeriod = new BookingTimePeriod(
                            newMonth, newDate, newTime, newDuration, numWeeks);

                    String response = system.updateExistingBookings(user,
                            roomName, newTitle, oldPeriod, newPeriod,
                            newCapacity);
                    if (response == null)
                        response = "Booking rejected";
                    else
                        response = "Room " + response + " assigned";

                    System.out.println(response);
                } else if (command.equals("Delete")) {
                    // Delete <user> <room> <numWeeks> <month> <date> <time>

                    String user = commandStream.next();
                    String roomName = commandStream.next();
                    int numWeeks = commandStream.nextInt();
                    String month = commandStream.next();
                    int date = commandStream.nextInt();
                    int time = commandStream.nextInt();

                    boolean success = system.deleteBookings(user, roomName,
                            new BookingTimePeriod(month, date, time, 0,
                                    numWeeks));
                    System.out.println(success ? "Reservations deleted"
                            : "Deletion rejected");
                } else if (command.equals("Print")) {
                    // Print <room>
                    String roomName = commandStream.next();

                    String bookings = system.describeBookings(roomName);
                    System.out.println(bookings);
                } else {
                    throw new IllegalArgumentException("Unexpected command '"
                            + command + "'");
                }
            } catch (InputMismatchException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(
                        "Error in parsing arguments to " + command);
            }
        }
    }

    private BookingSystemCore system;
    private Scanner commandStream;
}
