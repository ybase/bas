package com.ybase.dorm.servlet;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasErrCode;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractAjaxDispatch;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

/**
 * ����/��Talk<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-27<br/>
 * 
 */
public class YnTalkServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrTalkManager talkDAO = ServiceFactory.getTalkManager();
		setJsonMap("status", "0");

		String talkId = getPar("talkId");
		String type = getPar("type");
		try {
			if (BasUtil.isNullOrEmpty(talkId) || BasUtil.isNullOrEmpty(type)) {
				throw new BasException(BasErrCode.E0009);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new BasException(BasErrCode.E0005);
			}

			if ("Y".equals(type.trim())) {
				if (!talkDAO.updateY(Integer.valueOf(talkId), loginUsr)) {
					throw new BasException(BasErrCode.D0007);
				}
			} else {
				if (!talkDAO.updateN(Integer.valueOf(talkId), loginUsr)) {
					throw new BasException(BasErrCode.D0007);
				}
			}

			DrTalk talk = talkDAO.queryTalkById(Integer.valueOf(talkId));
			if (talk == null) {
				throw new BasException(BasErrCode.D0002);
			} else {
				setJsonMap("show1", talk.getYesCount());
				setJsonMap("show2", talk.getNoCount());
			}

			setJsonMap("status", "1");
			setJsonMap("tip", "����/�����ɹ�");
		} catch (BasException e) {
			if (BasErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "δ��¼ϵͳ");
			} else if (BasErrCode.E0009.equals(e.getMessage().trim())) {
				setJsonMap("tip", "ϵͳ����");
			} else {
				setJsonMap("tip", "ÿ��ÿ���ֻ�ܵ��ޡ��²�һ��");
			}
		} catch (Exception e) {
			setJsonMap("tip", "����/����ʧ��");
			log.error(e.getMessage(), e);
		}
	}

}
