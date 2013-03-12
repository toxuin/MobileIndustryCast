package com.mobileindustrycast;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExtendedArrayAdapter extends ArrayAdapter<CustomListMessage> {
  private final Context context;
  private final ArrayList<CustomListMessage> values;

  public ExtendedArrayAdapter(Context context, ArrayList<CustomListMessage> values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    CustomListMessage message = values.get(position);
    
    textView.setText(message.getTimestamp()+" "+message.getUserName()+", "+message.getLocation()+", "+message.getStatus()+": "+message.getBody());
    
    // Change the icon/color for broadcast/post messages(with post type as a default one)
    imageView.setImageResource(R.drawable.post);
    textView.setTextColor(Color.parseColor("#90CA77"));
    
    if (message.getMessageType().equals("broadcast")) {
      imageView.setImageResource(R.drawable.broadcast);
      textView.setTextColor(Color.parseColor("#9E3B33"));}

    return rowView;
  }
} 
