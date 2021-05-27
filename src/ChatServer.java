import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

//Ŭ���̾�Ʈ -> ���� -> �ٸ� Ŭ���̾�Ʈ �̷��� ���ڿ� �޾Ƽ� ���ڿ� ����
class EchoThread extends Thread{
       Socket socket;
       String id;
       Vector<Socket> socketList; // Vector -> ����ȭ�� �޼ҵ�� ���� -> ��Ƽ������ ȯ�濡�� �����ϰ� ��ü �߰� �� ���� ����
       Vector<String> nameList;
       boolean isFirst = true;
       
       public EchoThread(Socket socket, Vector<Socket> vec, Vector<String> nameList){
             this.socket = socket;
             this.socketList = vec;
             this.nameList = nameList;
       }

       //Ŭ���̾�Ʈ -> ���� -> �ٸ� Ŭ���̾�Ʈ (���ڿ��� �޾Ƽ� str�� ���� �� sendmsg)
       public void run(){    	   
             BufferedReader fromClient = null;
             try{
                    fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                    
                    String str =null;
                    
                    while(true){
                    	String type = fromClient.readLine();
                    	System.out.println(type);
                    	if (type != null) {
                    		if (type.equals("user")) {
                        		if(isFirst) {
                            		
                            		str=fromClient.readLine();
                            		id = str;
                            		
                            		nameList.add(str);
                           
                            		String result = "" ;
                            		for(String name : nameList) {
                            			result += name + ", ";
                            		}
                            		toClient.println("user");
                            		toClient.println(result);
                            		toClient.flush();
                            		
                            		isFirst = false;
                            	}else {
                            		//Ŭ���̾�Ʈ�� ���� ���ڿ� �ޱ�
                                   str=fromClient.readLine();
                                   //��밡 ������ ������ break;
                                   if(str==null){
                                         socketList.remove(socket);
                                         nameList.remove(id);
                                         break;
                                   }
                                   //����� ���ϵ��� ���ؼ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ� �����ֱ�
                                   sendMsg(str);    
                            	}
                        	} else if (type.equals("lightning")) {
                        		System.out.println("server : light!!!");
                        		str=fromClient.readLine();
                        		sendSchedule(str);
                        	}
                    	}
                    }                 
             }
             catch(IOException ie){
            	 	
            	 	socketList.remove(socket);
            	 	nameList.remove(id);
                    System.out.println(ie.getMessage());
             }
             finally{
                    try{
                    	//if the connection die, close br & socket
                           if(fromClient != null) fromClient.close();
                           if(socket != null) socket.close();
                    }catch(IOException ie){
                           System.out.println(ie.getMessage());
                    }
             }
       }

      
       // ���� -> Ŭ���̾�Ʈ �޼��� ����
       public void sendMsg(String str){
             try{
                    for(Socket socket:socketList){
                    	   //�޼��� ���� socket�� ����
                           if(socket != this.socket){
                                 PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                                 toClient.println("user");
                                 toClient.println(str);
                                 toClient.flush();
                                 //���⼭ ���� �ٷ� ������ �� �ٸ��ֵ� ����..
                           }
                    }
             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
       }
       public void sendSchedule(String str){
           try{
                  for(Socket socket:socketList){
                  	   //�޼��� ���� socket�� ����
//                         if(socket != this.socket){
//                               //���⼭ ���� �ٷ� ������ �� �ٸ��ֵ� ����..
//                         }
                      PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                      System.out.println("server sending " + str);
                      toClient.println("lightning");
                      toClient.println(str);
                      toClient.flush();

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
             
             //Ŭ���̾�Ʈ�� ����� ���ϵ��� �迭ó�� ������ ���Ͱ�ü ����
             Vector<Socket> vec = new Vector<Socket>();
             Vector<String> nameList = new Vector<String>();

             try{
                    server= new ServerSocket(3000);
                    while(true){
                    		
                           System.out.println("Waiting for client...");
                           socket = server.accept(); //Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ���
                           vec.add(socket);
                           new EchoThread(socket, vec, nameList).start(); //������ ����
                           System.out.println("Someone connected");
                    }

             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }

       }

}