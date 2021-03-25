package com.mvc.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.alarm.dto.AlarmDTO;
import com.mvc.follow.dto.FollowDTO;
import com.mvc.genre.dto.GenreDTO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.photo.dto.PhotoDTO;
import com.mvc.question.dto.QuestionDTO;
import com.mvc.review.dto.ReviewDTO;

public class MemberDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MemberDAO() {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MemberDTO updateForm(String id) {
		MemberDTO dto = null;
		String sql = "SELECT id,pw,name,age,gender,email,genre,pw_answer,question_idx "
				+ "FROM member3 WHERE id=? AND withdraw='N'";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("데이터 담기");
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setGenre(rs.getString("genre"));
				dto.setPw_answer(rs.getString("pw_answer"));
				dto.setQuestion_idx(rs.getInt("question_idx"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}


	public ArrayList<QuestionDTO> bringQ() {
		String sql = "SELECT *FROM question3";
		ArrayList<QuestionDTO> Qlist = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Qlist = new ArrayList<QuestionDTO>();
			while(rs.next()) {
				QuestionDTO dto = new QuestionDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setContent(rs.getString("content"));
				Qlist.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Qlist;
	}
	
	public ArrayList<GenreDTO> bringG() {
		String sql = "SELECT * FROM genre3";
		ArrayList<GenreDTO> Glist = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Glist = new ArrayList<GenreDTO>();
			while(rs.next()) {
				GenreDTO dto = new GenreDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setContent(rs.getString("content"));
				Glist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Glist;
	}
	
	public int savePhoto(HashMap<String, Object> delMap, MemberDTO dto) { //프로필 사진 저장
		int success = 0;
		String sql="";
		try {
			if(!delMap.isEmpty()) {
				sql = "UPDATE photo3 SET oriFileName=?, newFileName=?, profileURL=? WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setString(3, dto.getProfileURL());
				ps.setString(4, dto.getId());
			} else {
				sql = "INSERT INTO photo3(idx,oriFileName,newFileName,id,profileURL) "
						+ "VALUES(photo3_seq.NEXTVAL,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setString(3, dto.getId());
				ps.setString(4, dto.getProfileURL());
			}	
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto.getOriFileName()+" / "+dto.getNewFileName()+" / "+dto.getId()+" / "+dto.getProfileURL());
		return success;
	}
	

	public int updateMember(MemberDTO mDto) { //회원정보 수정사항 저장
		int success = 0;
		String sql = "UPDATE member3 SET pw=?, name=?, age=?, gender=?, email=?, genre=?,pw_answer=? WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, mDto.getPw());
			ps.setString(2, mDto.getName());
			ps.setInt(3, mDto.getAge());
			ps.setString(4, mDto.getGender());
			ps.setString(5, mDto.getEmail());
			ps.setString(6, mDto.getGenre());
			ps.setString(7, mDto.getPw_answer());
			ps.setString(8, mDto.getId());
			success = ps.executeUpdate();
			if(success > 0) {
				System.out.println("회원 정보 업데이트 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public HashMap<String, Object> getFileName(String id) { //파일 변경(파일 기보유 여부 확인)
		HashMap<String, Object> delMap = new HashMap<String, Object>();
		String delFileName = null;
		String delURL = null;
		String sql = "SELECT newFileName,profileURL FROM photo3 WHERE id =?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) { 
				delFileName = rs.getString("newFileName");					
				delURL = rs.getString("profileURL");
				delMap.put("delFileName", delFileName);
				delMap.put("delURL", delURL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("삭제파일 : "+delFileName+" / "+delURL);
		return delMap;
	}

	public boolean withdraw(String id, String pw) {// 회원 탈퇴
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=? AND pw=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if(rs.next()) {
				sql = "UPDATE member3 SET withdraw=?, disable=? WHERE id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "Y");
				ps.setString(2, "Y");
				ps.setString(3, rs.getString("id"));
				int data = ps.executeUpdate();
				if(data>0) {
					success = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("성공여부 : "+success);
		return success;
	}
	
	public boolean idChk(String id) throws SQLException {
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		success = rs.next();

		return !success;
	}

	public boolean login(String myLoginId, String pw) throws SQLException {
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=? AND pw=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, myLoginId);
		ps.setString(2, pw);
		rs = ps.executeQuery();
		success = rs.next();
		
		if(success) {
			sql = "SELECT id FROM member3 WHERE id=? AND withdraw='Y'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, myLoginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("회원탈퇴한 애다 .");
				return !success;
			}
			
		}
		return success;
	}

	public ArrayList<QuestionDTO> questionList() throws SQLException {
		String sql = "SELECT *FROM question3";
		ArrayList<QuestionDTO> questionList = null;

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		questionList = new ArrayList<QuestionDTO>();
		while (rs.next()) {
			QuestionDTO dto = new QuestionDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
			questionList.add(dto);
		}

		return questionList;

	}

	public ArrayList<MemberDTO> getMemberList() throws SQLException {
		String sql = "SELECT id, name, age, gender, email, genre, withdraw, disable FROM member3";
		// 아이디 | 이름 | 나이 | 성별 | 이메일 | 장르 | 탈퇴여부 | 활성화 여부
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));

			list.add(dto);
		}
		rs.close();
		ps.close();
		return list;
	}

	public MemberDTO getMember(String id) throws SQLException {
		String sql = "SELECT id, pw, name, age, gender, email, genre, pw_answer, withdraw, disable, type, question_idx "
				+ "FROM member3 WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();

		MemberDTO dto = null;
		if (rs.next()) {
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));
		}

		rs.close();
		ps.close();
		return dto;
	}

	public int join(MemberDTO dto) throws SQLException {
		String sql = "INSERT INTO member3(id,pw,name,age,pw_answer,gender,genre,email,question_idx,withdraw,disable,type)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,'N','N','user')";
		int result = 0;

		ps = conn.prepareStatement(sql);
		ps.setString(1, dto.getId());
		ps.setString(2, dto.getPw());
		ps.setString(3, dto.getName());
		ps.setInt(4, dto.getAge());
		ps.setString(5, dto.getPw_answer());
		ps.setString(6, dto.getGender());
		ps.setString(7, dto.getGenre());
		ps.setString(8, dto.getEmail());
		ps.setInt(9, dto.getQuestion_idx());
		result = ps.executeUpdate();

		return result;

	}

	/***
	 * 
	 * @return 제목,평점을 review3 에서 score기준 내림차순으로 7개
	 * @throws SQLException
	 */
	public ArrayList<ReviewDTO> top() throws SQLException {
		String scoreSQL = "SELECT subject,score,idx FROM (SELECT subject,score,idx FROM review3 ORDER BY score DESC) WHERE ROWNUM <= 7";
		ArrayList<ReviewDTO> top_list = new ArrayList<ReviewDTO>();
		ps = conn.prepareStatement(scoreSQL);
		rs = ps.executeQuery();
		while (rs.next()) {
			ReviewDTO dto = new ReviewDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setSubject(rs.getString("subject"));
			dto.setScore(rs.getInt("score"));
			top_list.add(dto);
		}
		return top_list;
	}

	/***
	 * 
	 * @param keyWord
	 * @param search 
	 * @return 입력받은 id가 포함된 memberDTO들.
	 * @throws SQLException
	 */
	public HashMap<String, Object> searchList(int page, String keyWord, String search, String loginId) throws SQLException {
		int pagePerCnt = 10;
		int end = page * pagePerCnt;	
		int start = end-(pagePerCnt-1);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		//이 회원을 내가 팔로우 하고 있는지 체크하는 쿼리로 수정 21.03.18  - 이주원
		if(search.equals("id")) {
			String sql = "SELECT * FROM " + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM" + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM " + 
					"(SELECT * FROM (SELECT target_id, id loginId FROM follow3 WHERE id=?)f RIGHT OUTER JOIN " + 
					"(SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum,id,name,age,gender,genre,reg_date FROM member3 WHERE withdraw='N' AND disable='N')) m on f.target_id = m.id" + 
					")t WHERE id LIKE ?)WHERE rnum BETWEEN ? AND ?)";
			ps = conn.prepareStatement(sql);	
		}else if(search.equals("name")) {
			String nameSql = "SELECT * FROM " + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM" + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM " + 
					"(SELECT * FROM (SELECT target_id, id loginId FROM follow3 WHERE id=?)f RIGHT OUTER JOIN " + 
					"(SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum,id,name,age,gender,genre,reg_date FROM member3 WHERE withdraw='N' AND disable='N')) m on f.target_id = m.id" + 
					")t WHERE name LIKE ?)WHERE rnum BETWEEN ? AND ?)";
			ps = conn.prepareStatement(nameSql);	
		}else if(search.equals("age")) {
			String ageSql = "SELECT * FROM " + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM" + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM " + 
					"(SELECT * FROM (SELECT target_id, id loginId FROM follow3 WHERE id=?)f RIGHT OUTER JOIN " + 
					"(SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum,id,name,age,gender,genre,reg_date FROM member3 WHERE withdraw='N' AND disable='N')) m on f.target_id = m.id" + 
					")t WHERE age LIKE ?)WHERE rnum BETWEEN ? AND ?)";
			ps = conn.prepareStatement(ageSql);	
		}
		
		ps.setString(1, loginId);
		ps.setString(2,  "%" + keyWord + "%");
		ps.setInt(3, start);
		ps.setInt(4, end);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setGenre(rs.getString("genre"));
			if(rs.getString("target_id") != null) {
				dto.setFollow_check(1);
			}else {
				dto.setFollow_check(0);
			}
			list.add(dto);
		}
		System.out.println("list size : "+list.size());
		
		int maxPage = getSearchMaxPage(pagePerCnt, keyWord, search);
		map.put("list", list);
		map.put("maxPage", maxPage);
		System.out.println("max page : "+maxPage);	
		
	return map;	
	}

