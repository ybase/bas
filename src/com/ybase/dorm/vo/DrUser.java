package com.ybase.dorm.vo;

import com.ybase.bas.annotation.Column;
import com.ybase.bas.annotation.Table;
import com.ybase.bas.vo.BasVO;

/**
 * �û�<br/>
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_user")
public class DrUser extends BasVO {

	/** ��� */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** ���� */
	@Column(name = "passwd", type = "string")
	private String passwd;

	/** ��� */
	@Column(name = "name", type = "string")
	private String name;

	/** ���� */
	@Column(name = "email", type = "string")
	private String email;

	/** �绰 */
	@Column(name = "phone", type = "string")
	private String phone;

	/** ��ϵ��ַ */
	@Column(name = "address", type = "string")
	private String address;

	/** ����ϵͳ���� */
	@Column(name = "visit", type = "int")
	private Integer visit;

	/** �˺�״̬ 0:δ��¼ 1:�ѵ�¼ 2:ͣ�� */
	@Column(name = "status", type = "int")
	private Integer status;

	/** ϵͳ��ɫ admin-����Ա */
	@Column(name = "role", type = "string")
	private String role;

	/** ����¼ϵͳ���� */
	@Column(name = "logindate", type = "string")
	private String loginDate;

	/** ����¼ϵͳʱ�� */
	@Column(name = "logintime", type = "string")
	private String loginTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getVisit() {
		return visit;
	}

	public void setVisit(Integer visit) {
		this.visit = visit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

}
