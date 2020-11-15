package com.kh.john.member.model.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.john.board.model.vo.Board;
import com.kh.john.member.controller.MailHandler;
import com.kh.john.member.controller.UuidGenerator;
import com.kh.john.member.model.dao.MemberDao;
import com.kh.john.member.model.vo.License;
import com.kh.john.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao dao;
	
	@Autowired
	private MemberService service;

	@Autowired
	private SqlSessionTemplate session;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public List<Map<String, Object>> selectMember() {
		// TODO Auto-generated method stub
		return dao.selectMember(session);
	}

	@Override
	public String sendAuthKey(String email) throws Exception {
		String authKey=new UuidGenerator().generateUuid(10);
		MailHandler sendMail=new MailHandler(mailSender);
		sendMail.setSubject("재판하는 존경장님 인증을 부탁드려요:)");
		sendMail.setText(
                new StringBuffer()
                .append("<h1>메일인증</h1>")
                .append(authKey)
                .toString());
		sendMail.setFrom("22mailme@gmail.com", "관리자");
		sendMail.setTo(email);
		sendMail.send();
		return authKey;
	}
	
	@Override
	public Member selectMemberById(Member member) {
		return dao.selectMemberById(session, member);
	}
	@Override
	public Member selectMemberById(Map param) {
		return dao.selectMemberById(session, param);
	}
	
	@Override
	public Member nickDuplicate(Member member) {
		return dao.nickDuplicate(session, member);
	}

	@Override
	public Member phoneDuplicate(Member member) {
		return dao.phoneDuplicate(session, member);
	}
	
	@Override
	public int signUpEnd(Member member) {
		return dao.signUpEnd(session, member);
	}

	@Override
	@Transactional
	public int signUpExpert(Member member, List<License> licenseList) {
		int result=dao.signUpEnd(session, member);
	
		if(!licenseList.isEmpty()) {
			for(License l : licenseList) {
				dao.signUpExpert(session,l);
			}
		}
		return result;
	}

	@Override
	public Member findId(Member member) {
		return dao.findId(session, member);
	}

	@Override
	public Member findPw(Member member) {
		return dao.findPw(session, member);
	}

	@Override
	public int tempPw(Member member) {
		return dao.tempPw(session,member);
	}
	
	@Override
	public int updatePw(Member member) {
		return dao.updatePw(session,member);
	}

	@Override
	public int updateNick(Member member) {
		return dao.updateNick(session,member);
	}

	@Override
	public int updatePic(Member member) {
		return dao.updatePic(session, member);
	}

	@Override
	public int updatePhone(Member member) {
		return dao.updatePhone(session, member);
	}

	@Override
	public List<Board> myBoard(int cPage, int numPerPage, int usid) {
		return dao.myBoard(session, cPage, numPerPage, usid);
	}

	@Override
	public int myBoardCount(int usid) {
		return dao.myBoardCount(session, usid);
	}

	@Override
	public Board searchBoard(Board board) {
		return dao.searchBoard(session, board);
	}


}
