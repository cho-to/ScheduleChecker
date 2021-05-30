import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UsersPane extends JPanel {
	
	BufferedReader fromServer = null;
	PrintWriter toServer = null;
	
	private JPanel welcomePanel, connectPanel, UserPanel;
	private JLabel welcomeLabel, connectLabel, UserLabel;
	String id;
	String ip;
	String result;
	Socket socket;
	
	UsersPane(Socket s, String id) throws IOException{
		this.id = id;
		this.socket = s;
		
		
		//welcomePanel = new JPanel();
		welcomeLabel = new JLabel("Welcome [" + id + "] !");
		
		Font welcomeF = new Font("", Font.BOLD, 20);
		welcomeLabel.setFont(welcomeF);
		welcomeLabel.setForeground(Color.DARK_GRAY);
		welcomeLabel.setOpaque(true);
		welcomeLabel.setBackground(Color.white);
		welcomeLabel.setBounds(500, 20, 1000, 20);
		
		InetAddress iaddr=socket.getLocalAddress();                      
        ip = iaddr.getHostAddress();
		connectLabel = new JLabel("Your ip number is... " + ip );

		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toServer = new PrintWriter(socket.getOutputStream(), true);
		toServer.println("user");
		toServer.println(id);
		toServer.flush();
		
		String type = fromServer.readLine();
		result = fromServer.readLine();

		if (type.equals("user")) {
			UserLabel = new JLabel("Online Users : " + result);
		}
		
		add(welcomeLabel);
		add(connectLabel);
		add(UserLabel);
		
		setBackground(Color.white);
	}
	
	public void redrawUser(String str) {
		welcomeLabel = new JLabel("Welcome [" + id + "] !");
		
		Font welcomeF = new Font("", Font.BOLD, 20);
		welcomeLabel.setFont(welcomeF);
		welcomeLabel.setForeground(Color.DARK_GRAY);
		welcomeLabel.setOpaque(true);
		welcomeLabel.setBackground(Color.white);
		welcomeLabel.setBounds(500, 20, 1000, 20);
		connectLabel = new JLabel("Your ip number is... " + ip );
		UserLabel = new JLabel("Online Users : " + str);
		
		add(welcomeLabel);
		add(connectLabel);
		add(UserLabel);
		setBackground(Color.white);
		
	}
	
	
	
	
	
}