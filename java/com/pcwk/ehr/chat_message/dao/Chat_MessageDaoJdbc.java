package com.pcwk.ehr.chat_message.dao;

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
import com.pcwk.ehr.chat_message.domain.Chat_MessageVO;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class Chat_MessageDaoJdbc implements Chat_MessageDao {

	final Logger log = LogManager.getLogger(Chat_MessageDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Chat_MessageDaoJdbc() {
		super();
	}

	private RowMapper<Chat_MessageVO> chat_MessageMapper = new RowMapper<Chat_MessageVO>() {

		@Override
		public Chat_MessageVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			Chat_MessageVO outVO = new Chat_MessageVO();

			outVO.setChat_Message_Id(rs.getInt("chat_message_id"));
			outVO.setChat_Id(rs.getInt("chat_id"));
			outVO.setSender_Id(rs.getInt("sender_id"));
			outVO.setChat_Content(rs.getString("chat_content"));
			outVO.setMessage_Date(rs.getTimestamp("message_date").toLocalDateTime());
			outVO.setFormattedDate(rs.getString("formattedDate"));

			log.debug("2.outVO: " + outVO.toString());
			return outVO;
		}

	};

	@Override
	public int saveChatMessage(int chat_Id, UserVO sender, String chat_Content)
			throws SQLException, DataAccessException {
		int flag;

		StringBuilder sb = new StringBuilder(100);

		sb.append(" INSERT                              \n");
		sb.append("   INTO chat_message (               \n");
		sb.append("                      chat_id,       \n");
		sb.append("			    		 sender_id,     \n");
		sb.append("				    	 chat_content)  \n");
		sb.append("			      VALUES(?,             \n");
		sb.append("         	       	 ?,             \n");
		sb.append("					     ?)             \n");

		Object[] args = { chat_Id, sender.getUser_Id(), chat_Content };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public List<Chat_MessageVO> getChat_Messages(int chat_Id) throws SQLException, DataAccessException {
		
		List<Chat_MessageVO> chat_MessageList = new ArrayList<Chat_MessageVO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT c1.*,                                                      \n");
		sb.append("       TO_CHAR(c1.message_date, 'YYYY/MM/DD HH24:mi') formattedDate \n");
		sb.append("  FROM chat_message c1                                            \n");
		sb.append(" WHERE chat_Id = ?                                                \n");
		sb.append(" ORDER BY c1.message_date asc                                     \n");
		 

		Object[] args = { chat_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		chat_MessageList = this.jdbcTemplate.query(sb.toString(), args, chat_MessageMapper);

		return chat_MessageList;
	}

}
