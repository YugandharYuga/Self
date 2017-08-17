package com.example.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import util.Security;

public class ConfirmRequest extends Thread{
	String requestto,sender,city;
	String response;
	String status;
public String getResponse(){
	return response;
}
public ConfirmRequest(String requestto,String sender,String city,String status){
	try{
	this.requestto = Security.encryption(requestto);
	this.sender = Security.encryption(sender);
	this.city = Security.encryption(city);
	this.status = status;
	start();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void run(){
	try{
		String query = "t1=" + requestto + "&t2=" + sender+"&t3="+city+"&t4="+status;
		URL url = new URL(ServerUrl.getServerUrl()+"/Confirm");
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
