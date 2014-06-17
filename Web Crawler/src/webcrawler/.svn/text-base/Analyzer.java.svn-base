package webcrawler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Goes through the document and counts links. Count words total and count of keywords.
 * @author Mike Westbrook, Cody Shafer, Brandon Martin, and Jesse Kitterman
 *
 */
public class Analyzer {
    
    /**
     * Regex string
     */
	private static final String REGEX = "([\\xA0\\u25BC\\\u00A9.,@|:;\\?\"-]+|\\s+)+";

	/**
	 * The map of the string to the count
	 */
	private final Map<String, Integer> my_map = new HashMap<String, Integer>();
	
	/**
	 * Page count
	 */
	private int my_page_count = 0;
	
	/**
	 * The link count
	 */
	private int my_link_count = 0;
	
	/**
	 * The word count
	 */
	private int my_word_count = 0;
	
	/**
	 * The total parse time
	 */
	private long my_parse_time = 0l;
	
	/**
	 * The total time
	 */
	private double my_total_time = 0.0;
	
	/**
	 * Constructor 
	 * @param the_keys to be analyzed
	 */
	public Analyzer(final Set<String> the_keys){
		for (final String s : the_keys){
			my_map.put(s, 0);
		}
	}
	
	/**
	 * Increases the link count
	 * @param the_amount of links
	 */
	public void incrementLinks(final int the_amount){
		my_link_count += the_amount;
	}
	
	/**
	 * Adds the data to the keyword count
	 * @param the_data
	 */
	public void addData(final String the_data){
		my_page_count++;
		final String[] data = the_data.split(REGEX);

		my_word_count += data.length;
		updateKeywordCount(data);

	}
    
	/**
	 * Updates the keyword count
	 * 
	 * @param the_data the string that's updating
	 */
    private void updateKeywordCount(final String[] the_data)
	{
    	  for (int i = 0; i < the_data.length; i++) 
    	  {
    		  if(my_map.containsKey(the_data[i].toLowerCase()))
    		  {
    			  int test = my_map.get(the_data[i].toLowerCase());
    			  my_map.put(the_data[i].toLowerCase(), test + 1);
    		  }
    	  }
	}

	/**
	 * Getter for page count
	 * @return page count
	 * 
	 */
    public int getPageCount(){
        return my_page_count;       
    }
    
    /**
     * Getter for keyword count
     * @return keyword count
     */
    public Map<String, Integer> getKeywordCount() {
        return new HashMap<String, Integer>(my_map);
    }
    
    /**
     * Getter for word count
     * @return the word count
     */
    public int getWordCount() {
        return my_word_count;

    }
    
    /**
     * Getter for avg hits
     * @param the_keyword that's being searched
     * @return avg hits
     */
    public double getAverageHits(final String the_keyword)
    {
    	if (my_map.containsKey(the_keyword))
    	{
    		return (double) my_map.get(the_keyword) / getPageCount();
    	}
    	return 0.0;
    }

	/**
	 * Getter for link count
	 * 
	 * @return the link count
	 */
    public int getLinkCount() { 
       return my_link_count;
    }
    
    /**
     * Getter for avg links per page
     * @return the avg links per page
     */
    public double getAvgLinksPerPage(){
    	return (double) getLinkCount() / (double) getPageCount();
    }
    
    /**
     * Getter for avg words per page
     * @return get avg words per page 
     */
    public double getAvgWordsPerPage(){
    	return (double) getWordCount() / (double) getPageCount();
    }
    
    /**
     * Adder for parse time
     * @param the_time the parse time after updated
     */
    public void addParseTime(long the_time){
    	my_parse_time += the_time;
    }
    
    /**
     * 
     * Getter for total parse time
     * 
     * @return the total parse time
     */
    public long getTotalParseTime(){
    	return my_parse_time;
    }
    
    /**
     * Adder for total time
     * @param time after updated
     */
    public void addTotalTime(double time){
    	my_total_time = time;
    }
    
    /**
     * Getter total time
     * 
     * @return the total time
     */
    public double getTotalTime(){
    	return my_total_time;
    }
    

}
