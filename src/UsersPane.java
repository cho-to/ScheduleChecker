import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UsersPane extends JPanel {
	
	private JLabel welcomeLabel, connectLabel, UserLabel;
	private String id;
	Socket socket;
	
	UsersPane(Socket s, String id) throws IOException{
		this.id = id;
		this.socket = s;
		
		welcomeLabel = new JLabel("Welcome [" + id + "] !");
		add(welcomeLabel);
		
		InetAddress iaddr=socket.getLocalAddress();                      
        String ip = iaddr.getHostAddress();
		connectLabel = new JLabel("Connected to " + ip );
		add(connectLabel);
		
		/*
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter toServer = new PrintWriter(socket.getOutputStream());
		
		
		toServer.print(id);
		
		toServer.flush();
		
		
		String result = fromServer.readLine();
		
		System.out.println("ok");
		
		UserLabel = new JLabel(result);
		add(UserLabel);
		*/
		
		
		setBackground(Color.white);
	}
	
	
	
	
}