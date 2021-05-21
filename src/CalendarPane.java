import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CalendarPane extends JPanel {
	static final int Cal_Width = 7;//-> 반대로 된 것 덜고쳤을 수도
	final static int Cal_Height = 6;
	int dates[][] = new int[Cal_Height][Cal_Width];
	
	Calendar cal_day = Calendar.getInstance();//5월 달력
	//상수 string
	final String day_string_name[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	//LineBorder lb;
	JLabel calendarLabel;
	JButton dayName[] = new JButton[7];//맨 위에 요일 줄 표시
	JButton dateButton[][] = new JButton[Cal_Height][Cal_Width];// 각 date 버튼
	
	//calendar gui 구현
	CalendarPane() {
		//calendarLabel = new JLabel("calendar");
		//add(calendarLabel);
		setBackground(Color.white);
		
		//요일
		for (int i =0; i< Cal_Width; i++) {
			dayName[i]=new JButton(day_string_name[i]);
			add(dayName[i]);
			dayName[i].setBorderPainted(false);//테두리 없에기
			dayName[i].setContentAreaFilled(false);//클릭해도 소용없게
			dayName[i].setFocusPainted(false);//버튼 누를때 테두리 안생기게
			dayName[i].setOpaque(true);//배경색 불투명하게->굳이?
			//배경 색
			dayName[i].setBackground(Color.white/*new Color(200, 50, 50)*/);//->굳이?
			//요일별 글자색
			if (i == 0)
				dayName[i].setForeground(new Color(200, 50, 50));
			else if (i == 6)
				dayName[i].setForeground(new Color(50, 100, 200));
			else 
				dayName[i].setForeground(new Color(150, 150, 150));
		}
		
		//날짜
		for (int i =0; i < Cal_Height; i++) {
			for (int j = 0; j < Cal_Width; j++) {
				dateButton[i][j] = new JButton();
				add(dateButton[i][j]);
				dateButton[i][j].setBorderPainted(false);//테두리 없에기
				dateButton[i][j].setContentAreaFilled(false);//클릭해도 소용없게
				dateButton[i][j].setFocusPainted(false);//버튼 누를때 테두리 안생기게
				dateButton[i][j].setOpaque(true);//배경색 불투명하게->굳이?
				dateButton[i][j].setBackground(Color.lightGray);//굳이 배경색 
				dateButton[i][j].setHorizontalAlignment(SwingConstants.LEFT);
				dateButton[i][j].setVerticalAlignment(SwingConstants.TOP);;
				setLayout(new GridLayout(0,7,2,2));//그리드 (채우기)
				//경계
				TitledBorder lb = new TitledBorder(new LineBorder(Color.darkGray));
				setBorder(lb);
				//setBorder(Color.darkGray); 경계...
				setBorder(BorderFactory.createEmptyBorder(10, 20, 40, 20));//상.좌.하.우
				
				dateButton[i][j].addActionListener(new CalendarClickListener());
			}
		}
		
		//달력 표시
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
				File f = new File("MemoData/" + year + ( (month + 1) < 10 ? "0" : "")
						+ (month + 1) + (dates[i][j] < 10 ? " 0":"")
						+ dates[i][j]+".txt");
				//메모에 저장된 값이 있으면 뭐가 되나 확인 
				if(f.exists()){
					dateButton[i][j].setText(dates[i][j]+"");
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
	
	//달력 누르면 동작하는 거
	private class CalendarClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k=0,l=0;
			for(int i=0 ; i < Cal_Height ; i++){
				for(int j=0 ; j < Cal_Width ; j++){
					if(e.getSource() == dateButton[i][j]){ 
						k=i;
						l=j;
					}
				}
			}
			//해당 날짜의 일정 표시하는 창 띄우기 -> 파일 읽어 와서 
		}
	}
}
