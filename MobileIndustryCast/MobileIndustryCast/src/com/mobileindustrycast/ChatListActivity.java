package com.mobileindustrycast;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class ChatListActivity extends ListActivity {
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        ChatListAdapter adapter= new ChatListAdapter(this,
                android.R.layout.simple_list_item_2);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
      //  String item = (String) getListAdapter().getItem(position);
     //   Toast.makeText(this, item + " chosen", Toast.LENGTH_LONG).show();
    }
}
