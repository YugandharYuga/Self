package com.example.test;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;


public class Register extends Activity {
	EditText username,password,email,cpassword;
	Button setAccount;
	TextView registerErrorMsg,register;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);
	username = (EditText) findViewById(R.id.UserName);
	email = (EditText) findViewById(R.id.Email);
    password = (EditText) findViewById(R.id.PassWord);
    cpassword= (EditText) findViewById(R.id.CPassWord);
    setAccount = (Button) findViewById(R.id.set);
    registerErrorMsg = (TextView) findViewById(R.id.register_error);
    register = (TextView) findViewById(R.id.textView2);  
    setAccount.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
        	register();
        }

    });
}
public void register(){
	String s1 = username.getText().toString();
	String s2 = email.getText().toString();
	String s3 = password.getText().toString();
	String s4 = cpassword.getText().toString();
	if(s1.trim().length() == 0 ||s1 == null){
		Toast.makeText(Register.this, "Please enter username", Toast.LENGTH_LONG).show();
		username.requestFocus();
		return;
	}
	if(s2.trim().length() == 0 ||s2 == null){
		Toast.makeText(Register.this, "Please enter email id", Toast.LENGTH_LONG).show();
		email.requestFocus();
		return;
	}
	if(!CheckMail.checkMail(s2)){
		Toast.makeText(Register.this, "Please enter valid email id", Toast.LENGTH_LONG).show();
		email.requestFocus();
		return;
	}
	if(s3.trim().length() == 0 || s3 == null){
		Toast.makeText(Register.this, "Please enter password", Toast.LENGTH_LONG).show();
		password.requestFocus();
		return;
	}
	if(s4.trim().length() == 0 || s4 == null){
		Toast.makeText(Register.this, "Please enter confirm password", Toast.LENGTH_LONG).show();
		cpassword.requestFocus();
		return;
	}
	if(!s3.equals(s4)){
		Toast.makeText(Register.this, "Password & Confirm Password must be same", Toast.LENGTH_LONG).show();
		password.requestFocus();
		return;
	}
	String query = "t1=" + s1 + "&t2=" + s2 + "&t3=" + s3 + "&t4=" + s4;
	ProgressDialog dialog = new ProgressDialog(Register.this);
	dialog.setMessage("Processing...");
	dialog.setIndeterminate(true);
	dialog.setCancelable(false);
	dialog.show();
	RegisterThread register = new RegisterThread(query);
	try{
		register.join();
		dialog.dismiss();
		String res = register.getResponse();
		if (res.equals("success")) {
			Intent in1 = new Intent(Register.this,LoginActivity.class);
			startActivity(in1);
		} else {
			Toast.makeText(Register.this, "failed to register", Toast.LENGTH_LONG).show();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
