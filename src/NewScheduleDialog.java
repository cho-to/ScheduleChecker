import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class NewScheduleDialog extends JDialog implements ActionListener {
    private JPanel panel1, panel2;
    private JButton okayButton;
    private JButton cancelButton;
    private CalendarController calendarController;
    private JTextField titleTextField, dateTextField, timeTextField, memoTextField;
    private Boolean isLightning;
    private Socket socket;
	private Gson gson = new Gson();

    
    NewScheduleDialog(CalendarController calendarController, Boolean isLightning, Socket socket) {
    	setComp();
    	this.calendarController = calendarController;
    	this.isLightning = isLightning;
    	this.socket = socket;
    }
    
	//UI 그리는데 필요한 코드
    private void setComp() {
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));   
        add(panel1);
        
        titleTextField = new JTextField();
        dateTextField = new JTextField();
        timeTextField = new JTextField();
        memoTextField = new JTextField();
        addTextfield("title ", titleTextField, panel1);
        addTextfield("date (YYYY-MM-DD date)", dateTextField, panel1);
        addTextfield("time (hour-min) ", timeTextField, panel1);
        addTextfield("meno", memoTextField, panel1);

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
                dispose(); 
            }
        });
        setVisible(true);
        	
    }
    
    //판넬에 label과 TextField 묶음을 추가하기
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
			if (!this.isLightning) {
				//local에 새로운 일정 추가하기
				calendarController.addNewScheudle(newSchedule);
			} else {
				//네트워크 통신으로 새로운 일정 추가하기
				BufferedReader fromServer = null;
				PrintWriter toServer = null;
				String json = gson.toJson(newSchedule);
				System.out.println("client sending json :" + json);
				try {
					fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					toServer = new PrintWriter(socket.getOutputStream(), true);
					toServer.println("lightning");
					toServer.println(json);//새롭게 입력받은일정을 json파일로변환해서 보내준다
					toServer.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}else if (e.getSource() == cancelButton) {
		}
		dispose(); 
	}
}
