package com.example.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import util.Security;
public class ReadRequest extends Thread{
	String username;
	ArrayList<String> response = new ArrayList<String>();
public ArrayList<String> getResponse(){
	return response;
}
public ReadRequest(String username){
	this.username = username;
	start();
}
public void run(){
	try{
		String query = "t1=" + Security.encryption(username);
		URL url = new URL(ServerUrl.getServerUrl()+"/ReadRequest");
		URLConnection con = url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		System.out.println(query);
		BufferedWriter out = new BufferedWriter( new OutputStreamWriter( con.getOutputStream() ) );
		out.write(query);
		out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while((line = br.readLine())!=null) {
			if(line.trim().length() > 0){
				String str[] = line.split("#");
				response.add(Security.decryption(str[0])+"#"+Security.decryption(str[1])+"#"+str[2]+"#"+str[3]+"#"+str[4]);
			}
		}
		br.close();
		out.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
