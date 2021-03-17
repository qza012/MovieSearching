package com.mvc.movie.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.movie.dao.MovieDAO;
import com.mvc.movie.dto.MovieDTO;
import com.mvc.review.dao.ReviewDAO;
import com.mvc.review.dto.ReviewDTO;

public class MovieService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;

	public MovieService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public ArrayList<MovieDTO> main() throws ServletException, IOException {
		MovieDAO dao = new MovieDAO();
		ArrayList<MovieDTO> top = null;
		try {
			String week = "202102";
			top = dao.rankList(week);	// MovieDTO에 rank 정보만 포함.
			
			ArrayList<MovieDTO> topTemp = new ArrayList<MovieDTO>();
			for(MovieDTO dto : top) {
				MovieDTO movieDTO = dao.detail(dto.getMovieCode());
				topTemp.add(movieDTO);
			}
			top = topTemp;
				
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

	public void movieList() throws ServletException, IOException {
//		String loginId = (String) req.getSession().getAttribute("loginId");
//		if (loginId != null) {
		String pageParam = req.getParameter("page");
		System.out.println("현재 페이지 : " + pageParam);
		int group = 1;
		if (pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
		MovieDAO dao = new MovieDAO();
		try {
			HashMap<String, Object> map = dao.movieList(group);
			dao = new MovieDAO();
			req.setAttribute("maxPage", map.get("maxPage"));
			if (req.getAttribute("search_list") == null) {
				req.setAttribute("movie_list", map.get("list"));
			} else {
				req.setAttribute("movie_list", req.getAttribute("search_list"));
			}
			req.setAttribute("currPage", group);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		dis = req.getRequestDispatcher("movielist.jsp");
		dis.forward(req, resp);
//		} else {
//			resp.sendRedirect("index.jsp");
//		}
//	}
	}

	public void detail() throws ServletException, IOException {
		MovieDAO dao = new MovieDAO();
		MovieDTO dto = new MovieDTO();
		String loginId = (String) req.getSession().getAttribute("loginId");

//		if (loginId == null) {
//			resp.sendRedirect("home");
//		} else {
		String movieCode = req.getParameter("movieCode");
		dao = new MovieDAO();
		ArrayList<ReviewDTO> review = null;
		try {
			dto = dao.detail(movieCode);
			System.out.println("영화코드 : " + movieCode);
			req.setAttribute("movie", dto);
			review = dao.review(movieCode);
			System.out.println("리뷰 갯수 : " + review.size());
			req.setAttribute("review", review);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		dis = req.getRequestDispatcher("moviedetail.jsp");
		dis.forward(req, resp);
//		}
	}
	public void likeMovie() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			MovieDAO dao = new MovieDAO();
			try {
				HashMap<String, Object> map = dao.likeMovie(loginId,group);
				if(map != null) {
					req.setAttribute("movie_list", map.get("list"));
					req.setAttribute("maxPage", map.get("maxPage"));
					req.setAttribute("currPage", group);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
			RequestDispatcher dis = req.getRequestDispatcher("/member/movie.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}

	public void movieSearch() throws ServletException, IOException {
		String pageParam = req.getParameter("page");
		String search = req.getParameter("search");
		System.out.println("search값:" + search);
		String keyWord = req.getParameter("keyWord");
		System.out.println("검색 요청한 키워드:" + keyWord);

		int group = 1;
		if (pageParam != null) {
			group = Integer.parseInt(pageParam);
		}

		MovieDAO dao = new MovieDAO();
		try {
			HashMap<String, Object> map = dao.movieSearch(keyWord, search, group);
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("keyWord", keyWord);
			req.setAttribute("search", search);
			if (map != null) {
				req.setAttribute("search_list", map.get("list"));
			}
			req.setAttribute("currPage", group);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		dis = req.getRequestDispatcher("search.jsp");
		dis.forward(req, resp);
	}

}
