import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPane extends JPanel {
	CalendarPane() {
		JLabel temp = new JLabel("calendar");
		add(temp);
//		this.setPreferredSize(new Dimension(400,100));
		setBackground(Color.green);
	}
}