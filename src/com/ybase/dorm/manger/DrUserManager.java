package com.ybase.dorm.manger;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.dorm.vo.DrUser;

/**
 * �û�Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrUserManager {

	public static final Logger log = Logger.getLogger(DrUserManager.class.getName());

	/*
	 * �����û�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 */
	public boolean addDrUser(DrUser drUser);

	/*
	 * ���ID �����û���Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public DrUser queryDrUserById(Integer id);

	/*
	 * ���ID �����û�״̬<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateStatus(Integer id, Integer status) throws BasException;

	/*
	 * ���ID �����û����ʴ���<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateVisit(Integer id) throws BasException;

	/*
	 * ���ID �����û���Ϣ���ʴ������������ں�ʱ��<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateVDTS(Integer id) throws BasException;

	/*
	 * ���email ����USER<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param email
	 */
	public DrUser queryUsrByEmail(String email) throws BasException;

	/**
	 * �޸�����<br/>
	 * 
	 * @param id
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��29��
	 * @param newPass
	 * @return
	 */
	public boolean updatePass(Integer id, String newPass) throws BasException;

}
