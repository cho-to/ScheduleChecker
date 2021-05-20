import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonsPane extends JPanel implements ActionListener {
	JButton addEventButton;
	JButton appoinmentButton;
	JButton chatButton;
	private CalendarController calendarController;
	
	ButtonsPane() {
		setBackground(Color.white);
		//�����ܵ� �߰��ϸ� ���� �� 
		addEventButton = new JButton("add events");
		appoinmentButton = new JButton("make impromptu appointment");
		chatButton = new JButton("chatting");
		add(addEventButton);
		add(appoinmentButton);
		add(chatButton);
		addEventButton.addActionListener(this);
		
		addEventButton.setOpaque(true);
		appoinmentButton.setOpaque(true);
		chatButton.setOpaque(true);
		addEventButton.setBackground(Color.white);
		appoinmentButton.setBackground(Color.white);
		chatButton.setBackground(Color.white);
		
		setLayout(new GridLayout(0,1,2,2));
		setBorder(BorderFactory.createEmptyBorder(10, 5, 40, 15));//��.��.��.��
	}

	public void setCalendarController(CalendarController calendarController) {
		this.calendarController = calendarController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEventButton) {
        	new NewScheduleDialog(calendarController);
        } 
		
	}
}