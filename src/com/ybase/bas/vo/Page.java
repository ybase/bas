package com.ybase.bas.vo;

/**
 * 分页实体VO<br/>
 *
 * @bas_V1.0, yangxb, 2014-7-16<br/>
 */
public class Page {

	/** 当前页码 */
	private int current;

	/** 当前显示第一页 */
	private int first;

	/** 当前显示第一条记录 */
	private int firstRecord;

	/** 当前显示最后一条记录 */
	private int lastRecord;

	/** 当前显示尾页 */
	private int last;

	/** 总条数 */
	private int totalRecord;

	/** 总页数 */
	private int totalPage;

	/** 每页显示条数 */
	private int pageRecord = 15;

	/** 每次显示页数 */
	private int pageRow = 7;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
	}

	public int getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(int firstRecord) {
		this.firstRecord = firstRecord;
	}

	public int getLastRecord() {
		return lastRecord;
	}

	public void setLastRecord(int lastRecord) {
		this.lastRecord = lastRecord;
	}

	/**
	 * 分页对象初始化<br/>
	 * 第一次调用分页对象，进行分页时，将将进行对象的初始化<br/>
	 * 后续使用分页对象时，需要将分页对象的前last,first 状态保存，<br/>
	 * 并用于后一个分页对象做判断使用。<br/>
	 * 
	 * @bas_V1.0, yangxb, 2014-7-16<br/>
	 */
	public void initPage() {
		int pageC = this.totalRecord / this.pageRecord;
		int pageY = this.totalRecord % this.pageRecord;
		if (pageY != 0) {
			this.totalPage = pageC + 1;
		} else {
			this.totalPage = pageC;
		}

		if (this.current == 1 || this.current > this.totalPage) {
			this.current = 1;
			this.first = 1;
			this.firstRecord = 0;
			if (totalPage <= pageRow) {
				this.last = this.totalPage;
				if (this.pageRecord >= this.totalRecord) {
					this.lastRecord = this.totalRecord - 1;
				} else {
					this.lastRecord = this.pageRecord - 1;
				}
			} else {
				this.last = this.pageRow;
				this.lastRecord = this.pageRecord - 1;
			}
		} else {
			if (this.current == this.last) {
				if ((this.current + this.pageRow - 2) > this.totalPage) {
					this.last = this.totalPage;
					if (this.totalPage > this.pageRow) {
						this.first = this.current - 1;
					}

					this.firstRecord = (this.current - 1) * this.pageRecord;
					if (this.current != this.totalPage) {
						this.lastRecord = this.firstRecord + this.pageRecord - 1;
					} else {
						this.lastRecord = this.totalRecord - 1;
					}
				} else {
					this.first = this.current - 1;
					this.last = this.current + this.pageRow - 1;
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				}
			} else if (this.current == this.first) {
				if (this.current + 1 - (this.pageRow - 1) >= 1) {
					this.last = this.current + 1;
					this.first = this.last - (this.pageRow - 1);
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				} else {
					this.last = this.pageRow;
					this.first = 1;
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				}
			} else {
				this.firstRecord = (this.current - 1) * this.pageRecord;
				this.lastRecord = this.firstRecord + this.pageRecord - 1;
				if (this.totalPage - this.first + 1 >= this.pageRow) {
					this.last = this.first - 1 + this.pageRow;
				} else {
					this.last = this.totalPage;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Page [current=" + current + ", first=" + first + ", firstRecord=" + firstRecord + ", lastRecord=" + lastRecord + ", last=" + last + ", totalRecord=" + totalRecord + ", totalPage=" + totalPage
				+ ", pageRecord=" + pageRecord + ", pageRow=" + pageRow + "]";
	}

}
