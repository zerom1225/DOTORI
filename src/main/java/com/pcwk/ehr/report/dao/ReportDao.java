package com.pcwk.ehr.report.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.report.domain.ReportVO;

public interface ReportDao {

	public int doSave(ReportVO inVO) throws SQLException, DataAccessException;

}
