import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewScheduleDialog extends JDialog implements ActionListener {

    private JPanel panel1, panel2;
    private JLabel label;
    private JButton okayButton;
    private JButton cancelButton;
    private CalendarController calendarController;

    NewScheduleDialog(CalendarController calendarController) {
    	setComp();
    	this.calendarController = calendarController;
    }
    
    private void setComp() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        add(panel1);

        label = new JLabel("일정 추가하기", JLabel.CENTER);
        panel1.add(label, BorderLayout.CENTER);
        panel2 = new JPanel();
        panel1.add(panel2, BorderLayout.SOUTH);

        okayButton = new JButton("OK");
        okayButton.addActionListener(this);
        panel2.add(okayButton);
        cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(this);
        panel2.add(cancelButton);
        
        System.out.println("dialog");
        setBounds(100, 100, 500, 500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose(); //다이얼로그 제거
            }
        });
        setVisible(true);

    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okayButton) {
        	calendarController.addNewScheudle();
        }else if (e.getSource() == cancelButton) {
        }
        dispose(); //다이얼로그 제거
	}
}
