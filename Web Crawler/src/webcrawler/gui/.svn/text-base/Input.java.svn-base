package webcrawler.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")

/**
 * Class that handles the user input
 * 
 * @author Cody Shafer, Mike Westbrook, Brandon Martin, and Jesse Kitterman
 *
 */
public class Input extends JPanel
{
    /**
     * The constant for max keys
     */
	private static final int MAX_KEYS = 10;

	/**
	 * The constant for keyword label
	 */
	private static final JLabel KEYWORDS = 
			new JLabel("Enter keywords in the boxes below.");
	/**
	 * Constant for site label
	 */
	private static final JLabel SITE = 
			new JLabel("Enter the page to start crawling on. Must end with /, .txt, .html");
	
	/**
	 * The text field for max keys
	 */
	private final JTextField[] my_fields = new JTextField[MAX_KEYS];
	
	/**
	 * The text field of the site 
	 */
	private JTextField my_site = new JTextField(60);
	
	/**
	 * Constructor 
	 */
	public Input()
	{
		super();
		setLayout(new BorderLayout());
		for (int i = 0; i < my_fields.length; i++)
		{
			my_fields[i] = new JTextField(10);
		}
		setUpNorth();
		setUpCenter();
		setUpSouth();
	}
	
	/**
	 * Getter for keywords
	 * 
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
	
	/**
	 * Sets up the north panel
	 */
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
	
	/**
	 * Sets up the center panel
	 */
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
	
	/**
	 * Sets up the south panel
	 */
	private void setUpSouth()
	{
		final JPanel panel = new JPanel();
		final JButton button = new JButton("Crawl");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_e)
			{
				new Output(new SingleThread(my_site.getText(), getKeywords()).analyze(), Input.this);
			}
		});
		panel.add(button);
		add(panel, BorderLayout.SOUTH);
	}

}