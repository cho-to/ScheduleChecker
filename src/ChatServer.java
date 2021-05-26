import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

//클라이언트 -> 서버 -> 다른 클라이언트 이렇게 문자열 받아서 문자열 전달
class EchoThread extends Thread{
       Socket socket;
       String id;
       Vector<Socket> socketList; // Vector -> 동기화된 메소드로 구성 -> 멀티스레드 환경에서 안전하게 객체 추가 및 삭제 가능
       Vector<String> nameList;
       boolean isFirst = true;
       
       public EchoThread(Socket socket, Vector<Socket> vec, Vector<String> nameList){
             this.socket = socket;
             this.socketList = vec;
             this.nameList = nameList;
       }

       //클라이언트 -> 서버 -> 다른 클라이언트 (문자열로 받아서 str에 저장 후 sendmsg)
       public void run(){    	   
             BufferedReader fromClient = null;
             try{
                    fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                    
                    String str =null;
                    
                    while(true){
                    	
                    	if(isFirst) {
         
                    		str=fromClient.readLine();
                    		id = str;
                    		nameList.add(str);
                    		
                    		String result = "" ;
                    		for(String name : nameList) {
                    			result += name + ", ";
                    		}
                    		
                    		toClient.println(result);
                    		toClient.flush();
                    		isFirst = false;
                    	}else {
                    		
                           str=fromClient.readLine();
                           
                           if(str==null){
                                 socketList.remove(socket);
                                 nameList.remove(id);
                                 break;
                           }
                           
                           sendMsg(str);    
                    	}
                    }                 
             }
             catch(IOException ie){
            	 	//접속이 끊어질 때
            	 	socketList.remove(socket);
            	 	nameList.remove(id);
                    System.out.println(ie.getMessage());
             }
             finally{
                    try{
                           if(fromClient != null) fromClient.close();
                           if(socket != null) socket.close();
                    }catch(IOException ie){
                           System.out.println(ie.getMessage());
                    }
             }
       }

      
       // 서버 -> 클라이언트 메세지 전송
       public void sendMsg(String str){
             try{
                    for(Socket socket:socketList){
                    	   //메세지 보낸 socket은 제외
                           if(socket != this.socket){
                                 PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                                 toClient.println(str);
                                 toClient.flush();
                                 //여기서 소켓 바로 닫으면 걍 다른애들 꺼짐..
                           }
                    }
             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
       }
       
}

 
public class ChatServer {
	
       public static void main(String[] args) {
             ServerSocket server = null;
             Socket socket =null;
             
             //클라이언트와 연결된 소켓들을 배열처럼 저장할 벡터객체 생성
             Vector<Socket> vec = new Vector<Socket>();
             Vector<String> nameList = new Vector<String>();

             try{
                    server= new ServerSocket(3000);
                    while(true){
                    		
                           System.out.println("Waiting for client...");
                           socket = server.accept(); //클라이언트와 연결된 소켓을 벡터에 담기
                           vec.add(socket);
                           new EchoThread(socket, vec, nameList).start(); //스레드 구동
                           System.out.println("Someone connected");
                    }

             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }

       }

}