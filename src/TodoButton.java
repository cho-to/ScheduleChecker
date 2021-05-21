import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
public class TodoButton extends JButton {
	JLabel dateLabel;
	JLabel titleLabel;
	JLabel memoLabel;
<<<<<<< HEAD

=======
	
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	TodoButton() {
		setOpaque(true);
		setBackground(new Color(0,42,126));
		setBorderPainted(false);
		setFocusPainted(false);
		setLayout(new GridLayout(1,0,0,5));
	}
<<<<<<< HEAD

=======
	
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	void configure(ScheduleModel schedule) {
		System.out.println(schedule.dateString);
		System.out.println(schedule.id);
		System.out.println();
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
		dateLabel = new JLabel(schedule.dateString);
		titleLabel = new JLabel(schedule.title);
		dateLabel.setForeground(Color.white);
		titleLabel.setForeground(Color.white);
		add(dateLabel);
		add(titleLabel);
//		setLayout(new FlowLayout());
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
