package com.pcwk.ehr.chat_message.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat_message.dao.Chat_MessageDao;
import com.pcwk.ehr.chat_message.domain.Chat_MessageVO;
import com.pcwk.ehr.report.dao.ReportDao;
import com.pcwk.ehr.report.domain.ReportVO;
import com.pcwk.ehr.user.domain.UserVO;

@Service
public class Chat_MessageServiceImpl implements Chat_MessageService {
	
final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	Chat_MessageDao chat_MessageDao;

	@Override
	public int saveChatMessage(int chat_Id, UserVO sender, String chat_Content)
			throws SQLException, DataAccessException {
		return chat_MessageDao.saveChatMessage(chat_Id, sender, chat_Content);
	}

	@Override
	public List<Chat_MessageVO> getChat_Messages(int chat_Id) throws SQLException, DataAccessException {
		return chat_MessageDao.getChat_Messages(chat_Id);
	}


	


}
