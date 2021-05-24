import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleModel {
	String id;
	String dateString;
	String timeString;
	String title; 
	String memo; 
	
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
			e.printStackTrace();
			return Calendar.getInstance().getTime();
		}
	}
}
