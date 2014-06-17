package crawler.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jsoup.nodes.Document;

import crawler.Analyzer;
import crawler.Broker;
import crawler.Data;
import crawler.Parser;
import crawler.Retriever;

/**
 * Collects the keywords to search for and the site to crawl.
 * 
 * @author Mike Westbrook
 *
 */
@SuppressWarnings("serial")
public class Input extends JPanel
{
	/**
	 * Max number of keys to search for.
	 */
	private static final int MAX_KEYS = 10;

	/**
	 * Prompts for the panel.
	 */
	private static final JLabel KEYWORDS = 
			new JLabel("Enter keywords in the boxes below.");
	private static final JLabel SITE = 
			new JLabel("Enter the page to start crawling on. MUST end with /, .txt, .html.");

	/**
	 * Max items in the buffer.
	 */
	protected static final int MAX_BUFFER = 100;

	/**
	 * Default URL. Sandbox site.
	 */
	private static final String DEF_URL = "http://131.191.55.70/";
	
	/**
	 * Fields to enter keywords.
	 */
	private final JTextField[] my_fields = new JTextField[MAX_KEYS];
	
	/**
	 * Field to enter the site to crawl.
	 */
	private JTextField my_site = new JTextField(60);
	
	/**
	 * Sets up the panels
	 */
	public Input()
	{
		super();
		setLayout(new BorderLayout());
		for (int i = 0; i < my_fields.length; i++)
		{
			my_fields[i] = new JTextField(10);
		}
		my_site.setText("http://");
		setUpNorth();
		setUpCenter();
		setUpSouth();
	}
	
	/**
	 * @return All of the unique keys entered. Converted to lowercase.
	 */
	public Set<String> getKeywords()
	{
		final Set<String> keys = new HashSet<String>();
		for (final JTextField b : my_fields)
		{
			if (!b.getText().equals(""))
			{
				keys.add(b.getText().toLowerCase().trim());
			}
		}
		return keys;
	}

	private void setUpNorth()
	{
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		JPanel inner = new JPanel();
		inner.add(KEYWORDS);
		panel.add(inner);
		
		inner = new JPanel();
		for (int i = 0; i < my_fields.length / 2; i++)
		{
			inner.add(my_fields[i]);
		}
		panel.add(inner);
		
		inner = new JPanel();
		for (int i = my_fields.length / 2; i < my_fields.length; i++)
		{
			inner.add(my_fields[i]);
		}
		panel.add(inner);
		add(panel, BorderLayout.NORTH);
	}
	
	private void setUpCenter()
	{
		final JPanel panel = new JPanel();
		JPanel inner = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		panel.add(inner);
		inner.add(SITE);
		
		inner = new JPanel();
		panel.add(inner);
		inner.add(my_site);
		add(panel, BorderLayout.CENTER);
	}
	
	private void setUpSouth()
	{
		final JPanel panel = new JPanel();
		final JButton button = new JButton("Crawl");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_e)
			{
				crawl();
			}
		});
		panel.add(button);
		add(panel, BorderLayout.SOUTH);
	}

	private void crawl()
	{
		final Broker<Document> doc_broker = new Broker<Document>(MAX_BUFFER);
		final Broker<URL> url_broker = new Broker<URL>(MAX_BUFFER);
		final Broker<Data> data_broker = new Broker<Data>(MAX_BUFFER);
		try
		{
			final Analyzer a = new Analyzer(getKeywords(), data_broker);
			String url = my_site.getText().equals("http://") ? DEF_URL : my_site.getText();
			if (!url.endsWith("/"))
			{
				url = url + "/";
			}
			url_broker.add(new URL(url));
			final Parser p = new Parser(doc_broker, url_broker, data_broker);
			final Retriever r = new Retriever(doc_broker, url_broker);
			a.addObserver((Observer) SwingUtilities.getWindowAncestor(this));
			
			final ExecutorService pool = Executors.newFixedThreadPool(3);
			pool.execute(r);
			pool.execute(p);
			pool.execute(a);
			pool.shutdown();
		} catch (MalformedURLException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}