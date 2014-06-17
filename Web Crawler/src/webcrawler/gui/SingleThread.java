package webcrawler.gui;

import java.util.Set;

import org.jsoup.nodes.Document;

import webcrawler.Analyzer;
import webcrawler.NoMoreLinksException;
import webcrawler.Parser;
import webcrawler.Retriever;

public class SingleThread
{
//	TODO Test url has to end in "/", ".html", or ".txt"  for now. Defaults to sand box.
	private static String DEF_URL = "http://131.191.55.70/";
	
	/**
	 * Max pages to crawl.
	 */
	private static final int MAX_PAGES = 500;
	
	/**
	 * Analyzes the data from the pages.
	 */
    private final Analyzer my_analyzer;
    
    /**
     * URL to start crawling.
     */
    private final String my_url;
  
    /**
     * @param the_url The URL to start crawling.
     * @param the_keys The search terms.
     */
	public SingleThread(final String the_url, final Set<String> the_keys)
	{
		my_analyzer = new Analyzer(the_keys);
		my_url = the_url;
	}
	
	/**
	 * Analyzes all of the data encountered during the crawl.
	 * @return An analyzer of the data encountered.
	 */
	public Analyzer analyze()
	{
		long start = System.currentTimeMillis();
		Parser p;
		final Retriever r = new Retriever();
		//Uses default url if blank.
		r.addLink(my_url.equals("") ? DEF_URL : my_url);
		try
		{
			
			Document d = r.getDoc();

			while (r.numberOfPagesVisited() < MAX_PAGES){
				if (d != null)
				{
					long start_time = System.currentTimeMillis();
					p = new Parser(d);
					start_time = System.currentTimeMillis() - start_time;
					my_analyzer.addParseTime(start_time);
					my_analyzer.addData(p.getText());
					my_analyzer.incrementLinks(p.getLinks().size());
					r.addLinks(p.getLinks());
				}
				d = r.getDoc();
			}
		}
		catch (final NoMoreLinksException the_e)
		{
			return my_analyzer;
		}
		finally {
			 double time = (System.currentTimeMillis() - start) / 1000.0;
			 my_analyzer.addTotalTime(time);
		}
		return null;
	}
}
