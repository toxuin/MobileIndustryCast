package com.mobileindustrycast;



import android.app.Activity;


import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import android.util.Log;





/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class XMPP_setting extends Activity {
    //for final server use
	public  final String HOST = "SERVER.ru";
	public  final int PORT_NUMBER = 5222 ;
	public  final String SERVICE = "service_name";

	public  final String LOGIN="awesome_login";
	public  final String PASSWORD="1234";

	XMPPConnection connection;
	
		
	public void connect()
	{	
		
		//Configuring connection parameters with the setting predefined by team or user's input if available
		ConnectionConfiguration config = new ConnectionConfiguration(HOST, PORT_NUMBER, SERVICE);
		
		// config.setDebuggerEnabled(true); <--helpful if you are encountering a problem on the XMPP side(LogCat output)


		connection = new XMPPConnection(config);

		//Connecting to selected host and service
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection.connect();
		} catch (XMPPException ex) {
			Log.d("OLOLO", "Exception at connect");
			Log.d("OLOLO", ex.toString());
		}
	}
	
    //Set of functions necessary to perform login(from user point perspective)
	public void login() throws XMPPException {

		try {
			//user login. Needs to match the data from the DB associated with OpenFire Server
			connection.login(LOGIN, PASSWORD);
		} catch (XMPPException ex) {
			Log.d("OLOLO", ex.toString());
		}
	}
    
    
    
    //Function used to disconnect from the server connection(may be necessary if the server doesn't kick idle users) to be used in onDestruct()
    public void disconnect() {
        connection.disconnect();
    }
    

}
