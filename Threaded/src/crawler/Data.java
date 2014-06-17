package crawler;

/**
 * This class keeps track of the webpages information in Data.
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */
public class Data
{
	/**
	 * String to be stored in data
	 */
	private final String my_data;
	
	/**
	 * Number of links on a page
	 */
	private final int my_link_count;
	
	/**
	 * Runtime of analyzing a page
	 */
	private final long my_parse_time;

	/**
	 * Constructor that stores a pages strings, link count, and parse time.
	 * @param the_data a string from the page to be stored
	 * @param the_link_count the number of links on a page
	 * @param the_parse_time the amount of time to analyze a page
	 */
	public Data(final String the_data, final int the_link_count, final long the_parse_time)
	{
		my_data = the_data;
		my_link_count = the_link_count;
		my_parse_time = the_parse_time;
	}

	/**
	 * Gets the data
	 * @return my_data the string currently stored
	 */
	public String getData()
	{
		return my_data;
	}

	/**
	 * Gets the link count
	 * @return my_link_count number of links on a page
	 */
	public int getLinkCount()
	{
		return my_link_count;
	}
	
	/**
	 * Gets the parse time
	 * @return my_parse_time the time of analyzing a page
	 */
	public long getParseTime()
	{
		return my_parse_time;
	}
}
