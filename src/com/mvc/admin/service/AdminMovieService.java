package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.admin.dao.AdminDAO;
import com.mvc.admin.util.AdminSql;
import com.mvc.admin.util.AdminUtil;
import com.mvc.movie.dto.MovieDTO;

public class AdminMovieService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminMovieService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void movieList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "movie.jsp";
		req.setAttribute("finalPage", finalPage);
		
		String standard = req.getParameter("standard");
		String keyWord = req.getParameter("keyWord");
		String strCurPage = req.getParameter("curPage");
		String strRowsPerPage = req.getParameter("rowsPerPage");
		//AdminUtil.log(keyWord);
		// 값이 request에 존재하면 가져옴.  default : curPage 1, rowsPerPage 10
		int curPage = (strCurPage != null) ? Integer.parseInt(strCurPage) : 1;
		int rowsPerPage = (strRowsPerPage != null) ? Integer.parseInt(strRowsPerPage) : 10;
		
		List<MovieDTO> movieList = null;
		
		AdminDAO dao = new AdminDAO();
		try {

			if(keyWord == null || keyWord.equals("")) {
				movieList = dao.getMovieList(curPage, rowsPerPage);
				req.setAttribute("maxPage", dao.getRowCount(AdminSql.MOVIE_TABLE)/rowsPerPage + 1);
				req.removeAttribute("keyWord");
			} else {
				movieList = dao.getMovieList(curPage, rowsPerPage, standard, keyWord);
				req.setAttribute("maxPage", dao.getRowCount(AdminSql.MOVIE_TABLE, standard, keyWord)/rowsPerPage + 1);
				req.setAttribute("keyWord", keyWord);
			}
			
			
			req.setAttribute("curPage", curPage);
			req.setAttribute("standard", standard);
			req.setAttribute("list", movieList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, resp);
	}

	public void updateYoutubeUrl() throws ServletException, IOException {
		String movieCode = req.getParameter("movieCode");
		
		MovieDTO movieDto = null;
		AdminDAO dao = new AdminDAO();
		try {
			movieDto = dao.getMovie(movieCode);
			if(movieDto != null) {
				int result = dao.updateYoutubeUrl(req.getParameter("youtubeUrl"), movieCode);
				movieDto = dao.getMovie(movieCode);	// 바꾼 데이터로 갱신
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(movieDto != null) {
			System.out.println("여기통과해라");
			map.put("youtubeUrl", movieDto.getYoutubeUrl());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}

	public void updatePosterUrl() throws ServletException, IOException {
		String movieCode = req.getParameter("movieCode");
		
		MovieDTO movieDto = null;
		AdminDAO dao = new AdminDAO();
		try {
			movieDto = dao.getMovie(movieCode);
			if(movieDto != null) {
				int result = dao.updatePosterUrl(req.getParameter("posterUrl"), movieCode);
				movieDto = dao.getMovie(movieCode);	// 바꾼 데이터로 갱신
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(movieDto != null) {
			map.put("posterUrl", movieDto.getPosterUrl());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
}
