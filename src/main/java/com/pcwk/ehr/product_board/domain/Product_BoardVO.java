package com.pcwk.ehr.product_board.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;

public class Product_BoardVO extends DTO {

	private int board_Id; // 게시글 ID
	private int seller_Id; // 판매자 ID
	private int buyer_Id; // 구매자 ID
	private LocalDateTime reg_Date; // 등록일
	private LocalDateTime mod_Date; // 수정일
	private int view_Count; // 조회수
	private String board_Title; // 게시글 제목
	private List<String> board_Image; // 게시글 이미지
	private int category_Id; // 카테고리 ID
	private int price; // 가격
	private String board_Content; // 게시글 내용
	private int how_Trade; // 거래 방식
	private String trade_Address; // 거래 지역
	private int board_Status; // 게시글 상태
	private String preview_Board_Title;
	private String munic_Address; // 지방자치 단체까지 지역 표기

	public Product_BoardVO() {
		super();
	}

	public Product_BoardVO(int seller_Id, String board_Title, List<String> board_Image, int category_Id, int price,
			String board_Content, int how_Trade, String trade_Address) {
		super();
		this.seller_Id = seller_Id;
		this.board_Title = board_Title;
		this.board_Image = board_Image;
		this.category_Id = category_Id;
		this.price = price;
		this.board_Content = board_Content;
		this.how_Trade = how_Trade;
		this.trade_Address = trade_Address;
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

	public LocalDateTime getReg_Date() {
		return reg_Date;
	}

	public void setReg_Date(LocalDateTime reg_Date) {
		this.reg_Date = reg_Date;
	}

	public LocalDateTime getMod_Date() {
		return mod_Date;
	}

	public void setMod_Date(LocalDateTime mod_Date) {
		this.mod_Date = mod_Date;
	}

	public int getView_Count() {
		return view_Count;
	}

	public void setView_Count(int view_Count) {
		this.view_Count = view_Count;
	}

	public String getBoard_Title() {
		return board_Title;
	}

	public void setBoard_Title(String board_Title) {
		this.board_Title = board_Title;
	}

	public List<String> getBoard_Image() {
		return board_Image;
	}

	public void setBoard_Image(List<String> board_Image) {
		this.board_Image = board_Image;
	}

	public int getCategory_Id() {
		return category_Id;
	}

	public void setCategory_Id(int category_Id) {
		this.category_Id = category_Id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBoard_Content() {
		return board_Content;
	}

	public void setBoard_Content(String board_Content) {
		this.board_Content = board_Content;
	}

	public int getHow_Trade() {
		return how_Trade;
	}

	public void setHow_Trade(int how_Trade) {
		this.how_Trade = how_Trade;
	}

	public String getTrade_Address() {
		return trade_Address;
	}

	public void setTrade_Address(String trade_Address) {
		this.trade_Address = trade_Address;
	}

	public int getBoard_Status() {
		return board_Status;
	}

	public void setBoard_Status(int board_Status) {
		this.board_Status = board_Status;
	}

	public String getMunic_Address() {
		return munic_Address;
	}

	public void setMunic_Address(String munic_Address) {
		this.munic_Address = munic_Address;
	}

	public String getPreview_Board_Title() {
		return preview_Board_Title;
	}

	public void setPreview_Board_Title(String preview_Board_Title) {
		this.preview_Board_Title = preview_Board_Title;
	}

	@Override
	public String toString() {
		return "Product_BoardVO [board_Id=" + board_Id + ", seller_Id=" + seller_Id + ", buyer_Id=" + buyer_Id
				+ ", reg_Date=" + reg_Date + ", mod_Date=" + mod_Date + ", view_Count=" + view_Count + ", board_Title="
				+ board_Title + ", board_Image=" + board_Image + ", category_Id=" + category_Id + ", price=" + price
				+ ", board_Content=" + board_Content + ", how_Trade=" + how_Trade + ", trade_Address=" + trade_Address
				+ ", board_Status=" + board_Status + ", munic_Address=" + munic_Address + "]";
	}

}
