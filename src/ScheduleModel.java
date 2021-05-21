import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleModel {
	String id;
	String dateString;
	String timeString;
<<<<<<< HEAD
	String title; // �씪�젙 �젣紐�
	String memo; // 媛꾨떒�븳 硫붾え
=======
	String title; 
	String memo; 
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	public ScheduleModel(String date, String time, String title, String memo) {
		this.id = date + " " + time;
		this.dateString = date;
		this.timeString = time;
		this.title = title;
		this.memo = memo;
	}

	public Date getDateInDateType() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		Date date;
		try {
			date = format.parse(id);
			return date;
		} catch (ParseException e) {
<<<<<<< HEAD
			// 留뚯빟 Date �깮�꽦�븯�뒗�뜲 �뿉�윭媛� �굹硫� �쁽�옱 �떆媛꾩쓣 諛뷀솚�븳�떎.
=======
			// 
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
			e.printStackTrace();
			return Calendar.getInstance().getTime();
		}
	}
}
