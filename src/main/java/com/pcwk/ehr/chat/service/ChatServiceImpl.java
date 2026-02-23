package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat.dao.ChatDao;
import com.pcwk.ehr.chat.domain.ChatDTO;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.product_board.dao.Product_BoardDao;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

@Service
public class ChatServiceImpl implements ChatService {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	ChatDao chatDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	Product_BoardDao product_BoardDao;


	public int is_Exist_Chat(int board_Id, int buyer_Id) throws SQLException, DataAccessException {

		return chatDao.is_Exist_Chat(board_Id, buyer_Id);
	}

	@Override
	public int create_Chat(int board_Id, int buyer_Id) throws SQLException, DataAccessException {

		int flag;
		
		Product_BoardVO pbVO = product_BoardDao.doSelectOne(board_Id);
		
		if(pbVO.getSeller_Id() == buyer_Id) {
			return -1;
		}

		flag = chatDao.create_Chat(board_Id, pbVO.getSeller_Id(), buyer_Id);

		if (flag != 1) {
			return -1;
		}

		ChatVO chatVO = chatDao.doSelectOne(board_Id, buyer_Id);

		if (chatVO == null) {
			return -1;
		}

		return chatVO.getChat_Id();
	}

	@Override
	public List<ChatDTO> getActiveChatList(UserVO user) throws SQLException, DataAccessException {
		
		// 채팅방 리스트 조회
		List<ChatVO> chatVO_List = chatDao.getActiveChatList(user);
		
		List<ChatDTO> chatDTO_List = new ArrayList<ChatDTO>();
		
		Product_BoardVO product_Board;
		UserVO seller;
		UserVO buyer;
		
		
		for(ChatVO chatVO : chatVO_List) {
			
			product_Board = product_BoardDao.doSelectOne(chatVO.getBoard_Id());
			product_Board.setBoard_Content(product_Board.getBoard_Content().replaceAll("\\r\\n|\\r|\\n", " "));
			seller = userDao.doSelectOne_With_User_Id(chatVO.getSeller_Id());
			buyer = userDao.doSelectOne_With_User_Id(chatVO.getBuyer_Id());
			
			ChatDTO chatDTO = new ChatDTO(chatVO, product_Board, seller, buyer);
			
			chatDTO_List.add(chatDTO);			
		}
		
		return chatDTO_List;
	}

	@Override
	public ChatDTO doSelectOne(int chat_Id) throws SQLException, DataAccessException {
		
		ChatVO chatVO = chatDao.doSelectOne(chat_Id);
		
		Product_BoardVO product_Board = product_BoardDao.doSelectOne(chatVO.getBoard_Id());
		UserVO seller = userDao.doSelectOne_With_User_Id(chatVO.getSeller_Id());
		UserVO buyer = userDao.doSelectOne_With_User_Id(chatVO.getBuyer_Id());
		
		ChatDTO chatDTO = new ChatDTO(chatVO, product_Board, seller, buyer);
		
		
		return chatDTO;
	}

	@Override
	public int doSelect_Chat_Id(int board_Id, int buyer_Id) throws SQLException, DataAccessException {
		return chatDao.doSelect_Chat_Id(board_Id, buyer_Id);
	}

	@Override
	public List<UserVO> doSelect_buyers(int board_Id) throws SQLException, DataAccessException {
		
		List<Integer> buyer_IdList = chatDao.doSelect_buyer_Id(board_Id);
		
		List<UserVO> buyers = new ArrayList<UserVO>();
		
		for(int buyer_Id : buyer_IdList) {
			
			UserVO buyer = userDao.doSelectOne_With_User_Id(buyer_Id);
			
			if(buyer != null) {
				buyers.add(buyer);
			}
			
		}
		
		return buyers;
	}

	@Override
	public int exit_Chat(int chat_Id) throws SQLException, DataAccessException {
		return chatDao.exit_Chat(chat_Id);
	}

	@Override
	public ChatVO doSelectOne_ChatVO(int chat_Id) throws SQLException, DataAccessException {
		return chatDao.doSelectOne(chat_Id);
	}

}
