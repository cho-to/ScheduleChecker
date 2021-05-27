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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;


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
			setupComp();
			setupControllers();
			setTitle("Scheduler");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(1000, 800);
			setVisible(true);

			new FooThread(socket, this, calendarController).start();

		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
		
		

	}
	
	private void setupComp() throws IOException{
		calendarPane = new CalendarPane(id);
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
		calendarPane.setController(calendarController);
	}
	
	
}

class FooThread extends Thread{

    Socket socket;
    SchedulerFrame f;
    CalendarController controller;
    String id;
	private Gson gson = new Gson();

    public FooThread(Socket socket, SchedulerFrame f, CalendarController controller) {
          this.f = f;
          this.socket=socket;
          this.controller = controller;
    }

    public void run() {
		BufferedReader fromServer = null;
		try {
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	while(true) {
				String type = fromServer.readLine();
				String result = fromServer.readLine();
				if (type != null) {
	    			if (type.equals("lightning")) {
	    				System.out.println("received text : " +result);
	    				ScheduleModel receivedSchedule = gson.fromJson(result, ScheduleModel.class);
	    				controller.addNewScheudle(receivedSchedule);
	    			}
				}

	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

}
