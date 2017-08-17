package com.example.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import util.Security;

public class SendMeetRequest extends Thread{
	String username,requestto;
	String distance;
	String time,date;
	String response;
public String getResponse(){
	return response;
}
public SendMeetRequest(String username,String requestto,String distance,String time,String date){
	this.username = username;
	this.requestto = requestto;
	this.distance = distance;
	this.time = time;
	this.date = date;
	start();
}
public void run(){
	try{
		String dis[] = distance.split("#");
		String query = "t1=" + Security.encryption(username) + "&t2=" + Security.encryption(requestto) + "&t3=" + Security.encryption(dis[0]) + "&t4=" + dis[1] + "&t5=" + dis[2] + "&t6=" + dis[3] + "&t7=" + dis[4]+ "&t8=" + time+ "&t9=" + date;
		URL url = new URL(ServerUrl.getServerUrl()+"/MeetRequest");
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
