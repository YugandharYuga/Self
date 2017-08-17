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
import java.sql.Statement;
import java.sql.ResultSet;
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
	if(type.equals("generatekey")){
		String user = (String)input[1];
		String filename = (String)input[2];
		String privilege = (String)input[3];
		byte filedata[] = (byte[])input[4];
		String key = SHAHashCode.encrypt(filedata);
		String token = DBCon.checkDuplicate(key);
		if(token.equals("none"))
			DBCon.addKey(user,privilege,filename,key,token);
		else
			DBCon.addKey(user,privilege,token,key,token);
		Object res[] = {"key",key,token};
		out.writeObject(res);
		out.flush();
		area.append("Key generation response sent to client\n");
	}
	if(type.equals("search")){
		String user = (String)input[1];
		String des = (String)input[2];
		ArrayList<String> fname = new ArrayList<String>();
		ArrayList<String> key = new ArrayList<String>();
		Statement stmt = DBCon.getCon().createStatement();
		ResultSet rs = stmt.executeQuery("select filename,token from privilege where user='"+user+"'");
		while(rs.next()){
			String file = rs.getString(1);
			String token = rs.getString(2);
			if(!fname.contains(file)){
				fname.add(file);
				key.add(token);
			}
		}
		rs.close();stmt.close();
		stmt = DBCon.getCon().createStatement();
		rs = stmt.executeQuery("select permission,filename,token from privilege");
		while(rs.next()){
			String permission[] = rs.getString(1).split("#");
			boolean flag = false;
			for(String str : permission){
				if(str.equals(des)){
					flag = true;
					break;
				}
			}
			if(flag){
				String file = rs.getString(2);
				String token = rs.getString(3);
				if(!fname.contains(file)){
					fname.add(file);
					key.add(token);
				}
			}
		}
		rs.close();stmt.close();
		String files[] = new String[fname.size()];
		String keys[] = new String[key.size()];
		for(int i=0;i<fname.size();i++){
			files[i] = fname.get(i);
			keys[i] = key.get(i);
		}
		Object res[] = {"files",files,keys};
		out.writeObject(res);
		out.flush();
		area.append("Response sent to client\n");
	}
}
}
