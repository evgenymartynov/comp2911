import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A first-in, first-out collection of messages. This implementation is not very
 * efficient. We will consider a more efficient implementation in chapter 3.
 */
public class MessageQueue {
	/**
	 * Constructs an empty message queue.
	 */
	public MessageQueue() {
		queue = new Message[INITIAL_SIZE];
	}

	/**
	 * Remove message at head.
	 *
	 * @return message that has been removed from the queue
	 */
	public Message remove() {
		// Safeguard against popping an empty queue.
		if (numItems == 0)
			throw new NoSuchElementException();

		// Extract first message and shift the remainder down by one.
		Message head = queue[0];
		queue = Arrays.copyOfRange(queue, 1, queue.length);
		numItems--;

		return head;
	}

	/**
	 * Append message at tail.
	 *
	 * @param newMessage
	 *            the message to be appended
	 */
	public void add(Message newMessage) {
		// Check if we need to grow the size of our queue.
		if (numItems == queue.length)
			extendQueue();

		// Put the new message at the back.
		queue[numItems] = newMessage;
		numItems++;
	}

	/**
	 * Deletes given message from the queue. Will remove first matching message
	 * in FIFO order.
	 *
	 * @param message
	 *            the message to be deleted
	 */
	public void delete(Message message) {
		int index = Arrays.asList(queue).indexOf(message);

		// If not found, do nothing.
		if (index < 0)
			return;

		// Every time you shift an array by hand, a kitten dies.
		for (int i = index; i < numItems - 1; i++)
			queue[i] = queue[i + 1];

		// Just in case, drop the reference to former-last element.
		queue[numItems - 1] = null;
		numItems--;
	}

	/**
	 * Get the total number of messages in the queue.
	 *
	 * @return the total number of messages in the queue
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Get message at head.
	 *
	 * @return message that is at the head of the queue, or null if the queue is
	 *         empty
	 */
	public Message peek() {
		if (numItems == 0)
			return null;
		else
			return queue[0];
	}

	/**
	 * Increases the allocated size for the queue.
	 */
	private void extendQueue() {
		queue = Arrays.copyOf(queue, queue.length * 2);
	}

	private Message[] queue;
	private int numItems = 0;
	private final int INITIAL_SIZE = 1;
}
