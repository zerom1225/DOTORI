package com.pcwk.ehr.report.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.report.domain.ReportVO;
import com.pcwk.ehr.report.service.ReportService;
import com.pcwk.ehr.user.domain.UserVO;

@Controller
public class ReportController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("reportServiceImpl")
	@Autowired
	private ReportService reportService;
	
	
	@RequestMapping(value =  "/report/product_Board.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String report_Registration(@RequestBody ReportVO inVO, HttpSession session) {
		
		int flag = 0;
		String message;
		
		UserVO user = (UserVO)session.getAttribute("user");
		
		if(user == null) {
			return new Gson().toJson(new MessageVO(2, "로그인 후 신고를 진행해 주세요."));
		}
		
		try {
			
			inVO.setReport_User_Id(user.getUser_Id());
			log.debug("신고 정보 : {}", inVO.toString());
			flag = reportService.doSave(inVO);
			message = "신고가 정상적으로 접수 되었습니다.";
		} catch (DataAccessException | SQLException e) {
			log.debug("신고 접수 중 DB 예외 발생");
			return new Gson().toJson(new MessageVO(0, "신고 접수 중 DB 예외가 발생하였습니다."));
		}
		
		return new Gson().toJson(new MessageVO(flag, message));
	}
	

}
