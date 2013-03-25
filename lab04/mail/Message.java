/**
 * A message left by the caller.
 */
public class Message {
	/**
	 * Construct a Message object.
	 *
	 * @param messageText
	 *            the message text
	 */
	public Message(String messageText) {
		text = messageText;
	}

	/**
	 * Get the message text.
	 *
	 * @return message text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Compares two messages based on their underlying message text.
	 *
	 * @param other
	 *            The message to compare against.
	 * @return Whether messages are equivalent.
	 */
	@Override
	public boolean equals(Object otherObject) {
		if (!(otherObject instanceof Message)) {
			return false;
		}

		Message other = (Message) otherObject;
		return text == other.text;
	}

	private String text;
}