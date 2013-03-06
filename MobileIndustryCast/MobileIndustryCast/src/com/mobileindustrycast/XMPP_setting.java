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

	private ArrayList<String> messages = new ArrayList<String>();
	XMPPConnection connection;

	  
	
	
	/*public void createListAdapter() {
	
		public final ListView mlist = (ListView) findViewById(R.id.message_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_2, messages);
		mlist.setAdapter(adapter);
	}*/
	
	
    
	public void login() throws XMPPException {

		// createListAdapter();
		System.out.println("XMPP_Connect class' function 'login' started");
		ConnectionConfiguration config = new ConnectionConfiguration(HOST,
				PORT_NUMBER, SERVICE);
		// config.setDebuggerEnabled(true);
		System.out
				.println("XMPP_Connect class' function 'login' configuration compleate");

		connection = new XMPPConnection(config);

		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();
		} catch (XMPPException ex) {
			System.out.println("Exception at connect");
			System.out.println(ex.toString());
		}
		System.out.println("connection to the server established");

		try {
			connection.login(LOGIN, PASSWORD);
			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);
			//setUpListener(connection);
			
			

		} catch (XMPPException ex) {
			System.out.println(ex.toString());
		}
		System.out.println("XMPP_Connect class' function 'login' ended");

	}
    
    public void setUpListener(XMPPConnection con){
    	
    	PacketFilter filter =new MessageTypeFilter(Message.Type.chat);// new AndFilter(new PacketTypeFilter(Message.class));

    //	PacketCollector myCollector = con.createPacketCollector(filter);
    	// Normally, you'd do something with the collector, like wait for new packets.
    	System.out.println("Listener is set up");
    	// Next, create a packet listener. We use an anonymous inner class for brevity.
    	PacketListener myListener = new PacketListener() {
    			@Override
    	        public void processPacket(Packet packet) {
    	            Message msg = (Message) packet;
    	            System.out.println(msg.getFrom()+ ": " + msg.getBody());
    	        }
    	    };
    	
    	con.addPacketListener(myListener, filter);
    	
    	
    }
 
    public void sendMessage(String message_body) throws XMPPException {
    	System.out.println("XMPP_Connect class' function 'sendMessage' started");
    	        
        if(!message_body.equals(""))
        {
        	 MultiUserChat muc = new MultiUserChat(connection, "industrycast@conference.velington-pc");
        	 muc.join("testbot");
        	 ConsumerMUCMessageListener listener = new ConsumerMUCMessageListener();
             muc.addMessageListener(listener);
        	
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
    }
    
 
    	

      /* private String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String to = params[0];

            // Accept only messages from friend@gmail.com
            PacketFilter filter 
                = new AndFilter(new PacketTypeFilter(Message.class), 
                                new FromContainsFilter(to));

            // Collect these messages
            PacketCollector collector = connection.createPacketCollector(filter);

            while(true) {
              Packet packet = collector.nextResult();

              if (packet instanceof Message) {
                Message msg = (Message) packet;
                // Process message
               System.out.println(msg.getFrom()+ ": " + msg.getBody());
              }
            }

            //return null;
        }*/


    private void processMessage(Message message)
    {
        //String messageBody = message.getBody();
        //String JID = message.getFrom();
        try{
        sendMessage(message.getBody());}
        catch(XMPPException e)
        {
        	System.out.println(e.getMessage());
        }

    }
    
    public void disconnect() {
        connection.disconnect();
    }


    class ConsumerMUCMessageListener implements PacketListener {
 
        public void processPacket(Packet packet) {
            if ( packet instanceof Message) {
            	Message msg = (Message) packet;
                System.out.println(msg.getFrom() +": " + msg.getBody());
            }
        }

    }
}
