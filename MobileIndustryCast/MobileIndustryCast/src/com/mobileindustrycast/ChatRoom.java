
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

		new Thread(new Runnable() {
			public void run() {
				//Looper.prepare();
				try {
					xmpp.login();

				} catch (XMPPException ex) {
					System.out
							.println("Unable to log in with credinals provided");
					System.out.println(ex.toString());
				}
				// xmpp.disconnect();
				//Looper.loop();
			}
		}).start();

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Here we should have a request sending to XMPP server
				System.out.println("Button pressed event occured");

				String message = ((EditText) findViewById(R.id.post_text))
						.getText().toString();
				System.out.println("Text from the text field: " + message);

				try {
					xmpp.sendMessage(message); // (message, userTosent)

				} catch (XMPPException e) {
					System.out.println("Sending message failed");
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

