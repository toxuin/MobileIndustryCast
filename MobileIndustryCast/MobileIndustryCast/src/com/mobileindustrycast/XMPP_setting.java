package com.mobileindustrycast;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.pubsub.EventElement;




/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class XMPP_setting extends Activity {
    //for final server use
	private static final String HOST = "Velington-PC";//"http://velington-pc"
	private static final int PORT_NUMBER = 5222 ;
	private static final String SERVICE = "conference.velington-pc";
	private static final String RECIPIENT = "industrycast";
	private static final String LOGIN="andrey";
	private static final String PASSWORD="12345";
	
	MultiUserChat muc; 

	private Handler mHandler = new Handler();
	private ArrayList<String> messages = new ArrayList<String>();
	XMPPConnection connection;

	  
	
	
	public void createListAdapter() {
	
		final ListView mlist = (ListView) findViewById(R.id.messagesListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, messages);
		mlist.setAdapter(adapter);
	}
	
	
    //Set of functions necessary to perform login(from user point perspective)
	public void login() throws XMPPException {

		
		System.out.println("XMPP_Connect class' function 'login' started");
		
		//Configuring connection parameters with the setting predefined by team or user's input if available
		ConnectionConfiguration config = new ConnectionConfiguration(HOST,PORT_NUMBER, SERVICE);
		
		// config.setDebuggerEnabled(true); <--helpful if you are encountering a problem on the XMPP side(LogCat output)
		System.out.println("XMPP_Connect class' function 'login' configuration compleate");

		connection = new XMPPConnection(config);

		//Connecting to selected host and service
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();
		} catch (XMPPException ex) {
			System.out.println("Exception at connect");
			System.out.println(ex.toString());
		}
		System.out.println("connection to the server established");

		try {
			//user login. Needs to match the data from the DB associated with OpenFire Server
			connection.login(LOGIN, PASSWORD);
			
			//Setting up message listener
			muc  = new MultiUserChat(connection, "industrycast@conference.velington-pc");
		    muc.join("Testbot");//To include preferred user name(constrains may apply)
	        ConsumerMUCMessageListener listener = new ConsumerMUCMessageListener();
	        muc.addMessageListener(listener);
			
	        //sending presence packet, showing your availability to the server
			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);						
		} catch (XMPPException ex) {
			System.out.println(ex.toString());
		}
		System.out.println("XMPP_Connect class' function 'login' ended");

	}
    
	//sending message to the Multi-User Chat
    public void sendMessage(String message_body) throws XMPPException {
    	System.out.println("XMPP_Connect class' function 'sendMessage' started");
        	 	
            try
            {
                muc.sendMessage(message_body);
            }
            catch (XMPPException e)
            {
                System.out.println(e.getMessage());
            }

            messages.add(": " + message_body);
    }
    
 
    //Function used to disconnect from the server connection(may be necessary if the server doesn't kick idle users) to be used in onDestruct()
    public void disconnect() {
        connection.disconnect();
    }
    //Multi-User Chat listener class, necessary for packet(message type in this case) retrieval from the server. Adds messages received to the "messages" array
    class ConsumerMUCMessageListener implements PacketListener {
 
        public void processPacket(Packet packet) {
            if ( packet instanceof Message) {
            	Message msg = (Message) packet;
            	messages.add(msg.getFrom() +": " + msg.getBody());
                System.out.println(msg.getFrom() +": " + msg.getBody());
                // Add the incoming message to the list view(crashes the application currently)
                /*mHandler.post(new Runnable() {
                    public void run() {
                      createListAdapter();
                    }
                  });*/
            }
        }

    }
}
