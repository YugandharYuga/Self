package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
public class RequestStatus extends Activity {
	ListView list;
	String[] listdata;
	String username;
	ArrayList<String> location;
	ArrayList<String> slocation;
	ArrayList<String> fid;
	Button back;
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_requeststatus);
	    list=(ListView)findViewById(R.id.list);
	    back  = (Button)findViewById(R.id.back);
	    back.setOnClickListener(backListener);
	    fid = getIntent().getStringArrayListExtra("fid");
	    location = getIntent().getStringArrayListExtra("location");
	    slocation = getIntent().getStringArrayListExtra("slocation");
	    username = getIntent().getStringExtra("username");
	    ReadStatus rr = new ReadStatus(username);
	    try{
	    	rr.join();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    ArrayList<String> listarr = rr.getResponse();
	    System.out.println("No meeting size========================="+listarr);
	    if(listarr.size() > 0){
	    	listdata = new String[listarr.size()];
	    	for(int i=0;i<listarr.size();i++){
	    		listdata[i] = listarr.get(i);
	    	}
	    	StatusAdapter adapter = new StatusAdapter(RequestStatus.this,listdata);
	    	list.setAdapter(adapter);
	    }else{
	    	Toast.makeText(RequestStatus.this, "You received no acceptance from any user", Toast.LENGTH_SHORT).show();
	    	MainActivity.setData(fid, location,slocation);
	    	Intent i = new Intent(RequestStatus.this,MainActivity.class);
			startActivity(i);
	    }
	    
}
	private OnClickListener backListener = new OnClickListener(){
		public void onClick(View v){
			MainActivity.setData(fid, location, slocation);
			Intent i = new Intent(RequestStatus.this,MainActivity.class);
			startActivity(i);
		}
   };
}
