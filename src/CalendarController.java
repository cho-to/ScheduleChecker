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
		System.out.println(schedules.size()); 
		
		//TODO: sort�ؼ� ���ñ������� �ֱ� 5���� �����ؾ�
		Date now = new Date();
		ArrayList<ScheduleModel> impendingSchedules = (ArrayList<ScheduleModel>) schedules.clone();
		impendingSchedules.removeIf(s -> s.getDateInDateType().compareTo(now) < 0);
		todoPane.showImpending(impendingSchedules.subList(0, 1));
		System.out.println(impendingSchedules.size()); 
		System.out.println(schedules.size()); 
	}
	
	//�ֻ�� ���丮�� ����Ǿ��ִ� json ���ϵ��� �о�ͼ� 
	//����Ҽ� �ִ� �ڹ� ��ü�� ��ȯ�����ش�
	private void readFiles() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
	    FileReader fileReader;

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if (file.getName().startsWith("2021")) {// ������ 2021�� �����ϴ°͸� ��ȭ�Ѵ�
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
