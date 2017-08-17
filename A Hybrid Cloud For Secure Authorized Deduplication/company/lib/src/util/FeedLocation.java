package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import com.example.test.ServerUrl;
import java.util.ArrayList;
public class FeedLocation extends Thread{
	ArrayList<String> places;
public FeedLocation(ArrayList<String> places){
	this.places = places;
	start();
}
public void run(){
	try{
		for(String str : places){
			System.out.println("kkkkkkkkkkkkkkkkkkk");
			String s1[] = str.split("#");
			String query = "t1=" + s1[0]+"&t2=" + s1[1]+"&t3=" + s1[2]+"&t4=" + s1[3];
			URL url = new URL(ServerUrl.getServerUrl()+"/AddPlaces");
			URLConnection con = url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			BufferedWriter out = new BufferedWriter( new OutputStreamWriter( con.getOutputStream() ) );
			out.write(query);
			out.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			System.out.println(br.readLine());
			br.close();
			out.close();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
