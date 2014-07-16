package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ybase.bas.BasException;
import com.ybase.bas.annotation.Service;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

public class DrBlogManagerImpl extends JdbcEntityDaoTemplate implements DrBlogManager {

	@Service("recordManager")
	private DrRecordManager recordDAO;

	@Service("blogManager")
	private DrTalkManager talkDAO;

	@Override
	public boolean addDrBlog(DrBlog blog) throws BasException {
		boolean flag = false;
		try {
			if (BasUtil.isNullOrEmpty(blog.getTheme())) {
				throw new BasException("addDrBlog �ֶ�themeΪ��!");
			}

			blog = addVO(blog);
			if (blog != null) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrBlog queryDrBlogById(Integer id) throws BasException {
		if (id != null) {
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("id_Eq", id);
			List<DrBlog> list;
			try {
				list = executeQuery(DrBlog.class, par);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(e.getMessage());
			}
			return BasUtil.getOne(list);
		} else {
			throw new BasException("����idΪ��!");
		}
	}

	@Override
	public boolean updateYesCount(Integer id, DrUser usr) throws BasException {
		boolean flag = false;

		if (usr == null) {
			throw new BasException("updateYesCount ����[usr] Ϊ��!");
		}

		if (id == null) {
			throw new BasException("updateYesCount ����[id] Ϊ��!");
		}

		try {
			Map<String, Object> updateProps = new HashMap<String, Object>();
			updateProps.put("yesCount", "yescount+1");
			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", id);
			int row = update(DrBlog.class, updateProps, conProp);
			if (row > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), BasUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_0), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(BasUtil.getDate8Str());
					record.setCrTime(BasUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_0));
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
			throw new BasException(e.getMessage());
		}

		return flag;
	}

	@Override
	public Map<String, Object> queryBlogByImageId(Integer imageId) throws BasException {
		try {
			if (imageId == null) {
				throw new BasException("����[imageId] Ϊ��!");
			}

			List<Map<String, Object>> list = execXmlSqlQuery("selectBlogAndImage", null, imageId);
			if (list.size() > 1) {
				throw new BasException("����[imageId=" + imageId + "]���ز�ֹһ����ݣ�ʵ�ʷ���:" + list.size() + " �����");
			}

			return list != null && list.size() == 1 ? list.get(0) : null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTopFiveBlog() {
		try {
			List<Map<String, Object>> rst = execXmlSqlQuery("selectBlogAndImageT5", null);
			if (rst != null && rst.size() > 0) {
				return rst;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private int countAllBlog() {
		try {
			return executeCountQuery(DrBlog.class, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public Map<String, Object> pageAllBlogWrapTalk(Page page) throws BasException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new BasException("Page ����Ϊ��!");
			}
			page.setPageRecord(1);
			page.setTotalRecord(countAllBlog());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);// ����
			List<DrBlog> blogs = executeQuery(DrBlog.class, null, page, sort);

			if (!BasUtil.isNullOrEmpty(blogs)) {
				Iterator<DrBlog> iter = blogs.iterator();
				int count = 1;
				while (iter.hasNext()) {
					DrBlog blog = iter.next();
					Map<String, Object> arg = new HashMap<String, Object>();
					arg.put("blog", blog);
					List<DrTalk> talks = talkDAO.queryTop20DrTalkByBlogId(blog.getId());
					arg.put("talk", talks);
					rsMap.put("rs" + count, arg);
					count++;
				}
			}
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

	@Override
	public Map<String, Object> pageAllBlog(Page page) throws BasException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new BasException("Page ����Ϊ��!");
			}
			page.setPageRecord(1);
			page.setTotalRecord(countAllBlog());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			List<DrBlog> blogs = executeQuery(DrBlog.class, null, page, sort);

			rsMap.put("result", blogs);
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

}
