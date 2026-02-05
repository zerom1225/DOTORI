package com.pcwk.ehr.report.service;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.report.domain.ReportVO;

public interface ReportService {
	
	/**
	 * 신고 데이터 저장
	 * @param inVO
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public int doSave(ReportVO inVO) throws SQLException, DataAccessException;

}
