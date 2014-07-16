package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.annotation.Service;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

public class DrTalkManagerImpl extends JdbcEntityDaoTemplate implements DrTalkManager {

	@Service("recordManager")
	private DrRecordManager recordDAO;

	@Override
	public boolean addTalk(DrTalk talk) {
		boolean flag = false;
		try {
			talk = addVO(talk);
			if (talk.getId() != null) {
				// ����Talk �ɹ�
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public List<DrTalk> queryListDrTalkByBlogId(Integer blogId) throws BasException {
		try {
			if (blogId == null) {
				throw new BasException("queryListDrTalkByBlogId ����[blogId] Ϊ��!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("blogId_Eq", new Object[] { blogId, BasConstants.SQL_AND });

			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			return executeQuery(DrTalk.class, con, sort);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrTalk> queryTop20DrTalkByBlogId(Integer blogId) throws BasException {
		try {
			if (blogId == null) {
				throw new BasException("queryTop20DrTalkByBlogId ����[blogId] Ϊ��!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("blogId_Eq", new Object[] { blogId, BasConstants.SQL_AND });

			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			return executeQuery(DrTalk.class, con, sort, 20);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updateY(Integer id, DrUser usr) {
		boolean flag = false;
		try {
			if (usr == null) {
				throw new BasException("updateY ����[usr] Ϊ��!");
			}

			if (id == null) {
				throw new Exception("updateY ����[id] Ϊ��!");
			}

			Map<String, Object> updateProp = new HashMap<String, Object>();
			updateProp.put("yesCount", "yescount+1");

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });

			int rows = update(DrTalk.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), BasUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_2), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(BasUtil.getDate8Str());
					record.setCrTime(BasUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_2));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("��ǰ�û�[%s]�ѵ��޹�ǰ����[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateN(Integer id, DrUser usr) {
		boolean flag = false;
		try {
			if (usr == null) {
				throw new BasException("updateN ����[usr] Ϊ��!");
			}

			if (id == null) {
				throw new BasException("updateN ����[id] Ϊ��!");
			}

			Map<String, Object> updateProp = new HashMap<String, Object>();
			updateProp.put("noCount", "nocount+1");

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });

			int rows = update(DrTalk.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), BasUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_2), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(BasUtil.getDate8Str());
					record.setCrTime(BasUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_2));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("��ǰ�û�[%s]���²۹�ǰ����[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrTalk queryTalkById(Integer id) throws BasException {
		try {
			if (id == null) {
				throw new BasException("queryTalkById ����idΪ��!");
			}
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", id);

			List<DrTalk> list = executeQuery(DrTalk.class, con);
			return BasUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
