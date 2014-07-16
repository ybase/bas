package com.ybase.dorm.vo;

import java.io.Serializable;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.vo.BasVO;

/**
 * ͼƬ<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_image")
public class DrImage extends BasVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ��� */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** ͼƬ·�� */
	@Column(name = "picpath", type = "string")
	private String picPath;

	/** �������� */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** ����ʱ�� */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** ���޴��� */
	@Column(name = "yescount", type = "int")
	private Integer yesCount = 0;

	/** ͼƬϵͳ����λ�� C:������Ƭ O:���� */
	@Column(name = "position", type = "string")
	private String position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	public String getCrTime() {
		return crTime;
	}

	public void setCrTime(String crTime) {
		this.crTime = crTime;
	}

	public Integer getYesCount() {
		return yesCount;
	}

	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
