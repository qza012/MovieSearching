package com.mvc.admin.util;

public enum AdminSql {
	// del_type
	UPDATE_COMMENT_DELTYPE("UPDATE comment3 SET del_type=? WHERE idx=?")
	,UPDATE_REVIEW_DELTYPE("UPDATE review3 SET del_type=? WHERE idx=?")
	,UPDATE_REPORT_COMPLETE("UPDATE report3 SET complete=? WHERE idx=?")
	
	// member3
	,MEMBER_TABLE("member3")
	,MEMBER_COLUMNS("id, pw, name, age, gender, email, genre, pw_answer, withdraw, disable, type, question_idx")
	
	// question3
	,QUESTION_TABLE("question3")
	,QUESTION_COLUMNS("idx, content")
	
	// review3
	,REVIEW_TABLE("review3")
	,REVIEW_COLUMNS("idx, id, movieCode, subject, content, reg_date, del_type")
	
	// comment3
	,COMMENT_TABLE("comment3")
	,COMMENT_COLUMNS("idx, id, review_idx, content, reg_date, del_type")
	
	// report3
	,REPORT_TABLE("report3")
	,REPORT_COLUMNS("idx, report_id, report_idx, content, reg_date, type_idx, complete")
	
	// movie3
	,MOVIE_TABLE("movie3")
	,MOVIE_COLUMNS("movieCode, movieName, openDate, genre, director, country, actors, grade, youtubeUrl, posterUrl")
	
	;

	private final String value;
	
	AdminSql(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
