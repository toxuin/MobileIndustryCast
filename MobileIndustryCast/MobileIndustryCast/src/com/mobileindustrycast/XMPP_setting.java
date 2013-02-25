package com.mobileindustrycast;


import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
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
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;




/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class XMPP_setting extends Activity {
    //for final server use
	private static final String HOST = "Velington-PC";//"http://velington-pc"
	private static final int PORT_NUMBER = 5222 ;
	private static final String SERVICE = "conference.velington-pc";
	private static final String RECIPIENT = "industrycast";
	

	private ArrayList<String> messages = new ArrayList<String>();
	XMPPConnection connection;

	//public final ListView mlist = (ListView) findViewById(R.id.message_List);
	//ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, messages);  
	//mlist.setAdapter(adapter);
	
    
    public void login(String userName, String password) throws XMPPException {
    	

    	
    	System.out.println("XMPP_Connect class' function 'login' started");
    	ConnectionConfiguration config = new ConnectionConfiguration(HOST ,PORT_NUMBER, SERVICE);

    	System.out.println("XMPP_Connect class' function 'login' configuration compleate");
    	
        connection = new XMPPConnection(config);

        try
        {
        	SASLAuthentication.supportSASLMechanism("PLAIN", 0);
        	connection.connect();
        } 
        catch(XMPPException ex)
        {
        	System.out.println("Exception at connect");
        	System.out.println(ex.toString());
        }
        System.out.println("connection to the server established");
        
        
        
        try
        {
        connection.login(userName, password);
        Presence presence = new Presence(Presence.Type.available);
        connection.sendPacket(presence);
        

        } 
        catch(XMPPException ex)
        {
        	System.out.println("Exception at login");
        }
        System.out.println("XMPP_Connect class' function 'login' ended");
    }
 
    public void sendMessage(String  to, String message_body) throws XMPPException {
    	System.out.println("XMPP_Connect class' function 'sendMessage' started");
    	        
        if(!message_body.equals(""))
        {
            ChatManager chatmanager = connection.getChatManager();
            Chat newChat = chatmanager.createChat(to, null);
            
            try
            {
                newChat.sendMessage(message_body);
            }
            catch (XMPPException e)
            {
                System.out.println(e.getMessage());
            }

            messages.add(": " + message_body);
        }
    }
    
    public void listeningForMessages() 
    {
        PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));

        PacketListener myListener = new PacketListener() 
        {
            public void processPacket(Packet packet) 
            {
                if (packet instanceof Message) 
                {
                    Message message = (Message) packet;                
              
                    if(message.getType() == Message.Type.groupchat)
                    {
                    processMessage(message);  
                    
                    messages.add(message.getFrom() + ": " + message.getBody());
                    }
                  
                }
            }
        };

    }


    private void processMessage(Message message)
    {
        String messageBody = message.getBody();
        String JID = message.getFrom();
        try{
        sendMessage(JID, messageBody);}
        catch(XMPPException e)
        {
        	System.out.println(e.getMessage());
        }

    }
    
    public void disconnect() {
        connection.disconnect();
    }
	

}
