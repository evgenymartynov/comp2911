import java.util.Scanner;

/**
 * This program tests the mail system. A single phone communicates with the
 * program through System.in/System.out.
 */
public class MailSystemTester {
	public static void main(String[] args) {
		MailSystem system = new MailSystem(MAILBOX_COUNT);
		Scanner console = new Scanner(System.in);
		TelephoneMultiplexer p = new TelephoneMultiplexer(system, console);
		p.run();
	}

	private static final int MAILBOX_COUNT = 20;
}
