package com.pcwk.ehr.report.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.report.domain.ReportVO;

@Repository
public class ReportDaoJdbc implements ReportDao {

	final Logger log = LogManager.getLogger(ReportDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ReportDaoJdbc() {
		super();
	}

	public int doSave(ReportVO inVO) throws SQLException, DataAccessException{

		int flag;

		StringBuilder sb = new StringBuilder(100);

		sb.append("INSERT INTO board_report     \n");
		sb.append("           (board_id,        \n");
		sb.append("            report_user_id,  \n");
		sb.append("            report_category, \n");
		sb.append("            report_reason)   \n");
		sb.append("     VALUES (?,              \n");
		sb.append("             ?,              \n");
		sb.append("             ?,              \n");
		sb.append("             ?)              \n");

		Object[] args = { inVO.getBoard_Id(), inVO.getReport_User_Id(), inVO.getReport_Category(), inVO.getReport_Reason() };
		
		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}
		
		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

}
