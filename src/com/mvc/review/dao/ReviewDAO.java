package com.mvc.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.comment.dto.CommentDTO;
import com.mvc.review.dto.ReviewDTO;

public class ReviewDAO {
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ReviewDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean write(ReviewDTO dto) {
		boolean success = false;
		String sql="INSERT INTO review3(idx, id, movieCode, subject, content, score, del_type) "
				+ "VALUES(review3_seq.NEXTVAL,?,?,?,?,?,'N')";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getMovieCode());
			ps.setString(3, dto.getSubject());
			ps.setString(4, dto.getContent());
			ps.setInt(5, dto.getScore());
			if(ps.executeUpdate()>0) {
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public ArrayList<ReviewDTO> list() {
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		String sql="SELECT * FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " + 
				" JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l ON r.IDX = l.IDX WHERE del_type='N' ORDER BY R.IDX DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setCntLike(rs.getInt("cntLike"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ReviewDTO dtail(int idx) {
		ReviewDTO dto = new ReviewDTO();
		
		String sql="SELECT * FROM (SELECT r.idx, r.id, r.subject, r.content, r.score, r.reg_date, r.del_type, m.movieName, m.posterurl FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " + 
				" JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l ON r.IDX = l.IDX WHERE del_type='N' AND r.idx=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idx);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setPosterURL(rs.getString("posterURL"));
				dto.setCntLike(rs.getInt("cntLike"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public CommentDTO commentList(int idx) {
		CommentDTO dto = new CommentDTO();
		
		String sql="SELECT r.idx, c.idx, c.id, c.content, c.reg_date, c.del_type FROM review3 r left outer join comment3 c ON r.idx = c.review_idx WHERE r.idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idx);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

}
