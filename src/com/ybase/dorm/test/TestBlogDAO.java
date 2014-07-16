package com.ybase.dorm.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.ybase.bas.BasException;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.impl.DrBlogManagerImpl;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

public class TestBlogDAO {

	private DrBlogManager blogDAO = new DrBlogManagerImpl();

	@Test
	public void testAddBlog() throws BasException {
		DrBlog blog = new DrBlog();
		blog.setBlogDesc("��������17����������2���������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡�������⣡");
		blog.setCrDate(BasUtil.getDate8Str());
		blog.setCrTime(BasUtil.getTime9Str());
		blog.setTheme("��������17");
		blog.setCrUsr(1);
		blog.setUsrName("������");
		blog.setImgId(1);
		blog.setYesCount(0);
		System.out.println(blogDAO.addDrBlog(blog));
	}

	@Test
	public void testQueryDrBlogById() throws BasException {
		DrBlog blog = null;
		blog = blogDAO.queryDrBlogById(1);
		System.out.println(blog.getBlogDesc());
	}

	@Test
	public void testUpdateYesCount() throws BasException {
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("������");
		System.out.println(blogDAO.updateYesCount(1, usr));
	}

	@Test
	public void testQueryBlogByImageId() throws BasException {
		Map<String, Object> map = null;
		map = blogDAO.queryBlogByImageId(1);
		Set<String> keys = map.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + " = " + map.get(key));
		}
		System.out.println(map.size());
	}

	@Test
	public void testQueryTopFiveBlog() throws BasException {
		// Map<String, Object> map = null;
		// map = blogDAO.queryTopFiveBlog();
		// Set<String> keys = map.keySet();
		// Iterator<String> iter = keys.iterator();
		// while (iter.hasNext()) {
		// String key = iter.next();
		// System.out.println(key + " ======================= ");
		// @SuppressWarnings("unchecked")
		// Map<String, Object> sub = (Map<String, Object>) map.get(key);
		// Set<String> subKeys = sub.keySet();
		// Iterator<String> subIter = subKeys.iterator();
		// while (subIter.hasNext()) {
		// String subKey = subIter.next();
		// System.out.println(subKey + " = " + sub.get(subKey));
		// }
		// }
		// System.out.println(map.size());
	}

	@Test
	public void testPageAllBlog() throws BasException {
		Map<String, Object> maps = null;
		Page page = new Page();
		page.setCurrent(1);
		maps = blogDAO.pageAllBlog(page);
		Set<String> keys = maps.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object obj = maps.get(key);
			if (obj instanceof Page) {
				page = (Page) obj;
				System.out.println(page);
			} else if (obj instanceof List) {
				@SuppressWarnings("unchecked")
				List<DrBlog> list = (List<DrBlog>) obj;
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getCrTime() + " - " + list.get(i).getBlogDesc());
				}
			}
			System.out.println(key);
		}
		System.out.println(maps.keySet().size());
	}

	@Test
	public void testPageAllBlogWrapTalk() throws BasException {
		Map<String, Object> maps = null;
		Page page = new Page();
		page.setCurrent(1);
		maps = blogDAO.pageAllBlogWrapTalk(page);

		Set<String> keys = maps.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + "=================================");
			Object obj = maps.get(key);
			if (obj instanceof Page) {
				page = (Page) obj;
				System.out.println(page);
			} else if (obj instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> subMap = (Map<String, Object>) obj;

				Set<String> subKeys = subMap.keySet();
				Iterator<String> subIter = subKeys.iterator();
				while (subIter.hasNext()) {
					String subKey = subIter.next();
					System.out.println(subKey + "+++++++++++++++++++++++++++++");
					Object subObj = subMap.get(subKey);
					if (subObj instanceof DrBlog) {
						DrBlog blog = (DrBlog) subObj;
						System.out.println(blog);
					} else if (subObj instanceof List) {
						@SuppressWarnings("unchecked")
						List<DrTalk> talks = (List<DrTalk>) subObj;
						for (int i = 0; i < talks.size(); i++) {
							System.out.println(talks.get(i));
						}
					}
				}
			}
			System.out.println(maps.keySet().size());
		}
	}
}
