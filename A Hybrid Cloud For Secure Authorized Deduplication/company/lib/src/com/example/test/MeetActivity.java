package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MeetActivity extends Activity {

	Button button1,button2;
@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_meet);
 	addListenerOnButton();
}
public void addListenerOnButton(){
	button1 = (Button) findViewById(R.id.button1);
 	button1.setOnClickListener(new OnClickListener(){
 	@Override
 	public void onClick(View arg0){
 		Intent login = new Intent(MeetActivity.this, LoginActivity.class);
 		startActivity(login);
 	}
 	});
 	button2 = (Button) findViewById(R.id.button2);
 	button2.setOnClickListener(new OnClickListener(){
 	@Override
 	public void onClick(View arg0){
 		Intent register = new Intent(MeetActivity.this, Register.class);
 		startActivity(register);
 	}
 	});
}
}
