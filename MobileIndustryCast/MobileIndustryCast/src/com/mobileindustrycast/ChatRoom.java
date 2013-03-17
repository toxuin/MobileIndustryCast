
package com.mobileindustrycast;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class ChatRoom extends Activity implements MessageListener {

	XMPP_setting xmpp = new XMPP_setting();
	String RECEPIENT = "SOME_LOGIN";

    TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        final Button postButton = (Button) findViewById(R.id.post_btn);
        final EditText messageText = (EditText) findViewById(R.id.post_text);

        logView = (TextView) findViewById(R.id.logView);

        postButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendMessage(messageText.getText().toString(), RECEPIENT);
                } catch (XMPPException e) {
                    Log.d("OLOLO", e.toString());
                }
            }
        });

		//Working Thread that's associated with networking function login()
		new Thread(new Runnable() {
			public void run() {
				try 
				{
					xmpp.connect();
                    if (xmpp.connection.isConnected()) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                logView.setText(logView.getText() + "\n CONNECTED TO SERVER!");
                            }
                        });
                        xmpp.login();

                        if (xmpp.connection.isAuthenticated()) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    logView.setText(logView.getText() + "\n LOGGED IN!");
                                }
                            });
                        }

                        //sending presence packet, showing your availability to the server
                        Presence presence = new Presence(Presence.Type.available);
                        xmpp.connection.sendPacket(presence);
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                logView.setText(logView.getText() + "\n NO CONNECTION TO SERVER. CHECK INTERNETS.");
                            }
                        });
                    }

				} catch (XMPPException ex) {
                    Log.d("OLOLO", ex.toString());
				}

			}
		}).start();

    }
            
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_chat_room, menu);
        return true;
    }

    public void sendMessage(String message, String reciever) throws XMPPException {
        Log.d("OLOLO", "Trying to send message to " + reciever);

        if (xmpp.connection.isAuthenticated()) {
            if (!message.equals("")) {
                logView.setText(logView.getText() + "\n You: " + message);
                Chat chat = xmpp.connection.getChatManager().createChat(reciever, this);
                chat.sendMessage(message);
            }
        } else {
            logView.setText(logView.getText() + "\n NO CONNECTION");
        }

    }

    // INCOMING MESSAGES
    @Override
    public void processMessage(final Chat chat, final Message message) {
        Log.d("OLOLO", "INCOMING!");

        if (message.getType() == Message.Type.chat) {
            Log.d("OLOLO", chat.getParticipant() + " says: " + message.getBody());

            runOnUiThread(new Runnable() {
                public void run() {
                    logView.setText(logView.getText() + "\n " + chat.getParticipant() + ": " + message.getBody());
                }
            });

        }
    }


    protected void onDestroy()
    {
        xmpp.disconnect();
    }

}
