package com.pcwk.ehr.report.domain;

import java.time.LocalDateTime;

import com.pcwk.ehr.cmn.DTO;

public class ReportVO extends DTO {
	
	private int report_Id; // 신고 고유 ID
	private int board_Id; // 게시글 ID
	private int report_User_Id; // 신고한 유저 ID
	private int report_Category; // 신고 카테고리
	private String report_Reason; // 신고 사유
	private LocalDateTime report_Date; // 신고 날짜
	private int report_Status; // 신고 상태
	
	
	public ReportVO(int board_Id, int report_User_Id, int report_Category, String report_Reason) {
		super();
		this.board_Id = board_Id;
		this.report_User_Id = report_User_Id;
		this.report_Category = report_Category;
		this.report_Reason = report_Reason;
	}


	public ReportVO() {
		super();
	}


	public int getReport_Id() {
		return report_Id;
	}


	public void setReport_Id(int report_Id) {
		this.report_Id = report_Id;
	}


	public int getBoard_Id() {
		return board_Id;
	}


	public void setBoard_Id(int board_Id) {
		this.board_Id = board_Id;
	}


	public int getReport_User_Id() {
		return report_User_Id;
	}


	public void setReport_User_Id(int report_User_Id) {
		this.report_User_Id = report_User_Id;
	}


	public int getReport_Category() {
		return report_Category;
	}


	public void setReport_Category(int report_Category) {
		this.report_Category = report_Category;
	}


	public String getReport_Reason() {
		return report_Reason;
	}


	public void setReport_Reason(String report_Reason) {
		this.report_Reason = report_Reason;
	}


	public LocalDateTime getReport_Date() {
		return report_Date;
	}


	public void setReport_Date(LocalDateTime report_Date) {
		this.report_Date = report_Date;
	}


	public int getReport_Status() {
		return report_Status;
	}


	public void setReport_Status(int report_Status) {
		this.report_Status = report_Status;
	}


	@Override
	public String toString() {
		return "ReportVO [report_Id=" + report_Id + ", board_Id=" + board_Id + ", report_User_Id=" + report_User_Id
				+ ", report_Category=" + report_Category + ", report_Reason=" + report_Reason + ", report_Date="
				+ report_Date + ", report_Status=" + report_Status + "]";
	}
	
	
	
}