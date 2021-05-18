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
	static final int Cal_Width = 7;//-> �ݴ�� �� �� �������� ����
	final static int Cal_Height = 6;
	int dates[][] = new int[Cal_Height][Cal_Width];
	
	Calendar cal_day = Calendar.getInstance();//5�� �޷�
	//��� string
	final String day_string_name[] = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	//LineBorder lb;
	JLabel calendarLabel;
	JButton dayName[] = new JButton[7];//�� ���� ���� �� ǥ��
	JButton dateButton[][] = new JButton[Cal_Height][Cal_Width];// �� date ��ư
	
	//calendar gui ����
	CalendarPane() {
		//calendarLabel = new JLabel("calendar");
		//add(calendarLabel);
		setBackground(Color.white);
		
		//����
		for (int i =0; i< Cal_Width; i++) {
			dayName[i]=new JButton(day_string_name[i]);
			add(dayName[i]);
			dayName[i].setBorderPainted(false);//�׵θ� ������
			dayName[i].setContentAreaFilled(false);//Ŭ���ص� �ҿ����
			dayName[i].setFocusPainted(false);//��ư ������ �׵θ� �Ȼ����
			dayName[i].setOpaque(true);//���� �������ϰ�->����?
			//��� ��
			dayName[i].setBackground(Color.white/*new Color(200, 50, 50)*/);//->����?
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
				dateButton[i][j].setBorderPainted(false);//�׵θ� ������
				dateButton[i][j].setContentAreaFilled(false);//Ŭ���ص� �ҿ����
				dateButton[i][j].setFocusPainted(false);//��ư ������ �׵θ� �Ȼ����
				dateButton[i][j].setOpaque(true);//���� �������ϰ�->����?
				dateButton[i][j].setBackground(Color.lightGray);//���� ���� 
				dateButton[i][j].setHorizontalAlignment(SwingConstants.LEFT);
				dateButton[i][j].setVerticalAlignment(SwingConstants.TOP);;
				setLayout(new GridLayout(0,7,2,2));//�׸��� (ä���)
				//���
				TitledBorder lb = new TitledBorder(new LineBorder(Color.darkGray));
				setBorder(lb);
				//setBorder(Color.darkGray); ���...
				setBorder(BorderFactory.createEmptyBorder(10, 20, 40, 20));//��.��.��.��
				
				dateButton[i][j].addActionListener(new CalendarClickListener());
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
				File f = new File("MemoData/" + year + ( (month + 1) < 10 ? "0" : "")
						+ (month + 1) + (dates[i][j] < 10 ? " 0":"")
						+ dates[i][j]+".txt");
				//�޸� ����� ���� ������ ���� �ǳ� Ȯ�� 
				if(f.exists()){
					dateButton[i][j].setText(dates[i][j]+"");
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
	
	//�޷� ������ �����ϴ� ��
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
			//�ش� ��¥�� ���� ǥ���ϴ� â ���� -> ���� �о� �ͼ� 
		}
	}
}
