package com.example.test;
import java.util.Comparator;
public class Distance implements Comparator<Distance>{
	String city;
	double distance;
	double src_latitude,src_longitude,dest_latitude,dest_longitude;
public void setCity(String city){
	this.city = city;
}
public String getCity(){
	return city;
}
public void setDistance(double distance){
	this.distance = distance;
}
public double getDistance(){
	return distance;
}
public void setSourceLat(double src_latitude){
	this.src_latitude = src_latitude;
}
public double getSourceLat(){
	return src_latitude;
}
public void setSourceLon(double src_longitude){
	this.src_longitude = src_longitude;
}
public double getSourceLon(){
	return src_longitude;
}

public void setDestLat(double dest_latitude){
	this.dest_latitude = dest_latitude;
}
public double getDestLat(){
	return dest_latitude;
}
public void setDestLon(double dest_longitude){
	this.dest_longitude = dest_longitude;
}
public double getDestLon(){
	return dest_longitude;
}
public int compare(Distance p1, Distance p2){
	//Distance p1 =(Distance)sr1;
	//Distance p2 =(Distance)sr2;
	double s1 = p1.getDistance();
    double s2 = p2.getDistance();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}
