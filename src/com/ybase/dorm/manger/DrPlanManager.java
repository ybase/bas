package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.dorm.vo.DrPlan;

/**
 * �ƻ�Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��26��
 */
public interface DrPlanManager {

	public static final Logger log = Logger.getLogger(DrPlanManager.class.getName());

	/**
	 * ���Ӽƻ�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * 
	 * @param drPlan
	 */
	public boolean addPlan(DrPlan drPlan);

	/**
	 * ��ѯǰtop ���ƻ�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * @param top
	 * @return
	 */
	public List<DrPlan> queryPlanTopx(int top) throws BasException;

	/**
	 * ���id��ѯ�ƻ�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * @param id
	 * @return
	 */
	public DrPlan queryPlan(Integer id) throws BasException;

}