	public HashMap<String, Object> memberList(int page, String myId) throws SQLException {
		int pagePerCnt = 10;
		int end = page * pagePerCnt;	
		int start = end-(pagePerCnt-1);
		//이 회원을 내가 팔로우 하고 있는지 체크하는 쿼리로 수정 21.03.18  - 이주원
		String sql = "SELECT target_id, loginId, id, name, age, gender, genre, reg_date FROM " + 
				" (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum, target_id, loginId, id, name, age, gender, genre, reg_date FROM " + 
				" (SELECT * FROM (SELECT target_id, id loginId FROM follow3 WHERE id=?)f RIGHT OUTER JOIN " + 
				" (SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY id DESC) AS rnum,id,name,age,gender,genre,reg_date FROM member3 WHERE withdraw='N' AND disable='N') "
				+ "WHERE rnum BETWEEN ? AND ?) m on f.target_id = m.id) t WHERE rnum BETWEEN ? AND ?)";
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();				
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ps.setInt(4, start);
			ps.setInt(5, end);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setGender(rs.getString("gender"));
				dto.setGenre(rs.getString("genre"));
				if(rs.getString("target_id") != null) {
					dto.setFollow_check(1);
				}else {
					dto.setFollow_check(0);
				}
				list.add(dto);
			}
			System.out.println("list size : "+list.size());
			
