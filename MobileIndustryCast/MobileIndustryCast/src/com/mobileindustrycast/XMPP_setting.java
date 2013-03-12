package com.mobileindustrycast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
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
import org.jivesoftware.smackx.packet.DelayInformation;
import org.jivesoftware.smackx.pubsub.EventElement;




/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class XMPP_setting extends Activity {
    //for final server use
	public  final String HOST = "Velington-PC";//"http://velington-pc"
	public  final int PORT_NUMBER = 5222 ;
	public  final String SERVICE = "conference.velington-pc";
	public  final String RECIPIENT = "industrycast@conference.velington-pc";
	public  final String LOGIN="andrey";
	public  final String PASSWORD="12345";



	Message message;


	XMPPConnection connection;	  
	
		
	public void connect()
	{	
		
		//Configuring connection parameters with the setting predefined by team or user's input if available
		ConnectionConfiguration config = new ConnectionConfiguration(HOST,PORT_NUMBER, SERVICE);
		
		// config.setDebuggerEnabled(true); <--helpful if you are encountering a problem on the XMPP side(LogCat output)


		connection = new XMPPConnection(config);

		//Connecting to selected host and service
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();
		} catch (XMPPException ex) {
			System.out.println("Exception at connect");
			System.out.println(ex.toString());
		}
	}
	
    //Set of functions necessary to perform login(from user point perspective)
	public void login() throws XMPPException {

		try {
			//user login. Needs to match the data from the DB associated with OpenFire Server
			connection.login(LOGIN, PASSWORD);						
		} catch (XMPPException ex) {
			System.out.println(ex.toString());
		}
	}
    
    
    
    //Function used to disconnect from the server connection(may be necessary if the server doesn't kick idle users) to be used in onDestruct()
    public void disconnect() {
        connection.disconnect();
    }
    

}
