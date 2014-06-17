package crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Retrieves documents from the document broker. parses the document for links and text. 
 * Adds the text, link count, and parse time to the data broker. Adds URLs to the url broker.
 * @author Mike Westbrook
 */
public class Parser implements Runnable
{
	private static final String LINK = "a";

	private static final String HREF = "href";

	private final Broker<Document> my_doc_broker;

	private final Broker<URL> my_url_broker;

	private final Broker<Data> my_data_broker;

	public Parser(final Broker<Document> the_doc_broker, final Broker<URL> the_url_broker, 
			final Broker<Data> the_anal_broker)
	{
		my_doc_broker = the_doc_broker;
		my_url_broker = the_url_broker;
		my_data_broker = the_anal_broker;
	}

	/**
	 * Gets documents from the doc broker and adds urls to the url broker and data to the data broker.
	 */
	public void run()
	{
		try
		{
			Document doc = my_doc_broker.get();
			while (doc != null)
			{
//				The time parsing began.
				final long start = System.nanoTime();
				final String txt = doc.text();
				final Collection<URL> urls = getLinks(doc);
//				The time parsing ended.
				final long end = System.nanoTime();
				my_data_broker.add(new Data(txt, urls.size(), end - start));
				my_url_broker.addAll(urls);
				Thread.sleep(1);
				doc = my_doc_broker.get();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param the_doc the doc to get links from
	 * @return All of the links in the specified document.
	 * @throws MalformedURLException
	 */
	private Collection<URL> getLinks(final Document the_doc) throws MalformedURLException
	{
		final Collection<URL> list = new ArrayList<URL>();
		final Elements elements = the_doc.getElementsByTag(LINK);
		for (final Element e : elements)
		{
			final String url = e.absUrl(HREF);
			list.add(new URL(url));
		}
		return list;
	}
}
