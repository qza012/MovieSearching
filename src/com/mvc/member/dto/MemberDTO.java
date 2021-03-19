package com.mvc.member.dto;

import java.sql.Date;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private int age;
	private String gender;
	private String email;
	private String genre;
	private String pw_answer;
	private String withdraw;
	private String disable;
	private String type;
	private int question_idx;
	private int follow_check;
	private Date reg_date;
	
	//프로필 사진
	private int idx = 0;
	private String oriFileName = null;
	private String newFileName = null;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getPw_answer() {
		return pw_answer;
	}
	public void setPw_answer(String pw_answer) {
		this.pw_answer = pw_answer;
	}
	public String getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQuestion_idx() {
		return question_idx;
	}
	public void setQuestion_idx(int question_idx) {
		this.question_idx = question_idx;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getOriFileName() {
		return oriFileName;
	}
	public void setOriFileName(String oriFileName) {
		this.oriFileName = oriFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	public int getFollow_check() {
		return follow_check;
	}
	public void setFollow_check(int follow_check) {
		this.follow_check = follow_check;
	}
	
}
