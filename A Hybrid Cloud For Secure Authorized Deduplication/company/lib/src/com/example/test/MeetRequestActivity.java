package com.example.test;

import java.util.ArrayList;

import util.Security;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
public class MeetRequestActivity extends Activity {
	ListView list;
	String[] listdata;
	String username;
	ArrayList<String> location;
	ArrayList<String> slocation;
	ArrayList<String> fid;
	Button back;
	String sender,city;
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_meetrequest);
	    list=(ListView)findViewById(R.id.list);
	    back  = (Button)findViewById(R.id.back);
	    back.setOnClickListener(backListener);
	    fid = getIntent().getStringArrayListExtra("fid");
	    location = getIntent().getStringArrayListExtra("location");
	    slocation = getIntent().getStringArrayListExtra("slocation");
	    username = getIntent().getStringExtra("username");
	    ReadRequest rr = new ReadRequest(username);
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
	    	MeetAdapter adapter = new MeetAdapter(MeetRequestActivity.this,listdata);
	    	list.setAdapter(adapter);
	    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    		@Override
	    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	    			try{
	    			String selection[] = listdata[+position].split("#");
	    			sender = selection[0];
	    			city = selection[1];
	    			AlertDialog.Builder builder = new AlertDialog.Builder(MeetRequestActivity.this);
	    			builder.setMessage("Click Yes to confirm or No to reject").setPositiveButton("Yes", dialogClickListener)
	    			    .setNegativeButton("No", dialogClickListener).show();
	    			}catch(Exception e){
	    		    	e.printStackTrace();
	    		    }
	    		}
	    	});
	    }else{
	    	Toast.makeText(MeetRequestActivity.this, "No meeting request is received for you", Toast.LENGTH_SHORT).show();
	    	MainActivity.setData(fid, location,slocation);
	    	Intent i = new Intent(MeetRequestActivity.this,MainActivity.class);
			startActivity(i);
	    }
	    
}
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
            case DialogInterface.BUTTON_POSITIVE:
               sendRequest("Confirmed");
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                sendRequest("Rejected");
                break;
            }
        }
    };
	public void sendRequest(String status){
		ConfirmRequest cr = new ConfirmRequest(username,sender,city,status);
		try{
			cr.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		Toast.makeText(MeetRequestActivity.this, "Meeting request Confirmed With "+sender, Toast.LENGTH_SHORT).show();
		MainActivity.setData(fid, location,slocation);
		Intent i = new Intent(MeetRequestActivity.this,MainActivity.class);
		startActivity(i);
	}
	private OnClickListener backListener = new OnClickListener(){
		public void onClick(View v){
			MainActivity.setData(fid, location, slocation);
			Intent i = new Intent(MeetRequestActivity.this,MainActivity.class);
			startActivity(i);
		}
   };
}
