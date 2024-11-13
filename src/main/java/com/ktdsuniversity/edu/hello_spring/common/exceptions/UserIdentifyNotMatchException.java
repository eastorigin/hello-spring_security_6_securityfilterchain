package com.ktdsuniversity.edu.hello_spring.common.exceptions;

import com.ktdsuniversity.edu.hello_spring.member.vo.LoginMemberVO;

public class UserIdentifyNotMatchException  extends RuntimeException{

	private static final long serialVersionUID = -7086532449996918719L;
	
	private LoginMemberVO loginMemberVO;
	
	public UserIdentifyNotMatchException(LoginMemberVO loginMemberVO, String message) {
		super(message);
		this.loginMemberVO = loginMemberVO;
	}

	public LoginMemberVO getLoginMemberVO() {
		return loginMemberVO;
	}
}
