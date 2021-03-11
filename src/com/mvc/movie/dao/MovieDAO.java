package com.mvc.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.movie.dto.MovieDTO;

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

	public ArrayList<MovieDTO> top() throws SQLException {
		ArrayList<MovieDTO> top = new ArrayList<MovieDTO>();
		String sql = "SELECT r.rank, m.posterurl, m.moviename FROM rank3 r, movie3 m WHERE ROWNUM <= 10";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setRank(rs.getInt("rank"));
			dto.setPosterUrl(rs.getString("posterurl"));
			dto.setMovieName(rs.getString("moviename"));
		}

		return top;
	}

	public ArrayList<MovieDTO> list() throws SQLException {
		String sql = "SELECT posterurl, moviename FROM movie3 WHERE ROWNUM <= 10";
		ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			MovieDTO dto = new MovieDTO();
			dto.setPosterUrl(rs.getString("posterurl"));
			dto.setMovieName(rs.getString("moviename"));
			list.add(dto);
		}

		return list;
	}

	public MovieDTO detail(String movieCode) throws SQLException {
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

	/***
	 * 영화 목록 API에게 한 번에 가져올 수 있는 데이터가 movieCd, movieNm, prdtYear, openDt
	 * @return
	 */
	public int insertMovie(MovieDTO dto) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO movie3(movieCode, movieName, openDate, genre, director, country) VALUES(?, ?, to_date(?, 'yyyy-mm-dd'), ?, ?, ?)";
		
		//System.out.println(dto.getCode() + " / " + dto.getTitle() + " / " + dto.getOpeningDate() + " / " + dto.getGenre() + " / " + dto.getDirector() + " / " + dto.getCountry());
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, dto.getMovieCode());
		ps.setString(2, dto.getMovieName());
		ps.setDate(3, dto.getOpenDate());  // 코드 수정해야함
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
}
