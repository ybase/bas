package com.ybase.dorm.manger.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.vo.DrUser;

public class DrUserManagerImpl extends JdbcEntityDaoTemplate implements DrUserManager {

	@Override
	public boolean addDrUser(DrUser drUser) {
		boolean flag = false;
		try {
			drUser.setPasswd(MD5EncrytUtil.md5Encrypt(drUser.getPasswd()));
			drUser = addVO(drUser);
			if (drUser.getId() != 0) {
				// ����USER �ɹ�
				return flag;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrUser queryDrUserById(Integer id) {
		try {
			if (id != null) {
				Map<String, Object> prop = new HashMap<String, Object>();
				prop.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
				return BasUtil.getOne(executeQuery(DrUser.class, prop));
			} else {
				return BasUtil.getOne(executeQuery(DrUser.class, null));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updateStatus(Integer id, Integer status) throws BasException {
		boolean flag = false;

		if (!Arrays.asList(DormConstant.DR_USER_STATU_ARR).contains(String.valueOf(status))) {
			throw new BasException("DR_USER.STATUS ����!");
		}

		try {
			if (id == null) {
				throw new BasException("updateStatus ����[id] Ϊ��!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("status", status);
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateVisit(Integer id) throws BasException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new BasException("updateVisit ����[id] Ϊ��!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("visit", "visit+1");
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateVDTS(Integer id) throws BasException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new BasException("updateVDT ����[id] Ϊ��!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("visit", "visit+1");
			update.put("loginDate", BasUtil.getDate8Str());
			update.put("loginTime", BasUtil.getTime9Str());
			update.put("status", Integer.valueOf(DormConstant.DR_USER_STATUS_1));
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrUser queryUsrByEmail(String email) throws BasException {
		try {
			if (BasUtil.isNullOrEmpty(email)) {
				throw new BasException("queryUsrByEmail ����[email]Ϊ��!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("email_Eq", new Object[] { email, BasConstants.SQL_AND });
			List<DrUser> usrs = executeQuery(DrUser.class, con);
			if (usrs != null && usrs.size() > 1) {
				throw new BasException("queryUsrByEmail ����[email]=" + email + ",��ѯ���ֹһ��!");
			}
			return BasUtil.getOne(usrs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updatePass(Integer id, String newPass) throws BasException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new BasException("updatePass ����[id] Ϊ��!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("passwd", MD5EncrytUtil.md5Encrypt(newPass));
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });

			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}
}
