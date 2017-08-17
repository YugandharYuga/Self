package server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileOutputStream;
import java.util.ArrayList;
public class ServerProcess extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
public ServerProcess(Socket soc,JTextArea area){
    socket=soc;
	this.area=area;
	try{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
	}catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
	try{
		process();		
    }catch(Exception e){
        e.printStackTrace();
    }
}
public void process()throws Exception{
	Object input[]=(Object[])in.readObject();
	String type=(String)input[0];
	if(type.equals("upload")){
		String user = (String)input[1];
		String filename = (String)input[2];
		byte filedata[] = (byte[])input[3];
		File file = new File("cloud_data/"+user);
		if(!file.exists())
			file.mkdir();
		FileOutputStream fout = new FileOutputStream("cloud_data/"+user+"/"+filename);
		fout.write(filedata,0,filedata.length);
		fout.close();
		Object res[] = {"success"};
		out.writeObject(res);
		out.flush();
		area.append(filename+" from "+user+" saved at location "+file.getPath()+"\n");
	}
	if(type.equals("download")){
		String file = (String)input[1];
		FileInputStream fin = new FileInputStream("cloud_data/"+file);
		byte filedata[] = new byte[fin.available()];
		fin.read(filedata,0,filedata.length);
		fin.close();
		Object res[] = {filedata};
		out.writeObject(res);
		out.flush();
		area.append("File sent to client\n");
	}
}
}
