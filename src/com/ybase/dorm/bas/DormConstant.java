package com.ybase.dorm.bas;

/*
 * 用户<br/>
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
public class DormConstant {

	/** 系统用户状态 */
	public static final String DR_USER_STATUS = "status";

	/** 系统用户状态 0-未登录 */
	public static final String DR_USER_STATUS_0 = "0";

	/** 系统用户状态 1-已登录 */
	public static final String DR_USER_STATUS_1 = "1";

	/** 系统用户状态 2-停用 */
	public static final String DR_USER_STATUS_2 = "2";
	public static final String[] DR_USER_STATU_ARR={DR_USER_STATUS_0,DR_USER_STATUS_1,DR_USER_STATUS_2};
	

	/** 系统用户角色 admin-管理员 */
	public static final String DR_USER_ROLE_ADMIN = "admin";
	
	/** 图片系统位置 C-经典照片 */
	public static final String DR_IMAGE_C = "C";
	
	/** 图片系统位置 O-其他 */
	public static final String DR_IMAGE_O = "O";
	
	/** 图片上传应用位置 */
	public static final String SYS_UPLOAD_FILE = "/images/upload/";
	
	/** 图片备份位置 */
	public static final String SYS_UPLOAD_FILE_BAK = "E:\\upload\\";
	
	/** 点赞/吐槽范围 0-BLOG */
	public static final String DR_RECORD_TYPE_0 = "0";
	
	/** 点赞/吐槽范围 1-IMAGE */
	public static final String DR_RECORD_TYPE_1 = "1";
	
	/** 点赞/吐槽范围 2-TALK */
	public static final String DR_RECORD_TYPE_2 = "2";
	
	/** 点赞/吐槽范围 3-TOP */
	public static final String DR_RECORD_TYPE_3 = "3";
	
}
