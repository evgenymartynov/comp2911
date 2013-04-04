/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.util.Scanner;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
	 * Enter the read-parse-execute loop.
	 * Exits once there are no more commands to process.
	 *
	 * TODO
	 */
	public void execute() {
		throw new NotImplementedException();
	}

	private BookingSystem system;
	private Scanner stream;
}
