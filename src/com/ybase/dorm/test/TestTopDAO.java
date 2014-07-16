package com.ybase.dorm.test;

import org.junit.Test;

import com.ybase.bas.util.BasUtil;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.impl.DrTopManagerImpl;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

public class TestTopDAO {

	private DrTopManager topDAO = new DrTopManagerImpl();

	@Test
	public void testAddTop() {
		DrTop top = new DrTop();
		top.setCrDate(BasUtil.getDate8Str());
		top.setCrTime(BasUtil.getTime9Str());
		top.setCrUsr(2);
		top.setName("���̷ϲĲٵ�");
		top.setNoCount(0);
		top.setYesCount(0);
		top.setTopDesc("������˺ü��죬޹�˺ü��죬�ܲ����ˣ�����������");
		System.out.println(topDAO.addDrTop(top));
	}

	@Test
	public void testUpdateY() {
		DrUser usr = new DrUser();
		usr.setId(419004);
		usr.setName("����Ա");
		System.out.println(topDAO.updateY(20, usr));
	}

	@Test
	public void testUpdateN() {
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("������");
		System.out.println(topDAO.updateN(1, usr));
	}

	@Test
	public void testQueryRandomTop() {
		System.out.println(topDAO.queryRandomTop(1));
	}

	@Test
	public void testQueryIds() {
		System.out.println(topDAO.queryIds().get(2));
	}
}
