import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleModel {
	String id;
	String dateString;
	String timeString;
	String title; // 일정 제목
	String memo; // 간단한 메모
	public ScheduleModel(String date, String time, String title, String memo) {
		this.id = date + " " + time;
		this.dateString = date;
		this.timeString = time;
		this.title = title;
		this.memo = memo;
	}

	public Date getDateInDateType() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = format.parse(id);
			return date;
		} catch (ParseException e) {
			// 만약 Date 생성하는데 에러가 나면 현재 시간을 바환한다.
			e.printStackTrace();
			return Calendar.getInstance().getTime();
		}
	}
}
