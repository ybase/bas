package com.ybase.dorm.servlet;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ybase.bas.BasException;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.web.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrUser;

/**
 * �������<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class AddBlogServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;
	private static final String[] PIC_FORMAT = { "jpg", "gif", "png", "bmp", "jpeg" };

	@Override
	public void process() throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(wrappReq.get());
		DrBlogManager blogDAO = ServiceFactory.getBlogManger();
		DrImageManager imageDAO = ServiceFactory.getImageManager();
		CommonService commonService = ServiceFactory.getCommonService();
		if (isMultipart) {
			try {
				// ��ô����ļ���Ŀ����
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				DrBlog blog = new DrBlog();
				DrImage image = new DrImage();
				DrUser usr = (DrUser) getAttr(BasConstants.SCOPE_SESSION, "loginusr");
				if (usr != null && usr.getId() > 0) {
					blog.setCrUsr(usr.getId());
					blog.setUsrName(usr.getName());
				} else {
					throw new BasException("���¼ϵͳ");
				}

				Iterator<?> items = upload.parseRequest(wrappReq.get()).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// ȡ���ϴ��ļ����ļ����
						String name = item.getName();
						String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());
						if (!checkPicFormat(fileName)) {
							throw new BasException("�ϴ�ͼƬ��ʽ����(jpg,jpeg,png,gif,bmp)");
						}

						@SuppressWarnings("deprecation")
						String path = wrappReq.get().getRealPath(DormConstant.SYS_UPLOAD_FILE);
						// �ϴ��ļ�
						factory.setRepository(new File(path));
						// ���� ����Ĵ�С�����ϴ��ļ�����������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��
						factory.setSizeThreshold(1024 * 1024);

						String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
						String uploadFilePath = path + File.separatorChar + getFileName(uuidName, fileName);
						String backFilePath = DormConstant.SYS_UPLOAD_FILE_BAK + getFileName(uuidName, fileName);
						File uploadedFile = new File(uploadFilePath);
						item.write(uploadedFile);
						BasUtil.fileChannelCopy(uploadedFile, new File(backFilePath));

						image.setCrDate(BasUtil.getDate8Str());
						image.setCrTime(BasUtil.getTime9Str());
						image.setPosition(DormConstant.DR_IMAGE_O);
						image.setPicPath(getFileName(uuidName, fileName));
						blog.setPicPath(getFileName(uuidName, fileName));

						commonService.createMidSmallPic(path, uuidName, getFileExpand(fileName));
						// ��ӡ�ϴ��ɹ���Ϣ
					} else {
						// ��ü��������
						String fieldName = item.getFieldName();
						// ��ü����ֵ
						String fieldValue = BasUtil.isoToG180(item.getString());

						if ("name".equals(fieldName)) {
							blog.setTheme(fieldValue);
						} else if ("message".equals(fieldName)) {
							blog.setBlogDesc(fieldValue);
						}
					}
				}

				if (imageDAO.addDrImage(image)) {
					blog.setCrDate(BasUtil.getDate8Str());
					blog.setCrTime(BasUtil.getTime9Str());
					blog.setImgId(image.getId());
					if (!blogDAO.addDrBlog(blog)) {
						throw new BasException("��������ʧ��");
					}
				} else {
					throw new BasException("��������ʧ��");
				}

				setAttr(DormConstant.SCOPE_REQ, "prevText", "�����ѷ���");
			} catch (BasException de) {
				setAttr(DormConstant.SCOPE_REQ, "prevText", de.getMessage());
			} catch (Exception e) {
				setAttr(DormConstant.SCOPE_REQ, "prevText", "��ʱ�޷���������");
			}
		} else {
			setAttr(DormConstant.SCOPE_REQ, "prevText", "��������");
		}
		setDUrl("dormContact");
	}

	private String getFileName(String uuidName, String fileName) {
		StringBuffer nameStr = new StringBuffer(uuidName);
		if (fileName.indexOf(".") != -1) {
			nameStr.append(".").append(fileName.split("\\.")[1]);
		}
		return nameStr.toString();
	}

	private String getFileExpand(String fileName) {
		if (!BasUtil.isNullOrEmpty(fileName) && fileName.indexOf(".") != -1) {
			return fileName.split("\\.")[1];
		}
		return "jpg";
	}

	private boolean checkPicFormat(String fileName) {
		if (fileName.indexOf(".") != -1) {
			String[] fileStr = fileName.split("\\.");
			if (Arrays.asList(PIC_FORMAT).contains(fileStr[1])) {
				return true;
			}
		}
		return false;
	}
}
