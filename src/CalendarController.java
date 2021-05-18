import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class CalendarController {
	private CalendarPane calendarPane;
	private Gson gson = new Gson();
	private ArrayList<ScheduleModel> schedules = new ArrayList<ScheduleModel>();//타입설정 Student객체만 사용가능

	CalendarController(CalendarPane calendarPane){
		this.calendarPane = calendarPane;
		readFiles();
		System.out.println(schedules.size()); //저장되어있는 일정 갯수 (확인용)
		
	}
	
	
	// 프로그램을 처음킬때 폴더내에 있는 json파일을 모두 읽어와서
	// 파싱해서 Schedule 모델로 변환해준다.
	private void readFiles() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
	    FileReader fileReader;

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if (file.getName().startsWith("2021")) {//2021로 시작하는 파일만 가져온다
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
	
	public void addNewScheudle(ScheduleModel schedule) {
		try {
			writeNewSchedule(schedule);
			//calendar panel 없데이트 해주자!!
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void writeNewSchedule(ScheduleModel schedule) throws IOException {
		String json = gson.toJson(schedule);
		ScheduleModel test = gson.fromJson(json, ScheduleModel.class);
	    Files.write(Paths.get(schedule.id), json.getBytes());
	}
	
}
