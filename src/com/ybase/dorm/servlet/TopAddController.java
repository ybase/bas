package com.ybase.dorm.servlet;

import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractAjaxDispatch;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

public class TopAddController extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUser usr = getLoginUsrObj();
		if (usr == null) {
			setErrStatus();
			setTip("��δ��½ϵͳ");
			return;
		}

		String topDesc = getPar("topDesc");
		if (BasUtil.isNullOrEmpty(topDesc)) {
			setErrStatus();
			setTip("����Ϊ��");
			return;
		}
		
		String topName = getPar("topName");
		if (BasUtil.isNullOrEmpty(topName)) {
			setErrStatus();
			setTip("���Ϊ��");
			return;
		}

		DrTop top = new DrTop();
		top.setTopDesc(topDesc.trim());
		top.setName(topName.trim());
		top.setYesCount(0);
		top.setNoCount(0);
		setCommonVO(top);
		DrTopManager topManager = ServiceFactory.getTopManager();
		if (!topManager.addDrTop(top)) {
			setErrStatus();
			setTip("���ʧ��");
			return;
		}

		setSuccStatus();
		setTip("��ӳɹ�");
	}

}
