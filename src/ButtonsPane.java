import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonsPane extends JPanel implements ActionListener {
	JButton addEventButton;
	JButton appoinmentButton;
	JButton chatButton;
	Socket socket;
	String id;
	private CalendarController calendarController;
	
	ButtonsPane(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
		setBackground(Color.white);
		//�����ܵ� �߰��ϸ� ���� �� 
		ImageIcon event = new ImageIcon(("./icon/calendar.png"));//�⺻ �̹���
		ImageIcon alarm = new ImageIcon(("./icon/alarm.png"));//�⺻ �̹���
		ImageIcon chat = new ImageIcon(("./icon/chat.png"));//�⺻ �̹���
		Image imgE = event.getImage();
		Image imgA = alarm.getImage();
		Image imgC = chat.getImage();
		Image changeImgE = imgE.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon iconE = new ImageIcon(changeImgE);
		Image changeImgA = imgA.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon iconA = new ImageIcon(changeImgA);
		Image changeImgC = imgC.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon iconC = new ImageIcon(changeImgC);
		Font buttonF = new Font("",Font.BOLD, 13);
		
		addEventButton = new JButton("Add Events",iconE);
		appoinmentButton = new JButton("Make Impromptu Appointment",iconA);
		chatButton = new JButton("Chatting",iconC);
		add(addEventButton);
		add(appoinmentButton);
		add(chatButton);
		addEventButton.addActionListener(this);
		
		addEventButton.setFont(buttonF);
		addEventButton.setForeground(Color.darkGray);
		appoinmentButton.setFont(buttonF);
		appoinmentButton.setForeground(Color.darkGray);
		chatButton.setFont(buttonF);
		chatButton.setForeground(Color.darkGray);
		
		addEventButton.setOpaque(true);
		appoinmentButton.setOpaque(true);
		chatButton.setOpaque(true);
		chatButton.addActionListener(this);
		addEventButton.setBackground(Color.white);
		appoinmentButton.setBackground(Color.white);
		chatButton.setBackground(Color.white);
		
		setLayout(new GridLayout(0,1,2,2));
		setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 20));//��.��.��.��
	}

	public void setCalendarController(CalendarController calendarController) {
		this.calendarController = calendarController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEventButton) {
        	new NewScheduleDialog(calendarController);
        } 
        else if(e.getSource() == chatButton) {
        	new ChatFrame(socket, id);
        }
	}
}