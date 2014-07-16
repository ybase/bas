package com.ybase.dorm.vo;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.vo.BasVO;

/**
 * �ƻ�<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-7-2
 */
@Table("dr_plan")
public class DrPlan extends BasVO {

	/** ��� */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** �ƻ����� */
	@Column(name = "plandesc", type = "string")
	private String planDesc;

	/** �������� */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** ����ʱ�� */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** ������ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;

	/** ������name */
	@Column(name = "usrname", type = "string")
	private String usrName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlanDesc() {
		return planDesc;
	}

	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
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

	@Override
	public String toString() {
		return "DrPlan [id=" + id + ", planDesc=" + planDesc + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", usrName=" + usrName + "]";
	}

}
