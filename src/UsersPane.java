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
	private String id;
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
		
		//welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		
		InetAddress iaddr=socket.getLocalAddress();                      
        String ip = iaddr.getHostAddress();
		connectLabel = new JLabel("Your ip number is... " + ip );
		//connectPanel = new JPanel();
		
		
		//connectPanel.add(connectLabel);
		
		
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toServer = new PrintWriter(socket.getOutputStream(), true);
		
		toServer.println("user");
		toServer.println(id);
		toServer.flush();
		
		String type = fromServer.readLine();
		String result = fromServer.readLine();

		if (type.equals("user")) {
			UserLabel = new JLabel("Online Users : " + result);
		}

		
		
		
		
		//UserPanel = new JPanel();
		/*
		
		UserPanel.add(UserLabel);
		welcomePanel.setPreferredSize(new Dimension(1000, 10));
		connectPanel.setPreferredSize(new Dimension(1000, 10));
		UserPanel.setPreferredSize(new Dimension(1000, 30));
		add(welcomePanel, BorderLayout.NORTH);
		add(connectPanel);
		add(UserPanel, BorderLayout.SOUTH);
		*/
		
		add(welcomeLabel);
		add(connectLabel);
		add(UserLabel);
		
		setBackground(Color.white);
	}
	
	
	
	
}