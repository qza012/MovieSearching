package com.mvc.movie.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.movie.dao.MovieDAO;
import com.mvc.movie.dto.MovieDTO;

public class MovieService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;

	public MovieService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public ArrayList<MovieDTO> top() throws ServletException, IOException {
		MovieDAO dao = new MovieDAO();
		ArrayList<MovieDTO> top = null;
		try {
			top = dao.top();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		System.out.println("영화 갯수 : " + top.size());
		req.setAttribute("top", top);
		dis = req.getRequestDispatcher("index.jsp");
		dis.forward(req, resp);
		return top;
	}

	public void list() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId"); // 로그인 아이디 불러오기(저장된 세션)
		if (loginId == null) {
			resp.sendRedirect("home");
		} else {
			MovieDAO dao = new MovieDAO();
			ArrayList<MovieDTO> list = null;
			try {
				list = dao.list();
				System.out.println("영화 갯수 : " + list.size());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}
			
			req.setAttribute("list", list);
			dis = req.getRequestDispatcher("movielist.jsp");
			dis.forward(req, resp);
		}
	}

	public MovieDTO detail() throws ServletException, IOException {
		/*
		 * 
		 * 
		 * 수정 바람. 배종식   
		 * dao.detail(moviename);  moviename-> code로 바꾸세요
		 */
		
		MovieDTO dto = new MovieDTO();
//		String loginId = (String) req.getSession().getAttribute("loginId");
//		if (loginId == null) {
//			resp.sendRedirect("index.jsp");
//		} else {
//			String moviename = req.getParameter("moviename");
//			System.out.println("영화이름 : " + moviename);
//			MovieDAO dao = new MovieDAO();
//			dto = dao.detail(moviename);
//			dis = req.getRequestDispatcher("moviedetail.jsp");
//			dis.forward(req, resp);
//		}
		return dto;
	}
	
	public void likeMovie() throws ServletException, IOException {
		/*
		 * 
		 * 
		 * 수정 바람.정원석
		 * dao.detail(moviename);  moviename-> code로 바꾸세요
		 */
//		String loginId = (String) req.getSession().getAttribute("loginId");
//		if(loginId!=null) {
//			String id = req.getParameter("id");
//			System.out.println("영화보기 할 id"+id);
//			MovieDAO dao = new MovieDAO();
//			ArrayList<MovieDTO> movie_list = dao.detail(id);
//			if(movie_list!=null) {
//				req.setAttribute("movie_list", movie_list);
//			}
//			dis = req.getRequestDispatcher("movie.jsp");
//			dis.forward(req, resp);
//		}else {
//			resp.sendRedirect("index.jsp");
//		}
	}
}
