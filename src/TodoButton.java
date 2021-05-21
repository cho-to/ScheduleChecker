import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
public class TodoButton extends JButton {
	JLabel dateLabel;
	JLabel titleLabel;
	JLabel memoLabel;

	TodoButton() {
		setOpaque(true);
		setBackground(new Color(0,42,126));
		setBorderPainted(false);
		setFocusPainted(false);
		setLayout(new GridLayout(1,0,0,5));
	}

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