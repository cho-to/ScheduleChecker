import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UsersPane extends JPanel {
	
	private JLabel welcomeLabel, connectLabel;
	private String id;
	
	UsersPane(String id){
		this.id = id;
		JLabel welcomeLabel = new JLabel("Welcome [" + id + "] !");
		add(welcomeLabel);
		setBackground(Color.white);
	}
	
	
	
	
}