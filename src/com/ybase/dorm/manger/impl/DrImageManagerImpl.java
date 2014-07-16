package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ybase.bas.BasException;
import com.ybase.bas.annotation.Service;
import com.ybase.bas.constants.BasConstants;
import com.ybase.bas.jdbc.JdbcEntityDaoTemplate;
import com.ybase.bas.util.BasUtil;
import com.ybase.bas.vo.Page;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrUser;

public class DrImageManagerImpl extends JdbcEntityDaoTemplate implements DrImageManager {

	@Service(value = "imageManager")
	private DrRecordManager recordDAO;

	@Override
	public boolean addDrImage(DrImage image) throws BasException {
		boolean flag = false;

		if (BasUtil.isNullOrEmpty(image.getPicPath())) {
			throw new BasException("DrImage.picPath�ֶ�Ϊ��!");
		}

		try {
			image = addVO(image);
			if (image.getId() != null) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(e.getMessage());
		}

		return flag;
	}

	@Override
	public DrImage queryDrImageById(Integer id) throws BasException {
		if (id != null) {
			throw new BasException("��ѯ�ֶ�idΪ��!");
		} else {
			Map<String, Object> props = new HashMap<String, Object>();
			props.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
			List<DrImage> list;
			try {
				list = executeQuery(DrImage.class, props);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BasException(e.getMessage());
			}
			return BasUtil.getOne(list);
		}
	}

	@Override
	public List<DrImage> queryAllDrImage() {
		Map<String, String> sort = new TreeMap<String, String>();
		sort.put("crTime", BasConstants.SQL_SORT_DESC);
		sort.put("crDate", BasConstants.SQL_SORT_DESC);
		List<DrImage> list = null;
		try {
			list = executeQuery(DrImage.class, null, sort);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	private int countAllDrImage() {
		try {
			return executeCountQuery(DrImage.class, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public Map<String, Object> pageAllDrImage(Page page) throws BasException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new BasException("page ����Ϊ��!");
			}

			page.setTotalRecord(countAllDrImage());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			List<DrImage> list = executeQuery(DrImage.class, null, page, sort);

			rsMap.put("image", list);
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

	@Override
	public List<DrImage> queryIndex4DrImage() {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			return executeQuery(DrImage.class, null, sort, 4);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrImage> queryClassic10DrImage() {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			sort.put("yesCount", BasConstants.SQL_SORT_DESC);

			Map<String, Object> par = new HashMap<String, Object>();
			par.put("position_Eq", new Object[] { DormConstant.DR_IMAGE_C, BasConstants.SQL_AND });
			List<DrImage> images = executeQuery(DrImage.class, par, sort, 10);
			if (!BasUtil.isNullOrEmpty(images)) {
				List<DrImage> other = null;
				if (images.size() < 10) {
					other = queryTopXDrNonCImage(10 - images.size());
				}
				images.addAll(other);
			} else {
				return queryTopXDrNonCImage(10);
			}
			return images;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private List<DrImage> queryTopXDrNonCImage(int x) {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);
			sort.put("yesCount", BasConstants.SQL_SORT_DESC);

			Map<String, Object> par = new HashMap<String, Object>();
			par.put("position_Nq", new Object[] { DormConstant.DR_IMAGE_C, BasConstants.SQL_AND });
			return executeQuery(DrImage.class, par, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrImage> queryTopXDrImage(int x) {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", BasConstants.SQL_SORT_DESC);
			sort.put("crDate", BasConstants.SQL_SORT_DESC);

			return executeQuery(DrImage.class, null, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updateY(Integer id, DrUser usr) throws BasException {
		boolean flag = false;

		try {
			if (usr == null) {
				throw new BasException("updateY ����[usr] Ϊ��!");
			}

			if (id == null) {
				log.error("updateY ����[id] Ϊ��!");
				throw new BasException("updateY ����[id] Ϊ��!");
			}

			Map<String, Object> updateProps = new HashMap<String, Object>();
			updateProps.put("yesCount", "yescount+1");

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, BasConstants.SQL_AND });
			int rows = update(DrImage.class, updateProps, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), BasUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_1), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(BasUtil.getDate8Str());
					record.setCrTime(BasUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_1));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					throw new BasException(String.format("��ǰ�û�[%s]�ѵ��޹�ǰͼƬ[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BasException(e);
		}
		return flag;
	}
}
