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
       boolean isFirst = false;
       
       public EchoThread(Socket socket, Vector<Socket> vec, Vector<String> nameList){
             this.socket = socket;
             this.socketList = vec;
             this.nameList = nameList;
       }

       //Ŭ���̾�Ʈ -> ���� -> �ٸ� Ŭ���̾�Ʈ (���ڿ��� �޾Ƽ� str�� ���� �� sendmsg)
       public void run(){    	   
             BufferedReader br = null;
             try{
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str =null;

                    while(true){
                    	
                    	if(isFirst) {
                    		str=br.readLine();
                    		id = str;
                    		nameList.add(str);
                    		
                    		PrintWriter toClient = new PrintWriter(socket.getOutputStream());
                    		
                    		String result = null;
                    		for(String name : nameList) {
                    			result += name + " ";
                    		}
                    		
                    		toClient.println(result);
                    		toClient.flush();
                    		
                    		isFirst = false;
                    	}
                    	else {
                    		//Ŭ���̾�Ʈ�� ���� ���ڿ� �ޱ�
                           str=br.readLine();
                           
                           //��밡 ������ ������ break;
                           if(str==null){
                                 //���Ϳ��� ���ֱ�
                                 socketList.remove(socket);
                                 nameList.remove(id);
                                 break;
                           }
                           //����� ���ϵ��� ���ؼ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ� �����ֱ�
                           sendMsg(str);    
                    	}
                    }                 
             }
             catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
             finally{
                    try{
                    	//if the connection die, close br & socket
                           if(br != null) br.close();
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
                                 PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                                 pw.println(str);
                                 pw.flush();
                                 //���⼭ ���� �ٷ� ������ �� �ٸ��ֵ� ����..
                           }
                    }
             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
       }
       
       public void sendUser() {
    	  
    	   
    	   
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
                    		
                           System.out.println("Connecting Server...");
                           socket = server.accept(); //Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ���
                           vec.add(socket);
                           new EchoThread(socket, vec, nameList).start(); //������ ����

                    }

             }catch(IOException ie){
                    System.out.println(ie.getMessage());
             }

       }

}