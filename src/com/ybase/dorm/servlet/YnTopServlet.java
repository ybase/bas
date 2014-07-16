package com.ybase.dorm.servlet;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractAjaxDispatch;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * ����/�²�Top<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-27<br/>
 * 
 */
public class YnTopServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrTopManager topDAO = ServiceFactory.getTopManager();
		setJsonMap("status", "0");

		String topId = getPar("topId");
		String type = getPar("type");
		try {
			if (BasUtil.isNullOrEmpty(topId) || BasUtil.isNullOrEmpty(type)) {
				throw new BasException(BasErrCode.E0009);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new BasException(BasErrCode.E0005);
			}

			if ("Y".equals(type.trim())) {
				if (!topDAO.updateY(Integer.valueOf(topId), loginUsr)) {
					throw new BasException(BasErrCode.D0007);
				}
			} else {
				if (!topDAO.updateN(Integer.valueOf(topId), loginUsr)) {
					throw new BasException(BasErrCode.D0007);
				}
			}

			DrTop top = topDAO.queryRandomTop(Integer.valueOf(topId));
			if (top == null) {
				throw new BasException(BasErrCode.D0002);
			} else {
				setJsonMap("show1", top.getYesCount());
				setJsonMap("show2", top.getNoCount());
			}

			setJsonMap("status", "1");
			setJsonMap("tip", "����/�²۳ɹ�");
		} catch (BasException e) {
			if (BasErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "δ��¼ϵͳ");
			} else if (BasErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "ϵͳ����");
			} else {
				setJsonMap("tip", "ÿ��ÿ���ֻ�ܵ��޻��²�һ��");
			}
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "����/�²�ʧ��");
			log.error(e.getMessage(), e);
		}

	}

}
