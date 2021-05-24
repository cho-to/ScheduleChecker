import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class SendStr {
    Socket socket;
    ChatFrame cf;
    String str;
    String id;
    
    public SendStr(ChatFrame cf) {
          this.cf  = cf;
          this.socket= cf.socket;
          this.id = cf.id;
    } 

    public void sendMsg() {
          //키보드로부터 읽어오기 위한 스트림객체 생성
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          PrintWriter pw=null;

          try{

                 //서버로 문자열 전송하기 위한 스트림객체 생성
                 pw=new PrintWriter(socket.getOutputStream(),true);
               
                 if(cf.isFirst==true){                       
                        str = "["+id+"] "+cf.txtField.getText();
                        cf.isFirst = false;

                 }else{

                        str= "["+id+"] "+cf.txtField.getText();

                 }

                 //입력받은 문자열 서버로 보내기

                 pw.println(str);

          }catch(IOException ie){
                 System.out.println(ie.getMessage());
          }finally{
                 try{
                        if(br!=null) br.close();
                        //if(pw!=null) pw.close();
                        //if(socket!=null) socket.close();

                 }catch(IOException ie){
                        System.out.println(ie.getMessage());
                 }
          }
    }     

    
}

//서버가 보내온 문자열을 전송받는 스레드

class ReadThread extends Thread{

    Socket socket;
    ChatFrame cf;
    String id;
    
    
    public ReadThread(Socket socket, ChatFrame cf) {
          this.cf = cf;
          this.socket=socket;
          this.id = cf.id;
    }

    public void run() {
          BufferedReader br=null;
          try{
                 //서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성
                 br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 while(true){
                        //소켓으로부터 문자열 읽어옴
              
                 	
                        String str=br.readLine();
                        if(str==null){
                              System.out.println("접속이 끊겼음");
                              break;
                        }

                        //전송받은 문자열 화면에 출력
                        //System.out.println("[server] " + str);
                        cf.txtArea.append(str+"\n");
                 }

          }catch(IOException ie){
                 System.out.println(ie.getMessage());
          }finally{

                 try{
                        if(br!=null) br.close();
                        //if(socket!=null) socket.close();
                 }catch(IOException ie){}

          }

    }

}



public class ChatFrame extends JFrame implements ActionListener{

	   private JButton btnSend;
	   private JButton btnExit;
	   private JPanel p1;
	   JTextArea txtArea;
	   JTextField txtField;
       String id;
       
       boolean isFirst=true;
       Socket socket;
       SendStr wt;           

       public ChatFrame(Socket socket, String id) {    	   
    	   txtArea = new JTextArea();
           txtField = new JTextField(20);
           btnSend = new JButton("Send");
           btnExit = new JButton("Exit");
           
           JPanel p1 = new JPanel();
           this.socket = socket;
           this.id = id;
           setTitle("Multi-Chat");
           
           new ReadThread(socket, this).start();
           wt = new SendStr(this);
                          
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
       
       @Override
       public void actionPerformed(ActionEvent e) {
    	   	if(e.getSource()==btnSend){
                    if(txtField.getText().equals("")){
                           return;
                    }                  
                    txtArea.append("["+id+"] "+ txtField.getText()+"\n");
                    wt.sendMsg();
                    txtField.setText("");
             }
    	   	else if(e.getSource() == btnExit) {
                    this.dispose();
             }
    	   	else {
    	   		System.out.println("exception");
    	   	}
       }

} 