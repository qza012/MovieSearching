package com.mvc.admin.util;

public enum AdminSql {
	// del_type
	UPDATE_COMMENT_DELTYPE("UPDATE comment3 SET del_type=? WHERE idx=?")
	,UPDATE_REVIEW_DELTYPE("UPDATE review3 SET del_type=? WHERE idx=?")
	,UPDATE_REPORT_COMPLETE("UPDATE report3 SET complete=? WHERE idx=?")
	;

	private final String value;
	
	AdminSql(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
