import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests MessageQueue.delete() method.
 *
 * Assumes that .add(), .size(), and .peek(), and the underlying Message class
 * work as expected.
 */
public class MessageQueueDeletionTest {
	private MessageQueue mq;

	@Before
	public void setUp() {
		mq = new MessageQueue();
	}

	@Test
	public void testEmpty() {
		mq.delete(new Message("42"));
		assertEquals(0, mq.size());
	}

	@Test
	public void testNotFound() {
		mq.add(new Message("first message"));
		mq.add(new Message("second message"));
		assertEquals(2, mq.size());

		mq.delete(new Message("non-existent message"));
		assertEquals(2, mq.size());
	}

	@Test
	public void testDeletesFront() {
		mq.add(new Message("first message"));
		mq.add(new Message("second message"));
		assertEquals(2, mq.size());

		mq.delete(new Message("first message"));
		assertEquals(1, mq.size());
		assertEquals("second message", mq.peek().getText());
	}

	@Test
	public void testComplexInteraction() {
		mq.add(new Message("first message"));
		mq.add(new Message("second message"));
		assertEquals(2, mq.size());

		assertEquals("first message", mq.remove().getText());
		assertEquals(1, mq.size());

		mq.delete(new Message("first message"));
		assertEquals(1, mq.size());

		mq.add(new Message("third message"));
		assertEquals(2, mq.size());

		mq.delete(new Message("third message"));
		assertEquals(1, mq.size());
		
		assertEquals("second message", mq.remove().getText());
		assertEquals(0, mq.size());
		
		mq.delete(new Message("second message"));
		assertEquals(0, mq.size());
	}
}
