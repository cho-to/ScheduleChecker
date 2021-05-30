import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherHandler {
	
	static public ArrayList<WeatherModel> getWeathers() {
	    HttpURLConnection connection = null;
	    JsonObject responseJson = null;
	    String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=37.53&lon=127.02&exclude=current,minutely,hourly,alerts&appid=2079d6fb76902e678cc733a19f3be5fa";
	    try {
	        URL url = new URL(urlString);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        int responseCode = connection.getResponseCode();
	        if (responseCode == 400) {
	            System.out.println("400:: 해당 명령을 실행할 수 없음");
	        } else if (responseCode == 500) {
	            System.out.println("500:: 서버 에러, 문의 필요");
	        } else { // 성공
	            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            String line = "";
	            while ((line = br.readLine()) != null) {
	                sb.append(line);
	            }
	            
	            //json parser를 이용하여 필요한 정보들을 Weather 객체로 변활시켜준다.
			    Gson gson = new Gson();
	            JsonObject response = JsonParser.parseString(sb.toString()).getAsJsonObject();
			    WeatherGroupModel[] temp = gson.fromJson(response.get("daily").getAsJsonArray(), WeatherGroupModel[].class);
			    
			    ArrayList<WeatherModel> weathers = new ArrayList<WeatherModel>();
			    for (int i = 0; i < temp.length; i++) {	
			    	weathers.add(temp[i].weather[0]);
		    	}
			    
			    return weathers;
	        }
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
		return null;

	}
}
