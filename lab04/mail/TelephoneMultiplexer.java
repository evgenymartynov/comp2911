import java.util.ArrayList;
import java.util.Scanner;

/**
 * A telephone that takes simulated keystrokes and voice input from the user and
 * simulates spoken text.
 */
public class TelephoneMultiplexer {
	/**
	 * Construct phone object.
	 *
	 * @param aScanner
	 *            that reads text from a character-input stream
	 */
	public TelephoneMultiplexer(MailSystem system, Scanner aScanner) {
		scanner = aScanner;
		system_ = system;

		identifiers_ = new ArrayList<String>();
		connections_ = new ArrayList<Connection>();
		activeIdentifier_ = "null";
	}

	/**
	 * Speak a message to System.out.
	 *
	 * @param output
	 *            the text that will be "spoken"
	 */
	public void speak(String output) {
		System.out.println("[" + activeIdentifier_ + "] " + output);
	}

	/**
	 * Loops reading user input and passes the input to the Connection object's
	 * methods dial, record, switch identifier or hangup.
	 *
	 * Expects the connection to be created by providing an identifier before
	 * use of connection commands. Hanging up does not cause the connection to
	 * be dropped.
	 *
	 */
	public void run() {
		boolean more = true;

		while (more) {
			String input = scanner.nextLine();
			if (input == null)
				return;

			if (input.equalsIgnoreCase("H"))
				activeConnection_.hangup();
			else if (input.equalsIgnoreCase("Q"))
				more = false;
			else if (input.length() == 1 && "1234567890#".indexOf(input) >= 0)
				activeConnection_.dial(input);
			else if (input.length() > 1 && input.endsWith(":")) {
				String newIdentifier = input.substring(0, input.length() - 1);
				setIdentifier(newIdentifier);
			} else
				activeConnection_.record(input);
		}
	}

	/**
	 * Updates the current connection based on the identifier input by user.
	 *
	 * @param newIdentifier
	 *            Identifier of the new connection.
	 */
	private void setIdentifier(String newIdentifier) {
		int connectionIndex = identifiers_.indexOf(newIdentifier);

		if (connectionIndex < 0) {
			// We switch activeIdentifier because creating a new Connection will
			// use the current phone to speak back to us.
			activeIdentifier_ = newIdentifier;

			Connection newConnection = new Connection(system_, this);
			identifiers_.add(newIdentifier);
			connections_.add(newConnection);

			activeConnection_ = newConnection;
		} else {
			activeConnection_ = connections_.get(connectionIndex);
		}
	}

	private MailSystem system_;
	private Scanner scanner;

	private ArrayList<String> identifiers_;
	private ArrayList<Connection> connections_;
	private String activeIdentifier_;
	private Connection activeConnection_;
}
