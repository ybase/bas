package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.vo.DrRecord;

public class DrRecordManagerImpl extends JdbcEntityDaoTemplate implements DrRecordManager {

	@Override
	public boolean addDrRecord(DrRecord record) {
		boolean flag = false;
		try {
			record = addVO(record);
			if (record.getId() != null) {
				// ����RECORD �ɹ�
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrRecord queryRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException {
		try {
			if (crusr == null || BasUtil.isNullOrEmpty(date) || !BasUtil.checkDrRecordTp(type) || relId == null) {
				throw new BasException("queryRecordByUsrDtTp �ĸ�����֮һ����Ϊ�գ����߲��Ϸ�");
			}

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("crUsr_Eq", new Object[] { crusr, BasConstants.SQL_AND });
			conProp.put("crDate_Eq", new Object[] { date, BasConstants.SQL_AND });
			conProp.put("drType_Eq", new Object[] { type, BasConstants.SQL_AND });
			conProp.put("relId_Eq", new Object[] { relId, BasConstants.SQL_AND });
			List<DrRecord> list = executeQuery(DrRecord.class, conProp);
			return BasUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrRecord> queryListRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException {
		try {
			if (crusr == null || BasUtil.isNullOrEmpty(date) || !BasUtil.checkDrRecordTp(type) || relId == null) {
				throw new BasException("queryRecordByUsrDtTp �ĸ�����֮һ����Ϊ�գ����߲��Ϸ�");
			}

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("crUsr_Eq", new Object[] { crusr, BasConstants.SQL_AND });
			conProp.put("crDate_Eq", new Object[] { date, BasConstants.SQL_AND });
			conProp.put("drType_Eq", new Object[] { type, BasConstants.SQL_AND });
			conProp.put("relId_Eq", new Object[] { relId, BasConstants.SQL_AND });
			return executeQuery(DrRecord.class, conProp);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean existRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException {
		try {
			if (crusr != null && !BasUtil.isNullOrEmpty(date) && BasUtil.checkDrRecordTp(type) && relId != null) {
				Map<String, Object> props = new HashMap<String, Object>();
				props.put("crUsr_Eq", new Object[] { crusr, BasConstants.SQL_AND });
				props.put("crDate_Eq", new Object[] { date, BasConstants.SQL_AND });
				props.put("drType_Eq", new Object[] { type, BasConstants.SQL_AND });
				props.put("relId_Eq", new Object[] { relId, BasConstants.SQL_AND });
				List<DrRecord> list = executeQuery(DrRecord.class, props);
				if (list != null && list.size() > 0) {
					return true;
				}
			} else {
				throw new BasException("�ĸ�����֮һ����Ϊ�գ����߲��Ϸ�");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

}
