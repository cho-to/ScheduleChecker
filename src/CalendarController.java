import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class CalendarController {
	private CalendarPane calendarPane;
	private Gson gson = new Gson();
	
	CalendarController(CalendarPane calendarPane){
		this.calendarPane = calendarPane;
		readFiles();
	}
	
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
						ScheduleModel test = gson.fromJson(reader, ScheduleModel.class);
						System.out.print(test.getDateInDateType() + " : ");
						System.out.println(test.memo);
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
