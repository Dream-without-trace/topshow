package com.luwei.common.utils;

import java.io.Serializable;

/**
 * @author Administrator
 *	分页条件
 */
public class PageQuery implements Serializable {

	
	private String phone;//

	//分页查询的条件
	private Integer startRow;//起始行
	private Integer pageSize = 6;//每页显示条数
	private Integer pageNo = 1;//页码
	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
	this.startRow = (pageNo-1)*pageSize;
	this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.startRow = (pageNo-1)*pageSize;
		this.pageNo = pageNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
