package com.mvc.alarm.dto;

public class AlarmDTO {
	private int idx;
	private String targetId;
	private String regDate;
	private int typeIdx;
	private String content;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
