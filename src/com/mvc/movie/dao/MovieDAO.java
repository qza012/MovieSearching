package com.mvc.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.movie.dto.MovieDTO;
import com.mvc.review.dto.ReviewDTO;

public class MovieDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MovieDAO() {
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

	public ArrayList<MovieDTO> main() throws SQLException {
		String sql = "SELECT DISTINCT * FROM rank3 r, movie3 m WHERE ROWNUM <= 10 ORDER BY rank";
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				dto.setRank(rs.getInt("rank"));
				dto.setPosterUrl(rs.getString("posterUrl"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setMovieCode(rs.getString("movieCode"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, Object> movieList(int group) throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		int pagePerCnt = 10;
		int end = group * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT posterUrl, movieName, movieCode FROM "
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY openDate) AS rnum, posterUrl, movieName, movieCode FROM movie3)"
				+ " WHERE rnum BETWEEN ? AND ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, start);
		ps.setInt(2, end);
		rs = ps.executeQuery();
		while (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setMovieName(rs.getString("movieName"));
			dto.setPosterUrl(rs.getString("posterUrl"));
			list.add(dto);
		}
		System.out.println("페이지별 영화 수 : " + list.size());
		int maxPage = getMaxPage(pagePerCnt);
		map.put("list", list);
		map.put("maxPage", maxPage);
		System.out.println("최대 페이지 수 : " + maxPage);
		return map;
	}

	public MovieDTO detail(String movieCode) throws SQLException {
		String sql = "SELECT * FROM movie3 WHERE movieCode=?";

		MovieDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, movieCode);
			rs = ps.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	/***
	 * 영화 목록 API에게 한 번에 가져올 수 있는 데이터가 movieCd, movieNm, prdtYear, openDt
	 * 
	 * @return
	 */
	public int insertMovie(MovieDTO dto) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO movie3(movieCode, movieName, openDate, genre, director, country) VALUES(?, ?, to_date(?, 'yyyy-mm-dd'), ?, ?, ?)";

		// System.out.println(dto.getCode() + " / " + dto.getTitle() + " / " +
		// dto.getOpeningDate() + " / " + dto.getGenre() + " / " + dto.getDirector() + "
		// / " + dto.getCountry());

		ps = conn.prepareStatement(sql);
		ps.setString(1, dto.getMovieCode());
		ps.setString(2, dto.getMovieName());
		ps.setDate(3, dto.getOpenDate()); // 코드 수정해야함
		ps.setString(4, dto.getGenre());
		ps.setString(5, dto.getDirector());
		ps.setString(6, dto.getCountry());

		result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public int setYoutubeUrl(String movieCode, String youtubeUrl) throws SQLException {
		int result = 0;
		String sql = "UPDATE movie3 SET youtubeUrl=? WHERE movieCode=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, youtubeUrl);
		ps.setString(2, movieCode);

		result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public int setPosterUrl(String movieCode, String posterUrl) throws SQLException {
		int result = 0;
		String sql = "UPDATE movie3 SET posterUrl=? WHERE movieCode=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, posterUrl);
		ps.setString(2, movieCode);

		result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public ArrayList<ReviewDTO> review(String movieCode) {
		String sql = "SELECT idx, id, movieCode, subject, content, reg_date, del_type FROM review3 WHERE ROWNUM <= 5 AND movieCode=?";
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, movieCode);
			rs = ps.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, Object> movieSearch(String keyWord, String search, int group) throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		int pagePerCnt = 10;
		int end = group * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = null;

		if (search.equals("movieName")) {
			sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
					+ "movieName, openDate, genre, director, country, posterUrl FROM movie3 WHERE movieName LIKE ?) WHERE rnum BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
		} else if (search.equals("genre")) {
			sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
					+ "movieName, openDate, genre, director, country, posterUrl FROM movie3 WHERE genre LIKE ?) WHERE rnum BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
		} else if (search.equals("director")) {
			sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
					+ "movieName, openDate, genre, director, country, posterUrl FROM movie3 WHERE director LIKE ?) WHERE rnum BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
		}
		ps.setString(1, "%" + keyWord + "%");
		ps.setInt(2, start);
		ps.setInt(3, end);
		rs = ps.executeQuery();
		while (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setMovieCode(rs.getString("movieCode"));
			dto.setMovieName(rs.getString("movieName"));
			dto.setOpenDate(rs.getDate("openDate"));
			dto.setGenre(rs.getString("genre"));
			dto.setDirector(rs.getString("director"));
			dto.setCountry(rs.getString("country"));
			dto.setPosterUrl(rs.getString("posterUrl"));
			list.add(dto);
		}

		System.out.println("페이지별 영화 수 : " + list.size());
		int maxPage = getMaxPage(pagePerCnt, keyWord);
		map.put("list", list);
		map.put("maxPage", maxPage);
		System.out.println("최대 페이지 수 : " + maxPage);

		return map;
	}

	private int getMaxPage(int pagePerCnt) throws SQLException {
		String sql = "SELECT COUNT(*) FROM movie3";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	private int getMaxPage(int pagePerCnt, String keyWord) {
		String sql = "SELECT COUNT(movieCode) FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
				+ "movieName, openDate, genre, director, country FROM movie3 WHERE movieName LIKE ?)";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + keyWord + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public ArrayList<MovieDTO> likeMovie(String id) {

		return null;
	}

	public HashMap<String, Object> myLikeMovie(String loginId, int group) {
		return null;
	}

}
