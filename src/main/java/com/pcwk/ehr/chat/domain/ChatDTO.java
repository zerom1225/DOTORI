package com.pcwk.ehr.chat.domain;

import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.user.domain.UserVO;

public class ChatDTO {

	private ChatVO chat;
	private Product_BoardVO product_Board;
	private UserVO seller;
	private UserVO buyer;

	public ChatDTO() {
		super();
	}

	public ChatDTO(ChatVO chat, Product_BoardVO product_Board, UserVO seller, UserVO buyer) {
		super();
		this.chat = chat;
		this.product_Board = product_Board;
		this.seller = seller;
		this.buyer = buyer;
	}

	public ChatVO getChat() {
		return chat;
	}

	public void setChat(ChatVO chat) {
		this.chat = chat;
	}

	public Product_BoardVO getProduct_Board() {
		return product_Board;
	}

	public void setProduct_Board(Product_BoardVO product_Board) {
		this.product_Board = product_Board;
	}

	public UserVO getSeller() {
		return seller;
	}

	public void setSeller(UserVO seller) {
		this.seller = seller;
	}

	public UserVO getBuyer() {
		return buyer;
	}

	public void setBuyer(UserVO buyer) {
		this.buyer = buyer;
	}

}
