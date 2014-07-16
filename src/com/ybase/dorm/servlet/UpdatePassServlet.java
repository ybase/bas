package com.ybase.dorm.servlet;

import com.ybase.bas.BasException;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractCommonDispatch;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * �޸�����<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-29
 * 
 */
public class UpdatePassServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUserManager userDAO = ServiceFactory.getUserManager();
		CommonService commonService = ServiceFactory.getCommonService();
		DrUser user = getLoginUsrObj();
		try {
			if (user == null) {
				throw new BasException("ϵͳδ��¼");
			}

			String oldPass = getPar("oldpass");
			if (BasUtil.isNullOrEmpty(oldPass)) {
				throw new BasException("����д������");
			}

			String newPass = getPar("newpass");
			if (BasUtil.isNullOrEmpty(newPass)) {
				throw new BasException("����д������");
			}

			String cfPass = getPar("cfpass");
			if (BasUtil.isNullOrEmpty(cfPass)) {
				throw new BasException("����дȷ������");
			}

			if (!cfPass.equals(newPass)) {
				throw new BasException("ȷ���������");
			}

			if (!user.getPasswd().equals(MD5EncrytUtil.md5Encrypt(oldPass.trim()))) {
				throw new BasException("���������");
			}

			if (!userDAO.updatePass(user.getId(), newPass)) {
				throw new BasException("�����޸�ʧ��");
			}

			DrTop top = commonService.getOneRandomTop(1, true);
			setReqAttr("top", top);

			setTip("�����޸ĳɹ�");
			setCrrStatus();
		} catch (BasException e) {
			setTip(e.getMessage());
		} catch (Exception ex) {
			setTip("ϵͳ����");
		}
		setDUrl("passwdDorm.jsp");
	}
}
