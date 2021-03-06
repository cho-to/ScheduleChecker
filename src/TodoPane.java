import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class TodoPane extends JPanel {
	//todo list 구현
	
	TodoButton todoButton[];
	TodoPane() {
		JLabel list_title = new JLabel("Todo List");
		add(list_title);
		Font titleF = new Font("",Font.BOLD, 25);
		list_title.setFont(titleF);
		list_title.setForeground(Color.darkGray);
		list_title.setOpaque(true);
		list_title.setBackground(Color.white);
		
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));//상.좌.하.우
		setLayout(new GridLayout(0,1,0,5));//(행,열, 수평gap, 수직gap)
		setBackground(Color.white);
	}
	
	void showImpending(List<ScheduleModel> schedules) {
		todoButton = new TodoButton[5]; 
		for (int i =0; i<5; i++) {
			todoButton[i] = new TodoButton();
			add(todoButton[i]);
		}
		for(int i = 0; i < schedules.size(); i++) {
			todoButton[i].configure(schedules.get(i));
		}
	}
	
	void removeButtons() {
		for (TodoButton component : todoButton) {
		    remove(component);  
		}
	}
}