import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TodoPane extends JPanel {
	//todo list 구현

	JButton todoButton[] = new JButton[5]; 
	TodoPane() {
		JLabel list_title = new JLabel("Todo List");
		add(list_title);
		Font titleF = new Font("",Font.BOLD, 25);
		list_title.setFont(titleF);
		list_title.setForeground(Color.lightGray);
		list_title.setOpaque(true);
		list_title.setBackground(Color.white);
		
		for (int i =0; i<5; i++) {
			todoButton[i] = new JButton();
			add(todoButton[i]);
			todoButton[i].setOpaque(true);
			todoButton[i].setBackground(new Color(0,42,126));
			todoButton[i].setBorderPainted(false);
			todoButton[i].setFocusPainted(false);
			
			setLayout(new GridLayout(0,1,0,5));//(행,열, 수평gap, 수직gap
			setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));//상.좌.하.우
		}
		setBackground(Color.white);
	}
}