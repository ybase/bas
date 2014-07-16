package com.ybase.dorm.test;

import org.junit.Test;

import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.manger.impl.DrUserManagerImpl;
import com.ybase.dorm.vo.DrUser;

public class TestUserDAO {

	private DrUserManager userDAO = new DrUserManagerImpl();
	
	@Test
	public void testAddUser(){
		DrUser user = new DrUser();
		user.setName("������");
		user.setPasswd("1");
		user.setEmail("8888@qq.com");
		user.setPhone("19902223");
		user.setAddress("�����ϲ�");
		user.setVisit(0);
		user.setStatus(Integer.valueOf(DormConstant.DR_USER_STATUS_0));
		user.setLoginDate(BasUtil.getDate8Str());
		user.setLoginTime(BasUtil.getTime9Str());

		userDAO.addDrUser(user);
	}
	
	@Test
	public void testQueryDrUserById(){
		DrUser user = userDAO.queryDrUserById(5);
		System.out.println("Result:" + user.getName());
	}
	
	@Test
	public void testUpdateStatus(){
		try {
			System.out.println(userDAO.updateStatus(10, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateVisit(){
		try {
			System.out.println(userDAO.updateVisit(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateVDT(){
		try {
			System.out.println(userDAO.updateVDTS(11));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryUsrByEmail(){
		try {
			DrUser usr = userDAO.queryUsrByEmail(null);
			System.out.println(usr.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdatePasswd(){
		try {
			System.out.println(userDAO.updatePass(419009, "password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
