package com.mvc.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.admin.builder.AdminQuery;
import com.mvc.admin.util.AdminSql;
import com.mvc.comment.dto.CommentDTO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.movie.dto.MovieDTO;
import com.mvc.question.dto.QuestionDTO;
import com.mvc.report.dto.ReportDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminDAO {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public AdminDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(AdminSql table) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + table.getValue();
		;

		int result = 0;

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			result = rs.getInt(1);
		}

		return result;
	}

	public int getRowCount(AdminSql table, String whereStandard, String keyWord) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + table.getValue() + " WHERE " + whereStandard + " LIKE ?";

		int result = 0;

		ps = conn.prepareStatement(sql);
		ps.setString(1, "%" + keyWord + "%");
		rs = ps.executeQuery();
		if (rs.next()) {
			result = rs.getInt(1);
		}

		return result;
	}
	
	public int getMemberCountByGender(boolean male) throws SQLException {
		String sql = null;
		
		if(male) {
			sql = "SELECT COUNT(*) FROM " + AdminSql.MEMBER_TABLE.getValue() + " WHERE gender='남' OR gender='male'";			
		} else {
			sql = "SELECT COUNT(*) FROM " + AdminSql.MEMBER_TABLE.getValue() + " WHERE gender='여' OR gender='female'";
		}

		int result = 0;

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			result = rs.getInt(1);
		}

		return result;
	}

	public List<MemberDTO> getMemberList() throws SQLException {
		setRs(AdminSql.MEMBER_COLUMNS, AdminSql.MEMBER_TABLE);

		List<MemberDTO> list = new ArrayList<MemberDTO>();

		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));

			list.add(dto);
		}

		return list;
	}

	/***
	 * 그냥 페이지 처리.
	 * 
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 * @throws SQLException
	 */
	public List<MemberDTO> getMemberList(int curPage, int rowsPerPage) throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.MEMBER_COLUMNS.getValue(), AdminSql.MEMBER_TABLE.getValue())
				.rnumSortColumn("id")
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<MemberDTO> list = new ArrayList<MemberDTO>();

		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));

			list.add(dto);
		}

		return list;
	}

	public List<MemberDTO> getMemberList(int curPage, int rowsPerPage, String standard, String keyWord)
			throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.MEMBER_COLUMNS.getValue(), AdminSql.MEMBER_TABLE.getValue())
				.rnumSortColumn("id")
				.whereStandardColumn(standard)
				.likeQuery(keyWord)
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<MemberDTO> list = new ArrayList<MemberDTO>();

		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));

			list.add(dto);
		}

		return list;
	}
	
	public List<MemberDTO> getMemberListByGender(int curPage, int rowsPerPage, boolean male) throws SQLException {
		String sql = null;
		
		if(male) {
			sql = "SELECT " + AdminSql.MEMBER_COLUMNS.getValue() + " FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum "
					+ ", " +  AdminSql.MEMBER_COLUMNS.getValue() + " FROM " + AdminSql.MEMBER_TABLE.getValue()
					+ " WHERE gender='남' OR gender='male') WHERE rnum BETWEEN ? AND ?";
		} else {
			sql = "SELECT " + AdminSql.MEMBER_COLUMNS.getValue() + " FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum "
					+ ", " +  AdminSql.MEMBER_COLUMNS.getValue() + " FROM " + AdminSql.MEMBER_TABLE.getValue()
					+ " WHERE gender='여' OR gender='female') WHERE rnum BETWEEN ? AND ?";
		}

		int start = (curPage - 1) * rowsPerPage + 1;
		int end = curPage * rowsPerPage;
		
		ps = conn.prepareStatement(sql);

		ps.setInt(1, start);
		ps.setInt(2, end);
		rs = ps.executeQuery();
		
		List<MemberDTO> list = new ArrayList<MemberDTO>();

		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));

			list.add(dto);
		}

		return list;
	}

	public ArrayList<QuestionDTO> getQuestionList() throws SQLException {
		setRs(AdminSql.QUESTION_COLUMNS, AdminSql.QUESTION_TABLE);

		ArrayList<QuestionDTO> list = new ArrayList<QuestionDTO>();

		while (rs.next()) {
			QuestionDTO dto = new QuestionDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
			list.add(dto);
		}

		return list;
	}

	public ArrayList<ReviewDTO> getReviewList() throws SQLException {
		setRs(AdminSql.REVIEW_COLUMNS, AdminSql.REVIEW_TABLE);

		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();

		while (rs.next()) {
			ReviewDTO dto = new ReviewDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setSubject(rs.getString("subject"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));
			list.add(dto);
		}

		return list;
	}

	public ReviewDTO getReview(int idx) throws SQLException {
		String sql = "SELECT idx, id, movieCode, subject, content, reg_date, del_type FROM review3 WHERE idx=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, idx);
		rs = ps.executeQuery();

		ReviewDTO dto = null;
		if (rs.next()) {
			dto = new ReviewDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setSubject(rs.getString("subject"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));
		}

		return dto;
	}

	public List<ReviewDTO> getReviewList(int curPage, int rowsPerPage) throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.REVIEW_COLUMNS.getValue(), AdminSql.REVIEW_TABLE.getValue())
				.rnumSortColumn("idx")
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<ReviewDTO> list = new ArrayList<ReviewDTO>();

		while (rs.next()) {
			ReviewDTO dto = new ReviewDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setSubject(rs.getString("subject"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));

			list.add(dto);
		}

		return list;
	}

	public List<ReviewDTO> getReviewList(int curPage, int rowsPerPage, String standard, String keyWord)
			throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.REVIEW_COLUMNS.getValue(), AdminSql.REVIEW_TABLE.getValue())
				.rnumSortColumn("idx")
				.whereStandardColumn(standard)
				.likeQuery(keyWord)
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<ReviewDTO> list = new ArrayList<ReviewDTO>();

		while (rs.next()) {
			ReviewDTO dto = new ReviewDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setSubject(rs.getString("subject"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));

			list.add(dto);
		}

		return list;
	}

	public ArrayList<CommentDTO> getCommentList() throws SQLException {
		setRs(AdminSql.COMMENT_COLUMNS, AdminSql.COMMENT_TABLE);

		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();

		while (rs.next()) {
			CommentDTO dto = new CommentDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setReview_idx(rs.getInt("review_Idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));
			list.add(dto);
		}

		return list;
	}

	public List<CommentDTO> getCommentList(int curPage, int rowsPerPage) throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.COMMENT_COLUMNS.getValue(), AdminSql.COMMENT_TABLE.getValue())
				.rnumSortColumn("idx")
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<CommentDTO> list = new ArrayList<CommentDTO>();

		while (rs.next()) {
			CommentDTO dto = new CommentDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setReview_idx(rs.getInt("review_Idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));

			list.add(dto);
		}

		return list;
	}

	public List<CommentDTO> getCommentList(int curPage, int rowsPerPage, String standard, String keyWord)
			throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.COMMENT_COLUMNS.getValue(), AdminSql.COMMENT_TABLE.getValue())
				.rnumSortColumn("idx")
				.whereStandardColumn(standard)
				.likeQuery(keyWord).build();

		setRsPaging(curPage, rowsPerPage, query);

		List<CommentDTO> list = new ArrayList<CommentDTO>();

		while (rs.next()) {
			CommentDTO dto = new CommentDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setReview_idx(rs.getInt("review_Idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));

			list.add(dto);
		}

		return list;
	}

	public CommentDTO getComment(int idx) throws SQLException {
		String sql = "SELECT idx, id, review_idx, content, reg_date, del_type FROM comment3 WHERE idx=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, idx);
		rs = ps.executeQuery();

		CommentDTO dto = null;
		if (rs.next()) {
			dto = new CommentDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setId(rs.getString("id"));
			dto.setReview_idx(rs.getInt("review_Idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setDel_type(rs.getString("del_Type"));
		}

		return dto;
	}

	public MovieDTO getMovie(String movieCode) throws SQLException {
		String sql = "SELECT " + AdminSql.MOVIE_COLUMNS.getValue() + " FROM " + AdminSql.MOVIE_TABLE.getValue()
				+ " WHERE movieCode=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, movieCode);
		rs = ps.executeQuery();

		MovieDTO dto = null;
		if (rs.next()) {
			dto = new MovieDTO();
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setMovieName(rs.getString("movieName"));
			dto.setOpenDate(rs.getDate("openDate"));
			dto.setGenre(rs.getString("genre"));
			dto.setDirector(rs.getString("director"));
			dto.setCountry(rs.getString("country"));
			dto.setActors(rs.getString("actors"));
			dto.setGrade(rs.getString("grade"));
			dto.setYoutubeUrl(rs.getString("youtubeUrl"));
			dto.setPosterUrl(rs.getString("posterUrl"));
		}

		return dto;
	}
	
	public List<MovieDTO> getMovieList(int curPage, int rowsPerPage) throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.MOVIE_COLUMNS.getValue(), AdminSql.MOVIE_TABLE.getValue())
				.rnumSortColumn("movieCode")
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<MovieDTO> list = new ArrayList<MovieDTO>();

		while (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setMovieName(rs.getString("movieName"));
			dto.setOpenDate(rs.getDate("openDate"));
			dto.setGenre(rs.getString("genre"));
			dto.setDirector(rs.getString("director"));
			dto.setCountry(rs.getString("country"));
			dto.setActors(rs.getString("actors"));
			dto.setGrade(rs.getString("grade"));
			dto.setYoutubeUrl(rs.getString("youtubeUrl"));
			dto.setPosterUrl(rs.getString("posterUrl"));

			list.add(dto);
		}

		return list;
	}

	public List<MovieDTO> getMovieList(int curPage, int rowsPerPage, String standard, String keyWord)
			throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.MOVIE_COLUMNS.getValue(), AdminSql.MOVIE_TABLE.getValue())
				.rnumSortColumn("movieCode")
				.whereStandardColumn(standard)
				.likeQuery(keyWord)
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<MovieDTO> list = new ArrayList<MovieDTO>();

		while (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setMovieName(rs.getString("movieName"));
			dto.setOpenDate(rs.getDate("openDate"));
			dto.setGenre(rs.getString("genre"));
			dto.setDirector(rs.getString("director"));
			dto.setCountry(rs.getString("country"));
			dto.setActors(rs.getString("actors"));
			dto.setGrade(rs.getString("grade"));
			dto.setYoutubeUrl(rs.getString("youtubeUrl"));
			dto.setPosterUrl(rs.getString("posterUrl"));

			list.add(dto);
		}

		return list;
	}
	
	public int updateYoutubeUrl(String url, String movieCode) throws SQLException {
		String sql = "UPDATE " + AdminSql.MOVIE_TABLE.getValue() + " SET youtubeUrl=? where movieCode=?";
		int result = 0;
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, url);
		ps.setString(2, movieCode);
		result = ps.executeUpdate();
		
		return result;
	}
	
	public int updatePosterUrl(String url, String movieCode) throws SQLException {
		String sql = "UPDATE " + AdminSql.MOVIE_TABLE.getValue() + " SET posterUrl=? where movieCode=?";
		int result = 0;
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, url);
		ps.setString(2, movieCode);
		result = ps.executeUpdate();
		
		return result;
	}

	public ReportDTO getReport(int idx) throws SQLException {
		String sql = "SELECT idx, report_id, report_idx, content, reg_date, type_idx, complete "
				+ "FROM report3 WHERE idx=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, idx);
		rs = ps.executeQuery();

		ReportDTO dto = null;
		if (rs.next()) {
			dto = new ReportDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setReport_id(rs.getString("report_id"));
			dto.setReport_idx(rs.getInt("report_idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setType_idx(rs.getInt("type_idx"));
			dto.setComplete(rs.getString("complete"));
		}

		return dto;
	}

	public List<ReportDTO> getReportList() throws SQLException {
		setRs(AdminSql.REPORT_COLUMNS, AdminSql.REPORT_TABLE);

		List<ReportDTO> list = new LinkedList<ReportDTO>();

		while (rs.next()) {
			ReportDTO dto = new ReportDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setReport_id(rs.getString("report_id"));
			dto.setReport_idx(rs.getInt("report_idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setType_idx(rs.getInt("type_idx"));
			dto.setComplete(rs.getString("complete"));

			list.add(dto);
		}

		return list;
	}

	public List<ReportDTO> getReportList(int curPage, int rowsPerPage) throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.REPORT_COLUMNS.getValue(), AdminSql.REPORT_TABLE.getValue())
				.rnumSortColumn("idx")
				.build();

		setRsPaging(curPage, rowsPerPage, query);

		List<ReportDTO> list = new ArrayList<ReportDTO>();

		while (rs.next()) {
			ReportDTO dto = new ReportDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setReport_id(rs.getString("report_id"));
			dto.setReport_idx(rs.getInt("report_idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setType_idx(rs.getInt("type_idx"));
			dto.setComplete(rs.getString("complete"));

			list.add(dto);
		}

		return list;
	}

	public List<ReportDTO> getReportList(int curPage, int rowsPerPage, String standard, String keyWord)
			throws SQLException {
		AdminQuery query = new AdminQuery
				.Builder(AdminSql.REPORT_COLUMNS.getValue(), AdminSql.REPORT_TABLE.getValue())
				.rnumSortColumn("idx")
				.whereStandardColumn(standard)
				.likeQuery(keyWord).build();

		setRsPaging(curPage, rowsPerPage, query);

		List<ReportDTO> list = new ArrayList<ReportDTO>();

		while (rs.next()) {
			ReportDTO dto = new ReportDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setReport_id(rs.getString("report_id"));
			dto.setReport_idx(rs.getInt("report_idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setType_idx(rs.getInt("type_idx"));
			dto.setComplete(rs.getString("complete"));

			list.add(dto);
		}

		return list;
	}

	public QuestionDTO getQuestion(String idx) throws SQLException {
		String sql = "SELECT " + AdminSql.QUESTION_COLUMNS.getValue() + " FROM " + AdminSql.QUESTION_TABLE.getValue()
					+ " WHERE idx=?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, idx);
		rs = ps.executeQuery();

		QuestionDTO dto = null;
		if (rs.next()) {
			dto = new QuestionDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
		}

		return dto;
	}
	
	public int updatePwQuestion(String idx, String content) throws SQLException {
		int result = 0;
		String sql = "UPDATE question3 SET content=? WHERE idx=?";
					
		ps = conn.prepareStatement(sql);
		ps.setString(1, content);
		ps.setString(2, idx);
		result = ps.executeUpdate();

		rs.close();
		ps.close();
		return result;
	}

	/***
	 * 
	 * @param memDto에 저장된 disable값을 토글 시켜서 DB에 저장.
	 * @return
	 * @throws SQLException
	 */
	public int toggleDisable(MemberDTO dto) throws SQLException {
		int result = 0;
		String disableFlag = dto.getDisable();

		if (disableFlag == null) {
			return result;
		}

		disableFlag = disableFlag.toUpperCase();
		String id = dto.getId();

		if (disableFlag.equals("Y")) {
			result = disable(id, false);
		} else if (disableFlag.equals("N")) {
			result = disable(id, true);
		}

		return result;
	}

	public int toggleDelType(CommentDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getDel_type();

		if (delType == null) {
			return result;
		}

		delType = dto.getDel_type().toUpperCase();
		int idx = dto.getIdx();

		if (delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_COMMENT_DELTYPE);
		} else if (delType.equals("N")) {
			result = delType(idx, true, AdminSql.UPDATE_COMMENT_DELTYPE);
		}

		return result;
	}

	public int toggleDelType(ReviewDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getDel_type();

		if (delType == null) {
			return result;
		}

		delType = dto.getDel_type().toUpperCase();
		int idx = dto.getIdx();

		if (delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_REVIEW_DELTYPE);
		} else if (delType.equals("N")) {
			result = delType(idx, true, AdminSql.UPDATE_REVIEW_DELTYPE);
		}

		return result;
	}

	public int toggleComplete(ReportDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getComplete();

		if (delType == null) {
			return result;
		}

		delType = dto.getComplete().toUpperCase();
		int idx = dto.getIdx();

		if (delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_REPORT_COMPLETE);
		} else if (delType.equals("N")) {
			result = delType(idx, true, AdminSql.UPDATE_REPORT_COMPLETE);
		}

		return result;
	}

	public void resClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setRs(AdminSql columns, AdminSql table) throws SQLException {
		String sql = "SELECT " + columns.getValue() + " FROM " + table.getValue();

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
	}

	/***
	 * 
	 * @param curPage  현재 페이지
	 * @param rowsPerPage 페이지당 보여줄 행 수
	 * @param query 조건들
	 * @throws SQLException
	 */
	private void setRsPaging(int curPage, int rowsPerPage, AdminQuery query) throws SQLException {
		String sql = null;
		boolean condition = query.getLikeQuery() == null ? false : true;

		if (condition) {
			sql = "SELECT " + query.getColumns() + " FROM (SELECT ROW_NUMBER() OVER(ORDER BY "
					+ query.getRnumSortColumn() + " DESC) AS rnum " + ", " + query.getColumns() + " FROM "
					+ query.getTable() + " WHERE " + query.getWhereStandardColumn() + " LIKE ?) "
					+ "WHERE rnum BETWEEN ? AND ?";
		} else {
			sql = "SELECT " + query.getColumns() + " FROM (SELECT ROW_NUMBER() OVER(ORDER BY "
					+ query.getRnumSortColumn() + " DESC) AS rnum " + ", " + query.getColumns() + " FROM "
					+ query.getTable() + ") " + "WHERE rnum BETWEEN ? AND ?";
		}

		int start = (curPage - 1) * rowsPerPage + 1;
		int end = curPage * rowsPerPage;
		
		ps = conn.prepareStatement(sql);
		if (condition) {
			ps.setString(1, "%" + query.getLikeQuery() + "%");
			ps.setInt(2, start);
			ps.setInt(3, end);
		} else {
			ps.setInt(1, start);
			ps.setInt(2, end);
		}

		rs = ps.executeQuery();
	}

	private int disable(String id, boolean disable) throws SQLException {
		int result = 0;
		String sql = "UPDATE member3 SET disable=? WHERE id=?";

		String strDisalbe = "Y";
		if (disable == false) {
			strDisalbe = "N";
		}

		ps = conn.prepareStatement(sql);
		ps.setString(1, strDisalbe);
		ps.setString(2, id);
		result = ps.executeUpdate();

		ps.close();
		return result;
	}

	private int delType(int idx, boolean delType, AdminSql eSql) throws SQLException {
		int result = 0;
		String sql = eSql.getValue();

		String strDelType = "Y";
		if (delType == false) {
			strDelType = "N";
		}

		ps = conn.prepareStatement(sql);
		ps.setString(1, strDelType);
		ps.setInt(2, idx);
		result = ps.executeUpdate();

		ps.close();
		return result;
	}
}
