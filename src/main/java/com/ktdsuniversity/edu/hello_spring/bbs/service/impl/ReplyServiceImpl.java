package com.ktdsuniversity.edu.hello_spring.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.hello_spring.bbs.dao.ReplyDao;
import com.ktdsuniversity.edu.hello_spring.bbs.service.ReplyService;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteReplyVO;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.AjaxException;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	private ReplyDao replyDao;

	@Override
	public List<ReplyVO> selectAllReplies(int boardId) {
		return this.replyDao.selectAllReplies(boardId);
	}
	
	@Transactional
	@Override
	public boolean insertNewReply(WriteReplyVO writeReplyVO) {
		int insertCount = replyDao.insertNewReply(writeReplyVO);
		return insertCount > 0;
	}
	
	@Transactional
	@Override
	public boolean deleteOneReply(DeleteReplyVO deleteReplyVO) {
		ReplyVO replyVO = replyDao.selectOneReply(deleteReplyVO.getReplyId());
		
		if(!deleteReplyVO.getEmail().equals(replyVO.getEmail())) {
			throw new AjaxException("잘못된 접근입니다");
		}
		return this.replyDao.deleteOneReply(deleteReplyVO) > 0;
	}
	
	@Transactional
	@Override
	public boolean updateOneReply(ModifyReplyVO modifyReplyVO) {
		ReplyVO originalReplyVO = replyDao.selectOneReply(modifyReplyVO.getReplyId());
		if(!modifyReplyVO.getEmail().equals(originalReplyVO.getEmail())) {
			throw new AjaxException("잘못된 접근입니다");
		}
		
		return this.replyDao.updateOneReply(modifyReplyVO) > 0;
	}
	
	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDao.selectOneReply(replyId);
		
		if(email.equals(replyVO.getEmail())) {
			throw new AjaxException("잘못된 접근입니다");
		}
		
		return this.replyDao.recommendOneReply(replyId) > 0;
	}
}
