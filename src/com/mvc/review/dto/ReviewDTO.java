package com.mvc.review.dto;

import java.util.Date;

public class ReviewDTO {
	private int idx;
	private String id;
	private String movieCode;
	private String posterURL;
	private String movieName;
	private String subject;
	private String content;
	private int score;
	private Date reg_date;
	private String del_type;
	private int cntLike;
	
	public String getPosterURL() {
		return posterURL;
	}
	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getCntLike() {
		return cntLike;
	}
	public void setCntLike(int cntLike) {
		this.cntLike = cntLike;
	}
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
	public String getMovieCode() {
		return movieCode;
	}
	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
