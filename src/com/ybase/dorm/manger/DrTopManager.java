package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * TOP Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月24日
 */
public interface DrTopManager {
	public static final Logger log = Logger.getLogger(DrTopManager.class.getName());

	/**
	 * 增加Top<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 */
	public boolean addDrTop(DrTop top);

	/**
	 * 根据top id 更新点赞<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * @param id
	 * @param usr
	 */
	public boolean updateY(Integer id, DrUser usr);

	/**
	 * 根据top id 更新吐槽<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * @param id
	 * @param usr
	 */
	public boolean updateN(Integer id, DrUser usr);

	/**
	 * 随机获取top<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月27日<br/>
	 * @param i
	 * @return
	 */
	public DrTop queryRandomTop(int i);

	/**
	 * 获取top ID<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月27日<br/>
	 * @return
	 */
	public List<Object> queryIds();

}
