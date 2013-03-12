package com.mobileindustrycast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomListMessage {
	
	private String timestamp;//timestamp of the message
	private String userName;//name of the user of the application
	private String message;//body of the message
	private String location;//Location of sender
	private String status;//"buyer", "seller", "trade", "need_info"
	private String messageType;//"broadcast","post"
	
	//basic constructor for class, assumes that type is "post" and  status is "need_info"
	public CustomListMessage(String user, String msg, String loc)
	{
		timestamp=addTimestamp();
		userName=user;
		message=msg;
		location=loc;
		status="Need Iinfo";
		messageType = "post";
	}
	
	public CustomListMessage(String user, String msg, String loc, String Status, String type)
	{
		timestamp=addTimestamp();
		userName= user;
		message=msg;
		location=loc;
		status=Status;
		messageType = type;
	}
	
	
	//Adds Zulu timestamp to the custom message
    public String addTimestamp()
    {
    	Date messageDate = new Date();

    	SimpleDateFormat hoursAmPm = new SimpleDateFormat("hh", Locale.US);
    	SimpleDateFormat minutesAmPm = new SimpleDateFormat("mm", Locale.US);
    	SimpleDateFormat lettersAmPm = new SimpleDateFormat("aa", Locale.US);

    	StringBuilder hours = new StringBuilder(hoursAmPm.format(messageDate));
    	StringBuilder minutes = new StringBuilder(minutesAmPm.format(messageDate));
    	StringBuilder letters = new StringBuilder(lettersAmPm.format(messageDate));

    	String timestamp = 	"["+hours+":"+minutes+" "+letters+"]";
    	
    	return timestamp;
	}
    
    public String getTimestamp()
    {
    	return timestamp;
    }
    
    
	public String getUserName()
	{
		return userName;
	}
	
    public String getBody()
	{
		return message;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public String getMessageType()
	{
		return messageType;
	}
	
	public void setTimestamp(String timeStamp)
	{
		timestamp=timeStamp;
	}
	
	public void setUser(String user)
	{
		userName=user;
	}
	
	public void setBody(String msg)
	{
       message= msg;
	}
	
	public void setLocation(String loc)
	{
		location = loc;
	}
	
	public void setStatus(String Status)
	{
		status = Status;
	}
	
	public void setMessageType(String type)
	{
		messageType = type;
	}
	
	

}
