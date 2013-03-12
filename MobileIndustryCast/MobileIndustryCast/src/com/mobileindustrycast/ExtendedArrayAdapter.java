package com.mobileindustrycast;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExtendedArrayAdapter extends ArrayAdapter<CustomListMessage> {
  private Context context;
  private ArrayList<CustomListMessage> messageList;


  public ExtendedArrayAdapter(Context context, ArrayList<CustomListMessage> values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.messageList = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
	View v = convertView;

	  
	if (convertView == null){
	LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    v = inflater.inflate(R.layout.rowlayout, parent, false);
    }

	TextView textView = (TextView) v.findViewById(R.id.label);
	ImageView imageView = (ImageView) v.findViewById(R.id.icon);
    
	
	CustomListMessage message;
	if (position<messageList.size())
	{message = messageList.get(position);
    
    textView.setText(message.getTimestamp()+" "+message.getUserName()+", "+message.getLocation()+", "+message.getStatus()+": "+message.getBody());
    
    // Change the icon/color for broadcast/post messages(with post type as a default one)
    imageView.setImageResource(R.drawable.post);
    textView.setTextColor(Color.parseColor("#90CA77"));
    
    if (message.getMessageType().equals("broadcast")) {
      imageView.setImageResource(R.drawable.broadcast);
      textView.setTextColor(Color.parseColor("#9E3B33"));}
	}
    return v;
  }
  
  @SuppressLint("DefaultLocale")
  @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                    FilterResults results) {
                 
                // Now we have to inform the adapter about the new list filtered
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else {
              	  messageList = (ArrayList<CustomListMessage>) results.values;
                    notifyDataSetChanged();
                }
                 
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // We implement here the filter logic
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                	results.count = messageList.size();
                	results.values = messageList;
                    
                }
                else {
                    // We perform filtering operation
              	  ArrayList<CustomListMessage> newMessageList = new ArrayList<CustomListMessage>();
                     
                    for (CustomListMessage p : messageList) {
                        if (p.getBody().toUpperCase(Locale.US).contains(constraint.toString().toUpperCase())||
                      		  p.getLocation().toUpperCase(Locale.US).contains(constraint.toString().toUpperCase())||
                      		       p.getStatus().toUpperCase(Locale.US).contains(constraint.toString().toUpperCase()))
                      	  newMessageList.add(p);
                    }
                     
                    results.count = newMessageList.size();
                    results.values = newMessageList;
                    
             
                }
                return results;
            }
        };
    
  } 
  
 
  }
