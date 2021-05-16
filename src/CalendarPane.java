import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPane extends JPanel {
	//TODO: 달력 화면 보여주기
	JLabel calendarLabel;
	
	CalendarPane() {
		calendarLabel = new JLabel("calendar");
		add(calendarLabel);
		setBackground(Color.green);
	}

}