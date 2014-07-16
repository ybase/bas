package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

/**
 * ����Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��26��
 */
public interface DrTalkManager {
	
	public static final Logger log = Logger.getLogger(DrTalkManager.class.getName());

	/**
	 * ��������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��
	 * 
	 * @param drTalk
	 */
	public boolean addTalk(DrTalk drTalk);

	/**
	 * ���blog id ��������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * 
	 * @param blogId
	 */
	public List<DrTalk> queryListDrTalkByBlogId(Integer blogId) throws BasException;

	/**
	 * ���blog id ����ǰ20������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * 
	 * @param id
	 */
	public List<DrTalk> queryTop20DrTalkByBlogId(Integer blogId) throws BasException;

	/**
	 * ���id ����<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��29��<br/>
	 * @param id
	 * @param usr
	 * @return
	 */
	public boolean updateY(Integer id, DrUser usr);

	/**
	 * ���id ��<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��29��<br/>
	 * @param id
	 * @param usr
	 * @return
	 */
	public boolean updateN(Integer id, DrUser usr);

	/**
	 * ���id ����Talk<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��29��<br/>
	 * @param id
	 * @return
	 */
	public DrTalk queryTalkById(Integer id) throws BasException;

}
