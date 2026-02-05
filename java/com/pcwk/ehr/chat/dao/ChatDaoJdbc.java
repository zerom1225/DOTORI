package com.pcwk.ehr.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.report.domain.ReportVO;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class ChatDaoJdbc implements ChatDao {

	final Logger log = LogManager.getLogger(ChatDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ChatDaoJdbc() {
		super();
	}

	private RowMapper<ChatVO> chatMapper = new RowMapper<ChatVO>() {

		@Override
		public ChatVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ChatVO outVO = new ChatVO();

			outVO.setChat_Id(rs.getInt("chat_Id"));
			outVO.setBoard_Id(rs.getInt("board_Id"));
			outVO.setSeller_Id(rs.getInt("seller_Id"));
			outVO.setBuyer_Id(rs.getInt("buyer_Id"));
			outVO.setCreate_Date(rs.getTimestamp("create_Date").toLocalDateTime());
			outVO.setIs_Active(rs.getInt("is_active"));

			log.debug("2.outVO: " + outVO.toString());
			return outVO;
		}

	};

	@Override
	public int is_Exist_Chat(int board_Id, int buyer_Id) throws SQLException, DataAccessException {

		int flag;

		StringBuilder sb = new StringBuilder(100);

		sb.append("SELECT count(*)      \n");
		sb.append("  FROM chat          \n");
		sb.append(" WHERE board_id = ?  \n");
		sb.append("   AND buyer_Id = ? \n");

		Object[] args = { board_Id, buyer_Id };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return flag;

	}
	
	@Override
	public int doSelect_Chat_Id( int board_Id, int buyer_Id) throws SQLException, DataAccessException {
		
		int flag;

		StringBuilder sb = new StringBuilder(100);

		sb.append("SELECT chat_id       \n");
		sb.append("  FROM chat          \n");
		sb.append(" WHERE board_id = ?  \n");
		sb.append("   AND buyer_Id = ? \n");

		Object[] args = { board_Id, buyer_Id };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return flag;
		
		
		
		
	}

	@Override
	public int create_Chat(int board_Id, int seller_Id, int buyer_Id) throws SQLException, DataAccessException {

		int flag;

		StringBuilder sb = new StringBuilder(150);

		sb.append(" INSERT INTO chat(      \n");
		sb.append("             board_Id,  \n");
		sb.append(" 			seller_Id, \n");
		sb.append("	    		buyer_Id)  \n");
		sb.append("	    VALUES (?,         \n");
		sb.append("	            ?,         \n");
		sb.append("			    ?)         \n");

		Object[] args = { board_Id, seller_Id, buyer_Id };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;

	}

	@Override
	public ChatVO doSelectOne(int chat_Id) throws SQLException, DataAccessException {

		ChatVO outVO = null;

		// 1.
		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT *            \n");
		sb.append("  FROM chat         \n");
		sb.append(" WHERE chat_Id = ?  \n");

		Object[] args = { chat_Id };
		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), chatMapper, args);

		return outVO;
	}

	@Override
	public ChatVO doSelectOne(int board_Id, int buyer_Id) throws SQLException, DataAccessException {

		ChatVO outVO = null;

		// 1.
		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT *             \n");
		sb.append("  FROM chat          \n");
		sb.append(" WHERE board_Id = ?  \n");
		sb.append("   AND buyer_Id = ? \n");

		Object[] args = { board_Id, buyer_Id };
		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), chatMapper, args);

		return outVO;
	}

	@Override
	public List<ChatVO> getActiveChatList(UserVO user) throws SQLException, DataAccessException {

		List<ChatVO> chatList = new ArrayList<ChatVO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT *              \n");
		sb.append("  FROM chat           \n");
		sb.append(" WHERE seller_id = ?  \n");
		sb.append("    OR buyer_id = ?   \n");
		sb.append(" ORDER BY create_date DESC \n");

		Object[] args = { user.getUser_Id(), user.getUser_Id() };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		chatList = this.jdbcTemplate.query(sb.toString(), args, chatMapper);

		return chatList;

	}

	@Override
	public List<Integer> doSelect_buyer_Id(int board_Id) throws SQLException, DataAccessException {
		List<Integer> buyerList = new ArrayList<Integer>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT buyer_Id     \n");
		sb.append("  FROM chat         \n");
		sb.append(" WHERE board_id = ? \n");

		Object[] args = { board_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		buyerList = this.jdbcTemplate.queryForList(sb.toString(), args, Integer.class);

		return buyerList;
	}

	@Override
	public int exit_Chat(int chat_Id) throws SQLException, DataAccessException {
		int flag;
		
		StringBuilder sb = new StringBuilder(100);
		
		sb.append("delete             \n");
		sb.append("  from chat        \n");
		sb.append("where chat_id = ?  \n");
		
		Object[] args = { chat_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}
		
		flag = this.jdbcTemplate.update(sb.toString(), args);
		
		return flag;
		
	}

}
