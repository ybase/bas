package com.ybase.dorm.manger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrUser;

/**
 * ����Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrBlogManager {

	public static Logger log = Logger.getLogger(DrBlogManager.class.getName());

	/*
	 * ��������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param blog
	 */
	public boolean addDrBlog(DrBlog blog) throws BasException;

	/*
	 * ���ID ����������Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public DrBlog queryDrBlogById(Integer id) throws BasException;

	/*
	 * ���ID �����������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 * 
	 * @param usr
	 */
	public boolean updateYesCount(Integer id, DrUser usr) throws BasException;

	/*
	 * ���ͼƬID ����BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * 
	 * @param imageId
	 */
	public Map<String, Object> queryBlogByImageId(Integer imageId) throws BasException;

	/*
	 * �ҳ������ŵ�5��BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 */
	public List<Map<String, Object>> queryTopFiveBlog();

	/*
	 * ��ҳBLOG ����ʱ������talk �б�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��25��<br/>
	 */
	public Map<String, Object> pageAllBlogWrapTalk(Page page) throws BasException;

	/*
	 * ��ҳBLOG <br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��25��<br/>
	 */
	public Map<String, Object> pageAllBlog(Page page) throws BasException;

}
