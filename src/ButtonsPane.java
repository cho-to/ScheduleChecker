import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
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
		setBackground(Color.white);
		//�����ܵ� �߰��ϸ� ���� �� 
		this.socket = socket;
		this.id = id;
		addEventButton = new JButton("add events");
		appoinmentButton = new JButton("make impromptu appointment");
		chatButton = new JButton("chatting");
		add(addEventButton);
		add(appoinmentButton);
		add(chatButton);
		addEventButton.addActionListener(this);
		appoinmentButton.addActionListener(this);
		addEventButton.setOpaque(true);
		appoinmentButton.setOpaque(true);
		chatButton.setOpaque(true);
		chatButton.addActionListener(this);
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
        else if(e.getSource() == chatButton) {
        	new ChatFrame(socket, id);
        }
        else if(e.getSource() == appoinmentButton) {
        	BufferedReader fromServer = null;
        	PrintWriter toServer = null;

    		try {
				fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    		toServer = new PrintWriter(socket.getOutputStream(), true);
	    		toServer.println("lightning");
	    		toServer.println(id);//���߿� �ٲ�
	    		toServer.flush();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        }

	}
}