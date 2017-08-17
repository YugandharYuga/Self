package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
public class TopPlacesActivity extends Activity {
	ListView list;
	String username,name;
	ArrayList<String> location;
	ArrayList<String> fid;
	ArrayList<String> slocation;
	ArrayList<String> distance;
	String listdata[];
	Button back;
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_topplaces);
	    list=(ListView)findViewById(R.id.list);
	    back  = (Button)findViewById(R.id.back);
	    back.setOnClickListener(backListener);
	    fid = getIntent().getStringArrayListExtra("fid");
	    location = getIntent().getStringArrayListExtra("location");
	    slocation = getIntent().getStringArrayListExtra("slocation");
	    username = getIntent().getStringExtra("username");
	    name = getIntent().getStringExtra("name");
	    distance = getIntent().getStringArrayListExtra("distance");
	    listdata = new String[distance.size()];
	    for(int i=0;i<distance.size();i++){
	    	String city[] = distance.get(i).split("#");
	    	listdata[i] = city[0];
	    }
	    TopPlacesAdapter adapter = new TopPlacesAdapter(TopPlacesActivity.this,listdata);
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	    		String selection = distance.get(+position);
	    		Intent i = new Intent(TopPlacesActivity.this,TimePickerActivity.class);
	    		i.putExtra("fid",fid);
	    		i.putExtra("location",location);
	    		i.putExtra("slocation",slocation);
	    		i.putExtra("username",username);
	    		i.putExtra("name",name);
	    		i.putExtra("distance",selection);
	    		startActivity(i);
	    	}
	    });

}
	private OnClickListener backListener = new OnClickListener(){
		public void onClick(View v){
			MainActivity.setData(fid, location, slocation);
			Intent i = new Intent(TopPlacesActivity.this,MainActivity.class);
			startActivity(i);
		}
   };
}
