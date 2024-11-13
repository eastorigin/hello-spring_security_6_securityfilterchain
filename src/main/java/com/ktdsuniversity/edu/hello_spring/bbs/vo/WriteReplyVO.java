package com.ktdsuniversity.edu.hello_spring.bbs.vo;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class WriteReplyVO {
	
	private int boardId;

	private String email;
	
	@NotBlank(message = "댓글 내용을 입력해주세요")
	@Length(min = 5, message = "댓글의 내용은 다섯글자 이상 입력해주세요")
	private String content;
	
	private int parentReplyId;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getParentReplyId() {
		return parentReplyId;
	}
	public void setParentReplyId(int parentReplyId) {
		this.parentReplyId = parentReplyId;
	}
}
