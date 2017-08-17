package com.example.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class LoginThread extends Thread{
	String user,pass;
	String response;
public String getResponse(){
	return response;
}
public LoginThread(String user,String pass){
	this.user = user;
	this.pass = pass;
	start();
}
public void run(){
	try{
		String query = "t1=" + user + "&t2=" + pass;
		URL url = new URL(ServerUrl.getServerUrl()+"/Login");
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
