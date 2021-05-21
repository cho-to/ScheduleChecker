import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
public class CalendarController {
	private CalendarPane calendarPane;
	private TodoPane todoPane;
	private Gson gson = new Gson();
	private ArrayList<ScheduleModel> schedules = new ArrayList<ScheduleModel>();
<<<<<<< HEAD
=======

>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	CalendarController(CalendarPane calendarPane, TodoPane todoPane){
		this.calendarPane = calendarPane;
		this.todoPane = todoPane;
		readFiles();
		configureTodo();
	}
	
	//�ֻ�� ���丮�� ����Ǿ��ִ� json ���ϵ��� �о�ͼ� 
	//����Ҽ� �ִ� �ڹ� ��ü�� ��ȯ�����ش�
	private void readFiles() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
	    FileReader fileReader;

		for (File file : listOfFiles) {
		    if (file.isFile()) {
<<<<<<< HEAD
		    	if (file.getName().startsWith("2021")) {// ������ 2021�� �����ϴ°͸� ��ȭ�Ѵ�
=======
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
		    	String extension = "";
		    	int i = file.getName().lastIndexOf('.');
		    	if (i > 0) {
		    	    extension = file.getName().substring(i+1);
		    	}
		    	System.out.println(extension);
		    	if (extension.equals("json")) {// ������ 2021�� �����ϴ°͸� ��ȭ�Ѵ�
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
<<<<<<< HEAD
	    Files.write(Paths.get(schedule.id), json.getBytes());
	    Files.write(Paths.get(schedule.id + ".json"), json.getBytes());
	}

=======
	    Files.write(Paths.get(schedule.id + ".json"), json.getBytes());
	}
	
>>>>>>> b09a44f8529eb594eb9669a495324d98880133a1
	private void configureTodo() {
		//TODO:���ο� ������ �߰��Ҷ��� refresh�ؾ�!
		Date now = new Date();
		ArrayList<ScheduleModel> impendingSchedules = (ArrayList<ScheduleModel>) schedules.clone();
		impendingSchedules.removeIf(s -> s.getDateInDateType().compareTo(now) < 0);
		todoPane.showImpending(impendingSchedules.subList(0, Math.min(5, impendingSchedules.size())));
	}
	
}