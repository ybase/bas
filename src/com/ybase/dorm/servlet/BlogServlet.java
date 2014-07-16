package com.ybase.dorm.servlet;

import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.vo.Page;
import com.ybase.bas.web.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;

/**
 * ����<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class BlogServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void process() throws Exception {
		try {
			CommonService commonService = ServiceFactory.getCommonService();
			DrBlogManager blogDAO = ServiceFactory.getBlogManger();
			Page page = commonService.createPage(wrappReq.get());
			Map<String, Object> rsMap = blogDAO.pageAllBlogWrapTalk(page);

			Page changePage = (Page) rsMap.get("page");
			setAttr(DormConstant.SCOPE_REQ, "page", changePage);
			Object rs = rsMap.get("rs1");
			if (rs == null) {
				throw new BasException(BasErrCode.D0002);
			}

			Map<String, Object> subRs = (Map<String, Object>) rs;
			Object srcBlog = subRs.get("blog");
			if (srcBlog == null) {
				throw new BasException(BasErrCode.D0002);
			}
			DrBlog blog = (DrBlog) srcBlog;
			setAttr(DormConstant.SCOPE_REQ, "blog", blog);

			Object srcTalk = subRs.get("talk");
			if (srcTalk != null) {
				setAttr(DormConstant.SCOPE_REQ, "talk", (List<DrTalk>) srcTalk);
			}

			List<Map<String, Object>> fiveMap = blogDAO.queryTopFiveBlog();
			setAttr(DormConstant.SCOPE_REQ, "fiveMap", fiveMap);
		} catch (BasException e) {
			setAttr(DormConstant.SCOPE_REQ, "status", "0");
			log.error(e.getMessage(), e);
		}
		setDUrl("blogDorm.jsp");
	}

}
