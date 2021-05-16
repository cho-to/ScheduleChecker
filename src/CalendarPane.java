import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPane extends JPanel {
	JLabel calendarLabel;
	CalendarPane() {
		calendarLabel = new JLabel("calendar");
		add(calendarLabel);
//		this.setPreferredSize(new Dimension(400,100));
		setBackground(Color.green);
	}
}