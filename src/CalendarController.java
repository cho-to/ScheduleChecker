import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
public class CalendarController {
	private CalendarPane calendarPane;
	private TodoPane todoPane;
	private Gson gson = new Gson();
	private ArrayList<ScheduleModel> schedules;
	private String id;
	
	CalendarController(CalendarPane calendarPane, TodoPane todoPane, String id){
		this.calendarPane = calendarPane;
		this.todoPane = todoPane;
		this.id = id;
		readFiles();
		configureTodo();
		configureDate();
		System.out.println("size : " + this.schedules.size());
	}
	
	//user ID에 해당하는 폴더에 접근을 해서 JSON파일들을 불러온다
	private void readFiles() {
		File directory = new File(id);
		boolean bool = directory.mkdirs();
		if (bool) {
			// 처음으로 로그인하는것! (폴더를 생성시킴) -> 비어있는 schedules를 만든다
		    schedules = new ArrayList<ScheduleModel>();

		} else {
			// 이미 폴더가 존재 -> 저장된 json파일들 불러오
			File folder = new File(id);
			File[] listOfFiles = folder.listFiles();
		    FileReader fileReader;
		    schedules = new ArrayList<ScheduleModel>();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	String extension = "";
			    	int i = file.getName().lastIndexOf('.');
			    	if (i > 0) {
			    	    extension = file.getName().substring(i+1);
			    	}
			    	if (extension.equals("json")) {//확장자가 json인경우에만 파싱을 할것이다
						try {
							fileReader = new FileReader(file.getAbsolutePath());
						    JsonReader reader = new JsonReader(fileReader);
							ScheduleModel temp = gson.fromJson(reader, ScheduleModel.class);
							schedules.add(temp);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	}
			    }
			}
		}
	}
	
	public void addNewScheudle(ScheduleModel schedule) {
		try {
			writeNewSchedule(schedule);
			refresh(); //화면 다시그리기 !
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//화면을 다시 그리기위해서는 dirty ui를 모두 지운후 다시 새롭게 ui를 configure해야한다.
	public void refresh() {
		todoPane.removeButtons();
		calendarPane.removeButtons();
		calendarPane.makeDateButtons();
		readFiles();
		configureTodo();
		configureDate();
		todoPane.revalidate();
		todoPane.repaint();
		calendarPane.revalidate();
		calendarPane.repaint();
	}
	
	//ScheduleModel객체를 JSON 형태로 변환후 파일로 저장한다
	private void writeNewSchedule(ScheduleModel schedule) throws IOException {
		String json = gson.toJson(schedule);
		ScheduleModel test = gson.fromJson(json, ScheduleModel.class);
	    Files.write(Paths.get(id,schedule.id + ".json"), json.getBytes());//자신의 폴더에 저장해야한다!
	}		

	private void configureTodo() {
		//현재 Date이후에 초대 5개의 일정만뽑아내야한다
		Date now = new Date();
		ArrayList<ScheduleModel> impendingSchedules = (ArrayList<ScheduleModel>) schedules.clone();
		Collections.sort(impendingSchedules, new Comparator<ScheduleModel>() {
			  public int compare(ScheduleModel o1, ScheduleModel o2) {
			      return o1.getDateInDateType().compareTo(o2.getDateInDateType());
			  }
			});
		impendingSchedules.removeIf(s -> s.getDateInDateType().compareTo(now) < 0);
		todoPane.showImpending(impendingSchedules.subList(0, Math.min(5, impendingSchedules.size())));
	}

	private void configureDate() {
		ArrayList<ScheduleModel> impendingSchedules = (ArrayList<ScheduleModel>) schedules.clone();
		calendarPane.showSchedule(impendingSchedules.subList(0, impendingSchedules.size()));
	}
	
}
