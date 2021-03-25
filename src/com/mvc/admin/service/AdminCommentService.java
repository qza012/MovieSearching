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
import com.mvc.comment.dto.CommentDTO;

public class AdminCommentService {

	private final HttpServletRequest req;
	private final HttpServletResponse resp;

	public AdminCommentService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void commentList() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String nextPage = "comment.jsp";

			String standard = req.getParameter("standard");
			String keyWord = req.getParameter("keyWord");
			String strCurPage = req.getParameter("curPage");
			String strRowsPerPage = req.getParameter("rowsPerPage");
			// AdminUtil.log(keyWord);
			// 값이 request에 존재하면 가져옴. default : curPage 1, rowsPerPage 10
			int curPage = (strCurPage != null) ? Integer.parseInt(strCurPage) : 1;
			int rowsPerPage = (strRowsPerPage != null) ? Integer.parseInt(strRowsPerPage) : 10;

			List<CommentDTO> commentList = null;

			AdminDAO dao = new AdminDAO();
			try {

				if (keyWord == null || keyWord.equals("")) {
					commentList = dao.getCommentList(curPage, rowsPerPage);
					req.setAttribute("maxPage", dao.getRowCount(AdminSql.COMMENT_TABLE) / rowsPerPage + 1);
					req.removeAttribute("keyWord");
				} else {
					commentList = dao.getCommentList(curPage, rowsPerPage, standard, keyWord);
					req.setAttribute("maxPage",
							dao.getRowCount(AdminSql.COMMENT_TABLE, standard, keyWord) / rowsPerPage + 1);
					req.setAttribute("keyWord", keyWord);
				}

				req.setAttribute("curPage", curPage);
				req.setAttribute("standard", standard);
				req.setAttribute("list", commentList);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}

			RequestDispatcher dis = req.getRequestDispatcher(nextPage);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void toggleDelType() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String strIdx = req.getParameter("idx");
			int idx = 0;
			if (strIdx != null) {
				strIdx = strIdx.trim();
				idx = Integer.parseInt(strIdx);
			}

			CommentDTO comDto = null;
			AdminDAO comDao = new AdminDAO();
			try {
				comDto = comDao.getComment(idx);
				if (comDto != null) {
					int result = comDao.toggleDelType(comDto);
					comDto = comDao.getComment(idx); // 토글한 데이터로 갱신
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				comDao.resClose();
			}

			Map<String, Object> map = new HashMap<String, Object>();

			if (comDto != null) {
				map.put("del_type", comDto.getDel_type());
			}

			Gson gson = new Gson();
			String json = gson.toJson(map);
			// System.out.println(json);

			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*");
			resp.getWriter().print(json);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}
}
