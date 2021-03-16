package com.mvc.admin.builder;

public class AdminQuery {
	private final String columns;				// 얻어올 컬럼 이름들
	private final String table;				// 테이블 이름
	private final String rnumSortColumn;		// row_number를 정렬할 기준
	private final String whereStandardColumn;	// where문에서 기준 컬럼
	private final String likeQuery;				// like문에서 사용할 쿼리문

	private AdminQuery(Builder builder) {
		this.columns = builder.columns;
		this.table = builder.table;
		this.rnumSortColumn = builder.rnumSortColumn;
		this.whereStandardColumn = builder.whereStandardColumn;
		this.likeQuery = builder.likeQuery;
	}
	
	public static class Builder {
		// 필수 인자.
		private final String columns;
		private final String table;
		private String rnumSortColumn = null;
		private String whereStandardColumn = null;
		private String likeQuery = null;
		
		public Builder(String columns, String table) {
			if (columns == null || table == null) {
		        throw new IllegalArgumentException("columns and table can not be null");
		      }
		      this.columns = columns;
		      this.table = table;
		}
		
		public Builder rnumSortColumn(String rnumSortColumn) {
			this.rnumSortColumn = rnumSortColumn;
			return this;
		}
		
		public Builder whereStandardColumn(String whereStandardColumn) {
			this.whereStandardColumn = whereStandardColumn;
			return this;
		}
		
		public Builder likeQuery(String likeQuery) {
			this.likeQuery = likeQuery;
			return this;
		}
		public AdminQuery build() {
			return new AdminQuery(this);
		}
	}
	
	public String getColumns() {
		return columns;
	}

	public String getTable() {
		return table;
	}

	public String getRnumSortColumn() {
		return rnumSortColumn;
	}

	public String getWhereStandardColumn() {
		return whereStandardColumn;
	}

	public String getLikeQuery() {
		return likeQuery;
	}
}
