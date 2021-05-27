import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.google.gson.stream.JsonReader;

public class CalendarPane extends JPanel {
	static final int Cal_Width = 7;
	final static int Cal_Height = 6;
	int dates[][] = new int[Cal_Height][Cal_Width];
	final String day_string_name[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	Calendar cal_day = Calendar.getInstance();//5�� �޷�
	JButton dayName[] = new JButton[7];//�� ���� ���� �� ǥ��
	JButton dateButton[][] = new JButton[Cal_Height][Cal_Width];// �� date ��ư
	JButton deleteButton[];//���� ���� ��ư
	Boolean checkSchedule1[][] = new Boolean [Cal_Height][Cal_Width];//������ �޷¿� �Էµƴ��� Ȯ��
	Boolean checkSchedule2[][] = new Boolean [Cal_Height][Cal_Width];
	String scheduleList[][] = new String [Cal_Height][Cal_Width];//���� �迭
	int numOfSchedule[][] = new int [Cal_Height][Cal_Width];//���� ����
	String times[][] = new String[32][101];//�ð�[��¥][numOfSchedule]
	int todayNumX = 0, todayNumY = 0; //������ ��¥�� �ε���
	String schedule; String[] scheduleName = new String[101];
	private String id;
	private CalendarController controller;
	//calendar gui ����
	CalendarPane(String id) {
		setBackground(Color.white);
		this.id = id;		
		//����
		for (int i =0; i< Cal_Width; i++) {
			dayName[i]=new JButton(day_string_name[i]);
			add(dayName[i]);
			dayName[i].setBorderPainted(false);
			dayName[i].setContentAreaFilled(false);//Ŭ���ص� �ҿ����
			dayName[i].setFocusPainted(false);//��ư ������ �׵θ� �Ȼ����
			dayName[i].setOpaque(true);//���� �������ϰ�->
			//��� ��
			dayName[i].setBackground(Color.white);
			//���Ϻ� ���ڻ�
			if (i == 0)
				dayName[i].setForeground(new Color(200, 50, 50));
			else if (i == 6)
				dayName[i].setForeground(new Color(50, 100, 200));
			else 
				dayName[i].setForeground(new Color(150, 150, 150));
		}
		
		makeDateButtons();
		
		//�޷� ǥ��
	}
	
	void makeDateButtons() {
		//��¥
		for (int i =0; i < Cal_Height; i++) {
			for (int j = 0; j < Cal_Width; j++) {
				dateButton[i][j] = new JButton();
				add(dateButton[i][j]);
				dateButton[i][j].setBorderPainted(false);
				dateButton[i][j].setContentAreaFilled(false);
				dateButton[i][j].setFocusPainted(false);
				dateButton[i][j].setOpaque(true);
				dateButton[i][j].setBackground(Color.white);
				dateButton[i][j].setHorizontalAlignment(SwingConstants.LEFT);
				dateButton[i][j].setVerticalAlignment(SwingConstants.TOP);;
				setLayout(new GridLayout(0,7,2,2));
				setBorder(BorderFactory.createEmptyBorder(10, 20, 40, 20));//��.��.��.��
				
				dateButton[i][j].addActionListener(new CalendarClickListener());
			}
		}
		initCal(cal_day);
		showCal();

	}
	
	//�޷� �����
	int month;
	int year;
	int last_date;
	int last_day_of_month[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	private void initCal(Calendar cal){
		//�ʱ�ȭ
		for(int i = 0 ; i < Cal_Height ; i++){
			for(int j = 0 ; j < Cal_Width ; j++){
				dates[i][j] = 0;
			}
		}
		
		for(int i = 0 ; i < Cal_Height ; i++){//������ �ʱ��
			for(int j = 0 ; j < Cal_Width ; j++){
				checkSchedule1[i][j] = false;
				checkSchedule2[i][j] = false;
				scheduleList[i][j] = "";
				numOfSchedule[i][j] = 0;//
			}
		}
		
		for (int i = 0; i<32 ; i++) {
			for (int j = 0; j < 101; j++) {
				times[i][j] = "";
				scheduleName[i] = "";
			}
		}
		//���� ���� ���� ��ġ
		int beginnig_date = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7 ) % 7;
		
		//������ ��¥
		//2���� ��� ���� ���
		if (month == 1) {
			if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				last_date = 29;
		}
		else 
			last_date = last_day_of_month[month];
		
		//�޷¸����
		int day = 1, start_pos = 0;//���� �����ϴ� ��ġ
		for(int i = 0; i < Cal_Height ; i++){
			if(i == 0) 
				start_pos= beginnig_date;
			else 
				start_pos = 0;
			
			for(int j = start_pos ; j < Cal_Width ; j++){
				if(day <= last_date) 
					dates[i][j] = day++;
			}
		}
	}
	
	//�ش� ��¥�� ���� ������ ǥ���ϱ� -> ���� ������ ���� ǥ��?
	private void showCal(){
		for(int i = 0; i < Cal_Height; i++){
			for(int j = 0; j < Cal_Width; j++){
				//��¥ ���� ��
				dateButton[i][j].setFont(new Font("",Font.BOLD, 15));
				dateButton[i][j].setForeground(Color.darkGray);
				if(j==0) 
					dateButton[i][j].setForeground(new Color(200, 50, 50));
				else if(j==6) 
					dateButton[i][j].setForeground(new Color(50, 100, 200));
		
				//���Ͽ� ��¥ �Է�
				File f = new File("calendars/" + year + ( (month + 1) < 10 ? "0" : "")
						+ (month + 1) + (dates[i][j] < 10 ? " 0":"")
						+ dates[i][j]+".txt");
				//�޸� ����� ���� ������
				if(f.exists()){
					dateButton[i][j].setText(dates[i][j]+"");
					//read file
				}
				//������ �׳� ǥ��
				else dateButton[i][j].setText(dates[i][j]+"");
				
				//0�̸� �Ⱥ��̰� 
				if(dates[i][j] == 0)
					dateButton[i][j].setVisible(false);
				else
					dateButton[i][j].setVisible(true);
				
			}
		}
	}
	
	private void getSchedule (ScheduleModel schedules) {
		JLabel s1; 
		JLabel s2; 
		JLabel s3; 
		/*showCalList.add(s1);
		showCalList.add(s2);
		showCalList.add(s3);*/
		schedule = schedules.title;
		System.out.println("done");
		//��¥ �������� 
		for(int i = 0; i < Cal_Height; i++){
			for(int j = 0; j < Cal_Width; j++){
				int today = dates[i][j];
				String dateS = schedules.dateString;// ������ �´����� Ȯ�� => ��, �� Ȯ�� �ʿ�?
				String fileName =  schedules.id;//����
				String date = dateS.substring(8);
				int cmpDate = Integer.parseInt(date);
				//��¥ ������
				if (cmpDate == today) {
					scheduleList[i][j] += schedule+"@";
					System.out.println(scheduleList[i][j]);//check
					
					++numOfSchedule[i][j];
					int row = dates[i][j];
					int col = numOfSchedule[i][j];
					times[row][col] = schedule+"@"+fileName;//times[��¥][col] = �����ٸ� + @ + ���ϸ�
				
					System.out.println(row);
					System.out.println(col);
					System.out.println(times[row][col]);					
					
					//������ ���� �޷� ��
					if (numOfSchedule[i][j] >= 1 && numOfSchedule[i][j] <= 2) {// 1~2�� �����
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(new Color(180, 199, 231));
					}
					if (numOfSchedule[i][j] >= 3 && numOfSchedule[i][j] <= 5) {// 3~5�� ��Ȳ��
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(new Color(112, 147, 210));
					}
					if (numOfSchedule[i][j] >= 6) {// 6~�� ������
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(new Color(55, 98, 175));
						dateButton[i][j].setForeground(Color.white);
					}
					
					//��ư�� ��� 
					if (!checkSchedule1[i][j]) {//ù��° ��¥ ǥ��
						s1 = new JLabel(schedule);
						dateButton[i][j].add(s1);
						checkSchedule1[i][j] = true;
					}
					else if (!checkSchedule2[i][j]) {//�ι�° ��¥ ǥ��
						s2 = new JLabel("<HTML><br>"+schedule);
						dateButton[i][j].add(s2);
						checkSchedule2[i][j] = true;
					}
					else {
						s3= new JLabel("<HTML><br><br><br>...");//���� �̻��� ... ǥ��
						dateButton[i][j].add(s3);
					}
				}
			}
		}
		
	}
	void showSchedule (List<ScheduleModel> schedules) {//������ ����
		for(int k = 0; k < schedules.size(); k++) {
			getSchedule(schedules.get(k));
			System.out.println("done2");
		}
		
	}

	void removeButtons() {
		for (int i =0; i < Cal_Height; i++) {
			for (int j = 0; j < Cal_Width; j++) {
			    remove(dateButton[i][j]);  
			}
		}
	}

	//�޷� ������ ����
	private class CalendarClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0 ; i < Cal_Height ; i++){
				for(int j=0 ; j < Cal_Width ; j++){
					if(e.getSource() == dateButton[i][j]){ 
						todayNumX = i;
						todayNumY = j;
						new DateDialog();
					}
				}
			}
		}
		
	}


	public class DateDialog extends JFrame{//
		String[] list;
		JButton dateSchedule[];
		JLabel noInputMessage;
		private JPanel datePanel;
 		public DateDialog() {
 			int numOfTodo = 0;
 			//������
 			addWindowListener(new WindowAdapter() {
 				public void windowClosing(WindowEvent e) {
 					setVisible(false);
 					dispose();
 				}
 			});
 			setSize(200,200);
 			setLocation(300,300);
			datePanel = new JPanel();
			add(datePanel);
			datePanel.setBackground(new Color(183, 255, 216));
			//������ư	
			if(scheduleList[todayNumX][todayNumY].length() > 0) {//�������� �ִ� ��� ǥ��
				String getList = scheduleList[todayNumX][todayNumY];//���� ��������
				String[] list = getList.split("@");
				numOfTodo = list.length;
	 			setSize(200,numOfTodo*60);
				System.out.println(numOfTodo);
				dateSchedule = new JButton[numOfTodo];
				deleteButton = new JButton[numOfTodo];
				datePanel.setLayout(new GridLayout(0,2,0,5));
				
				if (numOfTodo > 0) {
					for (int k = 0; k < list.length; k++) {
						datePanel.setBackground(Color.white);
						
						dateSchedule[k] = new JButton (list[k]);//������ư
						scheduleName[k] = list[k];//�̸�
						dateSchedule[k].setSize(100,50);
						dateSchedule[k].setFocusPainted(false);
						dateSchedule[k].setBorderPainted(false);
						dateSchedule[k].setBackground(Color.lightGray);
						Font dateF = new Font("",Font.PLAIN, 13);//��Ʈ
						dateSchedule[k].setFont(dateF);
						dateSchedule[k].setForeground(Color.black);
						dateSchedule[k].setOpaque(true);
						datePanel.add(dateSchedule[k]);
						
						deleteButton[k] = new JButton ("delete");//������ư
						deleteButton[k].setSize(100,50);
						deleteButton[k].setFocusPainted(false);
						deleteButton[k].setBorderPainted(false);
						deleteButton[k].setBackground(new Color(200, 50, 50));
						Font delF = new Font("",Font.PLAIN, 13);
						deleteButton[k].setFont(delF);
						deleteButton[k].setForeground(Color.white);
						deleteButton[k].setOpaque(true);
						datePanel.add(deleteButton[k]);
						deleteButton[k].addActionListener(new deleteCLickListner());
					}
				}
				setVisible(true);
			}
			else {//�������� ���� ���
				noInputMessage = new JLabel("<HTML><br>There is no schedule!<br><br>Add new Events HERE<br>using ADD button:)");
				noInputMessage.setOpaque(true);
				noInputMessage.setBackground(new Color(183, 255, 216));
				noInputMessage.setForeground(Color.darkGray);
				noInputMessage.setHorizontalAlignment(JLabel.CENTER);
				Font mesF = new Font("",Font.PLAIN, 13);
				noInputMessage.setFont(mesF);
				noInputMessage.setForeground(Color.darkGray);
				noInputMessage.setOpaque(true);
				datePanel.add(noInputMessage);		
				setVisible(true);
			}
 		}
	}
	
	private class  deleteCLickListner implements ActionListener{//���� ��ư
		int result = 0, idx = 0;;
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0 ; i < deleteButton.length ; i++){
				if(e.getSource() == deleteButton[i]){ 
					result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?");
					idx = i;
					
				}
			}
			if (result == 0) {//delete file
				//5���� ���� ���� ����
				int day = dates[todayNumX][todayNumY];
				String deleteFileName ="";
				for (int j = 0; j <101; j++) {
					if (times[day][j].length() > 0) {
						String title = times[day][j].substring(0, times[day][j].indexOf("@"));//���� ��
						if (title.equals(scheduleName[idx])) {
							deleteFileName = times[day][j].substring(times[day][j].indexOf("@")+1);//���� ��
						}
					}
				}

				File folder = new File(id);
				File[] listOfFiles = folder.listFiles();
				for (File file : listOfFiles) {
					if (file.isFile()) {
						String filename = "", extension = "";
						int i = file.getName().lastIndexOf('.');
						if (i > 0) {
							filename = file.getName().substring(0, i);
							extension = file.getName().substring(i + 1);
						}
						if (extension.equals("json")) {// Ȯ���� json
							if (filename.equals(deleteFileName)) {// ���� �� Ȯ�� deleteFileName
								File deleteFile = new File(file.getAbsolutePath());
								deleteFile.delete();// ����
							}
						}
					}
				   
				}

					
				}
				controller.refresh();
			}

	}


	
	public void setController(CalendarController controller) {
		this.controller = controller;
	}
}
