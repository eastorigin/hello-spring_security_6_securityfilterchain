package com.ktdsuniversity.edu.hello_spring.bbs.vo;

import jakarta.validation.constraints.NotBlank;

public class ModifyReplyVO {

	private int replyId;
	private int boardId;
	private String email;
	
	@NotBlank(message = "댓글 내용을 입력해주세요")
	private String content;
	
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
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

}
