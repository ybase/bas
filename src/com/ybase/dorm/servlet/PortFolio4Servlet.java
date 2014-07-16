package com.ybase.dorm.servlet;

import java.util.List;
import java.util.Map;

import com.ybase.bas.BasException;
import com.ybase.bas.vo.Page;
import com.ybase.bas.web.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrImage;

/**
 * ����<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class PortFolio4Servlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void process() throws Exception {
		try {
			System.out.println(getAttr(DormConstant.SCOPE_REQ, "status"));
			DrImageManager imageDAO = ServiceFactory.getImageManager();
			CommonService commonService = ServiceFactory.getCommonService();

			Page page = commonService.createPage(wrappReq.get());
			Map<String, Object> rsMap = imageDAO.pageAllDrImage(page);

			Page changePage = (Page) rsMap.get("page");
			setAttr(DormConstant.SCOPE_REQ, "page", changePage);

			Object srcImage = rsMap.get("image");
			if (srcImage != null) {
				setAttr(DormConstant.SCOPE_REQ, "image", (List<DrImage>) srcImage);
			}
		} catch (BasException e) {
			setAttr(DormConstant.SCOPE_REQ, "status", "0");
			log.error(e.getMessage(), e);
		}
		setDUrl("portfolio4Dorm.jsp");
	}
}
