package com.pcwk.ehr.chat.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ChatDao {

	public int is_Exist_Chat(int board_Id, int buyer_Id) throws SQLException, DataAccessException; 
	
	public int create_Chat(int board_Id, int seller_Id, int buyer_Id)  throws SQLException, DataAccessException;
	
	public ChatVO doSelectOne(int board_Id, int buyer_Id) throws SQLException, DataAccessException;
	
	public ChatVO doSelectOne(int chat_Id) throws SQLException, DataAccessException;
	
	public List<ChatVO> getActiveChatList(UserVO user) throws SQLException, DataAccessException;
	
	public int doSelect_Chat_Id( int board_Id, int buyer_Id) throws SQLException, DataAccessException;
	
	public List<Integer> doSelect_buyer_Id( int board_Id ) throws SQLException, DataAccessException;
	
	int exit_Chat(int chat_Id) throws SQLException, DataAccessException;
	
	
	
}
