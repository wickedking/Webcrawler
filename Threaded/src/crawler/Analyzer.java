package crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Keeps a running count of pages visited, total links encountered, and 
 * total word count.
 * 
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 */
public class Analyzer extends Observable implements Runnable
{
	
	/**
	 * Class variables used to keep track of data gathered.
	 */
	private static final String REGEX = "([\\xA0\\u25BC\\\u00A9.,@|:;\\?\"-]+|\\s+)+";

	private final Map<String, Integer> my_map = new HashMap<String, Integer>();

	private final Broker<Data> my_data_broker;

	private int my_page_count = 0;

	private int my_link_count = 0;

	private int my_word_count = 0;
	
	private long my_parse_time = 0l;
	
	private final long my_start_time;
	
	private long my_run_time;

	/**
	 * 	All keys are trimmed of whitespace and set to lowercase.
	 * @param the_keys The words to search for.
	 * @param the_broker 'File cabinet' where this analyzer gets its data.
	 */
	public Analyzer(final Set<String> the_keys, final Broker<Data> the_broker)
	{
		super();
		my_start_time = System.nanoTime();
		my_data_broker = the_broker;
		for (final String s : the_keys)
		{
			my_map.put(s.toLowerCase().trim(), 0);
		}
	}

	@Override
	/**
	 * Overridden method from runnable
	 */
	public void run()
	{
		try
		{
			Data data = my_data_broker.get();
			while (data != null)
			{
				updateMetrics(data);
				updateKeyCount(data);
				Thread.sleep(1);
				data = my_data_broker.get();
			}
			my_run_time = System.nanoTime() - my_start_time;
			setChanged();
			notifyObservers();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns a mapping from words to search for and the number of hits.
	 */
	public Map<String, Integer> keywordCount()
	{
		return new HashMap<String, Integer>(my_map);
	}

	/**
	 * @return The total pages crawled.
	 */
	public int pageCount()
	{
		return my_page_count;
	}

	/**
	 * @return The total number of links encountered.
	 */
	public int linkCount()
	{
		return my_link_count;
	}

	/**
	 * @return The total number of words on the pages.
	 */
	public int wordCount()
	{
		return my_word_count;
	}
	
	/**
	 * @return Total time application has run.
	 */
	public long runTime()
	{
		return my_run_time;
	}

	/**
	 * @return The number of links encountered divided by the number of pages encountered.
	 */
	public double avgLinksPerPage()
	{
		return (double) linkCount() / pageCount();
	}

	/**
	 * @return The number of words divided by the number of pages encountered.
	 */
	public double avgWordsPerPage()
	{
		return (double) wordCount() / pageCount();
	}

	/**
	 * @param the_key The search term to query.
	 * @return The number of hits for the keyword divided by the number of pages.
	 */
	public double avgHitsPerPage(final String the_key)
	{
		double result = 0.0;
		if (my_map.containsKey(the_key))
		{
			result = (double) my_map.get(the_key) / pageCount();
		}
		return result;
	}
	
	/**
	 * @return Average time spent parsing each page.
	 */
	public double avgParseTimePerPage()
	{
		return (double) parseTime() / pageCount();
	}

	/**
	 * Goes through each key. If the key is present, it goes through the data
	 * and counts each instance.
	 * 
	 * @param the_data The data to search through.
	 */
	private void updateKeyCount(final Data the_data)
	{
		final Map<String, Integer> hits = getHits(the_data);
		for (final String s : hits.keySet())
		{
			my_map.put(s, my_map.get(s) + hits.get(s));
		}
	}

	/**
	 * Counts each instance of a keyword in the supplied data.
	 * 
	 * @param the_data The data to search. 
	 * @return A mapping of strings to ints for each occurrence of a keyword.
	 */
	private Map<String, Integer> getHits(final Data the_data)
	{
		final Map<String, Integer> result = new HashMap<String, Integer>();
		final String[] txt = the_data.getData().split(REGEX);
		for (final String s : txt)
		{
			final String key = s.toLowerCase().trim();
			if (my_map.keySet().contains(key))
			{
				if (result.keySet().contains(key))
				{
					result.put(s.toLowerCase().trim(), result.get(key) + 1);
				}
				else
				{
					result.put(key, 1);
				}
			}
		}
		return result;
	}

	/**
	 * Updates the page, link, and word counts.
	 * @param the_data The data to update the numbers from.
	 */
	private void updateMetrics(final Data the_data)
	{
		my_page_count++;
		my_parse_time += the_data.getParseTime();
		final String[] text = the_data.getData().split(REGEX);
		my_word_count += text.length;
		my_link_count += the_data.getLinkCount();
	}

	/**
	 * Quick access to the parse time
	 * @return my_parse_time the amount of time to check the web page
	 */
	private long parseTime()
	{
		return my_parse_time;
	}
}