package com.pcwk.ehr.chat.domain;

import java.time.LocalDateTime;

import com.pcwk.ehr.cmn.DTO;

public class ChatVO extends DTO {

	private int chat_Id;
	private int board_Id;
	private int seller_Id;
	private int buyer_Id;
	private LocalDateTime create_Date;
	private int is_Active;

	public ChatVO(int board_Id, int seller_Id, int buyer_Id) {
		super();
		this.board_Id = board_Id;
		this.seller_Id = seller_Id;
		this.buyer_Id = buyer_Id;
	}

	public ChatVO() {
		super();
	}

	public int getChat_Id() {
		return chat_Id;
	}

	public void setChat_Id(int chat_Id) {
		this.chat_Id = chat_Id;
	}

	public int getBoard_Id() {
		return board_Id;
	}

	public void setBoard_Id(int board_Id) {
		this.board_Id = board_Id;
	}

	public int getSeller_Id() {
		return seller_Id;
	}

	public void setSeller_Id(int seller_Id) {
		this.seller_Id = seller_Id;
	}

	public int getBuyer_Id() {
		return buyer_Id;
	}

	public void setBuyer_Id(int buyer_Id) {
		this.buyer_Id = buyer_Id;
	}

	public LocalDateTime getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(LocalDateTime create_Date) {
		this.create_Date = create_Date;
	}

	public int getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(int is_Active) {
		this.is_Active = is_Active;
	}

	@Override
	public String toString() {
		return "ChatVO [chat_Id=" + chat_Id + ", board_Id=" + board_Id + ", seller_Id=" + seller_Id + ", buyer_Id="
				+ buyer_Id + ", create_Date=" + create_Date + ", is_Active=" + is_Active + "]";
	}

}