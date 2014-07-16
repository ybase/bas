package com.ybase.dorm.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.ybase.bas.BasException;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.manger.impl.DrImageManagerImpl;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrUser;

public class TestImageDAO {

	private DrImageManager imageDAO = new DrImageManagerImpl();

	@Test
	public void testAddImage() {
		DrImage image = new DrImage();
		image.setCrDate(BasUtil.getDate8Str());
		image.setCrTime(BasUtil.getTime9Str());
		image.setPicPath("/images/upload/img0032");
		image.setPosition(DormConstant.DR_IMAGE_C);
		image.setYesCount(0);
		try {
			System.out.println(imageDAO.addDrImage(image));
			System.out.println(image.getId());
		} catch (BasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDrImageById() {
		DrImage image;
		try {
			image = imageDAO.queryDrImageById(3);
			System.out.println(image.getPicPath());
		} catch (BasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryAllDrImage() {
		List<DrImage> images = null;
		images = imageDAO.queryAllDrImage();
		for(int i = 0;i<images.size();i++){
			System.out.println(images.get(i).getCrTime());
		}
		System.out.println("Result size:" + images.size());
	}
	
	@Test
	public void testQueryIndex4DrImage(){
		List<DrImage> images = null;
		images = imageDAO.queryIndex4DrImage();
		for(int i = 0;i<images.size();i++){
			System.out.println(images.get(i).getCrTime());
		}
		System.out.println(images.size());
	}
	
	@Test
	public void testQueryClassic10DrImage(){
		List<DrImage> images = null;
		images = imageDAO.queryClassic10DrImage();
		for(int i = 0;i<images.size();i++){
			System.out.println(images.get(i).getCrTime() + " - " + images.get(i).getPosition());
		}
		System.out.println(images.size());
	}
	
	@Test
	public void testQueryTopXDrImage(){
		List<DrImage> images = null;
		images = imageDAO.queryTopXDrImage(0);
		for(int i = 0;i<images.size();i++){
			System.out.println(images.get(i).getCrTime() + " - " + images.get(i).getPosition());
		}
		System.out.println(images.size());
	}
	
	@Test
	public void testPageAllDrImage() throws BasException{
		Map<String,Object> maps = null;
		Page page = new Page();
		page.setCurrent(1);
		maps = imageDAO.pageAllDrImage(page);
		Set<String> keys = maps.keySet();
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()){
			String key = iter.next();
			Object obj = maps.get(key);
			if(obj instanceof Page){
				page = (Page)obj;
				System.out.println(page);
			}else if(obj instanceof List){
				@SuppressWarnings("unchecked")
				List<DrImage> list = (List<DrImage>)obj;
				for(int i = 0;i<list.size();i++){
					System.out.println(list.get(i).getCrTime() + " - " + list.get(i).getPosition());
				}
			}
			System.out.println(key);
		}
		System.out.println(maps.keySet().size());
	}
	
	@Test
	public void testUpdateY() throws BasException{
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("������");
		System.out.println(imageDAO.updateY(1, usr));
	}

}
