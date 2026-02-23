package com.pcwk.ehr.chat_message.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.chat_message.domain.Chat_MessageVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface Chat_MessageDao {

	public int saveChatMessage(int chat_Id, UserVO sender, String chat_Content)
			throws SQLException, DataAccessException;

	public List<Chat_MessageVO> getChat_Messages(int chat_Id) throws SQLException, DataAccessException;
}
