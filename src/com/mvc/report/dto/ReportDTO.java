package com.mvc.report.dto;

public class ReportDTO {
	private int idx;
	private String reportId;
	private int reportIdx;
	private String content;
	private String regDate;
	private int typeIdx;
	private String complete;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public int getReportIdx() {
		return reportIdx;
	}
	public void setReportIdx(int reportIdx) {
		this.reportIdx = reportIdx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getTypeIdx() {
		return typeIdx;
	}
	public void setTypeIdx(int typeIdx) {
		this.typeIdx = typeIdx;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	
}
