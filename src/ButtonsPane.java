import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonsPane extends JPanel implements ActionListener {
	JButton addEventButton;
	JButton lightningButton;
	JButton chatButton;
	private CalendarController calendarController;

	ButtonsPane() {
		setLayout(new GridLayout(3, 1));
		addEventButton = new JButton("일정 입력");
		lightningButton = new JButton("번개 모임");
		chatButton = new JButton("채팅");

		add(addEventButton);
		add(lightningButton);
		add(chatButton);
		addEventButton.addActionListener(this);
	}

	public void setCalendarController(CalendarController calendarController) {
		this.calendarController = calendarController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEventButton) {
        	new NewScheduleDialog(calendarController);
        } //다이얼로그 제거
		
	}
}