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
	static final int Cal_Width = 7;//-> 반대로 된 것 덜고쳤을 수도
	final static int Cal_Height = 6;
	int dates[][] = new int[Cal_Height][Cal_Width];
	String schedule;
	Calendar cal_day = Calendar.getInstance();//5월 달력
	//상수 string
	final String day_string_name[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	//LineBorder lb;
	JLabel calendarLabel;
	JButton dayName[] = new JButton[7];//맨 위에 요일 줄 표시
	JButton dateButton[][] = new JButton[Cal_Height][Cal_Width];// 각 date 버튼
	JButton deleteButton[];//일정 삭제 버튼
	Boolean checkSchedule1[][] = new Boolean [Cal_Height][Cal_Width];
	Boolean checkSchedule2[][] = new Boolean [Cal_Height][Cal_Width];//일정이 달력에 입력됐는지 확인
	String scheduleList[][] = new String [Cal_Height][Cal_Width];//일정 배열
	int numOfSchedule[][] = new int [Cal_Height][Cal_Width];//일정 개수
	String times[][] = new String[32][101];//시간[날짜][numOfSchedule]
	int todayNumX = 0, todayNumY = 0; //열러는 날짜의 인덱스
	String[] scheduleName = new String[101];
	private String id;
	private CalendarController controller;
	
	//calendar gui 구현
	CalendarPane(String id) {
		setBackground(Color.white);
		this.id = id;		
		//요일
		for (int i =0; i< Cal_Width; i++) {
			dayName[i]=new JButton(day_string_name[i]);
			add(dayName[i]);
			dayName[i].setBorderPainted(false);//테두리 없에기
			dayName[i].setContentAreaFilled(false);//클릭해도 소용없게
			dayName[i].setFocusPainted(false);//버튼 누를때 테두리 안생기게
			dayName[i].setOpaque(true);//배경색 불투명하게->
			//배경 색
			dayName[i].setBackground(Color.white/*new Color(200, 50, 50)*/);//-
			//요일별 글자색
			if (i == 0)
				dayName[i].setForeground(new Color(200, 50, 50));
			else if (i == 6)
				dayName[i].setForeground(new Color(50, 100, 200));
			else 
				dayName[i].setForeground(new Color(150, 150, 150));
		}
		
		makeDateButtons();
		
		//달력 표시
	}
	
	void makeDateButtons() {
		//날짜
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
				setLayout(new GridLayout(0,7,2,2));//그리드 (채우기)
				//경계
				TitledBorder lb = new TitledBorder(new LineBorder(Color.darkGray));
				setBorder(lb);
				//setBorder(Color.darkGray); 경계...
				setBorder(BorderFactory.createEmptyBorder(10, 20, 40, 20));//상.좌.하.우
				
				dateButton[i][j].addActionListener(new /*DateDialog*/CalendarClickListener());
			}
		}
		initCal(cal_day);
		showCal();

	}
	
	//달력 만들기
	int month;
	int year;
	int last_date;
	int last_day_of_month[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	private void initCal(Calendar cal){
		//초기화
		for(int i = 0 ; i < Cal_Height ; i++){
			for(int j = 0 ; j < Cal_Width ; j++){
				dates[i][j] = 0;
			}
		}
		
		for(int i = 0 ; i < Cal_Height ; i++){//스케줄 초기롸
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
		//월의 시작 일의 위치
		int beginnig_date = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7 ) % 7;
		
		//마지막 날짜
		//2월인 경우 윤년 고려
		if (month == 1) {
			if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				last_date = 29;
		}
		else 
			last_date = last_day_of_month[month];
		
		//달력만들기
		int day = 1, start_pos = 0;//달이 시작하는 위치
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
	
	//해당 날짜에 일정 있으면 표시하기 -> 일정 개수에 따라 표시?
	private void showCal(){
		for(int i = 0; i < Cal_Height; i++){
			for(int j = 0; j < Cal_Width; j++){
				
				//날짜 색
				dateButton[i][j].setFont(new Font("",Font.BOLD, 15));
				dateButton[i][j].setForeground(Color.darkGray);
				if(j==0) 
					dateButton[i][j].setForeground(new Color(200, 50, 50));
				else if(j==6) 
					dateButton[i][j].setForeground(new Color(50, 100, 200));
		
				//파일에 날짜 입력
				File f = new File("calendars/" + year + ( (month + 1) < 10 ? "0" : "")
						+ (month + 1) + (dates[i][j] < 10 ? " 0":"")
						+ dates[i][j]+".txt");
				//메모에 저장된 값이 있으면
				if(f.exists()){
					dateButton[i][j].setText(dates[i][j]+"");
					//read file
				}
				//없으면 그냥 표시
				else dateButton[i][j].setText(dates[i][j]+"");
				
				//JLabel todayMark = new JLabel("<html><font color=green>*</html>");-> 오늘 표시
				//dateButton[i][j].removeAll();
				
				/*if(month == cal_day.get(Calendar.MONTH) &&	year == cal_day.get(Calendar.YEAR) && dates[i][j] == cal_day.get(Calendar.DAY_OF_MONTH)){
					//dateButton[i][j].add(todayMark);
					dateButton[i][j].setToolTipText("Today");
				}*/
				
				//0이면 안보이게 
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
		//날짜 가져오기 
		for(int i = 0; i < Cal_Height; i++){
			for(int j = 0; j < Cal_Width; j++){
				int today = dates[i][j];
				String dateS = schedules.dateString;// 몇일이 맞는지만 확인 => 년, 월 확인 필요?
				String fileName =  schedules.id;//제독
				String date = dateS.substring(8);
				int cmpDate = Integer.parseInt(date);
				//날짜 맞으면
				if (cmpDate == today) {
					scheduleList[i][j] += schedule+"@";
					System.out.println(scheduleList[i][j]);//check
					
					++numOfSchedule[i][j];
					int row = dates[i][j];
					int col = numOfSchedule[i][j];
					times[row][col] = schedule+"@"+fileName;//times[날짜][col] = 스케줄명 + @ + 파일명
				
					System.out.println(row);
					System.out.println(col);
					System.out.println(times[row][col]);
					
					
					
					//개수에 따른 달력 색
					if (numOfSchedule[i][j] >= 1 && numOfSchedule[i][j] <= 2) {// 1~2개 노란색
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.yellow);
					}
					if (numOfSchedule[i][j] >= 3 && numOfSchedule[i][j] <= 5) {// 3~5개 주황색
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.orange);
					}
					if (numOfSchedule[i][j] >= 6) {// 6~개 빨간색
						//System.out.println("*****************one");
						dateButton[i][j].setOpaque(true);
						dateButton[i][j].setBackground(Color.red);
					}
					
					
					
					
					//버튼에 출력 
					if (!checkSchedule1[i][j]) {//첫번째 날짜 표시
						s1 = new JLabel(schedule);
						dateButton[i][j].add(s1);
						checkSchedule1[i][j] = true;
					}
					else if (!checkSchedule2[i][j]) {//두번째 날짜 표시
						s2 = new JLabel("<HTML><br>"+schedule);
						dateButton[i][j].add(s2);
						checkSchedule2[i][j] = true;
					}
					else {
						s3= new JLabel("<HTML><br><br><br>...");//세개 이상은 ... 표시
						dateButton[i][j].add(s3);
					}
				}
				
			}
		}
		
	}
	void showSchedule (List<ScheduleModel> schedules) {//스케줄 열람
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
	
	
	//달력 누르면 동작
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
 			//프레임
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
			
			//일점버튼
			//for(int i=0 ; i < Cal_Height ; i++){
				//for(int j=0 ; j < Cal_Width ; j++){	
			if(scheduleList[todayNumX][todayNumY].length() > 0) {//스케줄이 있는 경우 표시
				String getList = scheduleList[todayNumX][todayNumY];//일정 가져오기
				String[] list = getList.split("@");
				numOfTodo = list.length;
				System.out.println(numOfTodo);
				dateSchedule = new JButton[numOfTodo];
				deleteButton = new JButton[numOfTodo];
						
				if (numOfTodo > 0) {
					for (int k = 0; k < list.length; k++) {
						dateSchedule[k] = new JButton (list[k]);//일정버튼
						scheduleName[k] = list[k];//이름
						datePanel.add(dateSchedule[k]);
							
						deleteButton[k] = new JButton ("delete");//삭제버튼
						datePanel.add(deleteButton[k]);
						deleteButton[k].addActionListener(new deleteCLickListner());
					}
				}
						
				//	}//for j	
			//}//for i
						
				setVisible(true);
			}
			else {//스케줄이 없는 경우
				noInput = new JLabel("There is no schedule");
				noInput.setForeground(Color.darkGray);
				datePanel.add(noInput);		
				setVisible(true);
			}
 		}
	}
	
	private class  deleteCLickListner implements ActionListener{//삭제 버튼
		int result = 0, idx = 0;;
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0 ; i < deleteButton.length ; i++){
				if(e.getSource() == deleteButton[i]){ 
					result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?");
					idx = i;
					
				}
			}
			if (result == 0) {//delete file

				//todayNumX,todayNumX 사용
				//5월자 날자 파일 삭제
				int day = dates[todayNumX][todayNumY];
				//System.out.println("name");
				//System.out.println(idx);
				//System.out.println(scheduleName[idx]);
				String deleteFileName ="";
				for (int j = 0; j <101; j++) {
					if (times[day][j].length() > 0) {
						String title = times[day][j].substring(0, times[day][j].indexOf("@"));//일정 명
						//System.out.println(title);
						if (title.equals(scheduleName[idx])) {
							deleteFileName = times[day][j].substring(times[day][j].indexOf("@")+1);//파일 명
							//System.out.println(deleteFileName);
						}
					}
				}
				

				File folder = new File(id);
				File[] listOfFiles = folder.listFiles();
				for (File file : listOfFiles) {
					System.out.println(file.getName());
					if (file.isFile()) {
						String filename = "", extension = "";
						int i = file.getName().lastIndexOf('.');
						if (i > 0) {
							filename = file.getName().substring(0, i);
							extension = file.getName().substring(i + 1);
						}
						if (extension.equals("json")) {// 확장자 json
							if (filename.equals(deleteFileName)) {// 파일 명 확인 deleteFileName
								File deleteFile = new File(file.getAbsolutePath());
								deleteFile.delete();// 삭제
							}
						}
					}
				}
				controller.refresh();
			}
		}
	}
	
	public void setController(CalendarController controller) {
		this.controller = controller;
	}
}
