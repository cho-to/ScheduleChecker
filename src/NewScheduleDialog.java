import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewScheduleDialog extends JDialog implements ActionListener {

    private JPanel panel1, panel2;
    private JLabel label;
    private JButton okayButton;
    private JButton cancelButton;
    private CalendarController calendarController;
    private JTextField titleTextField, dateTextField, timeTextField, memoTextField;

    NewScheduleDialog(CalendarController calendarController) {
    	setComp();
    	this.calendarController = calendarController;
    }
    
    private void setComp() {
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));   
        add(panel1);
        
        titleTextField = new JTextField();
        dateTextField = new JTextField();
        timeTextField = new JTextField();
        memoTextField = new JTextField();
        		
        addTextfield("일정 제목 ", titleTextField, panel1);
        addTextfield("날짜 (YYYY-MM-DD 형식)", dateTextField, panel1);
        addTextfield("시간 (시간:분 형) ", timeTextField, panel1);
        addTextfield("메모", memoTextField, panel1);
        
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
    
	private void addTextfield(String text, JTextField textField, Container container) {
		JLabel tempText = new JLabel(text);
		tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(tempText);
		textField.setPreferredSize(new Dimension(200, 60));
		textField.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(textField);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okayButton) {
        	ScheduleModel newSchedule = new ScheduleModel(dateTextField.getText(), timeTextField.getText(), titleTextField.getText(), memoTextField.getText());
        	calendarController.addNewScheudle(newSchedule);
        }else if (e.getSource() == cancelButton) {
        }
        dispose(); //다이얼로그 제거
	}
}
