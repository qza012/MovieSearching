package com.mvc.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<QuestionDTO> getQuestionList() throws SQLException {
		String sql = "SELECT idx, content FROM question3";
		
		ArrayList<QuestionDTO> list = new ArrayList<QuestionDTO>();
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			QuestionDTO dto = new QuestionDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
			list.add(dto);
		}
		
		return list;
	}
	
	public ArrayList<ReviewDTO> getReviewList() throws SQLException {
		String sql = "SELECT idx, id, movieCode, subject, content, reg_date, del_type FROM review3 ORDER BY idx DESC";
		
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
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
		if(rs.next()) {
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
	
	public ArrayList<CommentDTO> getCommentList() throws SQLException {
		String sql = "SELECT idx, id, review_idx, content, reg_date, del_type FROM comment3 ORDER BY idx DESC";
		
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
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
		if(rs.next()) {
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
		String sql = "SELECT movieCode, movieName, openDate, genre, director, country, actors, grade, youtubeUrl, posterUrl "
				+ "FROM movie3 WHERE movieCode=?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, movieCode);
		rs = ps.executeQuery();
		
		MovieDTO dto = null;
		if(rs.next()) {
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
	
	public LinkedList<ReportDTO> getReportList() throws SQLException {
		String sql = "SELECT idx, report_id, report_idx, content, reg_date, type_idx, complete "
				+ "FROM report3";
		
		LinkedList<ReportDTO> list = new LinkedList<ReportDTO>();
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
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
	
	public ReportDTO getReport(int idx) throws SQLException {
		String sql = "SELECT idx, report_id, report_idx, content, reg_date, type_idx, complete "
				+ "FROM report3 WHERE idx=?";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, idx);
		rs = ps.executeQuery();
		
		ReportDTO dto = null;
		if(rs.next()) {
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
	
	public int insertPwQuestion(String content) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO question3(idx, content) VALUES(question3_seq, ?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, content);
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

		if(disableFlag == null) {
			return result;
		}
		
		disableFlag = disableFlag.toUpperCase();
		String id = dto.getId();
		
		if(disableFlag.equals("Y")) {
			result = disable(id, false);
		} else if(disableFlag.equals("N")){
			result = disable(id, true);
		}
		
		return result;
	}
	
	public int toggleDelType(CommentDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getDel_type();
		
		if(delType == null) {
			return result;
		}
		
		delType = dto.getDel_type().toUpperCase();
		int idx = dto.getIdx();
	
		if(delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_COMMENT_DELTYPE);
		} else if(delType.equals("N")){
			result = delType(idx, true, AdminSql.UPDATE_COMMENT_DELTYPE);
		}
		
		return result;
	}

	public int toggleDelType(ReviewDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getDel_type();
		
		if(delType == null) {
			return result;
		}
			
		delType = dto.getDel_type().toUpperCase();
		int idx = dto.getIdx();
		
		if(delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_REVIEW_DELTYPE);
		} else if(delType.equals("N")){
			result = delType(idx, true, AdminSql.UPDATE_REVIEW_DELTYPE);
		}
		
		return result;
	}
	
	public int toggleComplete(ReportDTO dto) throws SQLException {
		int result = 0;
		String delType = dto.getComplete();
		
		if(delType == null) {
			return result;
		}
		
		delType = dto.getComplete().toUpperCase();
		int idx = dto.getIdx();
		
		if(delType.equals("Y")) {
			result = delType(idx, false, AdminSql.UPDATE_REPORT_COMPLETE);
		} else if(delType.equals("N")){
			result = delType(idx, true, AdminSql.UPDATE_REPORT_COMPLETE);
		}
		
		return result;
	}

	public void resClose() {
		try {
			if(rs != null) {
				rs.close();				
			}
			if(ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int disable(String id, boolean disable) throws SQLException {
		int result = 0;
		String sql = "UPDATE member3 SET disable=? WHERE id=?";
		
		String strDisalbe = "Y";
		if(disable == false) {
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
		if(delType == false) {
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
