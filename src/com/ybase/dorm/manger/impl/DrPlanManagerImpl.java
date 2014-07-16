package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.manger.DrPlanManager;
import com.ybase.dorm.vo.DrPlan;

public class DrPlanManagerImpl extends JdbcEntityDaoTemplate implements DrPlanManager {

	@Override
	public boolean addPlan(DrPlan plan) {
		boolean flag = false;
		try {
			plan = addVO(plan);
			if (plan.getId() != null) {
				// ����Talk �ɹ�
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public List<DrPlan> queryPlanTopx(int x) throws BasException {
		try {
			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);

			return executeQuery(DrPlan.class, null, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(e);
		}
	}

	@Override
	public DrPlan queryPlan(Integer id) throws BasException {
		try {
			if (id == null) {
				throw new BasException("queryPlan ����idΪ��!");
			}
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", id);

			List<DrPlan> list = executeQuery(DrPlan.class, con);
			return BasUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(e);
		}
	}

}
