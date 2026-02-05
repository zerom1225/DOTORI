package com.pcwk.ehr.report.service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.report.dao.ReportDao;
import com.pcwk.ehr.report.domain.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {
	
final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ReportDao reportDao;

	@Override
	public int doSave(ReportVO inVO) throws SQLException, DataAccessException {
		
		return reportDao.doSave(inVO);
	}
	


}
