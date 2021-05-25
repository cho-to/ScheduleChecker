import java.awt.Color;
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
	static final int Cal_Width = 7;//-> �ݴ�� �� �� �������� ����
	final static int Cal_Height = 6;
	int dates[][] = new int[Cal_Height][Cal_Width];
	String schedule;
	Calendar cal_day = Calendar.getInstance();//5�� �޷�
	//��� string
	final String day_string_name[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	//LineBorder lb;
	JLabel calendarLabel;
	JButton dayName[] = new JButton[7];//�� ���� ���� �� ǥ��
	JButton dateButton[][] = new JButton[Cal_Height][Cal_Width];// �� date ��ư
	JButton deleteButton[];//���� ���� ��ư
	Boolean checkSchedule1[][] = new Boolean [Cal_Height][Cal_Width];
	Boolean checkSchedule2[][] = new Boolean [Cal_Height][Cal_Width];//������ �޷¿� �Էµƴ��� Ȯ��
	String scheduleList[][] = new String [Cal_Height][Cal_Width];//���� �迭
	int numOfSchedule[][] = new int [Cal_Height][Cal_Width];//���� ����
	String times[][] = new String[32][101];//�ð�[��¥][numOfSchedule]
	int todayNumX = 0, todayNumY = 0; //������ ��¥�� �ε���
	String[] scheduleName = new String[101];
	

	//calendar gui ����
	CalendarPane() {
		setBackground(Color.white);
		
		//����
		for (int i =0; i< Cal_Width; i++) {
			dayName[i]=new JButton(day_string_name[i]);
			add(dayName[i]);
			dayName[i].setBorderPainted(false);//�׵θ� ������
			dayName[i].setContentAreaFilled(false);//Ŭ���ص� �ҿ����
			dayName[i].setFocusPainted(false);//��ư ������ �׵θ� �Ȼ����
			dayName[i].setOpaque(true);//���� �������ϰ�->
			//��� ��
			dayName[i].setBackground(Color.white/*new Color(200, 50, 50)*/);//-
			//���Ϻ� ���ڻ�
			if (i == 0)
				dayName[i].setForeground(new Color(200, 50, 50));
			else if (i == 6)
				dayName[i].setForeground(new Color(50, 100, 200));
			else 
				dayName[i].setForeground(new Color(150, 150, 150));
		}
		
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
				setLayout(new GridLayout(0,7,2,2));//�׸��� (ä���)
				//���
				TitledBorder lb = new TitledBorder(new LineBorder(Color.darkGray));
				setBorder(lb);
				//setBorder(Color.darkGray); ���...
				setBorder(BorderFactory.createEmptyBorder(10, 20, 40, 20));//��.��.��.��
				
				dateButton[i][j].addActionListener(new /*DateDialog*/CalendarClickListener());
			}
		}
		//�޷� ǥ��
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
				
				//��¥ ��
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
				
				//JLabel todayMark = new JLabel("<html><font color=green>*</html>");-> ���� ǥ��
				//dateButton[i][j].removeAll();
				
				/*if(month == cal_day.get(Calendar.MONTH) &&	year == cal_day.get(Calendar.YEAR) && dates[i][j] == cal_day.get(Calendar.DAY_OF_MONTH)){
					//dateButton[i][j].add(todayMark);
					dateButton[i][j].setToolTipText("Today");
				}*/
				
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
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.yellow);
					}
					if (numOfSchedule[i][j] >= 3 && numOfSchedule[i][j] <= 5) {// 3~5�� ��Ȳ��
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.orange);
					}
					if (numOfSchedule[i][j] >= 6) {// 6~�� ������
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.red);
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


	public class DateDialog {//extends Jframe
		String[] list;
		JButton dateSchedule[];
		JLabel noInput;
		private Frame fs = new Frame("Date Schedule");
		private JPanel datePanel;
 		public DateDialog() {
 			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 			int numOfTodo = 0;
 			//������
 			fs.setVisible(true);
 			fs.addWindowListener(new WindowAdapter() {
 				public void windowClosing(WindowEvent e) {
 					fs.setVisible(false);
 					fs.dispose();
 				}
 			});
 			fs.setSize(200,200);
 			fs.setLocation(200,200);
			datePanel = new JPanel();
			fs.add(datePanel);
			datePanel.setLayout(new GridLayout(5,0));
			
			//������ư
			//for(int i=0 ; i < Cal_Height ; i++){
				//for(int j=0 ; j < Cal_Width ; j++){	
			if(scheduleList[todayNumX][todayNumY].length() > 0) {//�������� �ִ� ��� ǥ��
				String getList = scheduleList[todayNumX][todayNumY];//���� ��������
				String[] list = getList.split("@");
				numOfTodo = list.length;
				System.out.println(numOfTodo);
				dateSchedule = new JButton[numOfTodo];
				deleteButton = new JButton[numOfTodo];
						
				if (numOfTodo > 0) {
					for (int k = 0; k < list.length; k++) {
						dateSchedule[k] = new JButton (list[k]);//������ư
						scheduleName[k] = list[k];//�̸�
						datePanel.add(dateSchedule[k]);
							
						deleteButton[k] = new JButton ("delete");//������ư
						datePanel.add(deleteButton[k]);
						deleteButton[k].addActionListener(new deleteCLickListner());
					}
				}
						
				//	}//for j	
			//}//for i
						
				setVisible(true);
			}
			else {//�������� ���� ���
				noInput = new JLabel("There is no schedule");
				noInput.setForeground(Color.darkGray);
				datePanel.add(noInput);		
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

				//todayNumX,todayNumX ���
				//5���� ���� ���� ����
				int day = dates[todayNumX][todayNumY];
				//System.out.println("name");
				//System.out.println(idx);
				//System.out.println(scheduleName[idx]);
				String deleteFileName ="";
				for (int j = 0; j <101; j++) {
					if (times[day][j].length() > 0) {
						String title = times[day][j].substring(0, times[day][j].indexOf("@"));//���� ��
						//System.out.println(title);
						if (title.equals(scheduleName[idx])) {
							deleteFileName = times[day][j].substring(times[day][j].indexOf("@")+1);//���� ��
							//System.out.println(deleteFileName);
						}
					}
				}
				
				
				File folder = new File(".");
				File[] listOfFiles = folder.listFiles();

				for (File file : listOfFiles) {
				    if (file.isFile()) {
				    	String filename = "", extension = "";
				    	int i = file.getName().lastIndexOf('.');
				    	if (i > 0) {
				    	    filename = file.getName().substring(0,i);
				    	    extension = file.getName().substring(i+1);
				    	}
				    	if (extension.equals("json")) {//Ȯ���� json
				    		if (filename.equals(deleteFileName)) {//���� �� Ȯ�� deleteFileName
				    			//System.out.println("s2");
				    			File deleteFile = new File(file.getName());
				    			deleteFile.delete();//����
				    		}
				    	}
				    }
				}
				
			
			}
		}
	}
}
