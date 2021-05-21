import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class TodoPane extends JPanel {
	//todo list 구현
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	TodoButton todoButton[] = new TodoButton[5]; 
	TodoPane() {
		JLabel list_title = new JLabel("Todo List");
		add(list_title);
		Font titleF = new Font("",Font.BOLD, 25);
		list_title.setFont(titleF);
		list_title.setForeground(Color.lightGray);
		list_title.setOpaque(true);
		list_title.setBackground(Color.white);
		
		for (int i =0; i<5; i++) {
			todoButton[i] = new TodoButton();
			add(todoButton[i]);
<<<<<<< HEAD
			todoButton[i].setOpaque(true);
			todoButton[i].setBackground(new Color(0,42,126));
			todoButton[i].setBorderPainted(false);
			todoButton[i].setFocusPainted(false);
			todoButton[i].setLayout(new GridLayout(1,0,0,5));
			setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));//상.좌.하.우
=======
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
		}
		setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));//상.좌.하.우
		setLayout(new GridLayout(0,1,0,5));//(행,열, 수평gap, 수직gap
		setBackground(Color.white);
	}
	
	void showImpending(List<ScheduleModel> schedules) {
		for(int i = 0; i < schedules.size(); i++) {
			todoButton[i].configure(schedules.get(i));
		}
	}
}