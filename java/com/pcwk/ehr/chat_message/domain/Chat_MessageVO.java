package com.pcwk.ehr.chat_message.domain;

import java.time.LocalDateTime;

import com.pcwk.ehr.cmn.DTO;

public class Chat_MessageVO extends DTO {

	private int chat_Message_Id;
	private int chat_Id;
	private int sender_Id;
	private String chat_Content;
	private LocalDateTime message_Date;
	private String formattedDate;

	public Chat_MessageVO() {
		super();

	}

	public Chat_MessageVO(int chat_Id, int sender_Id, String chat_Content) {
		super();
		this.chat_Id = chat_Id;
		this.sender_Id = sender_Id;
		this.chat_Content = chat_Content;
	}

	public int getChat_Message_Id() {
		return chat_Message_Id;
	}

	public void setChat_Message_Id(int chat_Message_Id) {
		this.chat_Message_Id = chat_Message_Id;
	}

	public int getChat_Id() {
		return chat_Id;
	}

	public void setChat_Id(int chat_Id) {
		this.chat_Id = chat_Id;
	}

	public int getSender_Id() {
		return sender_Id;
	}

	public void setSender_Id(int sender_Id) {
		this.sender_Id = sender_Id;
	}

	public String getChat_Content() {
		return chat_Content;
	}

	public void setChat_Content(String chat_Content) {
		this.chat_Content = chat_Content;
	}

	public LocalDateTime getMessage_Date() {
		return message_Date;
	}

	public void setMessage_Date(LocalDateTime message_Date) {
		this.message_Date = message_Date;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	@Override
	public String toString() {
		return "Chat_MessageVO [chat_Message_Id=" + chat_Message_Id + ", chat_Id=" + chat_Id + ", sender_Id="
				+ sender_Id + ", chat_Content=" + chat_Content + ", message_Date=" + message_Date + ", formattedDate="
				+ formattedDate + "]";
	}



}