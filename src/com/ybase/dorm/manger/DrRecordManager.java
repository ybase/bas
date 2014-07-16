package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.dorm.vo.DrRecord;

/**
 * Record Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��6��4��
 */
public interface DrRecordManager {

	public static final Logger log = Logger.getLogger(DrRecordManager.class.getName());

	/**
	 * ���ӵ���/�²ۼ�¼<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��6��4��
	 */
	public boolean addDrRecord(DrRecord record);

	/**
	 * ����û�ID�����ڡ���Χ�����ҵ���/�²�һ����¼<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��6��4��<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public DrRecord queryRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException;

	/**
	 * ����û�ID�����ڡ���Χ�����ҵ���/�²ۼ�¼�б�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��6��4��<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public List<DrRecord> queryListRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException;

	/**
	 * ����û�ID�����ڡ���Χ���жϵ���/�²ۼ�¼�Ƿ����<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��6��4��<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public boolean existRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws BasException;

}
