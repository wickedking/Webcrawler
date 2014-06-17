package crawler.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import crawler.Analyzer;

/**
 * Displays the information from the analyzer in a panel.
 * 
 * @author Mike Westbrook
 *
 */
@SuppressWarnings("serial")
public class Output extends JPanel
{
	/**
	 * Analyzer that has the information to display.
	 */
	private final Analyzer my_analyzer;
	
	/**
	 * Formats doubles to 3 decimal places.
	 */
	private final DecimalFormat my_df = new DecimalFormat("#.###");
	
	/**
	 * Creates and sets up the panels.
	 * 
	 * @param the_analyzer Has the data to be displayed.
	 */
	public Output(final Analyzer the_analyzer)
	{
		super();

		setLayout(new BorderLayout());
		my_analyzer = the_analyzer;
		setupPanel(getCenterData(), new String[]{"Keyword", "Avg hits per page", "Total hits"}, BorderLayout.CENTER);
		setupPanel(getNorthData(), new String[]{"Metric", "Value"}, BorderLayout.NORTH);
	}
	
	/**
	 * Sets up a panel with a table that displays the information.
	 * @param the_data The data to display.
	 * @param the_headers The column titles.
	 * @param the_constraint Where to put the panel.
	 */
	private void setupPanel(final String[][] the_data, final String[] the_headers, final String the_constraint)
	{
		final JTable table = new JTable(the_data, the_headers)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		final JScrollPane pane = new JScrollPane(table);		                                   
		add(pane, the_constraint);
	}

	/**
	 * @return Data for the metric data.
	 */
	private String[][] getCenterData()
	{
		final Map<String, Integer> map = my_analyzer.keywordCount();
		final String[][] data = new String[map.size()][3];
		int index = 0;
		for (final String s : map.keySet())
		{
			data[index][0] = s;
			data[index][1] = my_df.format(my_analyzer.avgHitsPerPage(s));
			data[index][2] = Integer.toString(map.get(s));
			index++;
		}
		return data;
	}

	/**
	 * @return Data for the keywords.
	 */
	private String[][] getNorthData()
	{
		final String[][] data = new String[5][2];
		final String[] title = 
			{"Pages retrieved", "Average words per page", "Average URLs per page", 
				"Average parse time per page", "Total run time"};
		
		for (int i = 0; i < data.length; i++)
		{
			data[i][0] = title[i];
		}
		data[0][1] = Integer.toString(my_analyzer.pageCount());
		data[1][1] = my_df.format(my_analyzer.avgWordsPerPage());
		data[2][1] = my_df.format(my_analyzer.avgLinksPerPage());
		data[3][1] = my_df.format(my_analyzer.avgParseTimePerPage() / 1000000000.0); // convert nano to second
		data[4][1] = Double.toString(my_analyzer.runTime()  / 1000000000.0);
		return data;
	}
}