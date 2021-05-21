
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrame extends JFrame implements ActionListener{

	   private JTextArea txtArea;
	   private JTextField txtField;
	   private JButton btnSend;
	   private JButton btnExit;
	   private JPanel p1;
       private String id;
       
       
       Socket socket;
       WriteThread wt;           

       public ChatFrame(Socket socket) {
    	   
    	   JTextArea txtArea = new JTextArea();
           JTextField txtField = new JTextField(20);
           JButton btnSend = new JButton("Send");
           JButton btnExit = new JButton("Exit");
           JPanel p1 = new JPanel();
           this.socket = socket;
           
           setTitle("Multi-Chat");
           
           wt = new WriteThread(this);
             
           
             
             add("Center", txtArea);

             p1.add(txtField);
             p1.add(btnSend);
             p1.add(btnExit);

             add("South", p1);
      

             btnSend.addActionListener(this);
             btnExit.addActionListener(this);
             setBounds(300, 300, 350, 300);
             setVisible(true); 

       }

      

       public void actionPerformed(ActionEvent e){
             
    	   	if(e.getSource()==btnSend){
                    if(txtField.getText().equals("")){
                           return;
                    }                  
                    txtArea.append("["+id+"] "+ txtF.getText()+"\n");
                    wt.sendMsg();
                    txtField.setText("");
             }else{
                    this.dispose();
             }

       }

} */