			int maxPage = getMaxPage(pagePerCnt);
			map.put("list", list);
			map.put("maxPage", maxPage);
			System.out.println("max page : "+maxPage);			
		return map;		
	}
	
	private int getMaxPage(int pagePerCnt) {		
		String sql="SELECT COUNT(id) FROM member3";		
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
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
	
	private int getSearchMaxPage(int pagePerCnt, String keyWord, String search) {		
		String sql = null;
		
		if(search.equals("id")) {
			sql = "SELECT COUNT(id) FROM member3 WHERE withdraw='N' AND disable='N' AND id LIKE ?";
		}else if(search.equals("name")) {
			sql = "SELECT COUNT(id) FROM member3 WHERE withdraw='N' AND disable='N' AND name LIKE ?";
		}else if(search.equals("age")) {
			sql = "SELECT COUNT(id) FROM member3 WHERE withdraw='N' AND disable='N' AND age LIKE ?";
		}
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, "%"+keyWord+"%");
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


	public String idFind(String name,String email) {
		String id = null;
		String sql="SELECT id FROM member3 WHERE name=? AND email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				id = rs.getString("id");
				System.out.println("if in "+id);
			}
		} catch(SQLException e){
			 e.printStackTrace();
		}
		return id;
	}
	
	public String pwFind(String id, String question_idx, String pw_answer) {
		String pw = null;
		String sql ="SELECT pw FROM member3 WHERE id=? AND question_idx=? AND pw_answer=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setString(2, question_idx);
			ps.setString(3, pw_answer);
			rs=ps.executeQuery();
			if(rs.next()) {
				pw=rs.getString("pw");
				System.out.println("PW : "+pw);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return pw;
	}

	public boolean follow(String myId, String targetId) {
		boolean success = false;
		String sql="INSERT INTO follow3(idx,id,target_id) VALUES(follow3_seq.NEXTVAL,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setString(2, targetId);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(myId+"가 팔로우 신청 ->"+targetId);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public HashMap<String, Object> followingList(String loginId, int group) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<FollowDTO> follow3List = new ArrayList<FollowDTO>();
		FollowDTO dto = null;
		String sql=null;
		
		int pagePerCnt = 8;
		int end = group*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		try {
			sql="SELECT idx, target_id FROM (SELECT ROW_NUMBER() OVER(ORDER BY idx DESC)AS rnum, idx, target_id FROM follow3 WHERE id=?) WHERE rnum BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			List<String> tarlist = new ArrayList<String>();
			while(rs.next()) {
				tarlist.add(rs.getString("target_id"));
			}
			for(String tarid : tarlist) {
				dto = new FollowDTO();
				dto.setTarget_id(tarid);
				//팔로잉 수
				sql="SELECT COUNT(target_id)AS to_num FROM follow3 WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, tarid);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setFollowingNum(rs.getInt("to_num"));					
				}
				//팔로워 수
				sql="SELECT COUNT(id)AS from_num FROM follow3 WHERE target_id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, tarid);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setFollowerNum(rs.getInt("from_num"));					
				}
				//프로필 사진
				sql="SELECT oriFileName,newFileName, profileURL FROM photo3 WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, tarid);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setOriFileName(rs.getString("oriFileName"));
					dto.setNewFileName(rs.getString("newFileName"));
					dto.setProfileURL(rs.getString("profileURL"));
					System.out.println(dto.getOriFileName()+" / "+dto.getNewFileName()+" / "+dto.getProfileURL());
				}
				follow3List.add(dto);
			}	
				System.out.println("listSize : "+follow3List.size());
				int maxPage = getFollowingMaxPage(pagePerCnt,loginId);
				map.put("list", follow3List);
				map.put("maxPage", maxPage);
				System.out.println("maxPage : "+maxPage);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return map;
	}

	private int getFollowingMaxPage(int pagePerCnt, String loginId) {
		String sql= "SELECT COUNT(target_id) FROM follow3 WHERE id=? AND withdraw='N'";
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				System.out.println(cnt);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public HashMap<String, Object> followerList(String loginId, int group) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<FollowDTO> follow3List = new ArrayList<FollowDTO>();
		FollowDTO dto = null;
		String sql=null;
		
		int pagePerCnt = 8;
		int end = group*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		try {
			sql="SELECT idx, id FROM (SELECT ROW_NUMBER() OVER(ORDER BY idx DESC)AS rnum, idx, id FROM follow3 WHERE target_id=?) "
					+ "WHERE rnum BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			List<String> idList = new ArrayList<String>();
			while(rs.next()) {
				idList.add(rs.getString("id"));
			}
			for(String fId : idList) {
				dto = new FollowDTO();
				dto.setId(fId);
				//팔로잉 수
				sql="SELECT COUNT(target_id)AS to_num FROM follow3 WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, fId);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setFollowingNum(rs.getInt("to_num"));					
				}
				//팔로워 수
				sql="SELECT COUNT(id)AS from_num FROM follow3 WHERE target_id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, fId);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setFollowerNum(rs.getInt("from_num"));					
				}
				//프로필 사진
				sql="SELECT oriFileName,newFileName,profileURL FROM photo3 WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, fId);
				rs = ps.executeQuery();
				if(rs.next()) {
					dto.setOriFileName(rs.getString("oriFileName"));
					dto.setNewFileName(rs.getString("newFileName"));
					dto.setProfileURL(rs.getString("profileURL"));
				}
				follow3List.add(dto);
			}
			System.out.println("listSize : "+follow3List.size());
			int maxPage = getFollowerMaxPage(pagePerCnt,loginId);
			map.put("list", follow3List);
			map.put("maxPage", maxPage);
			System.out.println("maxPage : "+maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	private int getFollowerMaxPage(int pagePerCnt, String loginId) {
		String sql= "SELECT COUNT(id) FROM follow3 WHERE target_id=? AND withdraw='N'";
		int max = 0;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				System.out.println(cnt);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public boolean notFollow(String myId, String targetId) {
		boolean success = false;
		String sql="DELETE follow3 WHERE id=? AND target_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setString(2, targetId);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(myId+"가 팔로우 취소 ->"+targetId);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean deleteFollower(String myId, String targetId) {
		boolean success = false;
		String sql="DELETE follow3 WHERE id=? AND target_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, targetId);
			ps.setString(2, myId);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(myId+"의 팔로워 삭제 ->"+targetId);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

//	public boolean followCheck(String id, String target_id) {
//		boolean fChk = false;
//		String sql="SELECT id, target_id FROM follow3 WHERE id=? AND target_id=?";
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, id);
//			ps.setString(2, target_id);
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				fChk = true;
//				System.out.println(rs.getString("id")+", "+rs.getString("target_id"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return fChk;
//	}
	//팔로우 체크 (내가 팔로우하고 있는 사람목록 가져오기)
	public ArrayList<FollowDTO> followCheck(String myId) throws SQLException {
		String sql="SELECT id, target_id FROM follow3 WHERE id=?";
		ArrayList<FollowDTO> list = new ArrayList<FollowDTO>();
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			rs = ps.executeQuery();
			while(rs.next()) {
				FollowDTO dto = new FollowDTO();
				dto.setId(rs.getString("id"));
				dto.setTarget_id(rs.getString("target_id"));
				list.add(dto);
			}
		return list;
	}

	public HashMap<String, Object> alarmChk(String myId, int group) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<AlarmDTO> alarmList = new ArrayList<AlarmDTO>();
		AlarmDTO dto = null;
		
		int pagePerCnt = 10;
		int end = group*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		String sql="SELECT idx, target_id, id, type_idx, content, reg_date FROM (SELECT ROW_NUMBER() OVER(ORDER BY idx DESC)"
				+ "AS rnum, idx, target_id, id, type_idx, content, reg_date FROM alarm3 WHERE target_id=?) WHERE rnum BETWEEN ? AND ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs=ps.executeQuery();
			while(rs.next()) {
				dto = new AlarmDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setTarget_id(rs.getString("target_id"));
				dto.setId(rs.getString("id"));
				dto.setType_idx(rs.getInt("type_idx"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				alarmList.add(dto);
			}
			System.out.println("listSize : "+alarmList.size());
			int maxPage = (int) Math.ceil(alarmList.size()/(double)pagePerCnt);
			map.put("list", alarmList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage : "+maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public int alarm(String target_id, String loginId) {
		String sql = "INSERT INTO alarm3(idx,content,target_id,id,type_idx)VALUES(alarm3_seq.NEXTVAL,?,?,?,?)";
		String msg=loginId+"님이 "+target_id+"님을 팔로우 했습니다.";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setString(2, target_id);
			ps.setString(3, loginId);
			ps.setInt(4, 1001);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}
	
	public ArrayList<AlarmDTO> alarmList(String myId) throws SQLException {
		String sql = "SELECT idx,content,reg_date FROM alarm3 WHERE target_id=? ORDER BY reg_date DESC";
		ArrayList<AlarmDTO> alarm_list = new ArrayList<AlarmDTO>();
		ps = conn.prepareStatement(sql);
		ps.setString(1, myId);
		rs = ps.executeQuery();
		while(rs.next()) {
			AlarmDTO dto = new AlarmDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
			dto.setReg_date(rs.getDate("reg_date"));
			alarm_list.add(dto);
		}
		return alarm_list;
	}
	
	public int alarmDel(String idx) throws SQLException {
		String sql = "DELETE FROM alarm3 WHERE idx=?";
		int success = 0;
		ps = conn.prepareStatement(sql);
		ps.setString(1, idx);
		ps.executeUpdate();
		return success;
	}
	
	//마이페이지에서 팔로우 누르면 팔로우 되어있는지 체크 21.03.18 -- 이주원
	public int myFollowCheck(String loginId, String target_id) {
		int follow_check = 0;
		
		String sql = "SELECT * FROM follow3 WHERE id=? AND target_id=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, target_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				follow_check = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return follow_check;
	}

	public boolean memberFollow(String loginId, String target_id) {
		boolean success = false;
		String sql = "INSERT INTO follow3(idx,target_id,id) VALUES(follow3_seq.NEXTVAL, ?,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, target_id);
			ps.setString(2, loginId);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean memberUnFollow(String loginId, String target_id) {
		boolean success = false;
		String sql = "DELETE FROM follow3 WHERE target_id=? AND id=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, target_id);
			ps.setString(2, loginId);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	
	public boolean emailChk(String email) throws SQLException {
		boolean success = false;
		String sql = "SELECT email FROM member3 WHERE email=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		rs = ps.executeQuery();
		success = rs.next();

		return !success;
	}
}
