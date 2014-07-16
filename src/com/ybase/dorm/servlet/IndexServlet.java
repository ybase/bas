package com.ybase.dorm.servlet;

import java.util.List;

import com.ybase.bas.web.AbstractCommonDispatch;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.manger.DrPlanManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrPlan;

/**
 * ��ҳ<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-23
 * 
 */
public class IndexServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrImageManager imageDAO = ServiceFactory.getImageManager();
		DrPlanManager planManager = ServiceFactory.getPlanManger();
		List<DrImage> top = imageDAO.queryIndex4DrImage();
		List<DrImage> classic = imageDAO.queryClassic10DrImage();
		List<DrPlan> plans = planManager.queryPlanTopx(4);
		setReqAttr("tops", top);
		setReqAttr("classic", classic);
		setReqAttr("plans", plans);
		setDUrl("indexDorm.jsp");
	}
}
