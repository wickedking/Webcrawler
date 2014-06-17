package webcrawler.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import webcrawler.Analyzer;

@SuppressWarnings("serial")
public class Output extends JPanel
{
	private final Analyzer my_analyzer;
	
	private final Input my_input;
	
	public Output(final Analyzer the_analyzer, final Input the_input)
	{
		super();
		my_analyzer = the_analyzer;
		my_input = the_input;
		setLayout(new BorderLayout());
		makeNorth();
		makeCenter();
		SwingUtilities.getWindowAncestor(my_input).add(this);
		SwingUtilities.getWindowAncestor(this).remove(my_input);
		SwingUtilities.getWindowAncestor(this).pack();
	}
	
	private void makeNorth()
	{
		final String[] headers = {"Metric", "Value"};
		final String[][] data = getAverages();
		final JScrollPane panel = new JScrollPane(new WebcrawlerJTable(data, headers));
		add(panel, BorderLayout.NORTH);
	}
	
	private void makeCenter()
	{
		final String[] headers = {"Keyword", "Avg. hits per page", "Total hits"};
		final String[][] data = getkeyCount();
		final JScrollPane pane = new JScrollPane(new WebcrawlerJTable(data, headers));
		add(pane, BorderLayout.CENTER);
	}

	private String[][] getkeyCount()
	{
		final String[][] data = new String[my_analyzer.getKeywordCount().keySet().size()][3];
		int index = 0;
		DecimalFormat df = new DecimalFormat("#.###");
		for (final String s : my_analyzer.getKeywordCount().keySet())
		{
			data[index][0] = s;
			data[index][1] = (df.format(my_analyzer.getAverageHits(s)));
			data[index][2] = Integer.toString(my_analyzer.getKeywordCount().get(s));
			index++;
		}
		return data;
	}

	private String[][] getAverages()
	{
		DecimalFormat df = new DecimalFormat("#.###");
		final String[][] data = new String[5][2];
		data[0][0] = "Pages Retrieved";
		data[0][1] = Integer.toString(my_analyzer.getPageCount());
		data[1][0] = "Average words per page";
		data[1][1] = df.format(my_analyzer.getAvgWordsPerPage());
		data[2][0] = "Average URLs per page";
		data[2][1] = df.format(my_analyzer.getAvgLinksPerPage());
		data[3][0] = "Average Parse time";
		data[3][1] = Double.toString(my_analyzer.getTotalParseTime() / 1000.0) + " Seconds";
		data[4][0] = "Total Running Time";
		data[4][1] = Double.toString(my_analyzer.getTotalTime()) + " Seconds";
		return data;
	}
}
