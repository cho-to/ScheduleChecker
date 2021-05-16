import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TodoPane extends JPanel {
	TodoPane() {
		JLabel temp = new JLabel("TodoPane");
		add(temp);
		setBackground(Color.red);
	}
}