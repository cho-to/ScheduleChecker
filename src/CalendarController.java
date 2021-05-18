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
	private ArrayList<ScheduleModel> schedules = new ArrayList<ScheduleModel>();//���엯�꽕�젙 Student媛앹껜留� �궗�슜媛��뒫

	CalendarController(CalendarPane calendarPane){
		this.calendarPane = calendarPane;
		readFiles();
		System.out.println(schedules.size()); //���옣�릺�뼱�엳�뒗 �씪�젙 媛��닔 (�솗�씤�슜)
		
	}
	
	// �봽濡쒓렇�옩�쓣 泥섏쓬�궗�븣 �뤃�뜑�궡�뿉 �엳�뒗 json�뙆�씪�쓣 紐⑤몢 �씫�뼱���꽌
	// �뙆�떛�빐�꽌 Schedule 紐⑤뜽濡� 蹂��솚�빐以��떎.
	private void readFiles() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
	    FileReader fileReader;

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if (file.getName().startsWith("2021")) {//2021濡� �떆�옉�븯�뒗 �뙆�씪留� 媛��졇�삩�떎
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
			//calendar panel �뾾�뜲�씠�듃 �빐二쇱옄!!
			
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
