package com.pcwk.ehr.cmn;

import java.time.Duration;
import java.time.LocalDateTime;

public class productBoard_Cmn {

	/**
	 * 카테고리 ID -> 카테고리 이름 변환
	 * 
	 * @param category_Id
	 * @return category_Name
	 */
	public static String Catecory_Id_To_Name(int category_Id) {

		String category_Name;

		switch (category_Id) {
		case 100:
			category_Name = "디지털기기";
			break;
		case 101:
			category_Name = "생활가전";
			break;
		case 102:
			category_Name = "가구/인테리어";
			break;
		case 103:
			category_Name = "생활/주방";
			break;
		case 104:
			category_Name = "유아동";
			break;
		case 105:
			category_Name = "여성패션/잡화";
			break;
		case 106:
			category_Name = "남성패션/잡화";
			break;
		case 107:
			category_Name = "뷰티/미용";
			break;
		case 108:
			category_Name = "스포츠/레저";
			break;
		case 109:
			category_Name = "취미/게임/음반";
			break;
		case 110:
			category_Name = "도서";
			break;
		case 111:
			category_Name = "티켓/교환권";
			break;
		case 112:
			category_Name = "가공식품";
			break;
		case 113:
			category_Name = "건강기능식품";
			break;
		case 114:
			category_Name = "반려동물용품";
			break;
		case 115:
			category_Name = "식물";
			break;
		case 116:
			category_Name = "기타 중고 물품";
		default:
			category_Name = "카테고리 이름 에러";
			break;
		}

		return category_Name;
	}

	public static String compare_Now(LocalDateTime mod_Date) {

		LocalDateTime now = LocalDateTime.now();

		// 현재와 수정일 비교 후 초 반환
		Duration duration = Duration.between(mod_Date, now);

		// 초 반환
		long seconds = duration.getSeconds();

		// 초 -> 분 변환
		long minutes = seconds / 60;

		// 분 -> 시간 변환
		long hours = minutes / 60;

		// 일 반환
		long days = duration.toDays();

		// 일 -> 개월 반환
		long months = days / 30;

		// 개월 -> 년 반환
		long year = months / 12;

		if (seconds < 60) {
			return "1분 전";
		} else if (minutes < 60) {
			return minutes + "분 전";
		} else if (hours < 24) {
			return hours + "시간 전";
		} else if (days < 30) {
			return days + "일 전";
		} else if (months < 12) {
			return months + "개월 전";
		} else {
			return year + "년 전";
		}

	}
	
	public static String convertMunic_Address(String trade_Address) {

		String[] splitSpace = trade_Address.split(" ");

		return splitSpace[0] + " " + splitSpace[1];
	}
	
	public static String how_Trade_ToString(int how_Trade) {
		
		if(how_Trade == 0) {
			return "직거래";
		} else {
			return "택배 거래";
		}
		
	}

}
