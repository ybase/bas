package com.ybase.dorm.vo;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.vo.BasVO;

/**
 * Ȥζ����<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_top")
public class DrTop extends BasVO {

	/** ��� */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** Ȥζ�ƺ� */
	@Column(name = "name", type = "string")
	private String name;

	/** Ȥζ���� */
	@Column(name = "topdesc", type = "string")
	private String topDesc;

	/** �������� */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** ����ʱ�� */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** ������ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;
	
	/** ���޴��� */
	@Column(name = "yescount", type = "int")
	private Integer yesCount;

	/** �²۴��� */
	@Column(name = "nocount", type = "int")
	private Integer noCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopDesc() {
		return topDesc;
	}

	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
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

	public Integer getCrUsr() {
		return crUsr;
	}

	public void setCrUsr(Integer crUsr) {
		this.crUsr = crUsr;
	}

	public Integer getYesCount() {
		return yesCount;
	}

	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	public Integer getNoCount() {
		return noCount;
	}

	public void setNoCount(Integer noCount) {
		this.noCount = noCount;
	}

	@Override
	public String toString() {
		return "DrTop [id=" + id + ", name=" + name + ", topDesc=" + topDesc + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", yesCount=" + yesCount + ", noCount=" + noCount
				+ "]";
	}

}
