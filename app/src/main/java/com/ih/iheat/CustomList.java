package com.ih.iheat;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomList extends ArrayAdapter<String>{
private final Activity context;
private final ArrayList<String> web;
private final Integer imageId;
public CustomList(Activity context,ArrayList<String> web, Integer imageId) 
{
super(context, R.layout.viewdevicelayout, web);
this.context = context;
this.web = web;
this.imageId = imageId;
}
@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.viewdevicelayout, null, true);
TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
String text=web.get(position).toString();
boolean b=false;
if(text.contains("$$$"))
	text=text.replace("$$$","");
if(text.contains("$"))
	text=text.replace("$","");

  if(text.contains(";"))
  {  b=true;
	  text=text.replace(";","").trim();
  }
txtTitle.setText(text);
if(b)
	imageView.setImageResource(R.drawable.connected);
else
imageView.setImageResource(imageId);
return rowView;
}
}
