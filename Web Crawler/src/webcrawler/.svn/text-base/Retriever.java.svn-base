package webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Mike Westbrook, Jesse Kitterman, Cody Shafer, and Brandon Martin 
 * @version 0.01b
 *
 */
public class Retriever
{		
	/**
	 * Time to wait for a connection.
	 */ 
	private static final int TIMEOUT = 5000;
	
	/**
	 * Holds the links that still need to be visited.
	 */
	private final List<String> my_links = new ArrayList<String>();
	private final Set<String> my_visited = new HashSet<String>();
	private final Set<String> my_invalid = new HashSet<String>();
	
	
	/**
	 * Adder for the links 
	 * 
	 * @param the_url the string of the url to add
	 */
	public void addLink(final String the_url)
	{
		if ((!my_visited.contains(the_url) && !my_invalid.contains(the_url) && !my_links.contains(the_url)) && 
				(the_url.endsWith("/") || the_url.endsWith(".html") || 
						the_url.endsWith(".txt")))
		{
			my_links.add(the_url.trim());
		}
		else if (!my_invalid.contains(the_url))
		{
			my_invalid.add(the_url.trim());
		}
	}
	
	/**
	 * 
	 * Adder for the links to the collection
	 * 
	 * @param the_links collection of string links
	 */
	public void addLinks(final Collection<String> the_links)
	{
		for (final String s : the_links)
		{
			addLink(s);
		}
	}
	
	/**
	 * Getter for getting the doc
	 * 
	 * @return A document if parsed correctly, null otherwise.
	 * @throws NoMoreLinksException If there are no more links to get.
	 */
	public Document getDoc() throws NoMoreLinksException
	{
		try
		{
			if (!my_links.isEmpty())
			{
				final String address = my_links.remove(0);
				my_visited.add(address.trim());
				final URL url = new URL(address);
				return Jsoup.parse(url, TIMEOUT);
			} 
			else
			{
				throw new NoMoreLinksException();
			}
		} 
		catch (MalformedURLException e)
		{
			return null;
		} 
		catch (IOException e)
		{
			return null;
		}
	}
	/**
	 * Tells the number of pages visited
	 * 
	 * @return int of pages visited
	 */
	public int numberOfPagesVisited()
	{
		return my_visited.size();
	}
}
