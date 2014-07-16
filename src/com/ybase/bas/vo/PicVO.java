package com.ybase.bas.vo;

import java.io.File;

/**
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 <br/>
 * 具体使用方法->compressPic<br/>
 * (大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class PicVO {
	/** 文件对象 */
	private File file = null;
	/** 输入图路径 */
	private String inputDir;
	/** 输出图路径 */
	private String outputDir;
	/** 输入图文件名 */
	private String inputFileName;
	/** 输出图文件名 */
	private String outputFileName;
	/** 默认输出图片宽 */
	private int outputWidth = 100;
	/** 默认输出图片高 */
	private int outputHeight = 100;
	/** 是否等比缩放标记(默认为等比缩放) */
	private boolean proportion = true;

	public PicVO() { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}

	public PicVO(int type) { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		proportion = false;
		if (type == 1) {
			outputWidth = 960;
			outputHeight = 400;
		} else if (type == 2) {
			outputWidth = 700;
			outputHeight = 520;
		} else if (type == 3) {
			outputWidth = 60;
			outputHeight = 60;
		} else {
			outputWidth = 100;
			outputHeight = 100;
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public int getOutputWidth() {
		return outputWidth;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public int getOutputHeight() {
		return outputHeight;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}

	public boolean isProportion() {
		return proportion;
	}

	public void setProportion(boolean proportion) {
		this.proportion = proportion;
	}

	/*
	 * 获得图片大小 传入参数 String path ：图片路径
	 */
	public long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}
}
