
public class CalendarController {
	private CalendarPane calendarPane;
	
	CalendarController(CalendarPane calendarPane){
		this.calendarPane = calendarPane;
	}
	
	public void addNewScheudle() {
		this.calendarPane.calendarLabel.setText("새로운 일정 추가!");
	}
	
}
