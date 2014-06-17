package crawler;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 'Filing cabinet' that is used to store and retrieve information for 
 * for the threads.
 * 
 * @author Michael Westbrook
 *
 * @param <MyType> Type of information that is shared between threads.
 */
/**
 * 
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 * @param <MyType>
 */
public class Broker <MyType>
{
	/**
	 * Time to wait on a thread.
	 */
	private static final int TIMEOUT = 1;
	
	/**
	 * Holds the information for the threads.
	 */
	private final ArrayBlockingQueue<MyType> my_queue;

	/**
	 * Initializes the Broker with an empty queue.
	 * 
	 * @param the_max The max number of slots in the buffer.
	 */
	public Broker(final int the_max)
	{
		my_queue = new ArrayBlockingQueue<MyType>(the_max);
	}
	
	/**
	 * Adds and item to the queue for retrieval from another thread.
	 * 
	 * @param the_item The item to add.
	 * @throws InterruptedException
	 */
	public void add(final MyType the_item) throws InterruptedException
	{
		my_queue.put(the_item);
	}
	
	/**
	 * Adds all of the items to the queue.
	 * 
	 * @param the_items The items to add.
	 * @throws InterruptedException
	 */
	public void addAll(final Collection<MyType> the_items) throws InterruptedException
	{
		for (final MyType type : the_items)
		{
			add(type);
		}
	}
	
	/**
	 * @return The first item in the queue.
	 * @throws InterruptedException
	 */
	public MyType get() throws InterruptedException
	{
		return my_queue.poll(TIMEOUT, TimeUnit.SECONDS);
	}
}
