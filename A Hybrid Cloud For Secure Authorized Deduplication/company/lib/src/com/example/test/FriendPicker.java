package com.example.test;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import java.util.ArrayList;
public class FriendPicker extends Activity {
	ArrayList<String> location;
	ArrayList<String> slocation;
	ArrayList<String> fid;
	ListView list;
	String name[];
	String id[];
	String username;
	Button back;
   @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.friend_picker);
    fid = getIntent().getStringArrayListExtra("fid");
    location = getIntent().getStringArrayListExtra("location");
    slocation = getIntent().getStringArrayListExtra("slocation");
    username = getIntent().getStringExtra("username");
    name = new String[fid.size()];
    id = new String[fid.size()];
    for(int i=0;i<fid.size();i++){
    	String str[] = fid.get(i).split(",");
    	id[i] = str[1];
    	name[i] = str[0];
    }
    Bitmap bit[] = new Bitmap[name.length];
    for(int i=0;i<name.length;i++){
    	DownloadImage img = new DownloadImage(id[i]);
    	try{
    		img.join();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	bit[i] = img.getImage();
    }
    CustomList adapter = new CustomList(FriendPicker.this,name,bit);
    list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    meetingRequest(name[+ position]);
                	
                }
            });
     back  = (Button)findViewById(R.id.back);
     back.setOnClickListener(backListener);
  }
   private OnClickListener backListener = new OnClickListener(){
		public void onClick(View v){
			MainActivity.setData(fid, location, slocation);
			Intent i = new Intent(FriendPicker.this,MainActivity.class);
			startActivity(i);
		}
   };
   private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}		
 public void meetingRequest(String name){
	 ArrayList<Distance> distance = new ArrayList<Distance>(); 
	 ArrayList<Distance> subdis = new ArrayList<Distance>();
	 ArrayList<String> dup = new ArrayList<String>();
	 ArrayList<String> friend = new ArrayList<String>();
	 for(int i=0;i<location.size();i++){
		 String data[] = location.get(i).split("#");
		 if(data[0].equals(name))
			 friend.add(location.get(i));
	 }
	 for(int i=0;i<slocation.size();i++){
		String src[] = slocation.get(i).split("#");
		System.out.println("User location "+slocation.get(i));
		for(int j=0;j<friend.size();j++){
			String des[] = friend.get(j).split("#");
			double slat = Double.parseDouble(src[3]);
			double slon = Double.parseDouble(src[2]);
			double dlat = Double.parseDouble(des[2]);
			double dlon = Double.parseDouble(des[1]);
			double dis = distance(slat,slon,dlat,dlon,'M');
			System.out.println("Friend location "+friend.get(j)+" Distance is "+dis);
			if(dis <= 50){
				Distance obj = new Distance();
				if(!dup.contains(des[3])){
					dup.add(des[3]);
					obj.setCity(des[3]);
					obj.setSourceLat(slat);
					obj.setSourceLon(slon);
					obj.setDestLat(dlat);
					obj.setDestLon(dlon);
					obj.setDistance(dis);
					subdis.add(obj);
				}
			}
		}
		if(subdis.size() > 0){
			java.util.Collections.sort(subdis,new Distance());
			distance.add(subdis.get(0));
		}
		subdis.clear();
	 }
	 if(distance.size() == 0){
		 distance = getMostVisitedPlaces(friend);
	 }
	 if(distance.size() > 0){
		java.util.Collections.sort(distance,new Distance());
		int j = 0;
		ArrayList<String> places = new ArrayList<String>();
		for(Distance dis : distance){
			if(j < 10){
				String value = dis.getCity()+"#"+dis.getSourceLat()+"#"+dis.getSourceLon()+"#"+dis.getDestLat()+"#"+dis.getDestLon();
				if(!places.contains(value))
					places.add(value);
			}
			j++;
		}
		Intent i = new Intent(FriendPicker.this,TopPlacesActivity.class);
		i.putExtra("fid",fid);
		i.putExtra("location",location);
		i.putExtra("slocation",slocation);
		i.putExtra("username",username);
		i.putExtra("name",name);
		i.putExtra("distance",places);
		startActivity(i);
	}else{
		Toast.makeText(FriendPicker.this, "No tagged places available for you", Toast.LENGTH_SHORT).show();
	}
	
 }
 public ArrayList<Distance> getMostVisitedPlaces(ArrayList<String> friend_places){
	 ArrayList<Distance> distance = new ArrayList<Distance>();
	 ArrayList<String> dup = new ArrayList<String>();
	 for(String places : friend_places){
		 String plcs[] = places.split("#");
		 if(!dup.contains(plcs[1])){
			 dup.add(plcs[1]);
			 double count = getPlaceCount(plcs[1],friend_places);
			 Distance obj = new Distance();
			 obj.setCity(plcs[1]);
			 obj.setSourceLat(Double.parseDouble(plcs[3]));
			 obj.setSourceLon(Double.parseDouble(plcs[2]));
			 obj.setDestLat(Double.parseDouble(plcs[3]));
			 obj.setDestLon(Double.parseDouble(plcs[2]));
			 obj.setDistance(count);
		 }
	 }
	 return distance;
 }
 public double getPlaceCount(String place,ArrayList<String> friend_places){
	 double count = 0;
	 for(String places : friend_places){
		 String plcs[] = places.split("#");
		 if(plcs[1].equals(place))
			 count+=count;
	 }
	 return count;
 }
}