import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

public class TodoButton extends JButton {
	JLabel dateLabel;
	JLabel titleLabel;
	JLabel memoLabel;
	
	void configure(ScheduleModel schedule) {
		System.out.println(schedule.dateString);
		System.out.println(schedule.id);
		System.out.println();

		dateLabel = new JLabel(schedule.dateString);
		titleLabel = new JLabel(schedule.title);
		dateLabel.setForeground(Color.white);
		titleLabel.setForeground(Color.white);
		add(dateLabel);
		add(titleLabel);
//		setLayout(new FlowLayout());
	}
}
