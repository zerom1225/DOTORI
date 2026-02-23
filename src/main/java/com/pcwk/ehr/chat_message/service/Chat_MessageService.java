package com.pcwk.ehr.chat_message.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.chat_message.domain.Chat_MessageVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface Chat_MessageService {

	/**
	 * 채팅 메시지 저장
	 * @param chat_Id
	 * @param sender
	 * @param chat_Content
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public int saveChatMessage(int chat_Id, UserVO sender, String chat_Content)
			throws SQLException, DataAccessException;

	/**
	 * 채팅 메시지 불러오기
	 * @param chat_Id
	 * @return List<Chat_MessageVO>
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public List<Chat_MessageVO> getChat_Messages(int chat_Id) throws SQLException, DataAccessException;

}
