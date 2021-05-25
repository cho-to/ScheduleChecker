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
	
	
	//占쌍삼옙占� 占쏙옙占썰리占쏙옙 占쏙옙占쏙옙퓸占쏙옙獵占� json 占쏙옙占싹듸옙占쏙옙 占싻억옙暠占� 
	//占쏙옙占쏙옙寗占� 占쌍댐옙 占쌘뱄옙 占쏙옙체占쏙옙 占쏙옙환占쏙옙占쏙옙占쌔댐옙
	private void readFiles() {
		File directory = new File(id);
		// Creating the directory
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
			    	if (extension.equals("json")) {// 占쏙옙占쏙옙占쏙옙 2021占쏙옙 占쏙옙占쏙옙占싹는것몌옙 占쏙옙화占싼댐옙
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
			todoPane.removeButtons();//remove contents before refresh
			// TODO: calendarPane도 remove해줘야할거가탕요!
			
			readFiles();
			configureTodo();
			todoPane.revalidate();
			todoPane.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void writeNewSchedule(ScheduleModel schedule) throws IOException {
		String json = gson.toJson(schedule);
		ScheduleModel test = gson.fromJson(json, ScheduleModel.class);
		//자신의 폴더에 저장해야한다!
	    Files.write(Paths.get(id,schedule.id + ".json"), json.getBytes());
	}



	private void configureTodo() {
		//TODO:占쏙옙占싸울옙 占쏙옙占쏙옙占쏙옙 占쌩곤옙占쌀띰옙占쏙옙 refresh占쌔억옙!
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
