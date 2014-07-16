package com.ybase.dorm.manger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrUser;

/**
 * ͼƬService<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrImageManager {

	public static final Logger log = Logger.getLogger(DrImageManager.class.getName());

	/**
	 * ����ͼƬ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��<br/>
	 */
	public boolean addDrImage(DrImage image) throws BasException;

	/**
	 * ���ID ����ͼƬ��Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��<br/>
	 * 
	 * @param id
	 */
	public DrImage queryDrImageById(Integer id) throws BasException;

	/**
	 * ����ͼƬ��Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��<br/>
	 * 
	 * @param id
	 */
	public List<DrImage> queryAllDrImage();

	/**
	 * ��ҳ����ͼƬ��Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��<br/>
	 * 
	 * @param page
	 */
	public Map<String, Object> pageAllDrImage(Page page) throws BasException;

	/**
	 * ����ǰ������ҳ��Ƭ��Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 */
	public List<DrImage> queryIndex4DrImage();

	/**
	 * ����ǰ10��������Ƭ��Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 */
	public List<DrImage> queryClassic10DrImage();

	/**
	 * ����ǰ10����Ƭ��Ϣ,����ϴ�ʱ������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * 
	 * @param x
	 */
	public List<DrImage> queryTopXDrImage(int x);

	/**
	 * ����ͼƬ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��28��<br/>
	 * @param id
	 */
	public boolean updateY(Integer id, DrUser usr) throws BasException;

}
