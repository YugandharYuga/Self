package com.example.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class RegisterThread extends Thread{
	String query;
	String response;
public String getResponse(){
	return response;
}
public RegisterThread(String query){
	this.query = query;
	start();
}
public void run(){
	try{
		URL url = new URL(ServerUrl.getServerUrl()+"/Register");
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

