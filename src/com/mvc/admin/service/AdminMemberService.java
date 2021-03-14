package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;

public class AdminMemberService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;

	public AdminMemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void memberList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "memberDisable.jsp";
		req.setAttribute("finalPage", finalPage);

		String standard = req.getParameter("standard");
		String keyWord = req.getParameter("keyWord");
		String strCurPage = req.getParameter("curPage");
		String strRowsPerPage = req.getParameter("rowsPerPage");
		//AdminUtil.log(keyWord);
		// 값이 request에 존재하면 가져옴.  default : curPage 1, rowsPerPage 10
		int curPage = (strCurPage != null) ? Integer.parseInt(strCurPage) : 1;
		int rowsPerPage = (strRowsPerPage != null) ? Integer.parseInt(strRowsPerPage) : 10;
		
		List<MemberDTO> memberList = null;

		AdminDAO dao = new AdminDAO();
		try {
			
			if(keyWord == null || keyWord.equals("")) {
				memberList = dao.getMemberList(curPage, rowsPerPage);
				req.setAttribute("maxPage", dao.getRowCount(AdminSql.MEMBER_TABLE)/rowsPerPage + 1);
				req.removeAttribute("keyWord");
			} else {
				memberList = dao.getMemberList(curPage, rowsPerPage, standard, keyWord);
				req.setAttribute("maxPage", dao.getRowCount(AdminSql.MEMBER_TABLE, standard, keyWord)/rowsPerPage + 1);
				req.setAttribute("keyWord", keyWord);
			}
			
			
			req.setAttribute("curPage", curPage);
			req.setAttribute("standard", standard);
			req.setAttribute("list", memberList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, resp);
		
	}

	public void toggleDisable() throws ServletException, IOException {
		String id = req.getParameter("id");
		MemberDTO memDto = null;
		AdminDAO adminDao = null;
		MemberDAO memDao = null;
		try {
			memDao = new MemberDAO();
			memDto = memDao.getMember(id);
			
			if (memDto != null) {
				adminDao = new AdminDAO();
				int result = adminDao.toggleDisable(memDto);
				memDto = memDao.getMember(id);	// 토글한 데이터도 갱신
				//System.out.println(result);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			memDao.resClose();
			adminDao.resClose();
		}

		Map<String, Object> map = new HashMap<String, Object>();

		if (memDto != null) {
			map.put("disable", memDto.getDisable());
		}

		Gson gson = new Gson();
		String json = gson.toJson(map);
		// System.out.println(json);

		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}

//	public void memberSearch() throws ServletException, IOException {
//		String nextPage = "list";
//		String standard = req.getParameter("standard");
//		String keyWord = req.getParameter("keyWord");
//		AdminUtil.log("search", standard, keyWord);
//		
//		String finalPage = "memberDisable.jsp";
//		req.setAttribute("finalPage", finalPage);
//		
//		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
//		dis.forward(req, resp);
//	}
}
