import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


class SchedulerFrame extends JFrame {

    private TodoPane todoPane;
    private UsersPane usersPane;
    private ButtonsPane buttonsPane;
    private CalendarPane calendarPane;
    private WeatherPane weatherPane;
    private CalendarController calendarController;
    private String id;
    
    Socket socket=null;
	
	SchedulerFrame(String id) throws IOException {
		this.id = id;
		
		try{
			
//         socket=new Socket("192.168.0.40",3000);
         socket=new Socket("localhost",3000);

		}catch(IOException ie){
            System.out.println(ie.getMessage());
		}
		
		
		setupComp();
		setupControllers();
		setTitle("Scheduler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
      	setSize(1000, 800);
		setVisible(true);
		
		
	}
	
	private void setupComp() throws IOException{
		calendarPane = new CalendarPane();
		todoPane = new TodoPane();
		weatherPane = new WeatherPane();
		weatherPane.setPreferredSize(new Dimension(300, 300));

		usersPane = new UsersPane(socket, id);
		buttonsPane = new ButtonsPane(socket, id);
		
		usersPane.setPreferredSize(new Dimension(1000, 70));
		todoPane.setPreferredSize(new Dimension(300, 300));
		buttonsPane.setPreferredSize(new Dimension(300, 200));

		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		temp.add(todoPane);
		temp.add(weatherPane);
		temp.add(buttonsPane);
		getContentPane().add(usersPane, BorderLayout.PAGE_START);
		getContentPane().add(calendarPane, BorderLayout.CENTER);
		getContentPane().add(temp, BorderLayout.EAST);
	}
	
	private void setupControllers() {
		calendarController = new CalendarController(calendarPane, todoPane, this.id);
		buttonsPane.setCalendarController(calendarController);
	}
	
}
