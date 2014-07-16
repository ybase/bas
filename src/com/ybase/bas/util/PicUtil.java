package com.ybase.bas.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.ybase.bas.vo.PicVO;

/**
 * 图片处理工具类<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class PicUtil {

	private static final Logger log = Logger.getLogger(PicUtil.class.getName());

	// 图片处理
	public static boolean compressPic(PicVO picVO) {
		try {
			// 获得源文件
			File file = new File(picVO.getInputDir() + picVO.getInputFileName());
			picVO.setFile(file);
			if (!file.exists()) {
				return false;
			}

			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				log.debug(" can't read,retry!" + "<BR>");
				return false;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (picVO.isProportion()) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null)) / (double) picVO.getOutputWidth() + 0.1;
					double rate2 = ((double) img.getHeight(null)) / (double) picVO.getOutputHeight() + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = picVO.getOutputWidth(); // 输出的图片宽度
					newHeight = picVO.getOutputHeight(); // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(picVO.getOutputDir() + picVO.getOutputFileName());
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
		return true;
	}

}
