package com.ybase.dorm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ybase.bas.BasException;
import com.ybase.bas.annotation.Service;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.vo.PicVO;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/*
 * ͨ��Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 */
public class CommonService {

	private static final Logger log = Logger.getLogger(CommonService.class.getName());
	@Service("userManager")
	public DrUserManager userDAO;
	@Service("blogManager")
	public DrBlogManager blogDAO;
	@Service("topManager")
	public DrTopManager topDAO;

	public boolean checkLogin(String email, String passwd, HttpServletRequest request) throws BasException {
		if (!BasUtil.isNullOrEmpty(email) && !BasUtil.isNullOrEmpty(passwd)) {
			try {
				DrUser usr = userDAO.queryUsrByEmail(email);
				if (usr != null) {
					if (usr.getStatus() == null) {
						throw new BasException(BasErrCode.E0010);
					} else if (DormConstant.DR_USER_STATUS_1.equals(String.valueOf(usr.getStatus()))) {
						throw new BasException(BasErrCode.E0011);
					} else if (DormConstant.DR_USER_STATUS_2.equals(String.valueOf(usr.getStatus()))) {
						throw new BasException(BasErrCode.E0012);
					} else if (!DormConstant.DR_USER_STATUS_0.equals(String.valueOf(usr.getStatus()))) {
						throw new BasException(BasErrCode.E0010);
					}

					boolean check = MD5EncrytUtil.md5Encrypt(passwd.trim()).equals(usr.getPasswd());
					if (check) {
						userDAO.updateVDTS(usr.getId());// ���·��ʴ�������¼ʱ��
						request.getSession().setAttribute("loginusr", usr);
					}
					return check;
				}
			} catch (BasException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String createBlogJson(HttpServletRequest req) {
		Page page = null;
		String status = "1";
		String tip = "";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			page = createPage(req);
			if (page == null) {
				throw new BasException(BasErrCode.D0001);
			}
			Map<String, Object> result = blogDAO.pageAllBlogWrapTalk(page);
			Page changePage = (Page) result.get("page");
			jsonMap.put("page", changePage);
			Object rs = result.get("rs1");
			if (rs == null) {
				throw new BasException(BasErrCode.D0002);
			}

			Map<String, Object> rsMap = (Map<String, Object>) rs;
			Object srcBlog = rsMap.get("blog");
			if (srcBlog == null) {
				throw new BasException(BasErrCode.D0002);
			}
			DrBlog blog = (DrBlog) srcBlog;
			jsonMap.put("blog", blog);
			Object srcTalk = rsMap.get("talk");
			if (srcTalk != null) {
				jsonMap.put("talk", (List<DrTalk>) srcTalk);
			}
		} catch (BasException e) {
			status = "0";
			tip = e.getMessage();
			log.error(e.getMessage(), e);
		}
		jsonMap.put("status", status);
		jsonMap.put("tip", tip);
		return BasUtil.isoToG180(JSONObject.fromObject(jsonMap).toString());
	}

	/**
	 * ���Request ��ʼ��Page<br/>
	 * 
	 * @DORMITORY_V1.O,yangxb,2014-05-27<br/>
	 * @param req
	 */
	public Page createPage(HttpServletRequest req) throws BasException {
		Page page = new Page();
		Object p1 = req.getParameter("pageCurrent");
		Object p2 = req.getParameter("pageFirst");
		Object p3 = req.getParameter("pageLast");

		if (p1 == null) {
			p1 = "1";
		}
		Integer current = Integer.valueOf((String) p1);
		page.setCurrent(current);

		if (current != 1 && (p2 == null || p3 == null)) {
			throw new BasException(BasErrCode.D0003);
		} else if (current != 1 && (BasUtil.isNullOrEmpty(((String) p2)) || BasUtil.isNullOrEmpty(((String) p3)))) {
			throw new BasException(BasErrCode.D0003);
		} else if (!BasUtil.isNullOrEmpty(((String) p2)) && !BasUtil.isNullOrEmpty(((String) p3))) {
			page.setFirst(Integer.valueOf((String) p2));
			page.setLast(Integer.valueOf((String) p3));
		}

		return page;
	}

	/**
	 * �����ϴ�ͼƬ<br/>
	 * 
	 * @DORMITORY_V1.O,yangxb,2014-05-27<br/>
	 * @param uploadFilePath
	 * @param uuidName
	 * @param expand
	 */
	public void createMidSmallPic(String uploadFilePath, String uuidName, String expand) {
		if (!BasUtil.isNullOrEmpty(uploadFilePath)) {
			uploadFilePath += "\\";
			PicVO picVO1 = new PicVO(1);
			picVO1.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_idx." + expand);
			picVO1.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_idx." + expand);
			PicVO picVO2 = new PicVO(2);
			picVO2.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_mid." + expand);
			picVO2.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_mid." + expand);
			PicVO picVO3 = new PicVO(3);
			picVO3.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_sma." + expand);
			picVO3.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_sma." + expand);
		}
	}

	public DrTop getOneRandomTop(int id, boolean flag) {
		DrTop top = null;
		if (flag) {
			List<Object> ids = topDAO.queryIds();
			if (ids != null && ids.size() > 0) {
				int count = ids.size();
				while (top == null && count > 0) {
					int rand = BasUtil.randomOne(ids.size(), 0);
					top = topDAO.queryRandomTop((Integer) ids.get(rand));
					count--;
				}
			}
		} else {
			top = topDAO.queryRandomTop(id);
		}
		return top;
	}
}
