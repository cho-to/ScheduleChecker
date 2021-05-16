//앱 내 달력/일정과 관련된 비즈니스 로직을 담당하는 Controller 
public class CalendarController {
	private CalendarPane calendarPane;
	
	CalendarController(CalendarPane calendarPane){
		this.calendarPane = calendarPane;
	}
	
	public void addNewScheudle() {
		//임시로 text만 바꾸게
		this.calendarPane.calendarLabel.setText("새로운 일정 추가!");
	}
	
}
