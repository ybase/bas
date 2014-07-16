package com.ybase.dorm.test;

import org.junit.Test;

import com.ybase.bas.BasException;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.manger.impl.DrRecordManagerImpl;
import com.ybase.dorm.vo.DrRecord;

public class TestRecordDAO {

	private DrRecordManager recordDAO = new DrRecordManagerImpl();

	@Test
	public void testAddDrRecord() {
		DrRecord record = new DrRecord();
		record.setDrType(0);
		record.setRelId(1);
		record.setCrDate("20140529");
		record.setCrTime("120000000");
		record.setCrUsr(419005);
		record.setUsrName("������");
		System.out.println(recordDAO.addDrRecord(record));
	}

	@Test
	public void testQueryRecordByUsrDtTp() {
		try {
			System.out.println(recordDAO.queryRecordByUsrDtTp(419005, "20140529", 0, 1));
		} catch (BasException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryListRecordByUsrDtTp() {
		try {
			System.out.println(recordDAO.queryListRecordByUsrDtTp(419005, "20140529", 0, 1));
		} catch (BasException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExistRecordByUsrDtTp() {
		try {
			System.out.println(recordDAO.existRecordByUsrDtTp(419005, "20140529", 0, 0));
		} catch (BasException e) {
			e.printStackTrace();
		}
	}
}
