package com.mobileindustrycast;

import java.util.ArrayList;
import android.app.Activity;
import android.view.View;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;



/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class XMPP_setting extends Activity implements MessageListener  {
    //for final server use
	private static final String HOST = "192.168.1.116";//"http://velington-pc"
	private static final int PORT_NUMBER = 5222 ;
	private static final String SERVICE = "conference.velington-pc";
	private static final String RECIPIENT = "industrycast";
	
	private ListView mlist;
	private ArrayList<String> messages = new ArrayList();
	XMPPConnection connection;
	private Handler mHandler;
	
	
    
    public void login(String userName, String password) throws XMPPException {
    	
    	System.out.println("XMPP_Connect class' function 'login' started");
    	ConnectionConfiguration config = new ConnectionConfiguration(HOST ,PORT_NUMBER, SERVICE);
    	//config.setSASLAuthenticationEnabled(true);
    	System.out.println("XMPP_Connect class' function 'login' configuration compleate");
    	
        connection = new XMPPConnection(config);

        connection.connect();
        System.out.println("connection to the server established");
 
        connection.login(userName, password);
        System.out.println("XMPP_Connect class' function 'login' ended");
    }
 
    public void sendMessage(String message_body) throws XMPPException {
    	System.out.println("XMPP_Connect class' function 'sendMessage' started");
    	Message newMessage = new Message(RECIPIENT, Message.Type.groupchat);
    	newMessage.setBody(message_body);
    	connection.sendPacket(newMessage);
        System.out.println("XMPP_Connect class' function 'sendMessage' ended");
    }
    
    public void processMessage(Chat chat, Message message) {
        if (message.getType() == Message.Type.groupchat) {
            System.out.println(chat.getParticipant() + " says: " + message.getBody());
            

            
            //change for activity message
            try {
                chat.sendMessage(message.getBody() + " echo");
            } catch (XMPPException ex) {
                
            }
        }
    }
    
    public void setConnection
    (XMPPConnection connection) {
    this.connection = connection;
    if (connection != null) {
      // Add a packet listener to get messages sent to us
     PacketFilter filter = new MessageTypeFilter(Message.Type.groupchat);
     connection.addPacketListener(new PacketListener() {
      public void processPacket(Packet packet) {
       Message message = (Message) packet;
        if (message.getBody() != null) {
       String fromName = StringUtils.parseBareAddress(message.getFrom());
       System.out.println("XMPPClient Got text [" + message.getBody() + "] from [" + fromName + "]");
       messages.add(fromName + ":");
       messages.add(message.getBody());
       // Add the incoming message to the list view
       mHandler.post(new Runnable() {
       public void run() {
       setListAdapter();
        }
       });
        }
       }
      }, filter);
     }
    }
    public void disconnect() {
        connection.disconnect();
    }
	
    private void setListAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (this, R.layout.activity_chat_room, messages);
        mlist.setAdapter(adapter);
    }
}

//package com.mobileindustrycast;
//
//import java.util.ArrayList;
//
//
//
//public class XMPP_setting  {
//
//    private XMPP_setting settings;
//    private XMPPConnection connection;
//    private ArrayList<String> messages = new ArrayList();
//    
//	    public void establish_connection() {
//	        String host = "192.168.1.116";
//	        String port = "5222";
//	        String service = "conference.velington-pc";
//	        String username = "andrey";
//	        String password = "12345";
//	        
//	        // Create connection
//	        
//	        ConnectionConfiguration connectionConfig =
//	            new ConnectionConfiguration(host, Integer.parseInt(port), service);
//	        XMPPConnection connection = new XMPPConnection(connectionConfig);
//	        
//	        try {
//	            connection.connect();
//	        } catch (XMPPException ex) {
//	        	connection.setConnection(null);
//	        }
//	        try {
//	            connection.login(username, password);
//	            
//	            // Set status to online / available
//	            Presence presence = new Presence(Presence.Type.available);
//	            connection.sendPacket(presence);
//	            chatRoom.setConnection(connection);
//	        } catch (XMPPException ex) {
//	        	chatRoom.setConnection(null);
//	        }
//	    }
//	    
//	    //Called by settings when connection is established
//	    
//	    public void setConnection (XMPPConnection connection) {
//	        this.connection = connection;
//	        if (connection != null) {
//	            //Packet listener to get messages sent to logged in user
//	            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
//	            connection.addPacketListener(new PacketListener() {
//	                public void processPacket(Packet packet) {
//	                    Message message = (Message) packet;
//	                    if (message.getBody() != null) {
//	                        String fromName = StringUtils.parseBareAddress(message.getFrom());
//	                        messages.add(fromName + ":");
//	                        messages.add(message.getBody());
//	                        handler.post(new Runnable(){
//	                            public void run() {
//	                                setListAdapter();
//	                            }
//	                        });
//	                    }
//	                }
//	            }, filter);
//	        }
//	    }
//	    
//	    private void setListAdapter() {
//	        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//	            (this, R.layout.activity_chat_room, messages);
//	        list.setAdapter(adapter);
//	    }
//}
