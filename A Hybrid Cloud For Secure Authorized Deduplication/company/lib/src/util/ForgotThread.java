package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import com.example.test.ServerUrl;
public class ForgotThread extends Thread{
	String user;
	String response;
public String getResponse(){
	return response;
}
public ForgotThread(String user){
	this.user = user;
	start();
}
public void run(){
	try{
		String query = "t1=" + user;
		URL url = new URL(ServerUrl.getServerUrl()+"/Forgot");
		URLConnection con = url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		System.out.println(query);
		BufferedWriter out = new BufferedWriter( new OutputStreamWriter( con.getOutputStream() ) );
		out.write(query);
		out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = br.readLine();
		br.close();
		out.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
