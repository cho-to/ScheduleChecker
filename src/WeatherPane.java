import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WeatherPane extends JPanel {
	JButton weatherButtons[] = new JButton[8];
	JLabel weatherIcon[] = new JLabel[10];
	
	public WeatherPane() {
		JLabel weather_title = new JLabel("Weather");
		add(weather_title);
		Font titleF = new Font("",Font.BOLD, 25);
		weather_title.setFont(titleF);
		weather_title.setForeground(Color.DARK_GRAY);
		weather_title.setOpaque(true);
		weather_title.setBackground(Color.white);
		setLayout(new GridLayout(8,1,2,2));
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));
		
		this.setBackground(Color.white);
		
		ArrayList<WeatherModel> weather = new ArrayList<WeatherModel>();
		weather = WeatherHandler.getWeathers();
		for (int k = 0; k < weather.size(); k++) {
			showWeather(weather.get(k).description, k);
		}
		
		for (int i =0; i<7; i++) {
			add(weatherButtons[i]);
			weatherButtons[i].setBorderPainted(false);
			weatherButtons[i].setContentAreaFilled(false);
			weatherButtons[i].setFocusPainted(false);
			weatherButtons[i].setBackground(new Color(211, 211, 211));
			Font weaF = new Font("",Font.BOLD, 12);
			weatherButtons[i].setFont(weaF);
			weatherButtons[i].setOpaque(true);
		}
		setVisible(true);
	}

	private Object showWeather(String description, int idx) {
		JLabel dateLbl; 
		
		ImageIcon sun = new ImageIcon(("./weather/sunny.png"));//기본 이미지
		Image img = sun.getImage();
		Image changeImg;

		
		if(idx == 0)
			dateLbl = new JLabel("TODAY ");
		else {
			dateLbl = new JLabel("D + "+idx);
		}
		System.out.println(description);
		
		if (description.equals("clear sky")) {
			ImageIcon sunny = new ImageIcon(("./weather/sunny.png"));
			img = sunny.getImage();
		}
		if (description.equals("light rain")) {
			ImageIcon lightRain = new ImageIcon(("./weather/light-rain.png"));
			img = lightRain.getImage();
		}
		if (description.equals("moderate rain")) {
			ImageIcon modRain = new ImageIcon(("./weather/moderate-rain.png"));
			img = modRain.getImage();
		}
		if (description.equals("overcast clouds")) {
			ImageIcon ovCloud = new ImageIcon(("./weather/cloudy&sunny.png"));
			img = ovCloud.getImage();
		}
		if (description.equals("scattered clouds")) {
			ImageIcon cloud = new ImageIcon("./weather/cloudy.png");
			img = cloud.getImage();
		}
		if (description.equals("heavy intensity rain")) {
			ImageIcon heavyRain = new ImageIcon(("./weather/heavyrain.png"));
			img = heavyRain.getImage();
		}
		
		changeImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changeImg);
		weatherButtons[idx] = new JButton(description, icon);
		weatherButtons[idx].add(dateLbl);//날짜
		

		return null;
	}
	
}