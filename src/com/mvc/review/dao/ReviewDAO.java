package com.mvc.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.comment.dto.CommentDTO;
import com.mvc.follow.dto.FollowDTO;
import com.mvc.movie.dto.MovieDTO;
import com.mvc.report.dto.ReportDTO;
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
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
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

	public HashMap<String, Object> list(int group) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		int pagePerCnt = 10; //한페이지에 몇개를 보여줄지
		int end = group*pagePerCnt; //페이지의 끝 rnum
		int start = end-(pagePerCnt-1); //페이지의 시작 rnum
		
		String sql = "SELECT RNUM, IDX, ID, SUBJECT, SCORE, REG_DATE, DEL_TYPE, MOVIENAME, CNTLIKE FROM(SELECT ROW_NUMBER() OVER(ORDER BY r.idx DESC) "
					+ "AS rnum, r.idx idx, id, subject, score, reg_date, del_type, movieName, cntlike FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
					+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " 
					+ "JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l "
					+ "ON r.IDX = l.IDX WHERE del_type='N' ORDER BY IDX DESC) WHERE rnum BETWEEN ? AND ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDel_type(rs.getString("del_type"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setCntLike(rs.getInt("cntLike"));
				list.add(dto);
			}
			int maxPage= getMaxPage(pagePerCnt);
			map.put("list",list);
			map.put("maxPage", maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private int getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(idx) FROM review3 r INNER JOIN movie3 m ON r.movieCode = m.movieCode WHERE del_type='N' ";
		
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);//첫번째 컬럼 가져오기
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
				System.out.println("마지막페이지" + max);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public ReviewDTO detail(int idx) {
		ReviewDTO dto = new ReviewDTO();
		
		String sql="SELECT * FROM (SELECT r.idx, r.id, r.subject, r.content, r.score, r.reg_date, r.del_type, m.movieName, m.posterurl FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike   " + 
				"FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l ON r.IDX = l.IDX WHERE r.idx=?";

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

	public ArrayList<CommentDTO> commentList(int idx) {
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		
		String sql="SELECT r.idx review_idx, c.idx idx, c.id, c.content, c.reg_date, c.del_type FROM review3 r left outer join comment3 c ON r.idx = c.review_idx "
				+ "WHERE c.del_type='N' AND r.idx=? ORDER BY IDX ASC";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idx);
			rs = ps.executeQuery();
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ReviewDTO> memReviewList(String id) throws SQLException {
		String sql = "SELECT idx,subject,movieCode,score,reg_date FROM review3 WHERE id=? ORDER BY reg_date DESC";
		ArrayList<ReviewDTO> review_list = new ArrayList<ReviewDTO>();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setSubject(rs.getString("subject"));
				dto.setMovieCode(rs.getString("movieCode"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				review_list.add(dto);
			}
		return review_list;
	}
	
	public ReviewDTO updateForm(int idx) {
		ReviewDTO dto = new ReviewDTO();
		
		String sql = "SELECT * FROM (SELECT r.idx, r.id, r.subject, r.content, r.score, m.movieCode, m.movieName FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r WHERE r.idx=?";
		
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
				dto.setMovieCode(rs.getString("movieCode"));
				dto.setMovieName(rs.getString("movieName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public boolean update(ReviewDTO dto) {
		boolean success = false;
		String sql="UPDATE review3 SET subject=?, content=?, score=? WHERE idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getSubject());
			ps.setString(2, dto.getContent());
			ps.setInt(3, dto.getScore());
			ps.setInt(4, dto.getIdx());
			if(ps.executeUpdate()>0) {
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	public boolean del(int reviewIdx) {
		boolean success = false;
		
		//댓글 갯수 확인
		int commentCnt = getCommentCnt(reviewIdx);
		//댓글이 있는 경우
		if(commentCnt > 0) {
			success = commentDel(reviewIdx, commentCnt);
			if(success == false) {
				return false;
			}
		}
		//리뷰 삭제
		success = false;
		String sql="UPDATE review3 SET del_type='Y' WHERE idx=?";
		try {
			ps= conn.prepareStatement(sql);
			ps.setInt(1, reviewIdx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	private int getCommentCnt(int reviewIdx) {
		int commentCnt = 0;
		String sql="SELECT COUNT(idx) FROM comment3 WHERE del_type='N' AND review_idx=?";
		try {
			ps= conn.prepareStatement(sql);
			ps.setInt(1, reviewIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				commentCnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentCnt;
	}
	
	public boolean commentDel(int reviewIdx, int commentCnt) {
		boolean success= false;
		String sql="UPDATE comment3 SET del_type='Y' WHERE review_idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reviewIdx);
			if(ps.executeUpdate()==commentCnt) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public HashMap<String, Object> myReviewList(String loginId, int group) { //내가 쓴 리뷰 리스트 얻기
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		ReviewDTO dto = null;
		
		int pagePerCnt = 10;
		int end = group*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		String sql="SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY idx DESC)AS rnum, r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
				+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode AND r.id = ?)r JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike "
				+ "FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l ON r.IDX = l.IDX "
				+ "WHERE del_type='N' AND rnum BETWEEN ? AND ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setCntLike(rs.getInt("cntLike"));
				list.add(dto);
			}
			System.out.println("listSize : "+list.size());
			int maxPage = (int) Math.ceil(list.size()/(double)pagePerCnt);
			map.put("list", list);
			map.put("maxPage", maxPage);
			System.out.println("maxPage : "+maxPage);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public HashMap<String, Object> myLikeReview(String loginId, int group) { //좋아요한 리뷰 리스트 얻기
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		ReviewDTO dto = null;
		
		int pagePerCnt = 10;
		int end = group*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		String sql="SELECT * FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
				+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike "
				+ "FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r RIGHT OUTER JOIN review_like3 l ON r.idx = l.review_idx AND l.id = ?) "
				+ "GROUP BY IDX)l ON r.IDX = l.IDX WHERE del_type='N' ORDER BY R.IDX DESC";
		try {	
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			ArrayList<Object> listToList = new ArrayList<Object>();
			while(rs.next()) {
				dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setCntLike(rs.getInt("cntLike"));
				list.add(dto);
			}
			for(int i=start-1; i<end; i++) {
				if(i<list.size()) {
					System.out.println(i);
					listToList.add(list.get(i));
				}
			}
			System.out.println("listSize : "+listToList.size());
			int maxPage = (int) Math.ceil(list.size()/(double)pagePerCnt);
			map.put("list", listToList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage : "+maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public boolean iDonotLike(String id, String idx) {
		boolean success = false;
		String sql = "DELETE review_like3 WHERE id=? AND review_idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, idx);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(id+"가 취소한 idx->"+idx);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean deleteReview(String id, String idx) {
		boolean success = false;
		String sql = "UPDATE review3 SET del_type='Y' WHERE id=? AND idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, idx);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(id+"가 삭제한 idx->"+idx);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean commentWrite(CommentDTO dto) {
		boolean success = false;
		String sql="INSERT INTO comment3(idx, id, review_idx, content, del_type) "
				+ "VALUES(comment3_seq.NEXTVAL, ? , ? , ?, 'N')";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setInt(2, dto.getReview_idx());
			ps.setString(3, dto.getContent());
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public String commentUpdateForm(int comment_idx) {
		String content = null;
		String sql="SELECT content FROM comment3 WHERE idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, comment_idx);
			rs = ps.executeQuery();
			while(rs.next()) {
				content = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return content;
	}

	public boolean commentUpdate(int comment_idx, String content) {
		boolean success = false;
		String sql="UPDATE comment3 SET content=? WHERE idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, content);
			ps.setInt(2, comment_idx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean commentDel(int comment_idx) {
		boolean success= false;
		String sql="UPDATE comment3 SET del_type='Y' WHERE idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, comment_idx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean reviewLikeCheck(int reviewIdx, String loginId) {
		boolean success= false;
		String sql="SELECT * FROM review_like3 WHERE review_idx=? AND id=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reviewIdx);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			if(rs.next()){
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	public boolean reviewLikeUp(int review_idx, String loginId) {
		boolean success= false;
		String sql = "INSERT INTO review_like3(idx, id, review_idx) VALUES(review_like3_seq.NEXTVAL,?,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, review_idx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean reviewLikeDown(int review_idx, String loginId) {
		boolean success= false;
		String sql = "DELETE FROM review_like3 WHERE id=? AND review_idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, review_idx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean reportCheck(String loginId, int idx, int type_idx) {
		boolean success = false;
		String sql = "SELECT * FROM report3 WHERE report_id=? AND report_idx=? AND type_idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, idx);
			ps.setInt(3, type_idx);
			rs = ps.executeQuery();
			if(rs.next()){
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public int getReviewIdx(int idx) {
		int review_idx = 0;
		String sql = "SELECT review_idx FROM comment3 WHERE idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idx);
			rs = ps.executeQuery();
			if(rs.next()){
				review_idx = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return review_idx;
	}

	public boolean report(ReportDTO dto) {
		boolean success = false;
		String sql = "INSERT INTO report3(idx, report_id, report_idx, content, type_idx, complete) VALUES(report3_seq.NEXTVAL,?,?,?,?,'N')";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getReport_id());
			ps.setInt(2, dto.getReport_idx());
			ps.setString(3, dto.getContent());
			ps.setInt(4, dto.getType_idx());
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public HashMap<String, Object> reviewMovieSearch(String subName, int group) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		int pagePerCnt = 10; //한페이지에 몇개를 보여줄지
		int end = group*pagePerCnt; //페이지의 끝 rnum
		int start = end-(pagePerCnt-1); //페이지의 시작 rnum
		
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
				+ "movieName, openDate, genre, director, country FROM movie3 WHERE movieName LIKE ?) WHERE rnum BETWEEN ? AND ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+subName+"%");
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				MovieDTO dto = new MovieDTO();
				dto.setMovieCode(rs.getString(2));
				dto.setMovieName(rs.getString(3));
				dto.setOpenDate(rs.getDate(4));
				dto.setGenre(rs.getString(5));
				dto.setDirector(rs.getString(6));
				dto.setCountry(rs.getString(7));
				list.add(dto);
			}
			int maxPage= getSearchMovieMaxPage(pagePerCnt, subName);

			map.put("list",list);
			map.put("maxPage", maxPage);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private int getSearchMovieMaxPage(int pagePerCnt, String subName) {
		String sql= "SELECT COUNT(movieCode) FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
				+ "movieName, openDate, genre, director, country FROM movie3 WHERE movieName LIKE ?)";
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, "%"+subName+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public String reviewMovieChoice(String movieCode) {
		String movieName = null;
		String sql = "SELECT movieName FROM movie3 WHERE movieCode=?";
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, movieCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				movieName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return movieName;
	}

	public int reviewMovieCheck(String movieCode, String loginId) {
		int haveReview = 0;
		String sql = "SELECT * FROM review3 WHERE movieCode=? AND id=?";
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, movieCode);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				haveReview = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return haveReview;
	}

	public HashMap<String, Object> reviewSearchList(int group, String search, String keyword) {
HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		int pagePerCnt = 10; //한페이지에 몇개를 보여줄지
		int end = group*pagePerCnt; //페이지의 끝 rnum
		int start = end-(pagePerCnt-1); //페이지의 시작 rnum
		
		String sql = null;
		
		if(search.equals("movieName")) {
			sql = "SELECT RNUM, IDX, ID, SUBJECT, SCORE, REG_DATE, DEL_TYPE, MOVIENAME, CNTLIKE FROM(SELECT ROW_NUMBER() OVER(ORDER BY r.idx DESC) "
					+ "AS rnum, r.idx idx, id, subject, score, reg_date, del_type, movieName, cntlike FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
					+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " 
					+ "JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l "
					+ "ON r.IDX = l.IDX WHERE del_type='N' AND movieName LIKE ? ORDER BY IDX DESC) WHERE rnum BETWEEN ? AND ?";
		}else if(search.equals("id")) {
			sql = "SELECT RNUM, IDX, ID, SUBJECT, SCORE, REG_DATE, DEL_TYPE, MOVIENAME, CNTLIKE FROM(SELECT ROW_NUMBER() OVER(ORDER BY r.idx DESC) "
					+ "AS rnum, r.idx idx, id, subject, score, reg_date, del_type, movieName, cntlike FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
					+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " 
					+ "JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l "
					+ "ON r.IDX = l.IDX WHERE del_type='N' AND id LIKE ? ORDER BY IDX DESC) WHERE rnum BETWEEN ? AND ?";
		}else if(search.equals("subject")) {
			sql = "SELECT RNUM, IDX, ID, SUBJECT, SCORE, REG_DATE, DEL_TYPE, MOVIENAME, CNTLIKE FROM(SELECT ROW_NUMBER() OVER(ORDER BY r.idx DESC) "
					+ "AS rnum, r.idx idx, id, subject, score, reg_date, del_type, movieName, cntlike FROM (SELECT r.idx, r.id, r.subject, r.score, r.reg_date, r.del_type, m.movieName "
					+ "FROM review3 r INNER JOIN movie3 m ON r.moviecode = m.moviecode)r " 
					+ "JOIN (SELECT IDX, COUNT(REVIEW_IDX)cntLike FROM (SELECT r.IDX, l.REVIEW_IDX FROM review3 r LEFT OUTER JOIN review_like3 l ON r.idx = l.review_idx) GROUP BY IDX)l "
					+ "ON r.IDX = l.IDX WHERE del_type='N' AND subject LIKE ? ORDER BY IDX DESC) WHERE rnum BETWEEN ? AND ?";
		}
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setScore(rs.getInt("score"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDel_type(rs.getString("del_type"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setCntLike(rs.getInt("cntLike"));
				list.add(dto);
			}
			int maxPage= getSearchMaxPage(pagePerCnt, search, keyword);
			map.put("list",list);
			map.put("maxPage", maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private int getSearchMaxPage(int pagePerCnt, String search, String keyword) {
		String sql = null;
		
		if(search.equals("movieName")) {
			sql = "SELECT COUNT(idx) FROM review3 r INNER JOIN movie3 m ON r.movieCode = m.movieCode WHERE del_type='N' AND m.movieName LIKE ?";
		}else if(search.equals("id")) {
			sql = "SELECT COUNT(idx) FROM review3 r INNER JOIN movie3 m ON r.movieCode = m.movieCode WHERE del_type='N' AND r.id LIKE ?";
		}else if(search.equals("subject")) {
			sql =  "SELECT COUNT(idx) FROM review3 r INNER JOIN movie3 m ON r.movieCode = m.movieCode WHERE del_type='N' AND r.subject LIKE ?";
		}
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);//첫번째 컬럼 가져오기
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
				System.out.println("마지막페이지" + max);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}
}
