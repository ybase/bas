package com.ybase.dorm.vo;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.vo.BasVO;

/**
 * ����/�²ۼ�¼<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_record")
public class DrRecord extends BasVO {

	/** ��� */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** ���޷�Χ 0-BLOG 1-IMAGE 2-TALK 3-TOP */
	@Column(name = "drtype", type = "int")
	private Integer drType;

	/** ����Id */
	@Column(name = "relid", type = "int")
	private Integer relId;

	/** �������� */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** ����ʱ�� */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** �����û�ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;

	/** �����û���� */
	@Column(name = "usrname", type = "string")
	private String usrName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
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

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public Integer getRelId() {
		return relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	@Override
	public String toString() {
		return "DrRecord [id=" + id + ", drType=" + drType + ", relId=" + relId + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", usrName=" + usrName + "]";
	}

}
