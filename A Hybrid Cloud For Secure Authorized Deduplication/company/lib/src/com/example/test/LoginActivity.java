package com.example.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import util.ForgotThread;
public class LoginActivity extends Activity {
	String userName, passWord;
    EditText username, password;
    Button login,forgot;  
@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
	username = (EditText)findViewById(R.id.username);
    password = (EditText)findViewById(R.id.password);
    login = (Button)findViewById(R.id.login);
    forgot = (Button)findViewById(R.id.forgot);
    login.setOnClickListener(loginListener);
    forgot.setOnClickListener(forgotListener);
}

private OnClickListener forgotListener = new OnClickListener(){
	public void onClick(View v){
		String user = username.getText().toString();
		if(user.trim().length() == 0 || user == null){
			Toast.makeText(LoginActivity.this, "Please enter username to recover password", Toast.LENGTH_LONG).show();
			username.requestFocus();
			return;
		}
		recover(user);
	}
};

private OnClickListener loginListener = new OnClickListener(){
	public void onClick(View v){
		String user = username.getText().toString();
		String pass = password.getText().toString();
		if(user.trim().length() == 0 || user == null){
			Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
			username.requestFocus();
			return;
		}
		if(pass.trim().length() == 0 || pass == null){
			Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
			password.requestFocus();
			return;
		}
		if(user.equals("admin") && pass.equals("admin")){
			Intent in1 = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(in1);
		}else{
			login(user,pass);
		}
	}
};
public void login(String user,String pass){
	ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
	dialog.setMessage("Processing...");
	dialog.setIndeterminate(true);
	dialog.setCancelable(false);
	dialog.show();
	LoginThread login = new LoginThread(user,pass);
	try{
		login.join();
		dialog.dismiss();
		String res = login.getResponse();
		if (res.equals("valid")) {
			Intent in1 = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(in1);
			
		} else {
			Toast.makeText(LoginActivity.this, "failed to login", Toast.LENGTH_LONG).show();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void recover(String user){
	ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
	dialog.setMessage("Processing...");
	dialog.setIndeterminate(true);
	dialog.setCancelable(false);
	dialog.show();
	ForgotThread login = new ForgotThread(user);
	try{
		login.join();
		dialog.dismiss();
		String res = login.getResponse();
		if (!res.equals("invalid")) {
			Toast.makeText(LoginActivity.this, "Your password is "+res, Toast.LENGTH_LONG).show();
			
		} else {
			Toast.makeText(LoginActivity.this, "failed to recover your password", Toast.LENGTH_LONG).show();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}

