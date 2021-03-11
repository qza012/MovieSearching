package com.mvc.comment.dto;

import java.util.Date;

public class CommentDTO {
	private int idx;
	private String id;
	private int review_idx;
	private String content;
	private Date reg_date;
	private String del_type;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReview_idx() {
		return review_idx;
	}
	public void setReview_idx(int review_idx) {
		this.review_idx = review_idx;
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
	public String getDel_type() {
		return del_type;
	}
	public void setDel_type(String del_type) {
		this.del_type = del_type;
	}
	
	
}
