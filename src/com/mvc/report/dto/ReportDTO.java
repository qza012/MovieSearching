package com.mvc.report.dto;

import java.sql.Date;

public class ReportDTO {
	private int idx;
	private String report_id;
	private int report_idx;
	private String content;
	private Date reg_date;
	private int type_idx;
	private String complete;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public int getReport_idx() {
		return report_idx;
	}
	public void setReport_idx(int report_idx) {
		this.report_idx = report_idx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getType_idx() {
		return type_idx;
	}
	public void setType_idx(int type_idx) {
		this.type_idx = type_idx;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	

}
