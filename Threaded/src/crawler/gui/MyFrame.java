package crawler.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import crawler.Analyzer;

/**
 * Frame that displays the input and output panels.
 * 
 * @author Mike Westbrook
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements Observer
{    
    /**
     * Panel that retrieves the keywords and website to crawl.
     */
	private final Input my_input = new Input();

	/**
	 * Creates the frame.
	 */
	public MyFrame()
	{
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Adds the input panel and shows the frame.
	 */
	public void start()
	{
		add(my_input);
		pack();

		setResizable(true);
		setVisible(true);
	}

	/**
	 * Called when the analyzer is complete.
	 * 
	 * @param the_obs The analyzer.
	 * @param the_obj Not used.
	 */
	public void update(final Observable the_obs, final Object the_obj)
	{
		final Analyzer a = (Analyzer) the_obs;
		remove(my_input);
		add(new Output(a));
		pack();
	}
}
