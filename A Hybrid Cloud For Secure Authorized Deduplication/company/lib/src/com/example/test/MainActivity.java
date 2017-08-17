package com.example.test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import util.FeedLocation;
import util.UnreadCount;
public class MainActivity extends FragmentActivity {
	private LoginButton loginBtn;
	private ProfilePictureView profilePictureView;
	private TextView userName,alert;
	private UiLifecycleHelper uiHelper;
	private Button meetrequest;
	private Button notification; 
	private Button requeststatus; 
	static ArrayList<String> fid = new ArrayList<String>();
	static ArrayList<String> location = new ArrayList<String>();
	static ArrayList<String> slocation = new ArrayList<String>();
	String username,id;
	public static void setData(ArrayList<String> id,ArrayList<String> loc,ArrayList<String> sloc){
		fid = id;
		location = loc;
		slocation = sloc;
	}
	private Session.StatusCallback callback = new Session.StatusCallback() {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            onSessionStateChange(session, state, exception);
	        }
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		userName = (TextView) findViewById(R.id.user_name);
		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
		meetrequest = (Button) findViewById(R.id.meet_request);
		notification = (Button) findViewById(R.id.notification);
		requeststatus = (Button) findViewById(R.id.requeststatus);
		alert = (TextView) findViewById(R.id.alert);
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("user_friends,user_tagged_places"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					userName.setText("Hello, " + user.getName());
					id = user.getId();
					profilePictureView.setProfileId(user.getId());
					username = user.getName();
					meetrequest.setEnabled(true);
					UnreadCount uc = new UnreadCount(username);
					try{
						uc.join();
					}catch(Exception e){
						e.printStackTrace();
					}
					String msg = uc.getResponse();
					alert.setText("Unread Notification : "+msg);
				} else {
					userName.setText("You are not logged");
					if(fid != null && location != null){
						fid.clear();
						location.clear();
						slocation.clear();
					}
				}
			}
		});
		meetrequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(fid.size() > 0 && location.size() > 0 && slocation.size() > 0){
					Intent i = new Intent(MainActivity.this,FriendPicker.class);
					i.putExtra("fid",fid);
					i.putExtra("location",location);
					i.putExtra("slocation",slocation);
					i.putExtra("username",username);
					startActivity(i);
				}else{
					Toast.makeText(MainActivity.this, "No friends or tagged places are associated with u", Toast.LENGTH_SHORT).show();
				}
			}
		});
		notification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(MainActivity.this,MeetRequestActivity.class);
				 i.putExtra("fid",fid);
				 i.putExtra("location",location);
				 i.putExtra("slocation",slocation);
				 i.putExtra("username",username);
		         startActivity(i);
			}
		});
		requeststatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(MainActivity.this,RequestStatus.class);
				 i.putExtra("fid",fid);
				 i.putExtra("location",location);
				 i.putExtra("slocation",slocation);
				 i.putExtra("username",username);
		         startActivity(i);
			}
		});
		meetrequest.setEnabled(false);
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			meetrequest.setEnabled(true);
			getUserData(session);
		} else if (state.isClosed()) {
			profilePictureView.setProfileId("");
			meetrequest.setEnabled(false);
			Log.d("FacebookLogin", "Facebook session closed");
			//exception.printStackTrace();
		}
	}
	private void getUserData(String response){
		try{
			JSONObject map = new JSONObject(response);
			//for(int i=0;i<map.length();i++){
				// JSONObject tobj = map.getJSONObject();
				 JSONArray obj =map.getJSONArray("data");
				 for(int j=0;j<obj.length();j++){
					 JSONObject s1 = obj.getJSONObject(j); //place,created_time,id
					 JSONObject s2 = s1.getJSONObject("place");
					 JSONObject s3 = s2.getJSONObject("location");
					 String value = s3.getString("city")+"#"+s2.getString("name")+"#"+s3.getString("longitude")+"#"+s3.getString("latitude"); 
					 if(!slocation.contains(value))
						 slocation.add(value);
				 }
			//}
			}catch(Exception e){
				e.printStackTrace();
			}
			if(slocation.size() > 0){
				new FeedLocation(slocation);
			}
	}
	private void getUserData(final Session session) {
		Bundle params1 = new Bundle();
		params1.putString("fields", "id,name,picture,tagged_places");
		Request request1 = new Request(session, "/me/tagged_places", null, HttpMethod.GET, new Request.Callback() {

		    @Override
		    public void onCompleted(Response response) {
		        try {
		        	getUserData(response.getRawResponse());
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
		    }
		});
		RequestAsyncTask task = new RequestAsyncTask(request1);
		task.execute();
		
		Request request2 = new Request(session, "/me/friends", null, HttpMethod.GET, new Request.Callback() {

		    @Override
		    public void onCompleted(Response response) {
		        try {
		        	getFriends(response.getRawResponse());
		          
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
		    }
		});
		RequestAsyncTask task1 = new RequestAsyncTask(request2);
		request2.setParameters(params1);
		task1.execute();
		
	}
	/*private void getPlaces(Response response,String id){
		try{
		JSONObject json = response.getGraphObject().getInnerJSONObject();
		JSONArray jarray = json.getJSONArray("data");
		for(int i = 0; i < jarray.length(); i++){
		  JSONObject obj = jarray.getJSONObject(i);
		  String cid = obj.getString("id");
		  if(cid.equals(id) && obj.has("location")){
			  //JSONArray tarray = obj.getJSONArray("data");
			 // for(int j=0;j<tarray.length();j++){
				  JSONObject tobj = obj.getJSONObject("location");
				  System.out.println(tobj.has("longitude")+" "+tobj.has("latitude")+" "+tobj.has("location"));
				  if(tobj.has("longitude") && tobj.has("latitude")){
					  String lon = obj.getString("longitude");
					  String lat = obj.getString("latitude");
					  System.out.println("Checking id======"+cid+" "+id+" "+lon+" "+lat);
				  }
			  }
		 // }
		}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}*/
	private void getFriends(String response) {
		try{
			//System.out.println("respon "+response);
		JSONObject root = new JSONObject(response);
		JSONArray map = root.getJSONArray("data");
					for(int i=0;i<map.length();i++){
						
							 JSONObject tobj = map.getJSONObject(i);
							 if(tobj.has("picture")){
								 JSONObject jobj = tobj.getJSONObject("picture");
								 JSONObject url = jobj.getJSONObject("data");
								 fid.add(tobj.getString("name")+","+url.getString("url"));
							 }
							 if(tobj.has("tagged_places")){
								 JSONObject jobj = tobj.getJSONObject("tagged_places");
								 JSONArray obj = jobj.getJSONArray("data");
								 for(int j=0;j<obj.length();j++){
									 JSONObject s1 = obj.getJSONObject(j); //place,created_time,id
									 JSONObject s2 = s1.getJSONObject("place");
									 JSONObject s3 = s2.getJSONObject("location");
									 String value = tobj.getString("name")+"#"+s3.getString("longitude")+"#"+s3.getString("latitude")+"#"+s2.getString("name");
									 if(s3.has("city")){
										 value = value+" "+s3.getString("city");
									 }
									 location.add(value);
									
								 }
							}
							
						
					}
					//for (GraphUser user : users) {
					//	fid.add(user.getId()+","+user.getName());
		          //  }
					
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

}