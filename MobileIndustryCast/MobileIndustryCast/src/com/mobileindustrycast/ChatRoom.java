
package com.mobileindustrycast;

import org.jivesoftware.smack.XMPPException;

import com.mobileindustrycast.R;
import com.mobileindustrycast.XMPP_setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class ChatRoom extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
       
        
        Button button = (Button) findViewById(R.id.post_btn);

        button.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v) {
        		

    			
    			//Here we should have a request sending to XMPP server
    			System.out.println("Button pressed event occured");
    			
    						
    			new Thread(new Runnable(){
    				public void run()
    				{
    					String message = ((EditText)findViewById(R.id.post_text)).getText().toString();
    					XMPP_setting xmpp = new XMPP_setting();
    					System.out.println("Connection successfully established");
    					try
    					{
    						System.out.println(message);
    						xmpp.login("andrey", "12345");
    					}
    					catch (XMPPException ex)
    					{
    						System.out.println("Unable to log in with credinals provided");
    					}//To be developed. Proposing Pop-up window "Cannot connect to the server"
    			 
    					try 
    					{
    						xmpp.sendMessage(message); // (message, userTosent)
    					} 
    					catch (XMPPException e) 
    					{
    						System.out.println("Sending message failed");
    					}

    					xmpp.disconnect();
    				}
    			}).start();
    	    	//EditText chatLog = (EditText) findViewById(R.id.chatBox);
    	    	
    	    	//chatLog.setText(chatLog.getText().append("\n").append(message));
    	    	System.out.println("Error I passed the action");

        		
        	  	
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


//package com.mobileindustrycast;
//
//import java.util.ArrayList;
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import org.jivesoftware.smack.XMPPConnection;
//
//import org.jivesoftware.smack.packet.Message;
//
//
//public class ChatRoom extends Activity {
//
//
//    private Handler handler = new Handler();
//    private EditText recipient;
//    private EditText text;
//    private ListView list;
//    private XMPP_setting settings;
//    private XMPPConnection connection;
//    
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_room);
//        
//        text = (EditText) this.findViewById(R.id.post_text);
//        list = (ListView) this.findViewById(R.id.message_List);
//        setListAdapter();
//        
//      //Window for getting settings
//       settings = new XMPP_setting();
//        
//        
//        
//        //Listener for chat message
//        Button send = (Button) this.findViewById(R.id.post_btn);
//        send.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                String to = "industrycast";
//                String text1 = text.getText().toString();
//                
//                Message msg = new Message(to, Message.Type.groupchat);
//                msg.setBody(text1);
//                connection.sendPacket(msg);
//                messages.add(connection.getUser() + ":");
//                messages.add(text1);
//                setListAdapter();                
//            }
//        });        
//    }
//    
//
//   
//    
//
//    
//
//
//}
