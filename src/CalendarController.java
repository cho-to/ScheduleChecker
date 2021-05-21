import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
public class CalendarController {
	private CalendarPane calendarPane;
	private TodoPane todoPane;
	private Gson gson = new Gson();
	private ArrayList<ScheduleModel> schedules = new ArrayList<ScheduleModel>();
	CalendarController(CalendarPane calendarPane, TodoPane todoPane){
		this.calendarPane = calendarPane;
		this.todoPane = todoPane;
		readFiles();
		configureTodo();
	}
	
	//최상단 디렉토리에 저장되어있는 json 파일들을 읽어와서 
	//사용할수 있는 자바 객체로 변환시켜준다
	private void readFiles() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
	    FileReader fileReader;

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if (file.getName().startsWith("2021")) {// 파일이 2021로 시작하는것만 변화한다
		    	String extension = "";
		    	int i = file.getName().lastIndexOf('.');
		    	if (i > 0) {
		    	    extension = file.getName().substring(i+1);
		    	}
		    	System.out.println(extension);
		    	if (extension.equals("json")) {// 파일이 2021로 시작하는것만 변화한다
					try {
						fileReader = new FileReader(file.getName());
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void writeNewSchedule(ScheduleModel schedule) throws IOException {
		String json = gson.toJson(schedule);
		ScheduleModel test = gson.fromJson(json, ScheduleModel.class);
	    Files.write(Paths.get(schedule.id), json.getBytes());
	    Files.write(Paths.get(schedule.id + ".json"), json.getBytes());
	}

	private void configureTodo() {
		//TODO:새로운 일정을 추가할때도 refresh해야!
		Date now = new Date();
		ArrayList<ScheduleModel> impendingSchedules = (ArrayList<ScheduleModel>) schedules.clone();
		impendingSchedules.removeIf(s -> s.getDateInDateType().compareTo(now) < 0);
		todoPane.showImpending(impendingSchedules.subList(0, Math.min(5, impendingSchedules.size())));
	}
	
}