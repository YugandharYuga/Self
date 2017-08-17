package com.example.test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class DownloadImage extends Thread{
	String url;
	Bitmap bitmap;
public DownloadImage(String url){
	this.url = url;
	start();
}
public Bitmap getImage(){
	return bitmap;
}
public void run(){
	try {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setReadTimeout(5000);
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Referer", "google.com");
		boolean redirect = false;
		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
		}
		if (redirect) {
			String newUrl = conn.getHeaderField("Location");
	 		String cookies = conn.getHeaderField("Set-Cookie");
	 		conn = (HttpURLConnection) new URL(newUrl).openConnection();
			conn.setRequestProperty("Cookie", cookies);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "google.com");
			System.out.println("new url "+newUrl);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream in = conn.getInputStream();
		byte[] chunk = new byte[4096];
	    int bytesRead;
	    while ((bytesRead = in.read(chunk)) > 0) {
	    	outputStream.write(chunk, 0, bytesRead);
	    }
		bitmap =  BitmapFactory.decodeByteArray(chunk,0,chunk.length);
		
	} catch (IOException e) {
		e.printStackTrace();
	} 
}
}
