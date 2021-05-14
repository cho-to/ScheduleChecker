import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsPane extends JPanel {
	JButton addEventButton;
	JButton lightningButton;
	JButton chatButton;
	
	ButtonsPane() {
		setLayout(new GridLayout(3, 1));
		addEventButton = new JButton("일정 입력");
		lightningButton = new JButton("번개 모임");
		chatButton = new JButton("채팅");

		add(addEventButton);
		add(lightningButton);
		add(chatButton);

	}
}