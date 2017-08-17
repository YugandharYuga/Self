package com.example.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatusAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] meet;
public StatusAdapter(Activity context,String[] meet) {
	super(context, R.layout.list_single, meet);
	this.context = context;
	this.meet = meet;
}
@SuppressLint("ViewHolder") @Override
public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.meet_single, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	txtTitle.setText(meet[position]);
	return rowView;
}
}

