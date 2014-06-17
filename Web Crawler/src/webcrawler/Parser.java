package webcrawler;

import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Will grab document from storage and will grab links
 * @author Brandon Martin, Mike Westbrook, Jesse Kitterman, Cody Shafer
 * 
 */
public class Parser {	
	/**
	 * HTML tags.
	 */
	private static final String LINK = "a";
	private static final String HREF = "href";
	
	/**
	 * Holds the set of links associated with the document.
	 */
	private final Collection<String> my_links = new ArrayList<String>();
	
	/**
	 * All of the visible text on the document.
	 */
	private final String my_text;

	/**
	 * Document to parse.
	 */
	private final Document my_doc;

	/**
	 * Parses an HTML document and provides methods to get all link URLs 
	 * and text in the page.
	 * 
	 * @param the_doc The document to parse.
	 * @param the_keys The words to look out for.
	 */
	public Parser(final Document the_doc){
		
		my_doc = the_doc;
		my_text = my_doc.text();
		final Elements elements = my_doc.getElementsByTag(LINK);
		for (final Element e : elements)
		{
			my_links.add(e.absUrl(HREF));
		}
	}
	
	/**
	 * @return The text of the HTML document as a single string.
	 */
	public String getText(){
		return my_text;
	}
	
	/**
	 * @return A set of strings that are linked from on the page.
	 */
	public Collection<String> getLinks(){
		return new ArrayList<String>(my_links);
	}
}