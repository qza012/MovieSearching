package com.mvc.file.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.mvc.member.dto.MemberDTO;
import com.oreilly.servlet.MultipartRequest;

public class FileService {

	HttpServletRequest req = null;
	
	public FileService(HttpServletRequest req) {
		this.req = req;
	}

	public MemberDTO regist() {
		String savePath = "C:/upload/";
		File dir = new File(savePath);
		if(dir.exists() == false) {
			dir.mkdir();
		}
		
		int maxSize = 10*1024*1024;
		MemberDTO dto = null;
		try {
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8");
			String id = multi.getParameter("userId");
			String pw = multi.getParameter("userPw");
			String answer = multi.getParameter("answer");
			String name = multi.getParameter("userName");
			String age = multi.getParameter("age");
			String gender = multi.getParameter("gender");
			String email = multi.getParameter("email");
			String genre = multi.getParameter("genre");
			System.out.println(id+" / "+pw+" / "+answer+" / "+name+" / "+age+" / "+gender+" / "+email+" / "+genre);
			
			dto = new MemberDTO();
			dto.setId(id);
			dto.setPw(pw);
			dto.setPw_answer(answer);
			dto.setName(name);
			dto.setAge(Integer.parseInt(age));
			dto.setGender(gender);
			dto.setEmail(email);
			dto.setGenre(genre);
			
			String profileURL = multi.getParameter("urlInput");
			if(profileURL != null) {
				dto.setProfileURL(profileURL);
			}
			String oriFileName = multi.getFilesystemName("photo");
			if(oriFileName != null) {
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis()+ext;
				File oriFile = new File(savePath+oriFileName);
				File newFile = new File(savePath+newFileName);
				System.out.println(savePath+oriFileName);
				boolean success = oriFile.renameTo(newFile);
				System.out.println("이름이 바뀌었는가?"+success);
				dto.setOriFileName(oriFileName);
				dto.setNewFileName(newFileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public void delete(Object object) {
		File file = new File("C:/upload/"+object);
		if(file.exists()) {
			boolean success = file.delete();
			System.out.println("삭제 성공 여부 : "+success);
		}
		
	}

}
