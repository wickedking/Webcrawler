package crawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Retrieves a document from the web and adds it to the document broker.
 * 
 * @author Mike Westbrook
 */
public class Retriever implements Runnable
{
	/**
	 * Max pages to crawl.
	 */
	private static final int MAX_PAGES = 500;
	
	/**
	 * Max time to wait on a page.
	 */
	private static final int TIMEOUT = 5000;

	/**
	 * Broker to add documents to.
	 */
	private final Broker<Document> my_doc_broker;

	/**
	 * Broker to get URLs from.
	 */
	private final Broker<URL> my_url_broker;
	
	
	/**
	 * Holds the links that still need to be visited.
	 */
	private final List<String> my_links = new ArrayList<String>();
	private final Set<String> my_visited = new HashSet<String>();
	private final Set<String> my_invalid = new HashSet<String>();
	
	/**
	 * 
	 * @param the_doc_broker
	 * @param the_url_broker
	 */
	public Retriever(final Broker<Document> the_doc_broker, final Broker<URL> the_url_broker)
	{
		my_doc_broker = the_doc_broker;
		my_url_broker = the_url_broker;
	}
	
	/**
	 * Collects URLs from the URL broker and adds documents to the document broker.
	 */
	public void run()
	{
		URL url;
		try
		{
			url = my_url_broker.get();
			while (url != null && my_visited.size() < MAX_PAGES)
			{
				if (checkURL(url))
				{
					my_visited.add(url.getPath());
					my_doc_broker.add(Jsoup.parse(url, TIMEOUT));
				}
				Thread.sleep(1);
				url = my_url_broker.get();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * @param the_url The URL to check. If it is invalid it is added to the set of invalid URLs.
	 * @return True if the link ends with /, .txt, or .html
	 */
	private boolean checkURL(final URL the_url)
	{
		final String url = the_url.getPath();
		if ((!my_visited.contains(url) && !my_invalid.contains(url) && 
				!my_links.contains(url)) && 
				(url.endsWith("/") || url.endsWith(".html") || 
						url.endsWith(".txt")))
		{
			return true;
		}
		else
		{
			my_invalid.add(url);
			return false;
		}
	}
}
