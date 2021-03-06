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
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<MovieDTO> rankList(String week) throws SQLException {
		String sql = "SELECT idx, moviecode, rank, week FROM rank3 WHERE week=?";
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, week);
			rs = ps.executeQuery();

			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setMovieCode(rs.getString("movieCode"));
				dto.setRank(rs.getInt("rank"));
				dto.setWeek(rs.getString("week"));
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
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) AS rnum, posterUrl, movieName, movieCode FROM movie3)"
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
		System.out.println("???????????? ?????? ??? : " + list.size());
		int maxPage = getMaxPage(pagePerCnt);
		map.put("list", list);
		map.put("maxPage", maxPage);
		System.out.println("?????? ????????? ??? : " + maxPage);
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
		String sql = "SELECT idx, id, movieCode, subject, content, reg_date, del_type FROM review3 WHERE ROWNUM <= 5 AND del_type='N' AND movieCode=? ORDER BY reg_date DESC";
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

		System.out.println("???????????? ?????? ??? : " + list.size());
		int maxPage = getMaxPage(pagePerCnt, keyWord, search);
		map.put("list", list);
		map.put("maxPage", maxPage);
		System.out.println("?????? ????????? ??? : " + maxPage);

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

	private int getMaxPage(int pagePerCnt, String keyWord, String search) {

		String sql = null;
		int max = 0;

		try {
			if (search.equals("movieName")) {
				sql = "SELECT COUNT(*) FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
						+ "movieName, openDate, genre, director, country FROM movie3 WHERE movieName LIKE ?)";
				ps = conn.prepareStatement(sql);
			} else if (search.equals("genre")) {
				sql = "SELECT COUNT(*) FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
						+ "movieName, openDate, genre, director, country FROM movie3 WHERE genre LIKE ?)";
				ps = conn.prepareStatement(sql);
			} else if (search.equals("director")) {
				sql = "SELECT COUNT(*) FROM (SELECT ROW_NUMBER() OVER(ORDER BY openDate DESC) rnum, movieCode, "
						+ "movieName, openDate, genre, director, country FROM movie3 WHERE director LIKE ?)";
				ps = conn.prepareStatement(sql);
			}
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

	public HashMap<String, Object> likeMovie(String loginId, int group) {
		int pagePerCnt = 10;
		int end = group * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT l.idx, m.movieName, m.genre, m.director, m.openDate, m.posterUrl FROM movie3 m, movie_like3 l WHERE m.movieCode = l.movieCode AND l.id=?";
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			ArrayList<Object> likeMovie_list = new ArrayList<Object>();
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setMovieName(rs.getString("movieName"));
				dto.setGenre(rs.getString("genre"));
				dto.setDirector(rs.getString("director"));
				dto.setOpenDate(rs.getDate("openDate"));
				dto.setPosterUrl(rs.getString("posterUrl"));
				list.add(dto);
			}
			for (int i = start - 1; i < end; i++) {
				if (i < list.size()) {
					System.out.println(i);
					likeMovie_list.add(list.get(i));
				}
			}
			System.out.println("listSize : " + likeMovie_list.size());
			int maxPage = (int) Math.ceil(list.size() / (double) pagePerCnt);
			map.put("list", likeMovie_list);
			map.put("maxPage", maxPage);
			System.out.println("maxPage : " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public boolean notLikeMovie(String loginId, String idx) {
		boolean success = false;
		String sql = "DELETE movie_like3 WHERE id=? AND idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, Integer.parseInt(idx));
			int count = ps.executeUpdate();
			if (count > 0) {
				success = true;
			}
			System.out.println("deleteIdx : " + idx + ", " + success);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean movieLike_Check(String id, String movieCode) {
		String sql = "SELECT * FROM movie_like3 WHERE id = ? AND movieCode = ?";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, movieCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean movieLike_Up(String id, String movieCode) {
		boolean success = false;
		String sql = "INSERT INTO movie_like3(idx, id, movieCode) VALUES(movie_like3_seq.NEXTVAL,?,?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, movieCode);
			if (ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean movieLike_Down(String id, String movieCode) {
		boolean success = false;
		String sql = "DELETE FROM movie_like3 WHERE id=? AND movieCode=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, movieCode);
			if (ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public int getmovieLike_Count(String movieCode) {
		String sql = "SELECT COUNT(movieCode) FROM movie_like3 WHERE movieCode=?";

		int movieLike_Count = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, movieCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				movieLike_Count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movieLike_Count;
	}

	public HashMap<String, Object> myLikeMovie(String loginId, int group) {
		return null;
	}

}
