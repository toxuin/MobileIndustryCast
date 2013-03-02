package com.mobileindustrycast;

public class ChatMessage {
	public static enum MsgType {POST, SHOUT};
	public static enum ShoutType {NULL, BUYER, SELLER, TRADE, INFO };
	
	private String author;
	private String message;
	private MsgType msgType;
	private ShoutType shoutType;
	
	public ChatMessage(String author,String message, MsgType mtype, ShoutType stype)
	{
		this.author=author;
		this.message=message;
		this.msgType=mtype;
		this.shoutType=stype;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public MsgType getMessageType()
	{
		return this.msgType;
	}
	
	public ShoutType getShoutType()
	{
		return this.shoutType;
	}
	
	@Override
	public String toString()
	{
		return this.author+": "+this.message;
	}
}
