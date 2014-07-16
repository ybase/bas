package com.ybase.dorm.servlet;

import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractAjaxDispatch;
import com.ybase.dorm.manger.DrPlanManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrPlan;
import com.ybase.dorm.vo.DrUser;

public class PlanAddController extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUser usr = getLoginUsrObj();
		if (usr == null) {
			setErrStatus();
			setTip("��δ��½ϵͳ");
			return;
		}

		String planDesc = getPar("planDesc");
		if (BasUtil.isNullOrEmpty(planDesc)) {
			setErrStatus();
			setTip("�ƻ�����Ϊ��");
			return;
		}

		DrPlan plan = new DrPlan();
		plan.setPlanDesc(planDesc);
		setCommonVO(plan);
		DrPlanManager planManager = ServiceFactory.getPlanManger();
		if (!planManager.addPlan(plan)) {
			setErrStatus();
			setTip("���ʧ��");
			return;
		}

		setSuccStatus();
		setTip("��ӳɹ�");
	}

}
