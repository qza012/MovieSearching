package com.mvc.member.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.alarm.dto.AlarmDTO;
import com.mvc.file.service.FileService;
import com.mvc.follow.dto.FollowDTO;
import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.question.dto.QuestionDTO;
import com.mvc.review.dto.ReviewDTO;

public class MemberService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		
	}

	public void updateMemberForm() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = req.getParameter("id");
			System.out.println(id+"님의 회원정보");
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto  = new MemberDTO();
			dto = dao.updateForm(id);
			ArrayList<QuestionDTO> list = new ArrayList<QuestionDTO>();
			list = dao.bringQ();
			
			
			String msg = "현재 이용이 불가합니다.";
			String page="/movie/home";
			
			if(dto != null) {
				System.out.println("데이터 보내주기");
				req.setAttribute("mDto", dto);
				req.setAttribute("qList", list);
				msg = "";
				page="./updateMember.jsp";
			}
			dao.resClose();
			
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");			
		}
	}

	public void update() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			FileService file = new FileService(req);
			MemberDTO dto = file.regist();
			System.out.println(dto.getOriFileName()+"=>"+dto.getNewFileName());
			System.out.println("or input : "+dto.getProfileURL());
		
			MemberDAO dao = new MemberDAO();
			int success = dao.updateMember(dto);
			
			if(success > 0) {
				if(dto.getOriFileName() != null || dto.getProfileURL() != null) {
					String id = dto.getId();
					String delFileName = dao.getFileName(id);
					int change = dao.savePhoto(delFileName,dto);
					System.out.println("교체한 파일 갯수 : "+change);
					if(delFileName != null) {
						file.delete(delFileName);
					}
				} 
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/updateMF?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}

	public void withdraw() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = (String) req.getSession().getAttribute("myLoginId");
			String pw = req.getParameter("userPw");
			System.out.println(id+" / "+pw);
			
			MemberDAO dao = new MemberDAO();
			boolean success = dao.withdraw(id, pw);
			
			String msg = "비밀번호를 다시 확인해주세요!";
			String page="/myPage/withdraw.jsp";
			
			if(success) {
				msg="탈퇴되었습니다.";
				page="/movie/home";
				req.getSession().removeAttribute("myLoginId");
			}
			dao.resClose();
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/movie/home");
		}
	}
	
	public void idChk() throws IOException {
		String id = req.getParameter("id");
		boolean success = false;
		System.out.println("id : " + id);
		MemberDAO dao = new MemberDAO();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			success = dao.idChk(id);
			System.out.println("아이디 사용여부 : " + success);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("use", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			resp.getWriter().print(json);
		}

	}

	public void join() throws IOException {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String question_idx = req.getParameter("question_idx");
		String pw_answer = req.getParameter("pw_answer");
		String gender = req.getParameter("gender");
		String genre = req.getParameter("genre");
		String email = req.getParameter("email_id") + "@" + req.getParameter("email_sel");
		
		System.out.println(id + "/" + pw + "/" + name + "/" + age + "/" + question_idx + "/" + pw_answer + "/" + gender
				+ "/" + genre + "/" + email);

		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setAge(Integer.parseInt(age));
		dto.setPw_answer(pw_answer);
		dto.setGender(gender);
		dto.setGenre(genre);
		dto.setEmail(email);
		dto.setQuestion_idx(Integer.parseInt(question_idx));
		int success = 0;
		try {
			success = dao.join(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("use", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	public void questionList() throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		ArrayList<QuestionDTO> questionList = null;
		try {
			questionList = dao.questionList();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		if (questionList != null) {
			req.setAttribute("questionList", questionList);
			RequestDispatcher rd = req.getRequestDispatcher("/join/joinForm.jsp");
			rd.forward(req, resp);
		}
	}

	public void login() throws IOException {
		MemberDAO dao = new MemberDAO();
		String myLoginId = req.getParameter("myLoginId");
		String pw = req.getParameter("pw");
		System.out.println(myLoginId + "/" + pw);

		boolean result = false;
		
		try {
			result = dao.login(myLoginId, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		if(result) {
			req.getSession().setAttribute("myLoginId", myLoginId);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("use", result);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	//회원 목록 불러오기
	public void getMemberList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			String target_id = req.getParameter("target_id");
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);	
			
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}		
			MemberDAO dao = new MemberDAO();
			try {
				
				//모든 회원들의 정보 가져오기&최대 페이지 수 가져오기
				HashMap<String, Object> map = dao.memberList(group, myId);
				//리뷰 평점 기준 7개 가져오기
				ArrayList<ReviewDTO> top_list = dao.top();
				
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("member_list", map.get("list"));
				req.setAttribute("currPage", group);
				req.setAttribute("top_list", top_list);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
			RequestDispatcher dis = req.getRequestDispatcher("member.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}
	
	public void search() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
		String search = req.getParameter("search");
		System.out.println("search값:"+search);
		String keyWord = req.getParameter("keyWord");
		System.out.println("검색 요청한 키워드:" + keyWord);
		String pageParam = req.getParameter("page");
		System.out.println("page : "+pageParam);
		
		if(search == null) {
			search = "id";
		}
		if(keyWord == null) {
			keyWord = "";
		}
		System.out.println(search + keyWord);
		
		int group = 1;
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}		
		
		MemberDAO dao = new MemberDAO();
		try {
			ArrayList<ReviewDTO> top_list = dao.top();
			HashMap<String, Object> map = dao.searchList(group,keyWord,search,loginId);
			
			req.setAttribute("search", search);
			req.setAttribute("keyword", keyWord);
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("member_list", map.get("list"));
			req.setAttribute("currPage", group);
			req.setAttribute("top_list", top_list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
		
		RequestDispatcher dis = req.getRequestDispatcher("memberSearch.jsp");
		dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}

	public void  idFind() {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		System.out.println(name+"/"+email);
		MemberDAO dao = new MemberDAO();
		String id = dao.idFind(name,email);
		System.out.println("아이디:"+id);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("userID", id);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		try {
			resp.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
		
	}

	public void pwFind() throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		
		String id = req.getParameter("id");
		String pw_answer = req.getParameter("pw_answer");
		String question_idx = req.getParameter("question_idx");
		System.out.println(id+"/"+pw_answer+"/"+question_idx);
		String pw = dao.pwFind(id, question_idx, pw_answer);
		System.out.println("비밀번호:"+pw);
		
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("userPW", pw);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		try {
			resp.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}
	
	public void pwQuestionList() throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		ArrayList<QuestionDTO> pwQuestionList = null;
		try {
			pwQuestionList = dao.questionList();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		if (pwQuestionList != null) {
			req.setAttribute("pwQuestionList", pwQuestionList);
			RequestDispatcher rd = req.getRequestDispatcher("/join/pwFind.jsp");
			rd.forward(req, resp);
		}
	}
	
	public void loginForMyPage() throws ServletException, IOException {
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id+" / "+pw);
		
		MemberDAO dao = new MemberDAO();
		boolean success = false;
		try {
			success = dao. loginForMyPage(id,pw);
			if(success) {
				req.getSession().setAttribute("myLoginId", id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		RequestDispatcher dis = req.getRequestDispatcher("./main.jsp");
		dis.forward(req, resp);
	}

	public void followingList() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			MemberDAO dao = new MemberDAO();
			HashMap<String, Object> map = dao.followingList(loginId,group);
			
			String page="./main.jsp";
			if(map!=null) {
				page="/myPage/followingList.jsp";
				req.setAttribute("fList", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("currPage", group);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}

	public void followerList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			MemberDAO dao = new MemberDAO();
			HashMap<String, Object> map = dao.followerList(loginId,group);
			
			String page="./main.jsp";
			if(map!=null) {
				page="/myPage/followerList.jsp";
				req.setAttribute("fList", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("currPage", group);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}
		
	public void deleteFollower() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			String targetId = req.getParameter("id");
			System.out.println(myId+"님을 팔로우 하는, "+targetId+"님 삭제");
			
			MemberDAO dao = new MemberDAO();
			boolean success = dao.deleteFollower(myId,targetId);
			
			if(success) {
				System.out.println("팔로워 삭제!");
			}
			dao.resClose();
			
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/followerList?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}
	
	public void alarmList() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			System.out.println("알람보기 할 id:"+myId);
			MemberDAO dao = new MemberDAO();
			try {
				ArrayList<AlarmDTO> alarm_list = dao.alarmList(myId);
				if(alarm_list!=null) {
					req.setAttribute("alarm_list", alarm_list);
					System.out.println("알람 리스트 불러오기 완료");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/alarm.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}
	
	public void alarmDel() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String idx = req.getParameter("idx");
			System.out.println("삭제 요청할 알람:"+idx);
			MemberDAO dao = new MemberDAO();
			try {
				int success = dao.alarmDel(idx);
				if(success!=0) {
					resp.sendRedirect("alarm.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/alarm?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}

//	public void followCheck() throws ServletException, IOException {
//		String id = (String) req.getSession().getAttribute("myLoginId");
//		String target_id = req.getParameter("target_id");
//		System.out.println(id+"는 "+target_id+"를 팔로우 하나?");
//		
//		MemberDAO dao = new MemberDAO();
//		boolean fChk = dao.followCheck(id, target_id);
//		System.out.println(fChk);
//		
//		if(fChk) {
//			req.setAttribute("fChk", fChk);
//			req.setAttribute("target", target_id);
//		}
//		dao.resClose();
//		RequestDispatcher dis = req.getRequestDispatcher("/follow?target_id="+target_id);
//		dis.forward(req, resp);
//	}

	public void alramChk() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			System.out.println(myId+"의 알람 체크");
			
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			MemberDAO dao = new MemberDAO();
			HashMap<String, Object> map = dao.alarmChk(loginId,group);
			
			String page="/movie/home";
			
			if(map!=null) {
				page="/myPage/alarm.jsp";
				req.setAttribute("aList", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("currPage", group);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/movie/home");
		}	
	}

	public void notFollow() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			String targetId = req.getParameter("target_id");
			System.out.println(myId+"님이 "+targetId+"님 팔로우 취소");
			
			MemberDAO dao = new MemberDAO();
			boolean success = dao.notFollow(myId,targetId);
			
			if(success) {
				System.out.println("팔로워 삭제!");
			}
			dao.resClose();
			
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/followingList?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("../movie/home");
		}
	}
	
	//비동기화로 회원리스트에서 팔로우 또는 언팔로우 추가 21.03.19 -- 이주원
		public void memberFollow() throws IOException {
			String loginId = (String) req.getSession().getAttribute("myLoginId");
			if(loginId != null) {
				String target_id = req.getParameter("target_id");
				System.out.println(target_id);
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				
				int success = 0;
				int follow_check = 0;
				
				MemberDAO dao = new MemberDAO();
				follow_check = dao.myFollowCheck(loginId, target_id);
				
				if(follow_check == 1) {//팔로우 되어있음
					if(dao.memberUnFollow(loginId, target_id)){
						success = 1;
					}
				}else { //팔로우 안되어있음
					if(dao.memberFollow(loginId, target_id)) {
						success = 1;
						dao.alarm(target_id,loginId);
						System.out.println("알람 전송");
					}
				}
				dao.resClose();
				
				map.put("success",success);
				map.put("follow_check", follow_check);
				
				Gson gson = new Gson();
				String json = gson.toJson(map);
				System.out.println(json);
				
				resp.setContentType("text/html; charset=UTF-8");
				resp.setHeader("Access-Control-Allow", "*"); 
				
				PrintWriter out = resp.getWriter();
				out.println(json);
			}
			else {
				resp.sendRedirect("../movie/home");
			}
		}

}