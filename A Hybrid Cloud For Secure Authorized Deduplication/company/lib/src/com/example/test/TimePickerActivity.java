package com.example.test;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
public class TimePickerActivity extends Activity {
	Dialog picker;
	Button select;
	Button set;
	TimePicker timep;
	DatePicker datep;
	Integer hour,minute,month,day,year;
	TextView time,date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		select = (Button)findViewById(R.id.btnSelect);
	    time = (TextView)findViewById(R.id.textTime);
	    date = (TextView)findViewById(R.id.textDate);
	    select.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View view) {
	        // TODO Auto-generated method stub
	        picker = new Dialog(TimePickerActivity.this);
	        picker.setContentView(R.layout.picker_fragment);
	        picker.setTitle("Select Date and Time");
	        datep = (DatePicker)picker.findViewById(R.id.datePicker);
	        timep = (TimePicker)picker.findViewById(R.id.timePicker1);
	        set = (Button)picker.findViewById(R.id.btnSet);
	        set.setOnClickListener(new View.OnClickListener() {
	          @Override
	          public void onClick(View view) {
	            // TODO Auto-generated method stub
	            month = datep.getMonth()+1;
	            day = datep.getDayOfMonth();
	            year = datep.getYear();
	            hour = timep.getCurrentHour();
	            minute = timep.getCurrentMinute();
	            time.setText("Time is "+hour+":" +minute);
	            date.setText("The date is "+day+"/"+month+"/"+year);
	            ArrayList<String> fid = getIntent().getStringArrayListExtra("fid");
	            ArrayList<String> location = getIntent().getStringArrayListExtra("location");
	            ArrayList<String> slocation = getIntent().getStringArrayListExtra("slocation");
	    	    String username = getIntent().getStringExtra("username");
	    	    String name = getIntent().getStringExtra("name");
	    	    String selection = getIntent().getStringExtra("distance");
	    	    picker.dismiss();
	    	    SendMeetRequest smr = new SendMeetRequest(username,name,selection,hour+":"+minute,year+"-"+month+"-"+day);
	    		try{
	    			 smr.join();
	    			 String msg = smr.getResponse();
	    			 MainActivity.setData(fid, location,slocation);
	    			 if(msg.equals("success")){
	    				 Toast.makeText(TimePickerActivity.this, "Meeting request sent to " +name, Toast.LENGTH_SHORT).show();
	    				 Intent i = new Intent(TimePickerActivity.this,MainActivity.class);
	    				 i.putExtra("uid",name);
	    		         startActivity(i);
	    			 }else{
	    				 Toast.makeText(TimePickerActivity.this, "Error occured in sending Meeting to " +name, Toast.LENGTH_SHORT).show();
	    				 Intent i = new Intent(TimePickerActivity.this,MainActivity.class);
	    				 i.putExtra("uid",name);
	    		         startActivity(i);
	    			 }
	    		 }catch(Exception e){
	    			 e.printStackTrace();
	    		 }
	          }
	        });
	        picker.show();
	      }
	    });
	}

	
}
