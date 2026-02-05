package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.chat.domain.ChatDTO;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ChatService {
	
	/**
	 * 채팅 존재 확인
	 * @param board_Id
	 * @param buyer_Id
	 * @return 1(존재O) / 0(존재X) 
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public int is_Exist_Chat(int board_Id, int buyer_Id) throws SQLException, DataAccessException;
	
	
	/**
	 * 채팅방을 생성하고 채팅 ID 를 리턴
	 * @param board_Id
	 * @param buyer_Id
	 * @return Chat_Id
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public int create_Chat(int board_Id, int buyer_Id)  throws SQLException, DataAccessException;
	
	public List<ChatDTO> getActiveChatList(UserVO user) throws SQLException, DataAccessException;
	
	public ChatDTO doSelectOne(int chat_Id) throws SQLException, DataAccessException;
	
	/**
	 * 채팅방 조회
	 * @param board_Id
	 * @param buyer_Id
	 * @return Chat_Id
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public int doSelect_Chat_Id( int board_Id, int buyer_Id) throws SQLException, DataAccessException;
	
	/**
	 * 판매 완료 상태 변경을 위한
	 * 후보 구매자 리스트 출력
	 * @param board_Id
	 * @return List<UserVO>
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public List<UserVO> doSelect_buyers( int board_Id ) throws SQLException, DataAccessException;
	
	/**
	 * 채팅방 나가기
	 * @param chat_Id
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	int exit_Chat(int chat_Id) throws SQLException, DataAccessException;
	
	/**
	 * 채팅 단건 조회
	 * @param chat_Id
	 * @return ChatVO
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public ChatVO doSelectOne_ChatVO(int chat_Id) throws SQLException, DataAccessException;
	

}
