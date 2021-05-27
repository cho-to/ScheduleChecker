import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


class AlertFrame extends JFrame implements ActionListener{

		private JPanel p1, p2, p3;
		private JLabel detailLabel, idLabel;
		private JButton OkButton;
		
		AlertFrame(String id, String str){
			setupComp(id, str);
			setTitle("Lightening!");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setBackground(Color.white);
			setSize(350, 150);
			setVisible(true);
		}
		
		private void setupComp(String id, String str) {
			setLayout(new BorderLayout());
			p1 = new JPanel();
			p2 = new JPanel();
			p3 = new JPanel();
			
			idLabel = new JLabel(id + " sent schedule!");
			
	        detailLabel = new JLabel(str);
	        detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        detailLabel.setHorizontalAlignment(JLabel.CENTER);
	        
	        
	        OkButton = new JButton("OK");
	        OkButton.addActionListener(this);
	        
	        p1.add(idLabel);
	        p2.add(detailLabel);
	        p3.add(OkButton);
	        
	       
	        add(p1, BorderLayout.NORTH);
	        add(p2, BorderLayout.CENTER);
	        add(p3, BorderLayout.SOUTH);

		}
		
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == OkButton) { 
			dispose();
        }
	}
}
