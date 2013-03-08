
package com.mobileindustrycast;

import org.jivesoftware.smack.XMPPException;

import com.mobileindustrycast.R;
import com.mobileindustrycast.XMPP_setting;
import com.mobileindustrycast.ChatListActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class ChatRoom extends Activity  {

	
	XMPP_setting xmpp = new XMPP_setting();

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);       
        Button button = (Button) findViewById(R.id.post_btn);
		ChatListActivity chatActivity = new ChatListActivity();

		
		//Working Thread that's associated with networking function login()
		new Thread(new Runnable() {
			public void run() {
				
				try 
				{
					xmpp.login();

				} catch (XMPPException ex) {
					System.out.println(ex.toString());
				}
				// xmpp.disconnect();

			}
		}).start();

		//Post button event. Sends message with user defined body to the Openfire Server
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println("Button pressed event occured");

				String message = ((EditText) findViewById(R.id.post_text)).getText().toString();//getting message body from user input
				System.out.println("Text from the text field: " + message);

				try 
				{
					xmpp.sendMessage(message); 
				} catch (XMPPException e) {
					System.out.println(e.toString());
				}

			}
		});

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_chat_room, menu);
        return true;
    }
    
	
   
}

