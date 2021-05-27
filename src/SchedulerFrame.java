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
    FooThread f;
    JPanel temp;
    
    Socket socket=null;
	
	SchedulerFrame(String id) throws IOException {
		this.id = id;

		try{

			//socket=new Socket("192.168.0.40",3000);
			socket=new Socket("localhost",3000);
			setupComp();
			setupControllers();

			f = new FooThread(socket, usersPane, calendarController);
			f.start(); 
			
			buttonsPane = new ButtonsPane(socket, id, f);
			buttonsPane.setPreferredSize(new Dimension(300, 200));
			temp.add(buttonsPane);
			getContentPane().add(temp, BorderLayout.EAST);
			buttonsPane.setCalendarController(calendarController);
			
			
			setTitle("Scheduler");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(1000, 800);
			setVisible(true);
			
			
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
		//buttonsPane = new ButtonsPane(socket, id, f);
		
		usersPane.setPreferredSize(new Dimension(1000, 70));
		todoPane.setPreferredSize(new Dimension(300, 300));
		//buttonsPane.setPreferredSize(new Dimension(300, 200));

		temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		temp.add(todoPane);
		temp.add(weatherPane);
		//temp.add(buttonsPane);
		getContentPane().add(usersPane, BorderLayout.PAGE_START);
		getContentPane().add(calendarPane, BorderLayout.CENTER);
		//getContentPane().add(temp, BorderLayout.EAST);
	}
	
	private void setupControllers() {
		calendarController = new CalendarController(calendarPane, todoPane, this.id);
		//buttonsPane.setCalendarController(calendarController);
		calendarPane.setController(calendarController);
	}
	
	public String getID() {
		return id;
	}
	
}

class FooThread extends Thread{

    Socket socket;

    UsersPane usersPane;
    String id;
    SchedulerFrame f;
    CalendarController controller;
    AlertFrame alert;
    ChatFrame chatframe = null;
    
    private Gson gson = new Gson();
    
    public FooThread(Socket socket, UsersPane usersPane, CalendarController controller) {
          this.usersPane = usersPane;
          this.socket=socket;
          this.controller = controller;
          this.id = usersPane.id;
    }
    
    public void setChatFrame(ChatFrame cf) {
    	this.chatframe = cf;
    }

    public void run() {
		BufferedReader fromServer = null;
		try {
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	while(true) {
				String type = fromServer.readLine();
				String result = fromServer.readLine();
				if (type != null) {
	    			if (type.contains("lightning")) {
	    				System.out.println("received text : " +result);
	    				String strArray[] = type.split("@");
	    				String sendID = strArray[1];
	    				
	    				ScheduleModel receivedSchedule = gson.fromJson(result, ScheduleModel.class);
	    				controller.addNewScheudle(receivedSchedule);
	    				if(!sendID.equals(id)) {
	    				alert = new AlertFrame(sendID, receivedSchedule.title);
	    				}
	    			}
	    			else if (type.equals("user")) {
	    				System.out.println("user changed");
	    				usersPane.removeAll();
	    				usersPane.redrawUser(result);
	    				usersPane.revalidate();
	    				usersPane.repaint();
	    			}
	    			else if (type.equals("chat")) {
	    				if(chatframe != null) {
	    					chatframe.txtArea.append(result+"\n");
	    					
	    				}
	    			}
				}

	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

}
