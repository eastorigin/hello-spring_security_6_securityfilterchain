package com.ktdsuniversity.edu.hello_spring.bbs.service;

import java.util.List;

import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteReplyVO;

public interface ReplyService {

	public List<ReplyVO> selectAllReplies(int boardId);
	
	public boolean insertNewReply(WriteReplyVO writeReplyVO);
	
	public boolean deleteOneReply(DeleteReplyVO deleteReplyVO);
	
	public boolean updateOneReply(ModifyReplyVO modifyReplyVO);
	
	public boolean recommendOneReply(int replyId, String email);
}
