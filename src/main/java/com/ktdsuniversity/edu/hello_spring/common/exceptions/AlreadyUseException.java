package com.ktdsuniversity.edu.hello_spring.common.exceptions;

import com.ktdsuniversity.edu.hello_spring.member.vo.MemberRegistVO;

public class AlreadyUseException extends RuntimeException{
	
	private static final long serialVersionUID = -6172744969616117842L;
	
	private MemberRegistVO memberRegistVO;
	
	public AlreadyUseException(MemberRegistVO memberRegistVO, String message) {
		super(message);
		this.memberRegistVO = memberRegistVO;
	}
	
	public MemberRegistVO getMemberRegistVO() {
		return memberRegistVO;
	}
}
